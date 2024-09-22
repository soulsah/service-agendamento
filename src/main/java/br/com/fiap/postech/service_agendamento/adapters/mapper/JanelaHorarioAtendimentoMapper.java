package br.com.fiap.postech.service_agendamento.adapters.mapper;

import java.io.Serializable;

public record JanelaHorarioAtendimentoMapper(
        String documentoMedico,
        String horarioInicio,
        String horarioTermino,
        String email
) implements Serializable { }