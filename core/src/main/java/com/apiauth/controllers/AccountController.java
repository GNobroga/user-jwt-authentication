package com.apiauth.controllers;

import java.util.Collection;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.apiauth.interfaces.IUserService;
import com.apiauth.models.RequestUserDTO;
import com.apiauth.models.ResponseUserDTO;
import com.apiauth.utils.ResponseHandler;
import com.apiauth.utils.Pagination;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "account", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class AccountController {

    private final IUserService userService;

    @GetMapping
    public ResponseEntity<ResponseHandler<Collection<ResponseUserDTO>>> get(@ModelAttribute Pagination pagination) {
        var result = userService.takeAll(PageRequest.of(pagination.getPage(), pagination.getSize()));
        var responseHandler = new ResponseHandler<>(result, result.size(), pagination.getSize(), pagination.getPage());
        return ResponseEntity.ok(responseHandler);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseHandler<ResponseUserDTO>> get(@PathVariable UUID id) {
        var responseHandler = new ResponseHandler<ResponseUserDTO>();
        responseHandler.setResult(userService.getById(id));
        return ResponseEntity.ok(responseHandler);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<ResponseHandler<ResponseUserDTO>> post(@RequestBody @Valid RequestUserDTO record, HttpServletRequest request) {
        var responseHandler = new ResponseHandler<ResponseUserDTO>();
        var result = userService.create(record);
        responseHandler.setResult(result);
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromRequestUri(request);
        builder.pathSegment("", "{id}");
        var uri = builder.buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(uri).body(responseHandler);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> put(@PathVariable UUID id, @RequestBody @Valid RequestUserDTO record) {
        userService.update(id, record);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
