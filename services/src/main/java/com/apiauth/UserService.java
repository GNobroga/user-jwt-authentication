package com.apiauth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;


import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.apiauth.abstraction.IUserRepository;
import com.apiauth.concrete.User;
import com.apiauth.enums.ROLE;
import com.apiauth.exceptions.UserExistsException;
import com.apiauth.exceptions.UserNotFoundException;
import com.apiauth.interfaces.IUserService;
import com.apiauth.models.RequestUserDTO;
import com.apiauth.models.ResponseUserDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    private static ModelMapper modelMapper = new ModelMapper();

    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseUserDTO create(final RequestUserDTO record) {
        if (alreadyRegisteredUser(record)) {
            throw new UserExistsException();
        }
        var user = modelMapper.map(record, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.create(user);
        userRepository.addRole(user, ROLE.COMMON.getValue());
        return modelMapper.map(user, ResponseUserDTO.class);
    }

    private boolean alreadyRegisteredUser(RequestUserDTO record) {
        return userRepository.existsEmail(record.getEmail().toLowerCase()) || userRepository.existsUsername(record.getUsername().toLowerCase());
    }

    @Override
    public Collection<ResponseUserDTO> takeAll(final Pageable pageable) {
       return userRepository.takeAll(pageable).getContent()
        .stream()
        .map(source -> modelMapper.map(source, ResponseUserDTO.class))
        .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    @Override
    public ResponseUserDTO getById(final UUID id) {
        return userRepository.getById(id)   
            .map(source -> modelMapper.map(source, ResponseUserDTO.class))
            .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void update(final UUID id, final RequestUserDTO record) {
        var user = getById(id);
        if ((!user.getEmail().equalsIgnoreCase(record.getEmail()) || !user.getUsername().equalsIgnoreCase(record.getUsername())) && alreadyRegisteredUser(record)) {
            throw new UserExistsException();
        }
        var originalUser = userRepository.getById(id).get();
        modelMapper.map(record, originalUser);
        originalUser.setPassword(passwordEncoder.encode(originalUser.getPassword()));
        userRepository.update(originalUser);
    }

    @Override
    public void delete(final UUID id) {
        userRepository.delete(getById(id).getId());
    }
    
}
