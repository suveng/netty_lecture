package learn.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import learn.broadcast.BroadcastServerInitializer;

/**
 * 心跳机制服务端
 * @author suwenguang
 * @version 1.0.0
 **/
public class HeartBeatServer {
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup).channel( NioServerSocketChannel.class);
            serverBootstrap.handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new HeartBeatServerInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(30301).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            System.out.println("异常");
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
