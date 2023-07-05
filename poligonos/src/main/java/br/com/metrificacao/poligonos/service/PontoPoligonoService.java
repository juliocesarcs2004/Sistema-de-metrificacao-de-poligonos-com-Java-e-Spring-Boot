package br.com.metrificacao.poligonos.service;

import br.com.metrificacao.poligonos.dto.PontoPoligonoDto;
import br.com.metrificacao.poligonos.model.PontoPoligonoModel;
import br.com.metrificacao.poligonos.repository.PontoPoligonoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PontoPoligonoService {

    @Autowired
    private PontoPoligonoRepository pontoPoligonoRepository;

    @Autowired
    private ModelMapper modelMapper;


    public List<PontoPoligonoDto> listarTodosPoligonos() {
        List<PontoPoligonoModel> poligonos = pontoPoligonoRepository.findAll();
        return poligonos.stream()
                .map(p -> modelMapper.map(p, PontoPoligonoDto.class))
                .collect(Collectors.toList());
    }

    public PontoPoligonoDto obterPontoPorId(Long id) {
        PontoPoligonoModel pontoPoligonoModel = pontoPoligonoRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(pontoPoligonoModel, PontoPoligonoDto.class);
    }

    public PontoPoligonoDto salvarPontoPoligono(PontoPoligonoDto pontoPoligonoDto) {
        PontoPoligonoModel pontoPoligonoModel = modelMapper.map(pontoPoligonoDto, PontoPoligonoModel.class);
        pontoPoligonoRepository.save(pontoPoligonoModel);
        return modelMapper.map(pontoPoligonoModel, PontoPoligonoDto.class);
    }

    public PontoPoligonoDto atualizarPontoPoligono(Long id, PontoPoligonoDto pontoPoligonoDto) {
        PontoPoligonoModel pontoPoligonoModel = modelMapper.map(pontoPoligonoDto, PontoPoligonoModel.class);
        pontoPoligonoModel.setIdPoligono(id);
        pontoPoligonoModel = pontoPoligonoRepository.save(pontoPoligonoModel);
        return modelMapper.map(pontoPoligonoModel, PontoPoligonoDto.class);
    }

    public void excluirPoligonoPorId(Long id) {
        pontoPoligonoRepository.deleteById(id);
    }
}
