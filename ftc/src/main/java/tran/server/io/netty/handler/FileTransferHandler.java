package tran.server.io.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Description
 * @Author mrliz
 * @Date 2021/3/24 21:19
 */
public class FileTransferHandler extends ChannelInboundHandlerAdapter{
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
