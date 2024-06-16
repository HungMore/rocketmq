package org.apache.rocketmq.example.quickstart;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mo
 * @Description 生产者测试
 * @createTime 2024年06月03日 09:58
 */
@Slf4j
public class ProducerAlgDynamicProgrammingTest {

    public static void main(String[] args) throws Exception {
        ProducerAlgDynamicProgrammingTest test = new ProducerAlgDynamicProgrammingTest();
        System.out.println();

//        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
//        producer.setNamesrvAddr("127.0.0.1:9876");
//        producer.start();
//        Message message2 = new Message("order_topic",
//                null,
//                "key1",
//                JSON.toJSONString("").getBytes(StandardCharsets.UTF_8));
//        producer.send(message2, new SelectMessageQueueByHash(), "12");

        test.fib(5);
        System.out.println(test.count);

        test.count = 0;
        test.fibMemo(5);
        System.out.println(test.count);
    }

    // 计算执行fib函数的次数
    int count = 0;

    public int fib(int n) {
        count++;
        if (n == 0 || n == 1) {
            return n;
        }
        return fib(n - 1) + fib(n - 2);
    }

    Map<Integer, Integer> memo = new HashMap<>();

    public int fibMemo(int n) {
        count++;
        if (n == 0 || n == 1) {
            return n;
        }
        Integer res = memo.get(n);
        if (res == null) {
            res = fibMemo(n - 1) + fibMemo(n - 2);
            memo.put(n, res);
        }
        return res;
    }

    public int climbStairs(int n) {
        int[] dp = new int[Math.max(n + 1, 3)];
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

}
