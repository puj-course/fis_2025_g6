package com.fis_2025_g6;

import com.fis_2025_g6.service.SMSService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SMSServiceTest {

    @Mock
    private MessageCreator messageCreator;

    @InjectMocks
    private SMSService smsService;

    private final String testFromNumber = "+1234567890";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(smsService, "from", testFromNumber);
    }

    @Test
    void sendSMS_ShouldCreateMessageWithCorrectParameters() {
        String to = "+1987654321";
        String body = "Test message";

        try (MockedStatic<Message> mockedMessage = mockStatic(Message.class)) {
            // Mock the static creator method
            when(Message.creator(any(PhoneNumber.class), any(PhoneNumber.class), any(String.class)))
                .thenReturn(messageCreator);
            
            // Mock the create() method on the MessageCreator
            Message mockMessage = mock(Message.class);
            when(messageCreator.create()).thenReturn(mockMessage);

            // Execute the method under test
            smsService.sendSMS(to, body);

            // Verify the creator was called with correct parameters
            ArgumentCaptor<PhoneNumber> toCaptor = ArgumentCaptor.forClass(PhoneNumber.class);
            ArgumentCaptor<PhoneNumber> fromCaptor = ArgumentCaptor.forClass(PhoneNumber.class);
            ArgumentCaptor<String> bodyCaptor = ArgumentCaptor.forClass(String.class);

            mockedMessage.verify(() -> Message.creator(
                    toCaptor.capture(),
                    fromCaptor.capture(),
                    bodyCaptor.capture()));

            // Verify the values
            assertEquals(to, toCaptor.getValue().getEndpoint());
            assertEquals(testFromNumber, fromCaptor.getValue().getEndpoint());
            assertEquals(body, bodyCaptor.getValue());
            
            // Verify create() was called
            verify(messageCreator).create();
        }
    }

    @Test
    void sendSMS_ShouldTrimToNumber() {
        String to = "  +1987654321  "; // With spaces
        String body = "Test message";

        try (MockedStatic<Message> mockedMessage = mockStatic(Message.class)) {
            when(Message.creator(any(PhoneNumber.class), any(PhoneNumber.class), any(String.class)))
                .thenReturn(messageCreator);
            
            Message mockMessage = mock(Message.class);
            when(messageCreator.create()).thenReturn(mockMessage);

            smsService.sendSMS(to, body);

            ArgumentCaptor<PhoneNumber> toCaptor = ArgumentCaptor.forClass(PhoneNumber.class);
            
            mockedMessage.verify(() -> Message.creator(
                    toCaptor.capture(),
                    any(PhoneNumber.class),
                    any(String.class)));

            // Verify the number was trimmed
            assertEquals("+1987654321", toCaptor.getValue().getEndpoint());
        }
    }

    @Test
    void sendSMS_ShouldHandleNullBody() {
        String to = "+1987654321";
        String body = null;

        try (MockedStatic<Message> mockedMessage = mockStatic(Message.class)) {
            // Explicitly specify which creator method to use by matching parameter types
            when(Message.creator(
                any(PhoneNumber.class),  // First PhoneNumber (to)
                any(PhoneNumber.class),  // Second PhoneNumber (from)
                nullable(String.class)  // Explicitly allow null String
            )).thenReturn(messageCreator);
            
            Message mockMessage = mock(Message.class);
            when(messageCreator.create()).thenReturn(mockMessage);

            assertDoesNotThrow(() -> smsService.sendSMS(to, body));

            // Verify with the same explicit parameter types
            mockedMessage.verify(() -> Message.creator(
                any(PhoneNumber.class),
                any(PhoneNumber.class),
                nullable(String.class)));
        }
    }

    @Test
    void sendSMS_ShouldHandleEmptyBody() {
        String to = "+1987654321";
        String body = "";

        try (MockedStatic<Message> mockedMessage = mockStatic(Message.class)) {
            when(Message.creator(any(PhoneNumber.class), any(PhoneNumber.class), any(String.class)))
                .thenReturn(messageCreator);
            
            Message mockMessage = mock(Message.class);
            when(messageCreator.create()).thenReturn(mockMessage);

            assertDoesNotThrow(() -> smsService.sendSMS(to, body));

            ArgumentCaptor<String> bodyCaptor = ArgumentCaptor.forClass(String.class);
            
            mockedMessage.verify(() -> Message.creator(
                    any(PhoneNumber.class),
                    any(PhoneNumber.class),
                    bodyCaptor.capture()));

            assertEquals("", bodyCaptor.getValue());
        }
    }
}