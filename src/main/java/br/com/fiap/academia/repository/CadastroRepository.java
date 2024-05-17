package br.com.fiap.academia.repository;

import br.com.fiap.academia.entity.Cadastro;
import br.com.fiap.academia.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CadastroRepository extends JpaRepository<Cadastro, Long> {
    Cadastro findByLogin(Login login);
}
