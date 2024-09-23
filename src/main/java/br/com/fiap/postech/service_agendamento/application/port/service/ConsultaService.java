package br.com.fiap.postech.service_agendamento.application.port.service;

import br.com.fiap.postech.service_agendamento.domain.model.Consulta;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ConsultaService {
    ResponseEntity<String> gravarConsulta(Consulta consulta);
    List<Consulta>  getConsultaPorDocumentoPaciente(String documentoPaciente);
    List<Consulta>  getConsultaPorDocumentoMedico(String documentoMedico);
}
