package br.com.metrificacao.poligonos.repository;

import br.com.metrificacao.poligonos.model.ArquivoDetalhadoPoligonosModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArquivoDetalhadoPoligonosRepository extends JpaRepository<ArquivoDetalhadoPoligonosModel, Long> {
}
