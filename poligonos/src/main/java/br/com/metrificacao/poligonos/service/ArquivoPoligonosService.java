package br.com.metrificacao.poligonos.service;

import br.com.metrificacao.poligonos.dto.ArquivoPoligonosDto;
import br.com.metrificacao.poligonos.model.ArquivoPoligonosModel;
import br.com.metrificacao.poligonos.repository.ArquivoPoligonosRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArquivoPoligonosService {

    @Autowired
    ArquivoPoligonosRepository arquivoPoligonosRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ArquivoPoligonosDto> listarArquivosPoligonos() {
        List<ArquivoPoligonosModel> arquivosPoligonos = arquivoPoligonosRepository.findAll();
        return arquivosPoligonos.stream()
                .map(p -> modelMapper.map(p, ArquivoPoligonosDto.class))
                .collect(Collectors.toList());
    }
}
