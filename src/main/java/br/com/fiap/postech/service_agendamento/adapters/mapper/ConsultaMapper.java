package br.com.fiap.postech.service_agendamento.adapters.mapper;

import java.io.Serializable;

public record ConsultaMapper (
        String idConsulta,
        String documentoPaciente,
        String documentoMedico,
        String data,
        String horarioInicio,
        String horarioFinal
) implements Serializable { }
