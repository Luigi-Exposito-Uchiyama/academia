package br.com.fiap.academia.service;

import br.com.fiap.academia.dto.request.LoginRequest;
import br.com.fiap.academia.dto.response.LoginResponse;
import br.com.fiap.academia.entity.Login;
import br.com.fiap.academia.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LoginService implements ServiceDTO<Login, LoginRequest, LoginResponse> {

    @Autowired
    private LoginRepository repo;

    @Override
    public Login toEntity(LoginRequest r) {

        return Login.builder()
                .email( r.email() )
                .senha(r.senha())
                .build();

    }

    @Override
    public LoginResponse toResponse(Login e) {

        return LoginResponse.builder()
                .id( e.getId() )
                .email( e.getEmail() )
                .build();
    }

    @Override
    public List<Login> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Login> findAll(Example<Login> example) {
        return repo.findAll( example );
    }

    @Override
    public Login findById(Long id) {
        return repo.findById( id ).orElse( null );
    }

    @Override
    public Login save(Login e) {
        return repo.save( e );
    }
}