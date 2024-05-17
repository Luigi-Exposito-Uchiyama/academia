package br.com.fiap.academia.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "TB_CADASTRO")
public class Cadastro {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CADASTRO")
    @SequenceGenerator(name = "SQ_CADASTRO", sequenceName = "SQ_CADASTRO", allocationSize = 1)
    @Column(name = "ID_CADASTRO")
    private Long id;

    @Column(name = "ATLETA")
    private String atleta;

    @Column(name = "NASCIMENTO")
    private LocalDate nascimento;

    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.PERSIST
            })
    @JoinColumn(
            name = "LOGIN",
            referencedColumnName = "ID_LOGIN",
            foreignKey = @ForeignKey(
                    name = "FK_CADASTRO_LOGIN"
            )
    )
    private Login login;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "TB_CADASTRO_TREINO",
            joinColumns = @JoinColumn(name = "CADASTRO_ID"),
            inverseJoinColumns = @JoinColumn(name = "TREINO_ID"),
            foreignKey = @ForeignKey(name = "FK_CADASTRO_TREINO_CADASTRO"),
            inverseForeignKey = @ForeignKey(name = "FK_CADASTRO_TREINO_TREINO")
    )
    private List<Treino> treinos;
}
