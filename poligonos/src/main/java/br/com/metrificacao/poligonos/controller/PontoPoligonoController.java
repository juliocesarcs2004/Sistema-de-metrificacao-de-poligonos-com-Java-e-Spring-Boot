package br.com.metrificacao.poligonos.controller;

import br.com.metrificacao.poligonos.dto.PontoPoligonoDto;
import br.com.metrificacao.poligonos.service.PontoPoligonoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/poligonos")
public class PontoPoligonoController {

    @Autowired
    private PontoPoligonoService service;

    @GetMapping
    public Page<PontoPoligonoDto> listarPoligonos(@PageableDefault(size = 10) Pageable paginacao) {
        return service.listarTodosPoligonos(paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PontoPoligonoDto> selecionarPontoPoligono(@PathVariable @NotNull Long id) {
        PontoPoligonoDto PontoPoligonoDto = service.obterPontoPorId(id);
        return ResponseEntity.ok(PontoPoligonoDto);
    }

    @PostMapping
    public ResponseEntity<PontoPoligonoDto> salvarPontoPoligono(@RequestBody @Valid PontoPoligonoDto pontoPoligonoRequest, UriComponentsBuilder uriBuilder) {
        PontoPoligonoDto poligonoSalvo = service.salvarPontoPoligono(pontoPoligonoRequest);
        URI endereco = uriBuilder.path("/poligonos/{id}").buildAndExpand(poligonoSalvo.getId()).toUri();

        return ResponseEntity.created(endereco).body(poligonoSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PontoPoligonoDto> atualizarPontoPoligono(@PathVariable @NotNull Long id, @RequestBody @Valid PontoPoligonoDto pontoPoligonoRequest) {
        PontoPoligonoDto poligonoAtualizado = service.atualizarPontoPoligono(id, pontoPoligonoRequest);
        return ResponseEntity.ok(poligonoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PontoPoligonoDto> deletarPontoPoligono(@PathVariable @NotNull Long id) {
        service.excluirPoligono(id);
        return ResponseEntity.noContent().build();

    }
}