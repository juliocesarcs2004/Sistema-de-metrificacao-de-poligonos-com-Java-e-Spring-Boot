package br.com.metrificacao.poligonos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "detalhamento_poligonos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalhamentoPoligonoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @Positive
    private Integer numeroDiagonais;

    @NotNull
    @Positive
    private Double somaAngulosInternos;

}
