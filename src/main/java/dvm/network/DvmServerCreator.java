package dvm.network;

import DVM_Server.DVMServer;

/**
 * DvmServer 인스턴스 생성 클래스
 * 리시버를 NettyReceiver 클래스로 사용하는 경우에만 쓰이는 클래스
 */
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
