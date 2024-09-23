package br.com.fiap.postech.service_agendamento.port.service;

import br.com.fiap.postech.service_agendamento.adapters.mapper.NotificarConsultaMapper;
import br.com.fiap.postech.service_agendamento.application.port.repository.AgendamentoRepository;
import br.com.fiap.postech.service_agendamento.application.port.repository.ConsultaRepository;
import br.com.fiap.postech.service_agendamento.application.port.service.SqsService;
import br.com.fiap.postech.service_agendamento.application.port.service.impl.ConsultaServiceIMpl;
import br.com.fiap.postech.service_agendamento.domain.model.Consulta;
import br.com.fiap.postech.service_agendamento.domain.model.HorariosAtendimentos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConsultaServiceImplTest {

    @Mock
    private ConsultaRepository consultaRepository;

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @Mock
    private SqsService sqsService;

    @InjectMocks
    private ConsultaServiceIMpl consultaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGravarConsulta_ComSucesso() throws Exception {
        // Arrange
        Consulta consulta = new Consulta();
        consulta.setDocumentoMedico("123456");
        consulta.setHorarioInicio("10:00");
        consulta.setData("25/09/2024");
        consulta.setNomeMedico("Dr. Teste");
        consulta.setNomePaciente("Paciente Teste");

        HorariosAtendimentos horariosAtendimentos = new HorariosAtendimentos();
        horariosAtendimentos.setHorarioInicio("09:00");
        horariosAtendimentos.setHorarioFinal("17:00");

        when(agendamentoRepository.getHorariosAtendimentoByDocumento(consulta.getDocumentoMedico()))
                .thenReturn(horariosAtendimentos);
        when(consultaRepository.getConsultaByDocumentoMedico(consulta.getDocumentoMedico()))
                .thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<String> response = consultaService.gravarConsulta(consulta);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Consulta agendada com sucesso.", response.getBody());
        verify(consultaRepository, times(1)).save(consulta);
        verify(sqsService, times(1)).enviarMensagem(anyString(), anyString());
    }

    @Test
    void testGravarConsulta_ComConflitoDeAgenda() {
        // Arrange
        Consulta consulta = new Consulta();
        consulta.setDocumentoMedico("123456");
        consulta.setHorarioInicio("10:00");
        consulta.setData("25/09/2024");

        HorariosAtendimentos horariosAtendimentos = new HorariosAtendimentos();
        horariosAtendimentos.setHorarioInicio("09:00");
        horariosAtendimentos.setHorarioFinal("17:00");

        when(agendamentoRepository.getHorariosAtendimentoByDocumento(consulta.getDocumentoMedico()))
                .thenReturn(horariosAtendimentos);

        Consulta consultaExistente = new Consulta();
        consultaExistente.setData("25/09/2024");
        consultaExistente.setHorarioInicio("10:30");

        when(consultaRepository.getConsultaByDocumentoMedico(consulta.getDocumentoMedico()))
                .thenReturn(List.of(consultaExistente));

        // Act
        ResponseEntity<String> response = consultaService.gravarConsulta(consulta);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("O Dr. ja possui uma consulta agendada nesse horario.", response.getBody());
        verify(consultaRepository, times(0)).save(consulta);
        verify(sqsService, times(0)).enviarMensagem(anyString(), anyString());
    }

    @Test
    void testGravarConsulta_ComJanelaInvalida() {
        // Arrange
        Consulta consulta = new Consulta();
        consulta.setDocumentoMedico("123456");
        consulta.setHorarioInicio("08:00"); // Horário fora da janela de atendimento
        consulta.setData("25/09/2024");

        HorariosAtendimentos horariosAtendimentos = new HorariosAtendimentos();
        horariosAtendimentos.setHorarioInicio("09:00");
        horariosAtendimentos.setHorarioFinal("17:00");

        when(agendamentoRepository.getHorariosAtendimentoByDocumento(consulta.getDocumentoMedico()))
                .thenReturn(horariosAtendimentos);

        // Act
        ResponseEntity<String> response = consultaService.gravarConsulta(consulta);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Janela de atendimento do medico nao disponivel para esse horario.", response.getBody());
        verify(consultaRepository, times(0)).save(consulta);
        verify(sqsService, times(0)).enviarMensagem(anyString(), anyString());
    }

    @Test
    void testGetConsultaPorDocumentoPaciente() {
        // Arrange
        String documentoPaciente = "654321";
        Consulta consulta = new Consulta();
        when(consultaRepository.getConsultasByDocumentoPaciente(documentoPaciente))
                .thenReturn(List.of(consulta));

        // Act
        List<Consulta> result = consultaService.getConsultaPorDocumentoPaciente(documentoPaciente);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(consultaRepository, times(1)).getConsultasByDocumentoPaciente(documentoPaciente);
    }

    @Test
    void testGetConsultaPorDocumentoMedico() {
        // Arrange
        String documentoMedico = "123456";
        Consulta consulta = new Consulta();
        when(consultaRepository.getConsultaByDocumentoMedico(documentoMedico))
                .thenReturn(List.of(consulta));

        // Act
        List<Consulta> result = consultaService.getConsultaPorDocumentoMedico(documentoMedico);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(consultaRepository, times(1)).getConsultaByDocumentoMedico(documentoMedico);
    }
}
