package br.com.metrificacao.poligonos.dto;

import br.com.metrificacao.poligonos.model.DetalhamentoPoligonoSemNomeDeArquivoModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArquivoDetalhadoPoligonosSemNomeDeArquivoDto {

    private Long idArquivo;

    private String nomeDoArquivo;

    private List<DetalhamentoPoligonoSemNomeDeArquivoModel> listaPoligonosDetalhadosSemNomeDeArquivo;
}
