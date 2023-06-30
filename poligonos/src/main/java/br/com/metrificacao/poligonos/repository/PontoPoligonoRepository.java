package br.com.metrificacao.poligonos.repository;

import br.com.metrificacao.poligonos.model.PontoPoligonoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PontoPoligonoRepository extends JpaRepository<PontoPoligonoModel, Long> {
}
