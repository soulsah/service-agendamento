package br.com.fiap.postech.service_agendamento.application.port.service;

import br.com.fiap.postech.service_agendamento.domain.model.Consulta;

public interface ConsultaService {
    Consulta gravarConsulta(Consulta consulta);
    Consulta getConsultaPorDocumentoPaciente(String documentoPaciente);
    Consulta getConsultaPorDocumentoMedico(String documentoMedico);
}
