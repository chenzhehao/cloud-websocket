package com.czh.cloud.websocket.server.config;

import com.czh.cloud.websocket.server.socket.WebSocketServer;
import org.springframework.stereotype.Component;

/**
 * @author: zhehao.chen
 * @version: V1.0
 * @Description:
 * @date: 2018/12/4 13:38
 */
@Component
public class RedisMsgSub {

    /**
     * 接收消息的方法
     *
     * @param info 订阅消息
     */
    public void receiveMessage(String info) {
        System.out.println(info);
        WebSocketServer.subscribe(info);

    }
}