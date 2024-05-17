package br.com.fiap.academia.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "TB_TREINO")
public class Treino {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_TREINO")
    @SequenceGenerator(name = "SQ_TREINO", sequenceName = "SQ_TREINO", allocationSize = 1)
    @Column(name = "ID_TREINO")
    private Long id;

    @Column(name = "EXERCICIO")
    private String exercicio;

    @Column(name = "SERIES")
    private int series;

    @Column(name = "REPETICOES")
    private int repeticoes;

    @Column(name = "CARGA")
    private double carga;

    @Column(name = "TEMPO_DESCANSO")
    private int tempoDescanso;

}
