package tran.server.io.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.stereotype.Component;
import tran.server.io.netty.dispatcher.PackageDispatcher;

@Component
public class FileTransferHandler extends ChannelInboundHandlerAdapter {
    public FileTransferHandler() {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object message) {
        System.out.println(ctx);
        System.out.println(message);
//        PackageDispatcher.dispatch(ctx, message);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
