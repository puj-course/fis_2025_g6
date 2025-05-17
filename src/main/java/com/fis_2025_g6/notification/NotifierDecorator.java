package com.fis_2025_g6.notification;

public abstract class NotifierDecorator implements Notifier {
    protected Notifier wrappee;

    public NotifierDecorator(Notifier notifier) {
        this.wrappee = notifier;
    }

    @Override
    public void send(String message, String recipient) {
        wrappee.send(message, recipient);
    }
}
