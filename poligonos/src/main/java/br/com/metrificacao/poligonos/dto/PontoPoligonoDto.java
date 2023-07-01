package br.com.metrificacao.poligonos.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PontoPoligonoDto {

    private Long id;

    private Double coordenadaX;

    private Double coordenadaY;

    private String nomePoligono;

    private Integer ordemDoPonto;

    private String nomeDoArquivo;

}
