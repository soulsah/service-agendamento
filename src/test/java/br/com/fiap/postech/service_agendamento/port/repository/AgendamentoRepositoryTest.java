package br.com.fiap.postech.service_agendamento.port.repository;

import br.com.fiap.postech.service_agendamento.application.port.repository.AgendamentoRepository;
import br.com.fiap.postech.service_agendamento.domain.model.HorariosAtendimentos;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AgendamentoRepositoryTest {

    @Mock
    private DynamoDBMapper dynamoDBMapper;

    @InjectMocks
    private AgendamentoRepository agendamentoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        // Arrange
        HorariosAtendimentos horariosAtendimentos = new HorariosAtendimentos();
        horariosAtendimentos.setDocumentoMedico("123456");
        horariosAtendimentos.setHorarioInicio("09:00");
        horariosAtendimentos.setHorarioFinal("17:00");

        // Act
        HorariosAtendimentos result = agendamentoRepository.save(horariosAtendimentos);

        // Assert
        assertEquals(horariosAtendimentos, result);
        verify(dynamoDBMapper, times(1)).save(horariosAtendimentos);
    }

    @Test
    void testGetHorariosAtendimentoByDocumento() {
        // Arrange
        String documentoMedico = "123456";
        HorariosAtendimentos horariosAtendimentos = new HorariosAtendimentos();
        horariosAtendimentos.setDocumentoMedico(documentoMedico);
        horariosAtendimentos.setHorarioInicio("09:00");
        horariosAtendimentos.setHorarioFinal("17:00");

        when(dynamoDBMapper.load(HorariosAtendimentos.class, documentoMedico))
                .thenReturn(horariosAtendimentos);

        // Act
        HorariosAtendimentos result = agendamentoRepository.getHorariosAtendimentoByDocumento(documentoMedico);

        // Assert
        assertNotNull(result);
        assertEquals(documentoMedico, result.getDocumentoMedico());
        assertEquals("09:00", result.getHorarioInicio());
        assertEquals("17:00", result.getHorarioFinal());
        verify(dynamoDBMapper, times(1)).load(HorariosAtendimentos.class, documentoMedico);
    }
}
