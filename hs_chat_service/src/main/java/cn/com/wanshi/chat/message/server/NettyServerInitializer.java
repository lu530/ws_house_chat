package cn.com.wanshi.chat.message.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * nettyServer 启动类
 */
@Component
public class NettyServerInitializer  implements CommandLineRunner {

    @Value("${netty.hostname}")
    private String hostname;

    @Value("${netty.port}")
    private int port;

    @Autowired
    private NettyServer nettyServer;


    @Override
    public void run(String... args) throws Exception {
        InetSocketAddress address = new InetSocketAddress(hostname,port);
        nettyServer.start(address);

    }
}
