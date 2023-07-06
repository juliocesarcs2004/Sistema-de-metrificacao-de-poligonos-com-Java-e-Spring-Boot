package br.com.metrificacao.poligonos.controller;

import br.com.metrificacao.poligonos.dto.DetalhamentoPoligonoDto;
import br.com.metrificacao.poligonos.dto.PontoPoligonoDto;
import br.com.metrificacao.poligonos.repository.DetalhamentoPoligonoRepository;
import br.com.metrificacao.poligonos.repository.PontoPoligonoRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    @DisplayName("Deveria devolver o ponto polígono correto")
    void testSelecionarPontoPoligono() throws Exception {
        Long id = 1L;


        // Simula a chamada do endpoint "/{id}"
        MvcResult result = mvc.perform(get("/poligonos" + id))
                .andExpect(status().isOk())
                .andReturn();

        // Obtém a resposta do endpoint
        String responseContent = result.getResponse().getContentAsString();

        // Converte a resposta para um objeto PontoPoligonoDto
        ObjectMapper objectMapper = new ObjectMapper();
        PontoPoligonoDto pontoPoligonoDto = objectMapper.readValue(responseContent, PontoPoligonoDto.class);

        // Cria o objeto PontoPoligonoDto esperado
        PontoPoligonoDto pontoEsperado = new PontoPoligonoDto(
                1L,
                0.0,
                0.0,
                "triangulo",
                1,
                "input_file2.csv"
        );

        // Verifica se o ponto polígono retornado é igual ao ponto esperado
        assertThat(pontoPoligonoDto).isEqualTo(pontoEsperado);
    }

}
