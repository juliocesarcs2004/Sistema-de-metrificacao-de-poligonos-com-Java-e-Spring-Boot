package br.com.metrificacao.poligonos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArquivoDetalhadoPoligonosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_arquivo;

    @NotNull
    private String nomeDoArquivo;

    @NotNull
    private Long id_poligono;

    @NotNull
    @Size(max=20)
    private String nomePoligono;

    @NotNull
    @Positive
    private Integer numeroLados;

    @NotNull
    private Double perimetro;

    @NotNull
    @Positive
    private Double area;

    @NotNull
    private Integer numeroDiagonais;

    @NotNull
    @Positive
    private Double somaAngulosInternos;

}
