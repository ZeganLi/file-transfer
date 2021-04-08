package tran.server.io.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import tran.server.io.netty.model.FTPackage;

/**
 * @author mrliz
 */
public abstract class BasePackageHandler {
    public BasePackageHandler() {
    }

    public abstract boolean support(short var1);

    public abstract void handle(ChannelHandlerContext var1, FTPackage var2);

    public void response(ChannelHandlerContext ctx, FTPackage ack) {
        ByteBuf byteBuf = ack.getByteBuf();
        ctx.writeAndFlush(new DatagramPacket(byteBuf, ack.getRecipient()));
    }
}
