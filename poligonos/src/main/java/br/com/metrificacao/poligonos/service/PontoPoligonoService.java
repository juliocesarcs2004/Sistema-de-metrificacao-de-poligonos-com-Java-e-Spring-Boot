package br.com.metrificacao.poligonos.service;

import br.com.metrificacao.poligonos.dto.PontoPoligonoDto;
import br.com.metrificacao.poligonos.model.DetalhamentoPoligonoModel;
import br.com.metrificacao.poligonos.model.PontoPoligonoModel;
import br.com.metrificacao.poligonos.repository.DetalhamentoPoligonoRepository;
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
    DetalhamentoPoligonoRepository detalhamentoPoligonoRepository;

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

        processarMetricasPoligonos();
    }

    private void processarMetricasPoligonos(){

        List<PontoPoligonoModel> listaPoligonos = pontoPoligonoRepository.findAll();

        Map<String, List<PontoPoligonoModel>> poligonosMapeadosPorNome = listaPoligonos.stream()
                .collect(Collectors.groupingBy(PontoPoligonoModel::getNomePoligono));

        poligonosMapeadosPorNome.forEach((nomePoligono, PoligonoMapeadosPornome) -> {
            DetalhamentoPoligonoModel detalhamentoPoligonoModel = new DetalhamentoPoligonoModel();
            detalhamentoPoligonoModel.setNomePoligono(nomePoligono);
            detalhamentoPoligonoModel.setNumeroLados(PoligonoMapeadosPornome.size());
            detalhamentoPoligonoModel.setPerimetro(1D);
            detalhamentoPoligonoModel.setArea(1D);
            detalhamentoPoligonoModel.setNumeroDiagonais(getNumeroDiagonais(PoligonoMapeadosPornome.size()));
            detalhamentoPoligonoModel.setSomaAngulosInternos(getSomaAngulosInternos(PoligonoMapeadosPornome.size()));
            detalhamentoPoligonoModel.setNomeDoArquivo(PoligonoMapeadosPornome.get(0).getNomeDoArquivo());
            detalhamentoPoligonoRepository.save(detalhamentoPoligonoModel);
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

    public int getNumeroDiagonais(int numeroDePontos) {
        int numeroDiagonais = 0;
        if (numeroDePontos >= 4) {
            numeroDiagonais = (numeroDePontos * (numeroDePontos - 3)) / 2;
        }
        return numeroDiagonais;
    }

    public double getSomaAngulosInternos(int numeroDePontos) {
        return (numeroDePontos - 2) * 180;
    }

}