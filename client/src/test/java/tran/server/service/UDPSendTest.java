package tran.server.service;

import tran.server.io.netty.FtClient;

/**
 * @Description
 * @Author mrliz
 * @Date 2021/3/24 21:11
 */
public class UDPSendTest {
    public static void main(String[] args) throws Exception {
        String host = "127.0.0.1";
        int port = 9000;

        FtClient udpDataSender = new FtClient();
        udpDataSender.UdpDataSender(host, port);
        for (int i = 0; i < 100; i++) {
            String data = "Hello Netty UDP" + i;
            udpDataSender.send(data.getBytes());
            Thread.sleep(2);
        }
        udpDataSender.close();
    }
}
