package com.example.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * @author lc
 * @date 2019/12/26
 */
@Component
public class NettyServer {
    private static final Logger logger = Logger.getLogger(NettyServer.class);

    public void start(InetSocketAddress address) {
        //配置reactor线程池
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //创建ServerBootstrap实例
            ServerBootstrap bootstrap = new ServerBootstrap()
                    //绑定reactor线程池
                    .group(bossGroup, workerGroup)
                    //设置并绑定服务端Channel
                    .channel(NioServerSocketChannel.class)
                    .localAddress(address)
                    //绑定事件处理类
                    .childHandler(new ServerChannelInitializer())
                    //设置TCP参数，backlog表示等待队列长度
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            //绑定端口，开始接收进来的连接
            //绑定本是异步操作,这里将其变为同步阻塞
            ChannelFuture future = bootstrap.bind(address).sync();
            logger.info("Server start listen at " + address.getPort());
            //promise模式，阻塞至channel关闭后才退出
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        } finally {
            //优雅退出，释放线程资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
