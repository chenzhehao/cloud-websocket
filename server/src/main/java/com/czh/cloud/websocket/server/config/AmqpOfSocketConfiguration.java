package com.czh.cloud.websocket.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.HashMap;
import java.util.Map;

//@EnableRabbit
//@Configuration
public class AmqpOfSocketConfiguration {
    public Logger logger = LoggerFactory.getLogger(AmqpOfSocketConfiguration.class);

    public static final String EXCHANGE = "live-socket-FanoutEx";

    public static final String QUEUE_NAME = "live-socket--queue";
    public static final String QUEUE_NAME2 = "live-socket--queue2";


    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private Integer port;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;
    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;

    @Autowired
    private RabbitTemplate template;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(this.connectionFactory());
        template.setMessageConverter(this.jsonMessageConverter());
        template.setMandatory(true);
        return template;
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(EXCHANGE);
    }

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }
    @Bean
    public Queue queue2() {
        return new Queue(QUEUE_NAME2, true);
    }

    @Bean
    public Binding binding(Queue queue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queue()).to(fanoutExchange());
    }
    @Bean
    public Binding binding2(Queue queue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queue2()).to(fanoutExchange());
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleMessageListenerContainer messageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueueNames(AmqpOfSocketConfiguration.QUEUE_NAME,AmqpOfSocketConfiguration.QUEUE_NAME2);
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(2);
        container.setConcurrentConsumers(2);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                ObjectMapper objectMapper = new ObjectMapper();
                Map map = null;
                try {
                    // 将消息转换成UpdateOrderReq对象
                    map = objectMapper.readValue(new String(message.getBody()), HashMap.class);
                    // 业务逻辑处理
                    logger.info("支付回调消息内容:" + map);
                } catch (Exception e) {
                    // 记录错误日志
                    logger.error(map.toString(), e);
                } finally {
                    // 手动回复处理成功
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                }
            }
        });
        return container;
    }

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }
}