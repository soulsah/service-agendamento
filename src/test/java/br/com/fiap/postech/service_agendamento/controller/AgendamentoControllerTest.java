package br.com.fiap.postech.service_agendamento.controller;

import br.com.fiap.postech.service_agendamento.adapters.controller.AgendamentoController;
import br.com.fiap.postech.service_agendamento.application.port.service.AgendamentoService;
import br.com.fiap.postech.service_agendamento.application.port.service.ConsultaService;
import br.com.fiap.postech.service_agendamento.domain.model.Consulta;
import br.com.fiap.postech.service_agendamento.domain.model.HorariosAtendimentos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AgendamentoControllerTest {

    @InjectMocks
    private AgendamentoController agendamentoController;

    @Mock
    private AgendamentoService agendamentoService;

    @Mock
    private ConsultaService consultaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetHorariosAgendamento_WhenAgendamentoExists() {
        String documentoMedico = "123456";
        HorariosAtendimentos horariosAtendimentos = new HorariosAtendimentos();
        when(agendamentoService.getHorariosAgendamentoByDocumento(documentoMedico)).thenReturn(horariosAtendimentos);

        ResponseEntity<HorariosAtendimentos> response = agendamentoController.getHorariosAgendamento(documentoMedico);

        assertEquals(ResponseEntity.ok(horariosAtendimentos), response);
    }

    @Test
    void testGetHorariosAgendamento_WhenAgendamentoDoesNotExist() {
        String documentoMedico = "123456";
        when(agendamentoService.getHorariosAgendamentoByDocumento(documentoMedico)).thenReturn(null);

        ResponseEntity<HorariosAtendimentos> response = agendamentoController.getHorariosAgendamento(documentoMedico);

        assertEquals(ResponseEntity.noContent().build(), response);
    }

    @Test
    void testSaveHorarioAtendimento() {
        HorariosAtendimentos horariosAtendimentos = new HorariosAtendimentos();
        when(agendamentoService.gravarHorariosAtendimento(horariosAtendimentos)).thenReturn(horariosAtendimentos);

        ResponseEntity<HorariosAtendimentos> response = agendamentoController.saveHorarioAtendimento(horariosAtendimentos);

        assertEquals(ResponseEntity.ok(horariosAtendimentos), response);
    }

    @Test
    void testPostAgendarConsulta() {
        Consulta consulta = new Consulta();
        when(consultaService.gravarConsulta(consulta)).thenReturn(ResponseEntity.ok("Consulta agendada com sucesso."));

        ResponseEntity<String> response = agendamentoController.postAgendarConsulta(consulta);

        assertEquals(ResponseEntity.ok("Consulta agendada com sucesso."), response);
    }

    @Test
    void testGetConsultaPorDocumentoMedico_WhenConsultasExist() {
        String documentoMedico = "123456";
        List<Consulta> consultas = new ArrayList<>();
        when(consultaService.getConsultaPorDocumentoMedico(documentoMedico)).thenReturn(consultas);

        ResponseEntity<List<Consulta>> response = agendamentoController.getConsultaPorDocumentoMedico(documentoMedico);

        assertEquals(ResponseEntity.ok(consultas), response);
    }

    @Test
    void testGetConsultaPorDocumentoMedico_WhenConsultasDoNotExist() {
        String documentoMedico = "123456";
        when(consultaService.getConsultaPorDocumentoMedico(documentoMedico)).thenReturn(null);

        ResponseEntity<List<Consulta>> response = agendamentoController.getConsultaPorDocumentoMedico(documentoMedico);

        assertEquals(ResponseEntity.noContent().build(), response);
    }

    @Test
    void testGetConsultaPorDocumentoPaciente_WhenConsultasExist() {
        String documentoPaciente = "789012";
        List<Consulta> consultas = new ArrayList<>();
        when(consultaService.getConsultaPorDocumentoPaciente(documentoPaciente)).thenReturn(consultas);

        ResponseEntity<List<Consulta>> response = agendamentoController.getConsultaPorDocumentoPaciente(documentoPaciente);

        assertEquals(ResponseEntity.ok(consultas), response);
    }

    @Test
    void testGetConsultaPorDocumentoPaciente_WhenConsultasDoNotExist() {
        String documentoPaciente = "789012";
        when(consultaService.getConsultaPorDocumentoPaciente(documentoPaciente)).thenReturn(null);

        ResponseEntity<List<Consulta>> response = agendamentoController.getConsultaPorDocumentoPaciente(documentoPaciente);

        assertEquals(ResponseEntity.noContent().build(), response);
    }
}
