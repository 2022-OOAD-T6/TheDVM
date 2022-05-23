package dvm.network;

import DVM_Server.DVMServer;

import java.util.logging.Logger;

public class MyReceiver implements Runnable {

    Logger logger = Logger.getGlobal();
    @Override
    public void run() {
        logger.info("MyReceiver start");
        DVMServer server = new DVMServer();
        try {
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
