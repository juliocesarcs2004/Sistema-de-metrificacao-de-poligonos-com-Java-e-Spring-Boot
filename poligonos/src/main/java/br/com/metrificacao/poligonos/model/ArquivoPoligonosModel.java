package br.com.metrificacao.poligonos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "arquivos_poligonos")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArquivoPoligonosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArquivo;

    @NotNull
    private String nomeDoArquivo;

}
