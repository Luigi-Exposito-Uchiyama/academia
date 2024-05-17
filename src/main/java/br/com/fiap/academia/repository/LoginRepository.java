package br.com.fiap.academia.repository;

import br.com.fiap.academia.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login, Long> {
    Login findByEmail(String email);
}
