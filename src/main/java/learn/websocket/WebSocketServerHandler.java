package learn.websocket;

import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.EventExecutorGroup;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 *
 * @author suwenguang
 * @version 1.0.0
 **/
public class WebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        Channel channel = ctx.channel();
        String message = "收到消息" + msg.text();
        System.out.println(message);

        TextWebSocketFrame webSocketFrame = new TextWebSocketFrame("服务器时间: "+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm:ss")) + message);

        channel.writeAndFlush(webSocketFrame);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        Channel channel = ctx.channel();
        System.out.println("handler added:" + channel.id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("handler removed: " + channel.id().asLongText());

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("异常发生: " + channel.id().asLongText());
    }
}
