package dvm.network;

import Model.Message;

import java.util.Vector;

public interface Receiver extends Runnable {

    void changeWaitingMessageType(MessageType messageType);

    Vector<Message> getResponseMessages();

    void clearResponseMessages();
}
