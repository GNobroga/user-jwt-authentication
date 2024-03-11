package com.apiauth.security;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.apiauth.abstraction.IUserRepository;
import com.apiauth.concrete.User;
import com.apiauth.exceptions.UserNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserRepository userRepository;

    private static final ModelMapper modelMapper = new ModelMapper();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return userRepository
            .findByUsername(username)
            .map(UserDetailsServiceImpl::mapToApplicationUser)
            .orElseThrow(UserNotFoundException::new);
    }

    private static ApplicationUser mapToApplicationUser(User user) {
        return modelMapper.map(user, ApplicationUser.class);
    }
    
}
