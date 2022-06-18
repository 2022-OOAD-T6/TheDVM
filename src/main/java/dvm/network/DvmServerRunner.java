package dvm.network;

import DVM_Server.DVMServer;

/**
 * DvmServer 라이브러리 실행 클래스
 * 리시버를 NettyReceiver 클래스로 사용하는 경우에만 쓰이는 클래스
 */
public class DvmServerRunner implements Runnable {

    private final DVMServer server;

    public DvmServerRunner(DVMServer server) {
        this.server = server;
    }

    @Override
    public void run() {
        try {
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
