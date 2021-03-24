package tran.server.io.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.extern.slf4j.Slf4j;
import tran.server.io.netty.handler.FileTransferHandler;

import java.net.InetSocketAddress;

/**
 * FileTransferClient  文件传输客户端
 *
 * @author mrliz
 */
@Slf4j
public class FtClient {
    private String host;
    private int port;

    private EventLoopGroup group;
    private Bootstrap bootstrap;
    private Channel channel;

    public FtClient() {
    }

    public void UdpDataSender(String host, int port) {
        try {
            this.host = host;
            this.port = port;
            group = new NioEventLoopGroup();
            bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioDatagramChannel.class)
                    .remoteAddress(host, port)
                    .handler(new FileTransferHandler());
            channel = bootstrap.bind(0).sync().channel();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void close() {
        group.shutdownGracefully();
    }

    public void send(byte[] data) {

        try {
            ByteBuf byteBuf = Unpooled.buffer(data.length);
            byteBuf.writeBytes(data);

            channel.writeAndFlush(
                    new DatagramPacket(
                            byteBuf,
                            new InetSocketAddress(
                                    host, port
                            )));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void stopClient() {
        log.info("---------------------------------------------------");
        log.info("---- Stop File Transfer Client (Netty)       ------");
        log.info("---------------------------------------------------");
        group.shutdownGracefully();
    }


}
