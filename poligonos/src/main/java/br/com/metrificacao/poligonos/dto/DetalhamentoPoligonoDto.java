package br.com.metrificacao.poligonos.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DetalhamentoPoligonoDto {

    private Long id;

    private String nomePoligono;

    private Integer numeroLados;

    private Double perimetro;

    private Double area;

    private Integer numeroDiagonais;

    private Double somaAngulosInternos;

}
