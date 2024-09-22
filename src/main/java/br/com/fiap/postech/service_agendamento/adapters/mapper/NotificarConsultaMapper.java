package br.com.fiap.postech.service_agendamento.adapters.mapper;

import java.io.Serializable;

public record NotificarConsultaMapper(
        String doctorName,
        String doctorEmail,
        String patientName,
        String scheduleDate
) implements Serializable { }
