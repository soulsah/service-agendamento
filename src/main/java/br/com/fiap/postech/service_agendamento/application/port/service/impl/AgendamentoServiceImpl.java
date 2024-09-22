package br.com.fiap.postech.service_agendamento.application.port.service.impl;

import br.com.fiap.postech.service_agendamento.application.port.repository.AgendamentoRepository;
import br.com.fiap.postech.service_agendamento.application.port.service.AgendamentoService;
import br.com.fiap.postech.service_agendamento.domain.model.HorariosAtendimentos;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AgendamentoServiceImpl implements AgendamentoService {

    private AgendamentoRepository _agendamentoRepository;

    public HorariosAtendimentos gravarHorariosAtendimento(HorariosAtendimentos horariosAtendimentos) {
        return _agendamentoRepository.save(horariosAtendimentos);
    }

    @Override
    public HorariosAtendimentos getHorariosAgendamentoByDocumento(String documentoMedico) {
        return _agendamentoRepository.getHorariosAtendimentoByDocumento(documentoMedico);
    }

}
