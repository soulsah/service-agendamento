package br.com.fiap.postech.service_agendamento.domain.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamoDBTable(tableName = "consultas")
public class Consulta {

    @JsonIgnore
    @DynamoDBHashKey
    private String idConsulta;

    @DynamoDBAttribute
    private String nomePaciente;

    @DynamoDBAttribute
    private String documentoPaciente;

    @DynamoDBAttribute
    private String nomeMedico;

    @DynamoDBAttribute
    private String documentoMedico;

    @DynamoDBAttribute
    private String data;

    @DynamoDBAttribute
    private String horarioInicio;

    @DynamoDBAttribute
    private String horarioFinal;

}
