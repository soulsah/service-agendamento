package br.com.fiap.postech.service_agendamento.application.port.repository;

import br.com.fiap.postech.service_agendamento.domain.model.Consulta;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class ConsultaRepository {

    private DynamoDBMapper dynamoDBMapper;

    public Consulta save(Consulta consulta) {
        dynamoDBMapper.save(consulta);
        return consulta;
    }

    // To Do: Listar todas as consultas que o paciente tem
    public Consulta getConsultaByDocumentoPaciente(String documentoPaciente) {
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":documentoPaciente", new AttributeValue().withS(documentoPaciente));

        DynamoDBQueryExpression<Consulta> queryExpression = new DynamoDBQueryExpression<Consulta>()
                .withIndexName("documentoPacienteIndex")
                .withKeyConditionExpression("documentoPaciente = :documentoPaciente")
                .withExpressionAttributeValues(expressionAttributeValues)
                .withConsistentRead(false);

        List<Consulta> consultas = dynamoDBMapper.query(Consulta.class, queryExpression);

        if (!consultas.isEmpty())
            return consultas.get(0);
        else
            return null;
    }

    // To Do: Listar todas as consultas que o medico tem
    public Consulta getConsultaByDocumentoMedico(String documentoMedico) {
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":documentoMedico", new AttributeValue().withS(documentoMedico));

        DynamoDBQueryExpression<Consulta> queryExpression = new DynamoDBQueryExpression<Consulta>()
                .withIndexName("documentoMedicoIndex")
                .withKeyConditionExpression("documentoMedico = :documentoMedico")
                .withExpressionAttributeValues(expressionAttributeValues)
                .withConsistentRead(false);

        List<Consulta> consultas = dynamoDBMapper.query(Consulta.class, queryExpression);

        if (!consultas.isEmpty())
            return consultas.get(0);
        else
            return null;
    }

}
