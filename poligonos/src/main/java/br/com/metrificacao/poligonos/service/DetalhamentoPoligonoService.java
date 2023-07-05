package br.com.metrificacao.poligonos.service;

import br.com.metrificacao.poligonos.dto.DetalhamentoPoligonoDto;
import br.com.metrificacao.poligonos.model.DetalhamentoPoligonoModel;
import br.com.metrificacao.poligonos.repository.DetalhamentoPoligonoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetalhamentoPoligonoService {

    @Autowired
    DetalhamentoPoligonoRepository detalhamentoPoligonoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<DetalhamentoPoligonoDto> listarTodosPoligonosMetrificados() {
        List<DetalhamentoPoligonoModel> poligonosMetrificados = detalhamentoPoligonoRepository.findAll();
        return poligonosMetrificados.stream()
                .map(p -> modelMapper.map(p, DetalhamentoPoligonoDto.class))
                .collect(Collectors.toList());
    }
}
