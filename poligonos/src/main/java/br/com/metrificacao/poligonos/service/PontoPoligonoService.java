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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.opencsv.CSVReader;
import com.opencsv.CSVParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
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
        pontoPoligonoModel.setId(id);
        pontoPoligonoModel = pontoPoligonoRepository.save(pontoPoligonoModel);
        return modelMapper.map(pontoPoligonoModel, PontoPoligonoDto.class);
    }

    public void excluirPoligono(Long id) {
        pontoPoligonoRepository.deleteById(id);
    }

    public void salvarArquivoCsv(MultipartFile file) throws IOException, CsvException {
        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
        CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(file.getInputStream()))
                .withCSVParser(csvParser)
                .build();
        String nomeDoArquivo = file.getOriginalFilename();

        List<String[]> csvLines = csvReader.readAll();

        csvLines.stream()
                .skip(1)
                .map(line -> criarPontoPoligonoDto(line, nomeDoArquivo))
                .map(pontoPoligonoDto -> modelMapper.map(pontoPoligonoDto, PontoPoligonoModel.class))
                .forEach(pontoPoligonoRepository::save);

        csvReader.close();


    }

    private void processarMetricasPoligonos(){

        List<PontoPoligonoModel> listaPoligonos = pontoPoligonoRepository.findAll();

        Map<String, List<PontoPoligonoModel>> poligonosSeparadosPorNome = listaPoligonos.stream()
                .collect(Collectors.groupingBy(PontoPoligonoModel::getNomePoligono));

        poligonosSeparadosPorNome.forEach((nomePoligono, PoligonoSeparadoPornome) -> {

        });

    }




    private PontoPoligonoDto criarPontoPoligonoDto(String[] line, String nomeDoArquivo) {
        return PontoPoligonoDto.builder()
                .coordenadaX(Double.parseDouble(line[0].trim()))
                .coordenadaY(Double.parseDouble(line[1].trim()))
                .nomePoligono(line[2].trim())
                .ordemDoPonto(Integer.parseInt(line[3].trim()))
                .nomeDoArquivo(nomeDoArquivo)
                .build();
    }

}