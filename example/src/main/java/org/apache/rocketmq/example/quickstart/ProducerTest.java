package org.apache.rocketmq.example.quickstart;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author mo
 * @Description 生产者测试
 * @createTime 2024年04月03日 16:16
 */
public class ProducerTest {

    public static void main(String[] args) {
        MQProducer producer = startMQProducer("127.0.0.1:9876");

        // 发送异步消息
//        sendAsyncMessage(producer);

        // 发送顺序消息
        sendOrderMessage(producer);

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

    public static void stopMQProducer(MQProducer producer){
        // 优雅关闭。一定要等异步消息发送完毕以后，再去shutdown，不然异步消息可能会出现 RemotingConnectException: connect to xxxx failed 的异常
        // rocketmq-spring 是通过 @Bean(destroyMethod = "destroy") 来关闭的。那么问题来了，@Bean(destroyMethod = "destroy") 是如何保证优雅关闭的？大概是spring自动为每个destroyMethod注册一个钩子函数吧
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        producer.shutdown();
    }

    public static void sendOrderMessage(MQProducer producer){

    }

}
