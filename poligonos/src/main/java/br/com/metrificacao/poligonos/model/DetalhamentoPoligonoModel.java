package br.com.metrificacao.poligonos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "detalhamento_poligonos")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetalhamentoPoligonoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalhamento;

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

    @NotNull
    private String nomeDoArquivo;

}
