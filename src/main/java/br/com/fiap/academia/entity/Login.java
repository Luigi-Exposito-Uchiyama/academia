package br.com.fiap.academia.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "TB_LOGIN",
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_EMAIL_SENHA", columnNames = {"EMAIL", "SENHA"})
        }
)
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_LOGIN")
    @SequenceGenerator(name = "SQ_LOGIN", sequenceName = "SQ_LOGIN", allocationSize = 1)
    @Column(name = "ID_LOGIN")
    private Long id;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "SENHA")
    private String senha;

}
