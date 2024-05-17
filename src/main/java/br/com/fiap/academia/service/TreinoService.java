package br.com.fiap.academia.service;

import br.com.fiap.academia.dto.request.TreinoRequest;
import br.com.fiap.academia.dto.response.TreinoResponse;
import br.com.fiap.academia.entity.Treino;
import br.com.fiap.academia.repository.TreinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreinoService implements ServiceDTO<Treino, TreinoRequest, TreinoResponse> {

    @Autowired
    private TreinoRepository repo;

    @Override
    public Treino toEntity(TreinoRequest r) {
        return Treino.builder()
                .exercicio(r.exercicio())
                .series(r.series())
                .repeticoes(r.repeticoes())
                .carga(r.carga())
                .tempoDescanso(r.tempoDescanso())
                .build();
    }

    @Override
    public TreinoResponse toResponse(Treino e) {
        return TreinoResponse.builder()
                .id(e.getId())
                .exercicio(e.getExercicio())
                .series(e.getSeries())
                .repeticoes(e.getRepeticoes())
                .carga(e.getCarga())
                .tempoDescanso(e.getTempoDescanso())
                .build();
    }

    @Override
    public List<Treino> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Treino> findAll(Example<Treino> example) {
        return repo.findAll(example);
    }

    @Override
    public Treino findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Treino save(Treino e) {
        return repo.save(e);
    }
}
