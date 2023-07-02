package br.com.metrificacao.poligonos.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalhamentoPoligonoDto {

    private Long id;

    private String nomePoligono;

    private Integer numeroLados;

    private Double perimetro;

    private Double area;

    private Integer numeroDiagonais;

    private Double somaAngulosInternos;

    private String nomeDoArquivo;

}
