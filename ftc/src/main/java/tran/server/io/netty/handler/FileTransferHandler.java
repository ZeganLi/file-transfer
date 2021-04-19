package tran.server.io.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author mrliz
 * @Date 2021/3/24 21:19
 */
@Slf4j
public class FileTransferHandler extends ChannelInboundHandlerAdapter{
        public FileTransferHandler() {
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object message) {
            log.info(ctx.toString());
            log.info(message.toString());
//        PackageDispatcher.dispatch(ctx, message);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
}
