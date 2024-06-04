package org.apache.rocketmq.example.quickstart;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author mo
 * @Description 生产者测试
 * @createTime 2024年06月03日 09:58
 */
public class ProducerAlgRecursionBacktrackingTest {

    public static void main(String[] args) throws Exception {
        ProducerAlgRecursionBacktrackingTest test = new ProducerAlgRecursionBacktrackingTest();
        System.out.println();
        List<String> stringList = test.restoreIpAddresses("101023");
        System.out.println(stringList);

//        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
//        producer.setNamesrvAddr("127.0.0.1:9876");
//        producer.start();
//        Message message2 = new Message("order_topic",
//                null,
//                "key1",
//                JSON.toJSONString("").getBytes(StandardCharsets.UTF_8));
//        producer.send(message2, new SelectMessageQueueByHash(), "12");


    }

    public List<String> letterCombinations(String digits) {
        LinkedList<String> res = new LinkedList<>();
        combinations(digits, 0, new StringBuilder(), res);
        return res;
    }

    private void combinations(String digits, int digitIndex, StringBuilder pre, List<String> res) {
        if (digitIndex == digits.length()) {
            if (pre.length() != 0) {
                res.add(pre.toString());
            }
            return;
        }
        List<Character> myLetters = getMyLetters(digits.charAt(digitIndex));
        for (Character myLetter : myLetters) {
            // 拼接上字母
            pre.append(myLetter);
            combinations(digits, digitIndex + 1, pre, res);
            // 回溯的关键：去掉拼接的字母
            pre.deleteCharAt(digitIndex);
        }
    }

    private List<Character> getMyLetters(char digit) {
        if (digit == '2') {
            return Arrays.asList('a', 'b', 'c');
        } else if (digit == '3') {
            return Arrays.asList('d', 'e', 'f');
        } else if (digit == '4') {
            return Arrays.asList('g', 'h', 'i');
        } else if (digit == '5') {
            return Arrays.asList('j', 'k', 'l');
        } else if (digit == '6') {
            return Arrays.asList('m', 'n', 'o');
        } else if (digit == '7') {
            return Arrays.asList('p', 'q', 'r', 's');
        } else if (digit == '8') {
            return Arrays.asList('t', 'u', 'v');
        } else if (digit == '9') {
            return Arrays.asList('w', 'x', 'y', 'z');
        } else {
            throw new RuntimeException("unsupported digit:" + digit);
        }
    }

    public List<String> restoreIpAddresses(String s) {
        LinkedList<String> res = new LinkedList<>();
        ip(s, 0, 0, "", res);
        return res;
    }

    private void ip(String s, int index, int segmentNumber, String pre, List<String> res) {
        if (index == s.length() || segmentNumber == 4) {
            if (index == s.length() && segmentNumber == 4) {
                res.add(pre.substring(0, pre.length() - 1));
            }
            return;
        }
        for (int i = 1, endIndex; i < 4 && (endIndex = index + i) <= s.length(); i++) {
            String segment = s.substring(index, endIndex);
            if (isValidSegment(segment)) {
                ip(s, endIndex, segmentNumber + 1, pre + segment + ".", res);
            } else {
                // 提前剪枝
                break;
            }
        }
    }

    private boolean isValidSegment(String segment) {
        if (segment.startsWith("0")) {
            return "0".equals(segment);
        } else {
            int i = Integer.parseInt(segment);
            return i >= 0 && i <= 255;
        }
    }


}
