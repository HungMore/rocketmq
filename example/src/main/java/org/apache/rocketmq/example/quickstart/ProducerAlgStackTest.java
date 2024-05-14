package org.apache.rocketmq.example.quickstart;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author mo
 * @Description 生产者测试
 * @createTime 2024年05月14日 10:07
 */
public class ProducerAlgStackTest {

    public static void main(String[] args) throws Exception {
        ProducerAlgStackTest test = new ProducerAlgStackTest();
        System.out.println(test.simplifyPath("/home//foo/"));

//        System.out.println(producerAlgTest.threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
//        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
//        producer.setNamesrvAddr("127.0.0.1:9876");
//        producer.start();
//        Message message2 = new Message("order_topic",
//                null,
//                "key1",
//                JSON.toJSONString("").getBytes(StandardCharsets.UTF_8));
//        producer.send(message2, new SelectMessageQueueByHash(), "12");
    }

    public boolean isValid(String s) {
        Deque<Character> stack = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else if (stack.isEmpty() || !isMatch(stack.pop(), c)) {
                return false;
            }
        }
        return stack.isEmpty();
    }

    private boolean isMatch(char left, char right) {
        return (left == '[' && right == ']') || (left == '(' && right == ')') || (left == '{' && right == '}');
    }

    public int evalRPN(String[] tokens) {
        Deque<Integer> stack = new LinkedList<>();
        for (String token : tokens) {
            if (token.equals("+")) {
                Integer num2 = stack.pop();
                Integer num1 = stack.pop();
                stack.push(num1 + num2);
            } else if (token.equals("-")) {
                Integer num2 = stack.pop();
                Integer num1 = stack.pop();
                stack.push(num1 - num2);
            } else if (token.equals("*")) {
                Integer num2 = stack.pop();
                Integer num1 = stack.pop();
                stack.push(num1 * num2);
            } else if (token.equals("/")) {
                Integer num2 = stack.pop();
                Integer num1 = stack.pop();
                stack.push(num1 / num2);
            } else {
                stack.push(Integer.parseInt(token));
            }
        }
        return stack.pop();
    }

    public String simplifyPath(String path) {
        Deque<String> stack = new LinkedList<>();
        String[] split = path.split("/");
        for (String s : split) {
            if ("..".equals(s)) {
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else if (!"".equals(s) && !".".equals(s)) {
                stack.push(s);
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (stack.isEmpty()) {
            stringBuilder.append("/");
        } else {
            while (!stack.isEmpty()) {
                stringBuilder.insert(0, "/" + stack.pop());
            }
        }
        return stringBuilder.toString();
    }

}
