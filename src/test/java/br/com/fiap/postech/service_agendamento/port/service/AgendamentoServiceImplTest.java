package br.com.fiap.postech.service_agendamento.port.service;

import br.com.fiap.postech.service_agendamento.application.port.repository.AgendamentoRepository;
import br.com.fiap.postech.service_agendamento.application.port.service.impl.AgendamentoServiceImpl;
import br.com.fiap.postech.service_agendamento.domain.model.HorariosAtendimentos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AgendamentoServiceImplTest {

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @InjectMocks
    private AgendamentoServiceImpl agendamentoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGravarHorariosAtendimento() {
        // Arrange
        HorariosAtendimentos horariosAtendimentos = new HorariosAtendimentos();
        when(agendamentoRepository.save(horariosAtendimentos)).thenReturn(horariosAtendimentos);

        // Act
        HorariosAtendimentos result = agendamentoService.gravarHorariosAtendimento(horariosAtendimentos);

        // Assert
        assertEquals(horariosAtendimentos, result);
        verify(agendamentoRepository, times(1)).save(horariosAtendimentos);
    }

    @Test
    void testGetHorariosAgendamentoByDocumento_WhenExists() {
        // Arrange
        String documentoMedico = "123456";
        HorariosAtendimentos horariosAtendimentos = new HorariosAtendimentos();
        when(agendamentoRepository.getHorariosAtendimentoByDocumento(documentoMedico)).thenReturn(horariosAtendimentos);

        // Act
        HorariosAtendimentos result = agendamentoService.getHorariosAgendamentoByDocumento(documentoMedico);

        // Assert
        assertEquals(horariosAtendimentos, result);
        verify(agendamentoRepository, times(1)).getHorariosAtendimentoByDocumento(documentoMedico);
    }

    @Test
    void testGetHorariosAgendamentoByDocumento_WhenNotExists() {
        // Arrange
        String documentoMedico = "123456";
        when(agendamentoRepository.getHorariosAtendimentoByDocumento(documentoMedico)).thenReturn(null);

        // Act
        HorariosAtendimentos result = agendamentoService.getHorariosAgendamentoByDocumento(documentoMedico);

        // Assert
        assertEquals(null, result);
        verify(agendamentoRepository, times(1)).getHorariosAtendimentoByDocumento(documentoMedico);
    }
}
