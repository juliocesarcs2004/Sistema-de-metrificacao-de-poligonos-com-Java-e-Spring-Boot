package br.com.metrificacao.poligonos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArquivoDetalhadoPoligonosModel {

    private Long idArquivo;

    private String nomeDoArquivo;

    private List<DetalhamentoPoligonoModel> listaPoligonosDetalhados;

}
