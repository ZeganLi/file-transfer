package tran.server.io.netty.decoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

/**
 * 文件传输解码器
 *
 * @author mrliz
 */
@Slf4j
public class FTProtocolDecoder extends MessageToMessageDecoder<DatagramPacket> {


    public FTProtocolDecoder() {
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket msg, List out) {

    }
}
