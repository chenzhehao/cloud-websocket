package com.czh.cloud.websocket.server.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.czh.cloud.common.util.CommonUtil;
import com.czh.cloud.websocket.server.service.RedisService;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: zhehao.chen
 * @version: V1.0
 * @Description:
 * @date: 2018/12/3 14:32
 */

@Component
@ServerEndpoint(value = "/live/{token}")
public class WebSocketServer {
    private static Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
    public static Map<String, Map<String, Session>> clients = new ConcurrentHashMap();
    public String token;
    public String key;

    /**
     * 建立连接
     *
     * @param session
     */
    @OnOpen
    public void onOpen(@PathParam("token") String token, Session session) {
        logger.info("现在来连接的客户token：" + token);
        Map paramsMap = CommonUtil.getMapFromQueryString(session.getQueryString());
        String key = getKey(paramsMap.get("kind"), paramsMap.get("id"));
        addToClients(key, token, session);//加入到客户连接池中
        this.token = token;
        this.key = key;
    }

    /**
     * 功能描述: 订阅消息处理
     *
     * @author: zhehao.chen
     * @version: V1.0
     * @date: 2018/12/4 16:27
     * @param: [info]
     * @return: void
     */
    public static void subscribe(String info) {
        JSONObject json = JSONObject.parseObject(info);
        int messageType = json.getInteger("messageType");
        String key = json.getString("key");
        if (messageType == 4) {//普通消息
            String fromToken = json.getString("fromToken");
            sendMessageAllNoMe(info, key, fromToken);
        } else if (messageType == 5) {//下线消息
            String token = json.getString("token");
            removeFromClients(key, token);
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        logger.info("服务端发生了错误" + error.getMessage());
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose() {
        logger.info("用户下线：" + token);
        if (!removeFromClients(key, token)) {
            Map infoMap = new HashMap();
            infoMap.put("messageType", 5);
            infoMap.put("key", key);
            infoMap.put("token", token);
            infoMap.put("message", "用户下线：" + token);
            RedisService.stringRedisTemplate.convertAndSend("redisChat", JSON.toJSONString(infoMap));
            logger.info("用户下线--不在本机：" + token);
        }
    }

    /**
     * 收到客户端的消息
     *
     * @param message 消息
     * @param session 会话
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            logger.info("来自客户端消息：" + message + "客户端的id是：" + session.getId());
            JSONObject jsonObject = JSON.parseObject(message);
            String kind = jsonObject.getString("kind");
            String id = jsonObject.getString("id");
            String textMessage = jsonObject.getString("message");
            String fromToken = jsonObject.getString("token");
            String totoken = jsonObject.getString("to");
            String key = getKey(kind, id);

            //如果不是发给所有，那么就发给某一个人
            //messageType 1代表上线 2代表下线 3代表在线名单  4代表普通消息
            Map<String, Object> map1 = Maps.newHashMap();
            map1.put("messageType", 4);
            map1.put("key", key);
            map1.put("fromToken", fromToken);
            map1.put("totoken", totoken);
            for (int i = 0; i < 100; i++) {
                map1.put("textMessage", textMessage + i);
                RedisService.stringRedisTemplate.convertAndSend("redisChat", JSON.toJSONString(map1));//推送到其他服务器，给用户推送消息
            }
        } catch (Exception e) {
            logger.info("发生了错误了");
        }

    }

    public static void sendMessageAll(String message, String key) {
        Map<String, Session> map = clients.get(key);
        for (Session session : map.values()) {
            session.getAsyncRemote().sendText(message);
        }
    }

    public static void sendMessageAllNoMe(String message, String key, String fromToken) {
        Map<String, Session> map = clients.get(key);
        for (String set : map.keySet()) {
            if (!set.equals(fromToken)) {
                try {
                    map.get(set).getAsyncRemote().sendText(message);
                } catch (Exception e) {
                    logger.error("状态异常：{}{}", set, e);
                }
            }
        }
    }

    public static void addToClients(String key, String token, Session session) {
        Map<String, Session> map = clients.get(key);
        if (map == null) {
            map = new HashMap();
        }
        map.put(token, session);
        clients.put(key, map);
    }

    public static Session getFromClients(String key, String token) {
        try {
            return clients.get(key).get(token);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean removeFromClients(String key, String token) {
        Session session = getFromClients(key, token);
        boolean flag = false;
        if (session != null) {
            clients.get(key).remove(token);
            flag = true;
        }
        return flag;
    }

    public static String getKey(Object kind, Object id) {
        return "webSocket_token_" + kind + "_" + id;
    }
}
