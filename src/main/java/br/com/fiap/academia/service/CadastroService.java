package br.com.fiap.academia.service;


import br.com.fiap.academia.dto.request.CadastroRequest;
import br.com.fiap.academia.dto.response.CadastroResponse;
import br.com.fiap.academia.entity.Cadastro;
import br.com.fiap.academia.entity.Login;
import br.com.fiap.academia.repository.CadastroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class CadastroService implements ServiceDTO<Cadastro, CadastroRequest, CadastroResponse> {

    @Autowired
    private CadastroRepository repo;

    @Autowired
    private LoginService loginService;

    @Override
    public Cadastro toEntity(CadastroRequest r) {

        if (Objects.isNull( r )) return null;

        var login = loginService.toEntity(r.login());

        return Cadastro.builder()
                .login( login )
                .atleta( r.atleta() )
                .nascimento( r.nascimento() )
                .build();
    }

    @Override
    public CadastroResponse toResponse(Cadastro e) {

        if (Objects.isNull( e )) return null;

        var login = loginService.toResponse(e.getLogin());

        return CadastroResponse.builder()
                .id( e.getId() )
                .atleta( e.getAtleta() )
                .nascimento(e.getNascimento())
                .login( login )
                .build();
    }

    @Override
    public List<Cadastro> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Cadastro> findAll(Example<Cadastro> example) {
        return repo.findAll( example );
    }

    @Override
    public Cadastro findById(Long id) {
        return repo.findById( id ).orElse( null );
    }

    @Override
    public Cadastro save(Cadastro e) {
        return repo.save( e );
    }

    public Cadastro findByLogin(Login login) {
        return repo.findByLogin(login);
    }
}