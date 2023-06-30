package br.com.metrificacao.poligonos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PontoPoligonoDto {

    private Long id;

    private Long coordenadaX;

    private Long coordenadaY;

    private String nomePoligono;

    private Integer ordemDoPonto;

}
