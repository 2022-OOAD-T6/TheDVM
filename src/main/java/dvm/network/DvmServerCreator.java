package dvm.network;

import DVM_Server.DVMServer;

public class DvmServerCreator implements Runnable {

    @Override
    public void run() {
        DVMServer server = new DVMServer();
        try {
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
