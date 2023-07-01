package br.com.metrificacao.poligonos.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PontoPoligonoDto {

    private Long id;

    private Double coordenadaX;

    private Double coordenadaY;

    private String nomePoligono;

    private Integer ordemDoPonto;

    private String nomeDoArquivo;

}
