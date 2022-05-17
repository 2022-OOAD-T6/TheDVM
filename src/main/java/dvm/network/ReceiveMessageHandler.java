package dvm.network;

import dvm.service.ItemService;
import dvm.service.PrepaymentService;

import java.net.Socket;
import java.util.Vector;

/**
 * 
 */
public class ReceiveMessageHandler {

    /**
     * Default constructor
     */
    public ReceiveMessageHandler() {
    }

    /**
     * 
     */
    private Socket socket;

    /**
     * 
     */
    private ItemService itemService;

    /**
     * 
     */
    private PrepaymentService prepaymentService;

    /**
     * 
     */
    private Vector<Message> responseMessages;

    /**
     * @return
     */
    public void run() {
        // TODO implement here
    }

}