    package br.com.fiap.academia.resource;

    import br.com.fiap.academia.dto.request.CadastroRequest;
    import br.com.fiap.academia.dto.request.TreinoRequest;
    import br.com.fiap.academia.dto.response.CadastroResponse;
    import br.com.fiap.academia.dto.response.TreinoResponse;
    import br.com.fiap.academia.entity.Cadastro;
    import br.com.fiap.academia.entity.Login;
    import br.com.fiap.academia.entity.Treino;
    import br.com.fiap.academia.repository.LoginRepository;
    import br.com.fiap.academia.service.CadastroService;
    import br.com.fiap.academia.service.TreinoService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Example;
    import org.springframework.data.domain.ExampleMatcher;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.transaction.annotation.Transactional;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

    import jakarta.validation.Valid;
    import java.time.LocalDate;
    import java.util.ArrayList;
    import java.util.List;

    @RestController
    @RequestMapping(value = "/cadastro")
    public class CadastroResource {

        @Autowired
        private CadastroService service;

        @Autowired
        private LoginRepository loginRepository;

        @Autowired
        private TreinoService treinoService;

        @GetMapping
        public ResponseEntity<List<CadastroResponse>> findAll(
                @RequestParam(name = "atleta", required = false) String atleta,
                @RequestParam(name = "nascimento", required = false) LocalDate nascimento,
                @RequestParam(name = "login.id", required = false) Long idLogin,
                @RequestParam(name = "login.email", required = false) String loginEmail
        ) {
            Login login = null;
            if (idLogin != null && idLogin > 0) {
                login = loginRepository.findById(idLogin).orElse(null);
            } else if (loginEmail != null && !loginEmail.isEmpty()) {
                login = loginRepository.findByEmail(loginEmail);
            }

            Cadastro cadastro = Cadastro.builder()
                    .login(login)
                    .atleta(atleta)
                    .nascimento(nascimento)
                    .build();

            ExampleMatcher matcher = ExampleMatcher.matchingAll()
                    .withIgnoreNullValues()
                    .withIgnoreCase();

            Example<Cadastro> example = Example.of(cadastro, matcher);

            List<Cadastro> encontrados = service.findAll(example);

            if (encontrados.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            List<CadastroResponse> resposta = encontrados.stream()
                    .map(service::toResponse)
                    .toList();

            return ResponseEntity.ok(resposta);
        }

        @GetMapping("/id/{id}")
        public ResponseEntity<CadastroResponse> findById(@PathVariable Long id) {
            Cadastro encontrado = service.findById(id);
            if (encontrado == null) {
                return ResponseEntity.notFound().build();
            }
            CadastroResponse resposta = service.toResponse(encontrado);
            return ResponseEntity.ok(resposta);
        }

        @GetMapping("/email/{email}")
        public ResponseEntity<CadastroResponse> findByEmail(@PathVariable String email) {
            Login login = loginRepository.findByEmail(email);
            if (login == null) {
                return ResponseEntity.notFound().build();
            }

            Cadastro cadastro = service.findByLogin(login);
            if (cadastro == null) {
                return ResponseEntity.notFound().build();
            }

            CadastroResponse resposta = service.toResponse(cadastro);
            return ResponseEntity.ok(resposta);
        }


        @Transactional
        @PostMapping
        public ResponseEntity<CadastroResponse> save(@RequestBody @Valid CadastroRequest r) {
            Cadastro entity = service.toEntity(r);
            Cadastro saved = service.save(entity);
            CadastroResponse resposta = service.toResponse(saved);

            var uri = ServletUriComponentsBuilder
                    .fromCurrentRequestUri()
                    .path("/{id}")
                    .buildAndExpand(saved.getId())
                    .toUri();

            return ResponseEntity.created(uri).body(resposta);
        }

            @PostMapping("/login")
            public ResponseEntity<?> login(@RequestParam String email, @RequestParam String senha) {
                Login login = loginRepository.findByEmail(email);
                if (login == null || login.getSenha() == null || !login.getSenha().equals(senha)) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
                }
                return ResponseEntity.ok().body("Login bem-sucedido para o usuário: " + login.getEmail());
            }

        @GetMapping("/{cadastroId}/treinos")
        public ResponseEntity<List<TreinoResponse>> getTreinosByCadastroId(@PathVariable Long cadastroId) {
            Cadastro cadastro = service.findById(cadastroId);
            if (cadastro == null) {
                return ResponseEntity.notFound().build();
            }

            List<Treino> treinos = cadastro.getTreinos();
            List<TreinoResponse> resposta = treinos.stream()
                    .map(treinoService::toResponse)
                    .toList();

            return ResponseEntity.ok(resposta);
        }

        @PostMapping("/{cadastroId}/treinos")
        public ResponseEntity<List<TreinoResponse>> addTreinosToCadastro(
                @PathVariable Long cadastroId,
                @RequestBody @Valid List<TreinoRequest> treinosRequest) {

            Cadastro cadastro = service.findById(cadastroId);
            if (cadastro == null) {
                return ResponseEntity.notFound().build();
            }

            List<Treino> treinos = new ArrayList<>();
            for (TreinoRequest treinoRequest : treinosRequest) {
                Treino treino = treinoService.toEntity(treinoRequest);
                treinos.add(treino);
            }

            cadastro.getTreinos().addAll(treinos);
            service.save(cadastro);

            List<TreinoResponse> resposta = treinos.stream()
                    .map(treinoService::toResponse)
                    .toList();

            return ResponseEntity.ok(resposta);
        }
    }
