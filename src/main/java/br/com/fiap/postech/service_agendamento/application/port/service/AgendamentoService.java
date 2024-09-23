package br.com.fiap.postech.service_agendamento.application.port.service;

import br.com.fiap.postech.service_agendamento.domain.model.HorariosAtendimentos;

public interface AgendamentoService {
    HorariosAtendimentos gravarHorariosAtendimento(HorariosAtendimentos horariosAtendimentos);
    HorariosAtendimentos getHorariosAgendamentoByDocumento(String documento);
}
