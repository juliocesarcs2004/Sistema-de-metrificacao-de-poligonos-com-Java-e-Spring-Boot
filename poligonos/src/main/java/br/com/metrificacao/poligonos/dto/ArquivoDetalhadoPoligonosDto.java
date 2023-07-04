package br.com.metrificacao.poligonos.dto;

import br.com.metrificacao.poligonos.model.DetalhamentoPoligonoModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArquivoDetalhadoPoligonosDto {

    private Long idArquivo;

    private String nomeDoArquivo;

    private List<DetalhamentoPoligonoModel> listaPoligonosDetalhados;

}
