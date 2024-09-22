package br.com.fiap.postech.service_agendamento.adapters.controller;

import br.com.fiap.postech.service_agendamento.application.port.service.AgendamentoService;
import br.com.fiap.postech.service_agendamento.application.port.service.ConsultaService;
import br.com.fiap.postech.service_agendamento.domain.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agendamento")
public class AgendamentoController {

    @Autowired
    private AgendamentoService _agendamentoService;

    @Autowired
    private ConsultaService _consultaService;

    @GetMapping("/horarios")
    public ResponseEntity<HorariosAtendimentos> getHorariosAgendamento(@RequestParam String documentoMedico)
    {
        var agendamento = _agendamentoService.getHorariosAgendamentoByDocumento(documentoMedico);

        if (agendamento == null)
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok().body(agendamento);
    }

    @PostMapping("/horarios")
    public ResponseEntity<HorariosAtendimentos> saveHorarioAtendimento(@RequestBody HorariosAtendimentos horariosAtendimentos)
    {
        var response = _agendamentoService.gravarHorariosAtendimento(horariosAtendimentos);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/consulta")
    public ResponseEntity<Consulta> postAgendarConsulta(@RequestBody Consulta consulta)
    {
        var response = _consultaService.gravarConsulta(consulta);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/consulta/medico")
    public ResponseEntity<Consulta> getConsultaPorDocumentoMedico(@RequestParam String documentoMedico)
    {
        var response = _consultaService.getConsultaPorDocumentoMedico(documentoMedico);

        if (response == null)
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/consulta/paciente")
    public ResponseEntity<Consulta> getConsultaPorDocumentoPaciente(@RequestParam String documentoPaciente)
    {
        var response = _consultaService.getConsultaPorDocumentoPaciente(documentoPaciente);

        if (response == null)
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok().body(response);
    }

}
