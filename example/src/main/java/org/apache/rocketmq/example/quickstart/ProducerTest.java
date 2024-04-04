package org.apache.rocketmq.example.quickstart;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.rocketmq.client.QueryResult;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.selector.SelectMessageQueueByHash;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageClientIDSetter;
import org.apache.rocketmq.common.message.MessageDecoder;
import org.apache.rocketmq.common.message.MessageId;
import org.apache.rocketmq.example.quickstart.model.Order;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author mo
 * @Description 生产者测试
 * @createTime 2024年04月03日 16:16
 */
public class ProducerTest {

    public static void main(String[] args) {
        // 启动生产者
        MQProducer producer = startMQProducer("127.0.0.1:9876");

        // 发送同步消息
//        sendSyncMessage(producer);

        // 发送异步消息
//        sendAsyncMessage(producer);

        // 自定义队列选择，用于发送顺序消息
        // 发送顺序消息如何保证消息能准确送达broker呢？它能否和事务消息一起用，配合形成顺序事务消息？
        // 好像做不到呢，因为事务消息的消息回查是个延时的定时任务，它的触发时间可能比下一条消息更晚。
        // 所以顺序消息要保证数据的最终一致性，好像还是要用本地消息表比较实在。本地消息表+顺序消息！
//        sendOrderMessage(producer);

        // 通过topic和key查询消息。可以查看到msg被存储在哪个queue，dashboard并没有提供这个功能。
//        queryMsgByKey(producer, "order_topic", "3629ca78-de2f-4900-a92d-b0592afdf8a3");

        // 解析msgId
//        parseMsgId("C0A8006AB48418B4AAC211EEBD720000");

        // 解析offsetMsgId
//        parseOffsetMsgId("7F00000100002A9F00000000000009CA");

        // 关闭生产者
        stopMQProducer(producer);
    }

    public static MQProducer startMQProducer(String nameServer) {
        DefaultMQProducer producer = new DefaultMQProducer("producer_group_01");
        producer.setNamesrvAddr(nameServer);
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        return producer;
    }

    public static void sendSyncMessage(MQProducer producer) {
        Message msg = new Message("TopicTest" /* Topic */,
                "Tag1" /* Tag */,
                "key1", /* key */
                ("Hello RocketMQ 1").getBytes(StandardCharsets.UTF_8) /* Message body */
        );
        SendResult sendResult = null;
        try {
            sendResult = producer.send(msg);
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("%s%n", sendResult);
    }

    public static void sendAsyncMessage(MQProducer producer) {
        Message message = new Message("TOPIC_TEST", "hello world".getBytes(StandardCharsets.UTF_8));
        try {
            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("异步消息发送成功：" + sendResult);
                }

                @Override
                public void onException(Throwable e) {
                    System.out.println("异步消息发送失败");
                    e.printStackTrace();
                }
            });
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void stopMQProducer(MQProducer producer) {
        // 优雅关闭。一定要等异步消息发送完毕以后，再去shutdown，不然异步消息可能会出现 RemotingConnectException: connect to xxxx failed 的异常
        // rocketmq-spring 是通过 @Bean(destroyMethod = "destroy") 来关闭的。那么问题来了，@Bean(destroyMethod = "destroy") 是如何保证优雅关闭的？大概是spring自动为每个destroyMethod注册一个钩子函数吧
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        producer.shutdown();
    }

    public static void sendOrderMessage(MQProducer producer) {
        Order order = new Order();
        order.setId(1L);
        order.setOrderNo(UUID.randomUUID().toString());
        order.setTotalPrice(100D);
        order.setBuyer("lixiang");
        order.setSeller("elon musk");
        order.setStatus(0);
        Message message1 = new Message("order_topic",
                null,
                order.getOrderNo(),
                JSON.toJSONString(order).getBytes(StandardCharsets.UTF_8));
        SendResult sendResult = null;
        try {
            // 使用 order.getOrderNo() 来做hash
            sendResult = producer.send(message1, new SelectMessageQueueByHash(), order.getOrderNo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("自定义队列选择发送消息1：" + sendResult);
        order.setStatus(1);
        // 一定要用一个新的message对象。
        // 不能通过message.setBody()试图复用同一个message对象，这样会出问题
        // 比如message的msgId是一样的、body还是原来的旧值、两个消息不会发送到同一个队列等，具体的原因未知，还得看后序学习和看源码。
        Message message2 = new Message("order_topic",
                null,
                order.getOrderNo(),
                JSON.toJSONString(order).getBytes(StandardCharsets.UTF_8));
        try {
            // 使用 order.getOrderNo() 来做hash
            sendResult = producer.send(message2, new SelectMessageQueueByHash(), order.getOrderNo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("自定义队列选择发送消息2：" + sendResult);
    }

    public static void queryMsgByKey(MQProducer producer, String topic, String key) {
        Long from = null;
        Long to = null;
        try {
            from = DateUtils.parseDate("2024-01-01", "yyyy-MM-dd").getTime();
            to = DateUtils.parseDate("2025-01-01", "yyyy-MM-dd").getTime();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        try {
            QueryResult queryResult = producer.queryMessage(topic, key, 10, from, to);
            System.out.println("消息查询结果：" + queryResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // rocketmq还提供了根据 msgId 和 offsetMsgId 查询消息的api，需要用到的时候再看吧
    }

    public static void parseMsgId(String msgId) {
        /* msgId 的组成：
                - 客户端发送IP，支持 IPV4 和 IPV6
                - 进程 PID（2 字节）
                - 类加载器的 hashcode（4 字节）
                - 当前系统时间戳与启动时间戳的差值（4 字节）
                - 自增序列（2 字节）
           所以可以通过msgId解析出生产者的ip\进程id等
         */
        String ip = MessageClientIDSetter.getIPStrFromID(msgId);
        Date time = MessageClientIDSetter.getNearlyTimeFromID(msgId);
        int pid = MessageClientIDSetter.getPidFromID(msgId);
        System.out.println(ip + "\t" + time + "\t" + pid);
    }

    public static void parseOffsetMsgId(String offsetMsgId) {
        /* offsetMsgId 的组成：
                - Broker 的 IP 与端口号
                - commitlog 中的物理偏移量
           所以可以通过offsetMsgId解析出消息存储的broker的ip端口
           根据offsetMsgId的生成规则，我们也可以知道: 如果一个消息消费失败需要重试，RocketMQ的做法是将消息重新发送到Broker服务器
           此时全局 msgId 是不会发生变化的，但该消息的 offsetMsgId 会发送变化，因为其存储在服务器中的位置发生了变化。
         */
        MessageId messageId = null;
        try {
            messageId = MessageDecoder.decodeMessageId(offsetMsgId);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println(messageId.getAddress() + "\t" + messageId.getOffset());
    }

}
