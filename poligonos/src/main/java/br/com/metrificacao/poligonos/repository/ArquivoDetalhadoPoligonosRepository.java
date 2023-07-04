package br.com.metrificacao.poligonos.repository;

import br.com.metrificacao.poligonos.model.ArquivoDetalhadoPoligonosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArquivoDetalhadoPoligonosRepository extends JpaRepository<ArquivoDetalhadoPoligonosModel, Long> {

    @Query(value = "SELECT arquivos_poligonos.id as id_arquivo, " +
            "arquivos_poligonos.nome_do_arquivo, " +
            "detalhamento_poligonos.id as id_poligono, " +
            "detalhamento_poligonos.nome_poligono, " +
            "detalhamento_poligonos.numero_lados, " +
            "detalhamento_poligonos.perimetro, " +
            "detalhamento_poligonos.area, " +
            "detalhamento_poligonos.numero_diagonais, " +
            "detalhamento_poligonos.soma_angulos_internos " +
            "FROM poligonos.arquivos_poligonos " +
            "INNER JOIN poligonos.detalhamento_poligonos " +
            "ON arquivos_poligonos.nome_do_arquivo = detalhamento_poligonos.nome_do_arquivo", nativeQuery = true)
    List<ArquivoDetalhadoPoligonosModel> getArquivosDetalhadosPoligonos();

}
