package br.com.metrificacao.poligonos.service;

import br.com.metrificacao.poligonos.dto.ArquivoDetalhadoPoligonosSemNomeDeArquivoDto;
import br.com.metrificacao.poligonos.dto.PontoPoligonoDto;
import br.com.metrificacao.poligonos.model.ArquivoPoligonosModel;
import br.com.metrificacao.poligonos.model.DetalhamentoPoligonoModel;
import br.com.metrificacao.poligonos.model.DetalhamentoPoligonoSemNomeDeArquivoModel;
import br.com.metrificacao.poligonos.model.PontoPoligonoModel;
import br.com.metrificacao.poligonos.repository.ArquivoPoligonosRepository;
import br.com.metrificacao.poligonos.repository.DetalhamentoPoligonoRepository;
import br.com.metrificacao.poligonos.repository.PontoPoligonoRepository;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.opencsv.CSVReader;
import com.opencsv.CSVParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PoligonoService {

    @Autowired
    private PontoPoligonoRepository pontoPoligonoRepository;

    @Autowired
    DetalhamentoPoligonoRepository detalhamentoPoligonoRepository;

    @Autowired
    ArquivoPoligonosRepository arquivoPoligonosRepository;

    @Autowired
    private ModelMapper modelMapper;


    public List<ArquivoDetalhadoPoligonosSemNomeDeArquivoDto> listarArquivosPoligonosDetalhados() {
        List<ArquivoPoligonosModel> arquivos = arquivoPoligonosRepository.findAll();
        List<DetalhamentoPoligonoModel> detalhamentoPoligonos = detalhamentoPoligonoRepository.findAll();

        List<ArquivoDetalhadoPoligonosSemNomeDeArquivoDto> listaArquivosDetalhadosSemNomeDeArquivo = new ArrayList<>();

        for (ArquivoPoligonosModel arquivo : arquivos) {
            List<DetalhamentoPoligonoModel> detalhamentosPorArquivo = detalhamentoPoligonos.stream()
                    .filter(d -> d.getNomeDoArquivo().equals(arquivo.getNomeDoArquivo()))
                    .collect(Collectors.toList());

            List<DetalhamentoPoligonoSemNomeDeArquivoModel> listaDePoligonosSemNomeDeArquivo =
                    retirarNomeDeArquivoDaLista(detalhamentosPorArquivo);


            ArquivoDetalhadoPoligonosSemNomeDeArquivoDto arquivoDetalhado = ArquivoDetalhadoPoligonosSemNomeDeArquivoDto.builder()
                    .idArquivo(arquivo.getIdArquivo())
                    .nomeDoArquivo(arquivo.getNomeDoArquivo())
                    .listaPoligonosDetalhadosSemNomeDeArquivo(listaDePoligonosSemNomeDeArquivo)
                    .build();

            listaArquivosDetalhadosSemNomeDeArquivo.add(arquivoDetalhado);
        }

        return listaArquivosDetalhadosSemNomeDeArquivo;
    }

    private List<DetalhamentoPoligonoSemNomeDeArquivoModel> retirarNomeDeArquivoDaLista(List<DetalhamentoPoligonoModel> detalhamentosPorArquivo) {
        return detalhamentosPorArquivo
                .stream()
                .map(detalhamentoPoligonoModel -> DetalhamentoPoligonoSemNomeDeArquivoModel.builder()
                        .idDetalhamento(detalhamentoPoligonoModel.getIdDetalhamento())
                        .nomePoligono(detalhamentoPoligonoModel.getNomePoligono())
                        .numeroLados(detalhamentoPoligonoModel.getNumeroLados())
                        .perimetro(detalhamentoPoligonoModel.getPerimetro())
                        .area(detalhamentoPoligonoModel.getArea())
                        .numeroDiagonais(detalhamentoPoligonoModel.getNumeroDiagonais())
                        .somaAngulosInternos(detalhamentoPoligonoModel.getSomaAngulosInternos())
                        .build())
                .collect(Collectors.toList());
    }

    public void salvarArquivoCsv(MultipartFile file) throws IOException, CsvException {
        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
        CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(file.getInputStream()))
                .withCSVParser(csvParser)
                .build();
        String nomeDoArquivo = file.getOriginalFilename();
        salvarNomeDoArquivo(nomeDoArquivo);

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
            detalhamentoPoligonoModel.setPerimetro(getPerimetro(PoligonoMapeadosPornome));
            detalhamentoPoligonoModel.setArea(getArea(PoligonoMapeadosPornome));
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

    public double getPerimetro(List<PontoPoligonoModel> poligonosMapeadosPorNome) {
        double perimetro = 0;
        for (int i = 0; i < poligonosMapeadosPorNome.size(); i++) {
            PontoPoligonoModel pontoAtual = poligonosMapeadosPorNome.get(i);
            PontoPoligonoModel pontoProximo = poligonosMapeadosPorNome.get((i + 1) % poligonosMapeadosPorNome.size());
            perimetro += distanciaPontoAtualPontoProximo(pontoAtual,pontoProximo);
        }
        return perimetro;
    }

    public double distanciaPontoAtualPontoProximo(PontoPoligonoModel pontoAtual, PontoPoligonoModel pontoProximo) {
        double dx = pontoProximo.getCoordenadaX() - pontoAtual.getCoordenadaX();
        double dy = pontoProximo.getCoordenadaY() - pontoAtual.getCoordenadaY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double getArea(List<PontoPoligonoModel> poligonosMapeadosPorNome) {
        double area = 0;
        for (int i = 0; i < poligonosMapeadosPorNome.size(); i++) {
            PontoPoligonoModel pontoAtual = poligonosMapeadosPorNome.get(i);
            PontoPoligonoModel pontoProximo = poligonosMapeadosPorNome.get((i + 1) % poligonosMapeadosPorNome.size());
            area += pontoAtual.getCoordenadaX() * pontoProximo.getCoordenadaY();
            area -= pontoAtual.getCoordenadaY() * pontoProximo.getCoordenadaX();
        }
        area /= 2.0;
        return Math.abs(area);
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

    private void salvarNomeDoArquivo(String nomeDoArquivo) {
        ArquivoPoligonosModel arquivoPoligonosModel = new ArquivoPoligonosModel();
        arquivoPoligonosModel.setNomeDoArquivo(nomeDoArquivo);
        arquivoPoligonosRepository.save(arquivoPoligonosModel);
    }
}