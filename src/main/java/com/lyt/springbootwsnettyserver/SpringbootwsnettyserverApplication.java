package com.lyt.springbootwsnettyserver;

import com.lyt.springbootwsnettyserver.server.WsNettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootwsnettyserverApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringbootwsnettyserverApplication.class, args);
        //启动服务端
        WsNettyServer wsNettyServer = new WsNettyServer(8000);
        wsNettyServer.start();
    }

}
