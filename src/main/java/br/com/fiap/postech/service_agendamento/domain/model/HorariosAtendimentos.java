package br.com.fiap.postech.service_agendamento.domain.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamoDBTable(tableName = "horariosAtendimento")
public class HorariosAtendimentos {

    @DynamoDBHashKey
    private String documentoMedico;

    @DynamoDBAttribute
    private String horarioInicio;

    @DynamoDBAttribute
    private String horarioFinal;

    @DynamoDBAttribute
    private String email;

    // @DynamoDBTypeConvertedJson(targetType = Departamento.class)
    // Podemos usar isso para converter o retorno a uma classe ja mapeada
}
