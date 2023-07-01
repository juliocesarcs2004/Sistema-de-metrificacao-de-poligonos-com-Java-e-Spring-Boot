package br.com.metrificacao.poligonos.service;

import br.com.metrificacao.poligonos.dto.PontoPoligonoDto;
import br.com.metrificacao.poligonos.model.PontoPoligonoModel;
import br.com.metrificacao.poligonos.repository.PontoPoligonoRepository;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.opencsv.CSVReader;
import com.opencsv.CSVParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

    public void processCsvFile(MultipartFile file) throws IOException, CsvException {
        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
        CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(file.getInputStream()))
                .withCSVParser(csvParser)
                .build();

        List<String[]> csvLines = csvReader.readAll();
        List<PontoPoligonoDto> pontoPoligonoList = new ArrayList<>();

        for (int i = 1; i < csvLines.size(); i++) {
            String[] line = csvLines.get(i);

            PontoPoligonoDto pontoPoligonoDto = createPontoPoligonoDto(line);
            PontoPoligonoModel pontoPoligonoModel = modelMapper.map(pontoPoligonoDto, PontoPoligonoModel.class);
            repository.save(pontoPoligonoModel);
            pontoPoligonoList.add(pontoPoligonoDto);
        }

        csvReader.close();
    }

    private PontoPoligonoDto createPontoPoligonoDto(String[] line) {
        Double coordenadaX = Double.parseDouble(line[0].trim());
        Double coordenadaY = Double.parseDouble(line[1].trim());
        String nomePoligono = line[2].trim();
        Integer ordemDoPonto = Integer.parseInt(line[3].trim());

        PontoPoligonoDto pontoPoligonoDto = new PontoPoligonoDto();
        pontoPoligonoDto.setCoordenadaX(coordenadaX);
        pontoPoligonoDto.setCoordenadaY(coordenadaY);
        pontoPoligonoDto.setNomePoligono(nomePoligono);
        pontoPoligonoDto.setOrdemDoPonto(ordemDoPonto);

        return pontoPoligonoDto;
    }

}