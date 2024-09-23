package br.com.fiap.postech.service_agendamento.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
public class SqsConfiguration {

    @Bean
    public SqsClient sqsClient() {
        return SqsClient.create();
    }

}
