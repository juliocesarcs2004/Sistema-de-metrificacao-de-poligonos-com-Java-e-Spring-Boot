package br.com.metrificacao.poligonos.service;

import br.com.metrificacao.poligonos.dto.PontoPoligonoDto;
import br.com.metrificacao.poligonos.model.PontoPoligonoModel;
import br.com.metrificacao.poligonos.repository.PontoPoligonoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PontoPoligonoService {

    @Autowired
    private PontoPoligonoRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public Page<PontoPoligonoDto> listarTodosPoligonos(Pageable paginacao) {
        return repository
                .findAll(paginacao)
                .map(p -> modelMapper.map(p, PontoPoligonoDto.class));
    }

    public PontoPoligonoDto obterPontoPorId(Long id) {
        PontoPoligonoModel pontoPoligonoModel = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return modelMapper.map(pontoPoligonoModel, PontoPoligonoDto.class);
    }

    public PontoPoligonoDto salvarPontoPoligono(PontoPoligonoDto pontoPoligonoDto) {
        PontoPoligonoModel pontoPoligonoModel = modelMapper.map(pontoPoligonoDto, PontoPoligonoModel.class);
        repository.save(pontoPoligonoModel);

        return modelMapper.map(pontoPoligonoModel, PontoPoligonoDto.class);
    }

    public PontoPoligonoDto atualizarPontoPoligono(Long id, PontoPoligonoDto pontoPoligonoDto) {
        PontoPoligonoModel pontoPoligonoModel = modelMapper.map(pontoPoligonoDto, PontoPoligonoModel.class);
        pontoPoligonoModel.setId(id);
        pontoPoligonoModel = repository.save(pontoPoligonoModel);
        return modelMapper.map(pontoPoligonoModel, PontoPoligonoDto.class);
    }

    public void excluirPoligono(Long id) {
        repository.deleteById(id);
    }

}
