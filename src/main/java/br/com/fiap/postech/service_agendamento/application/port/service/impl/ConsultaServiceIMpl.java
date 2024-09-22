package br.com.fiap.postech.service_agendamento.application.port.service.impl;

import br.com.fiap.postech.service_agendamento.adapters.mapper.NotificarConsultaMapper;
import br.com.fiap.postech.service_agendamento.application.port.repository.AgendamentoRepository;
import br.com.fiap.postech.service_agendamento.application.port.repository.ConsultaRepository;
import br.com.fiap.postech.service_agendamento.application.port.service.ConsultaService;
import br.com.fiap.postech.service_agendamento.application.port.service.SqsService;
import br.com.fiap.postech.service_agendamento.domain.model.Consulta;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ConsultaServiceIMpl implements ConsultaService {

    private ConsultaRepository _consultaRepository;
    private AgendamentoRepository _agendamentoRepository;
    private SqsService _sqsService;

    @Override
    public ResponseEntity<String> gravarConsulta(Consulta consulta) {

        var temConflitoJanela = this.isJanelaAtendimentoInvalida(consulta.getDocumentoMedico(), consulta.getHorarioInicio());
        var consultasMedico = _consultaRepository.getConsultaByDocumentoMedico(consulta.getDocumentoMedico());
        var consultasFiltradas = this.filtrarConsultas(consultasMedico);
        var temConflitoAgenda = this.verificarConflitoAgenda(consultasFiltradas, consulta.getData(), consulta.getHorarioInicio());



        if (temConflitoJanela)
            return ResponseEntity.badRequest().body("Janela de atendimento do medico nao disponivel para esse horario.");

        if (temConflitoAgenda)
            return ResponseEntity.badRequest().body("O Dr. ja possui uma consulta agendada nesse horario.");

        consulta.setIdConsulta(UUID.randomUUID().toString());
        _consultaRepository.save(consulta);
        var medico = _agendamentoRepository.getHorariosAtendimentoByDocumento(consulta.getDocumentoMedico());
        var dataAgendamento = consulta.getData() + " " + consulta.getHorarioInicio();
        var notificarConsulta = new NotificarConsultaMapper(consulta.getNomeMedico(), medico.getEmail(), consulta.getNomePaciente(), dataAgendamento);

        String jsonNotificarConsulta = "";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            jsonNotificarConsulta = objectMapper.writeValueAsString(notificarConsulta);
        } catch (Exception e) {
            System.out.println("Erro ao converter NotificarConsultaMapper para JSON: " + e);
        }

        _sqsService.enviarMensagem("https://sqs.us-east-1.amazonaws.com/{ACCOUNT_ID}/NOTIFICATION_QUEUE", jsonNotificarConsulta);
        return ResponseEntity.ok().body("Consulta agendada com sucesso.");

    }

    @Override
    public List<Consulta> getConsultaPorDocumentoPaciente(String documentoPaciente) {
        return _consultaRepository.getConsultasByDocumentoPaciente(documentoPaciente);
    }

    @Override
    public List<Consulta> getConsultaPorDocumentoMedico(String documentoMedico) {
        return _consultaRepository.getConsultaByDocumentoMedico(documentoMedico);
    }

    private boolean isJanelaAtendimentoInvalida(String documentoMedico, String horarioInicioConsulta)
    {
        var horariosAtendimento = _agendamentoRepository.getHorariosAtendimentoByDocumento(documentoMedico);
        var horarioInicioAtendimentoLocalTime = this.ConverterStringParaHora(horariosAtendimento.getHorarioInicio());
        var horarioFinalAtendimentoLocalTime = this.ConverterStringParaHora(horariosAtendimento.getHorarioFinal());
        var horarioInicioConsultaLocalTime = this.ConverterStringParaHora(horarioInicioConsulta);

        if (horarioInicioAtendimentoLocalTime.isBefore(horarioInicioConsultaLocalTime) && horarioFinalAtendimentoLocalTime.isAfter(horarioInicioConsultaLocalTime))
            return false;
        else
            return true;
    }

    private LocalTime ConverterStringParaHora(String horario)
    {
        return LocalTime.parse(horario);
    }

    private List<Consulta> filtrarConsultas(List<Consulta> consultas) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataAtual = LocalDate.now();

        return consultas.stream()
                .filter(consulta -> {
                    LocalDate dataConsulta = LocalDate.parse(consulta.getData(), formatter);
                    return dataConsulta.isAfter(dataAtual) || dataConsulta.isEqual(dataAtual);
                })
                .collect(Collectors.toList());
    }

    private boolean verificarConflitoAgenda(List<Consulta> consultasAgendadas, String dataNovaConsulta, String horarioNovaConsulta)
    {
        var dataNovaConsultaParse = LocalDate.parse(dataNovaConsulta, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        var horarioNovaConsultaParse = LocalTime.parse(horarioNovaConsulta);

        for (var consulta : consultasAgendadas) {
            var dataConsultaAgendada = consulta.getData();
            var dataConsultaAgendadaParse = LocalDate.parse(dataConsultaAgendada, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            if (dataNovaConsultaParse.equals(dataConsultaAgendadaParse))
            {
                var horarioConsultaAgendada = consulta.getHorarioInicio();
                var horarioConsultaAgendadaParse = LocalTime.parse(horarioConsultaAgendada);
                if (Duration.between(horarioNovaConsultaParse, horarioConsultaAgendadaParse).abs().toHours() < 1)
                    return true;
            }
        }
        return false;
    }

}
