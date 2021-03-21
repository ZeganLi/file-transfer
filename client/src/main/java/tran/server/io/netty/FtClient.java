package tran.server.io.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.extern.slf4j.Slf4j;
import tran.server.io.netty.decoder.FTProtocolDecoder;
import tran.server.io.netty.handler.FileTransferHandler;

/**
 * FileTransferClient  文件传输客户端
 * @author mrliz
 */
@Slf4j
public class FtClient {
    private static final EventLoopGroup group = new NioEventLoopGroup();

    public FtClient() {
    }

    public static void startClient(final int port) {
        log.info("-------------------------------------------------------");
        log.info("----Start File Transfer Client 0.0.0.0:" + port + " ----------");
        log.info("-------------------------------------------------------");

        new Thread(() -> {
            try {
                final FTProtocolDecoder ftProtocolDecoder = new FTProtocolDecoder();
                final FileTransferHandler fileTransferHandler = new FileTransferHandler();
                Bootstrap b = new Bootstrap();
                b.group(FtClient.group)
                        .channel(NioDatagramChannel.class)
                        // 单播：传输到定义的主机组。只传输到指定的机器。
                        .option(ChannelOption.SO_BROADCAST, false)
                        .option(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(65535))
                        .handler(new ChannelInitializer<NioDatagramChannel>() {
                            @Override
                            public void initChannel(NioDatagramChannel ch) {
                                ch.pipeline().addLast(ftProtocolDecoder, fileTransferHandler);
                            }
                        });

                b.bind(port).sync().channel().closeFuture().await();
            } catch (Exception var7) {
                var7.printStackTrace();
            } finally {
                FtClient.group.shutdownGracefully();
            }
        }
        ).start();

    }

    public static void stopClient() {
        log.info("---------------------------------------------------");
        log.info("---- Stop File Transfer Client (Netty)       ------");
        log.info("---------------------------------------------------");
        group.shutdownGracefully();
    }
}
