package br.com.metrificacao.poligonos.repository;

import br.com.metrificacao.poligonos.model.DetalhamentoPoligonoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalhamentoPoligonoRepository extends JpaRepository<DetalhamentoPoligonoModel, Long> {
}
