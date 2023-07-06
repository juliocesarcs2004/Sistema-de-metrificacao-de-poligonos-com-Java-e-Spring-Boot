package br.com.metrificacao.poligonos.controller;

import br.com.metrificacao.poligonos.dto.DetalhamentoPoligonoDto;
import br.com.metrificacao.poligonos.dto.PontoPoligonoDto;
import br.com.metrificacao.poligonos.model.PontoPoligonoModel;
import br.com.metrificacao.poligonos.repository.DetalhamentoPoligonoRepository;
import br.com.metrificacao.poligonos.repository.PontoPoligonoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class PoligonoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<PontoPoligonoDto> pontoPoligonoDtoJson;

    @Autowired
    private JacksonTester<DetalhamentoPoligonoDto> detalhamentoPoligonoDtoJson;

    @MockBean
    private PontoPoligonoRepository pontoPoligonoRepository;

    @MockBean
    private DetalhamentoPoligonoRepository detalhamentoPoligonoRepository;

    @Test
    @DisplayName("Deveria devolver codigo 404 quando o endpoint é inválido")
    void carregar_arquivo_csv1() throws Exception{
        var response = mvc
                .perform(post("/carregar-csv"))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Deveria retornar o PontoPoligonoDto Correto")
    void obterPontoPorId() throws Exception {
        Long id = 1L;

        PontoPoligonoModel pontoPoligonoModel = PontoPoligonoModel.builder()
                .idPoligono(id)
                .coordenadaX(0.0)
                .coordenadaY(0.0)
                .nomePoligono("triangulo")
                .ordemDoPonto(1)
                .nomeDoArquivo("input_file.csv")
                .build();

        when(pontoPoligonoRepository.findById(id)).thenReturn(Optional.of(pontoPoligonoModel));

        var response = mvc.perform(get("/poligonos/" + id)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        PontoPoligonoDto pontoPoligonoDto = pontoPoligonoDtoJson.parseObject(response.getContentAsString());
        assertThat(pontoPoligonoDto.getId()).isEqualTo(id);
        assertThat(pontoPoligonoDto.getCoordenadaX()).isEqualTo(0.0);
        assertThat(pontoPoligonoDto.getCoordenadaY()).isEqualTo(0.0);
        assertThat(pontoPoligonoDto.getNomePoligono()).isEqualTo("triangulo");
        assertThat(pontoPoligonoDto.getOrdemDoPonto()).isEqualTo(1);
        assertThat(pontoPoligonoDto.getNomeDoArquivo()).isEqualTo("input_file.csv");
    }
}
