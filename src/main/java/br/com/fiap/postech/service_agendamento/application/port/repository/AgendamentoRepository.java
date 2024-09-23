package br.com.fiap.postech.service_agendamento.application.port.repository;

import br.com.fiap.postech.service_agendamento.domain.model.HorariosAtendimentos;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class AgendamentoRepository{

    private DynamoDBMapper dynamoDBMapper;

    public HorariosAtendimentos save(HorariosAtendimentos horariosAtendimentos) {
        dynamoDBMapper.save(horariosAtendimentos);
        return horariosAtendimentos;
    }

    public HorariosAtendimentos getHorariosAtendimentoByDocumento(String documentoMedico) {
        return dynamoDBMapper.load(HorariosAtendimentos.class, documentoMedico);
    }
}
