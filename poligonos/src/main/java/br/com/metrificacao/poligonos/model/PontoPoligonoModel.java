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
@Table(name = "poligonos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PontoPoligonoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long coordenadaX;

    @NotNull
    private Long coordenadaY;

    @NotNull
    @Size(max=20)
    private String nomePoligono;

    @NotNull
    @Positive
    private Integer ordemDoPonto;
}
