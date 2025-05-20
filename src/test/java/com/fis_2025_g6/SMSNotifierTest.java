package com.fis_2025_g6;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.fis_2025_g6.notification.SMSNotifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SMSNotifierTest {

    private SMSNotifier smsNotifier;
    private final String testFromNumber = "+1234567890";
    private final String testRecipient = "+573001234567";
    private final String testMessage = "Hola desde el test";

    @BeforeEach
    void setUp() {
        smsNotifier = new SMSNotifier();
        // Inyectar valor privado de `from` usando reflexión
        ReflectionTestUtils.setField(smsNotifier, "from", testFromNumber);
    }

    @Test
    void testSend_CreatesMessageSuccessfully() {
        try (MockedStatic<Message> mockedMessage = mockStatic(Message.class)) {
            MessageCreator mockCreator = mock(MessageCreator.class);
            Message mockMessage = mock(Message.class);

            when(mockCreator.create()).thenReturn(mockMessage);

            // Configurar el mock para Message.creator
            mockedMessage.when(() -> Message.creator(
                    new PhoneNumber(testRecipient),
                    new PhoneNumber(testFromNumber),
                    testMessage
            )).thenReturn(mockCreator);

            // Ejecutar el método
            smsNotifier.send(testMessage, testRecipient);

            // Verificar que se haya llamado a Message.creator con los parámetros correctos
            mockedMessage.verify(() -> Message.creator(
                    new PhoneNumber(testRecipient),
                    new PhoneNumber(testFromNumber),
                    testMessage
            ));

            // Verificar que se haya llamado a create()
            verify(mockCreator).create();
        }
    }
}
