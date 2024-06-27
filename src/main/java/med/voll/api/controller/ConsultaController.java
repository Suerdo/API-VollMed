package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key")
@CrossOrigin(origins = "http://localhost:4200")
public class ConsultaController {
    @Autowired
    private AgendaDeConsultas agenda;
    @Autowired
    ConsultaRepository repository;
    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
        var dto = agenda.agendar(dados);
        return ResponseEntity.ok(dto);
    }
    @GetMapping
    public ResponseEntity<Page<DadosListagemConsulta>> listar(@PageableDefault(size = 10, sort = {"id"}) Pageable paginacao) {
        Page<Consulta> consultas = repository.findAll(paginacao);
        Page<DadosListagemConsulta> page = consultas.map(DadosListagemConsulta::new);
        return ResponseEntity.ok(page);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var consulta = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoConsulta(consulta));
    }
}
