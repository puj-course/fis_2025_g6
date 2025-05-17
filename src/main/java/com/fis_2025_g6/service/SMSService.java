package com.fis_2025_g6.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SMSService {

    //credenciales
    public static final String ACCOUNT_SID = "SID";
    public static final String AUTH_TOKEN = "TOKEN";
    public static final String FROM_PHONE_NUMBER = "+18573763394"; // Número de Twilio

    static {
        // Inicializa la conexión con Twilio
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }
    public static void enviarSMS(String numeroDestino, String mensaje) {
        try {
            Message sms = Message.creator(
                    new PhoneNumber(numeroDestino),
                    new PhoneNumber(FROM_PHONE_NUMBER),
                    mensaje
            ).create();

            System.out.println("SMS enviado correctamente. SID: " + sms.getSid());
        } catch (Exception e) {
            System.out.println("Error al enviar SMS: " + e.getMessage());
        }
    }

    public void sendSMS(String number, String message) {

    }
}