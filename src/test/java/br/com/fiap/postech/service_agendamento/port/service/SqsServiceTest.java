package br.com.fiap.postech.service_agendamento.port.service;

import br.com.fiap.postech.service_agendamento.application.port.service.SqsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import static org.mockito.Mockito.*;

class SqsServiceTest {

    @Mock
    private SqsClient sqsClient;

    @InjectMocks
    private SqsService sqsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEnviarMensagem() {
        // Arrange
        String queueUrl = "https://sqs.us-east-1.amazonaws.com/123456789012/MyQueue";
        String messageBody = "This is a test message";

        // Act
        sqsService.enviarMensagem(queueUrl, messageBody);

        // Assert
        SendMessageRequest expectedRequest = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(messageBody)
                .build();

        verify(sqsClient, times(1)).sendMessage(expectedRequest);
    }

    @Test
    void testEnviarMensagem_ComVariosChamados() {
        // Arrange
        String queueUrl = "https://sqs.us-east-1.amazonaws.com/123456789012/MyQueue";
        String messageBody1 = "Message 1";
        String messageBody2 = "Message 2";

        // Act
        sqsService.enviarMensagem(queueUrl, messageBody1);
        sqsService.enviarMensagem(queueUrl, messageBody2);

        // Assert
        SendMessageRequest expectedRequest1 = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(messageBody1)
                .build();

        SendMessageRequest expectedRequest2 = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(messageBody2)
                .build();

        verify(sqsClient, times(1)).sendMessage(expectedRequest1);
        verify(sqsClient, times(1)).sendMessage(expectedRequest2);
    }
}
