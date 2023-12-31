package br.com.metrificacao.poligonos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Data
@Table(name = "poligonos")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PontoPoligonoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPoligono;

    @NotNull
    private Double coordenadaX;

    @NotNull
    private Double coordenadaY;

    @NotNull
    @Size(max=20)
    private String nomePoligono;

    @NotNull
    @Positive
    private Integer ordemDoPonto;

    @NotNull
    private String nomeDoArquivo;
}
