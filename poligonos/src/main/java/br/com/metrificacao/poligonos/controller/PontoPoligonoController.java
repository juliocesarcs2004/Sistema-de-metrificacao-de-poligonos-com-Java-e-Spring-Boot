package br.com.metrificacao.poligonos.controller;

import br.com.metrificacao.poligonos.dto.PontoPoligonoDto;
import br.com.metrificacao.poligonos.service.PontoPoligonoService;
import com.opencsv.exceptions.CsvException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/poligonos")
public class PontoPoligonoController {

    @Autowired
    private PontoPoligonoService service;

    @GetMapping
    public List<PontoPoligonoDto> listarPoligonos() {
        return service.listarTodosPoligonos();
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

    @PostMapping("/carregar-csv")
    public ResponseEntity<String> carregarArquivoCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Arquivo CSV não enviado");
        }

        try {
            service.salvarArquivoCsv(file);
            return ResponseEntity.ok("Arquivo CSV armazenado com sucesso no banco de dados");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao processar o arquivo CSV");
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
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