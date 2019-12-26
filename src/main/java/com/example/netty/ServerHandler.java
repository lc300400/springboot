package com.example.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author lc
 * @date 2019/12/26
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    //channel激活
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("channelActive----->");
    }

    //channel接收数据
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server channelRead......");
        System.out.println(ctx.channel().remoteAddress() + "----->Server :" + msg.toString());
        //将客户端的信息直接返回写入ctx
        ctx.write("server say :" + msg);
        //刷新缓存区
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
