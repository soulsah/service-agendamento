package br.com.fiap.postech.service_agendamento.application.port.service.impl;

import br.com.fiap.postech.service_agendamento.application.port.repository.ConsultaRepository;
import br.com.fiap.postech.service_agendamento.application.port.service.ConsultaService;
import br.com.fiap.postech.service_agendamento.application.port.service.SqsService;
import br.com.fiap.postech.service_agendamento.domain.model.Consulta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ConsultaServiceIMpl implements ConsultaService {

    private ConsultaRepository _consultaRepository;
    private SqsService _sqsService;

    @Override
    public Consulta gravarConsulta(Consulta consulta) {
        var uuid = UUID.randomUUID().toString();
        consulta.setIdConsulta(uuid);
        System.out.println("## UUID gerado: " + uuid);

        // To Do: Ajustar objeto de envio para a fila SQS
//        _sqsService.enviarMensagem("NOTIFICATION_QUEUE", consulta.toString());

        return _consultaRepository.save(consulta);

    }

    @Override
    public Consulta getConsultaPorDocumentoPaciente(String documentoPaciente) {
        return _consultaRepository.getConsultaByDocumentoPaciente(documentoPaciente);
    }

    @Override
    public Consulta getConsultaPorDocumentoMedico(String documentoMedico) {
        return _consultaRepository.getConsultaByDocumentoMedico(documentoMedico);
    }

}
