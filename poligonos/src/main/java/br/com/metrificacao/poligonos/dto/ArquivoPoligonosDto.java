package br.com.metrificacao.poligonos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArquivoPoligonosDto {

    private Long id;

    private String nomeDoArquivo;
}
