package br.com.metrificacao.poligonos.model;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetalhamentoPoligonoSemNomeDeArquivoModel {

    private Long idDetalhamento;

    private String nomePoligono;

    private Integer numeroLados;

    private Double perimetro;

    private Double area;

    private Integer numeroDiagonais;

    private Double somaAngulosInternos;

}
