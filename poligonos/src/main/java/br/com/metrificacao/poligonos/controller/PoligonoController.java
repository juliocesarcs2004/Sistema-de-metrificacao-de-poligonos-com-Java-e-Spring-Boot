package br.com.metrificacao.poligonos.controller;

import br.com.metrificacao.poligonos.dto.*;
import br.com.metrificacao.poligonos.service.ArquivoPoligonosService;
import br.com.metrificacao.poligonos.service.DetalhamentoPoligonoService;
import br.com.metrificacao.poligonos.service.PoligonoService;
import br.com.metrificacao.poligonos.service.PontoPoligonoService;
import com.opencsv.exceptions.CsvException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/poligonos")
@CrossOrigin(origins = "*")
public class PoligonoController {

    @Autowired
    private PoligonoService poligonoService;

    @Autowired
    private PontoPoligonoService pontoPoligonoservice;

    @Autowired
    private DetalhamentoPoligonoService detalhamentoPoligonoService;

    @Autowired
    private ArquivoPoligonosService arquivoPoligonosService;


    @GetMapping
    public List<PontoPoligonoDto> listarPoligonos() {
        return pontoPoligonoservice.listarTodosPoligonos();
    }

    @GetMapping("/metricas")
    public List<DetalhamentoPoligonoDto> listarMetricasPoligonos() {
        return detalhamentoPoligonoService.listarTodosPoligonosMetrificados();
    }

    @GetMapping("/arquivos")
    public List<ArquivoPoligonosDto> listarArquivosPoligonos() {
        return arquivoPoligonosService.listarArquivosPoligonos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PontoPoligonoDto> selecionarPontoPoligono(@PathVariable @NotNull Long id) {
        PontoPoligonoDto PontoPoligonoDto = pontoPoligonoservice.obterPontoPorId(id);
        return ResponseEntity.ok(PontoPoligonoDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PontoPoligonoDto> atualizarPontoPoligono(@PathVariable @NotNull Long id, @RequestBody @Valid PontoPoligonoDto pontoPoligonoRequest) {
        PontoPoligonoDto poligonoAtualizado = pontoPoligonoservice.atualizarPontoPoligono(id, pontoPoligonoRequest);
        return ResponseEntity.ok(poligonoAtualizado);
    }

    @GetMapping("/arquivos-detalhados")
    public List<ArquivoDetalhadoPoligonosSemNomeDeArquivoDto> listarArquivosDetalhados() {
        return poligonoService.listarArquivosPoligonosDetalhados();
    }

    @PostMapping("/carregar-csv")
    public ResponseEntity<String> carregarArquivoCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Arquivo CSV não enviado");
        }

        try {
            poligonoService.salvarArquivoCsv(file);
            return ResponseEntity.ok("Arquivo CSV armazenado com sucesso no banco de dados");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao processar o arquivo CSV");
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PontoPoligonoDto> deletarPontoPoligono(@PathVariable @NotNull Long id) {
        pontoPoligonoservice.excluirPoligonoPorId(id);
        return ResponseEntity.noContent().build();
    }
}