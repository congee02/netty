package com.congee02.netty.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * DiscardServerHandler
 *
 * @author gn
 */
@Slf4j
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当接收到新数据时执行的方法 (事件驱动)
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            ByteBuf byteBuf = (ByteBuf) msg;    // IO 原始字节
            // 不响应接收到的数据
            log.info(
                    "Discard: {}", byteBuf.toString(StandardCharsets.UTF_8)
            );
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    /**
     * 当出现异常时执行的方法
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.warn("Exception caught, close the session.", cause);
        ctx.close();
    }
}
