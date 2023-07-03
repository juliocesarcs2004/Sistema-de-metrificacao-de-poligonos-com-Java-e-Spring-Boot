package br.com.metrificacao.poligonos.repository;

import br.com.metrificacao.poligonos.model.ArquivoPoligonosModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArquivoPoligonosRepository extends JpaRepository<ArquivoPoligonosModel, Long> {
}
