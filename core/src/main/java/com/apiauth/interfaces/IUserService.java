package com.apiauth.interfaces;

import java.util.Collection;
import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.apiauth.models.RequestUserDTO;
import com.apiauth.models.ResponseUserDTO;

public interface IUserService {
    
    ResponseUserDTO create(RequestUserDTO record);

    Collection<ResponseUserDTO> takeAll(Pageable pageable);

    ResponseUserDTO getById(UUID id);

    void update(UUID id, RequestUserDTO record);

    void delete(UUID id);
}
