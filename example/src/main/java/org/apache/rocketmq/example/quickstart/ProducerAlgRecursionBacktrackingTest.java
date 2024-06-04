package org.apache.rocketmq.example.quickstart;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;

/**
 * @author mo
 * @Description 生产者测试
 * @createTime 2024年06月03日 09:58
 */
public class ProducerAlgRecursionBacktrackingTest {

    public static void main(String[] args) throws Exception {
        ProducerAlgRecursionBacktrackingTest test = new ProducerAlgRecursionBacktrackingTest();
        System.out.println();
//        List<String> stringList = test.restoreIpAddresses("101023");
//        System.out.println(stringList);
        long t1 = 0, t2 = 0, t3 = 0;
        for (int i = 0; i < 100; i++) {
            String s = RandomStringUtils.randomAscii(200);
            long l1 = System.currentTimeMillis();
            test.partition(s);
            long l2 = System.currentTimeMillis();
            test.partitionDP(s);
            long l3 = System.currentTimeMillis();
            test.partitionDPString(s);
            long l4 = System.currentTimeMillis();
            t1 += l2-l1;
            t2 += l3-l2;
            t3 += l4-l3;
        }
        System.out.println(t1);
        System.out.println(t2);
        System.out.println(t3);

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

    public List<List<String>> partition(String s) {
        List<List<String>> res = new LinkedList<>();
        partitionHelper(s, 0, new LinkedList<>(), res);
        return res;
    }

    private void partitionHelper(String s, int index, LinkedList<String> pre, List<List<String>> res) {
        if (index == s.length()) {
            res.add(new ArrayList<>(pre));
            return;
        }
        for (int i = index + 1; i <= s.length(); i++) {
            String substring = s.substring(index, i);
            if (isPalindrome(substring)) {
                pre.addLast(substring);
                partitionHelper(s, i, pre, res);
                pre.removeLast();
            }
        }
    }

    private boolean isPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }
        int i = 0, j = s.length() - 1;
        while (i < j) {
            if (s.charAt(i) == s.charAt(j)) {
                i++;
                j--;
            } else {
                return false;
            }
        }
        return true;
    }

    public List<List<String>> partitionDP(String s) {
        if (s == null || s.length() == 0) {
            return Collections.emptyList();
        }
        List<List<String>>[] dp = new List[s.length() + 1];
        dp[0] = Collections.singletonList(Collections.emptyList());
        dp[1] = Collections.singletonList(Collections.singletonList(s.substring(0, 1)));
        for (int i = 2; i <= s.length(); i++) {
            dp[i] = new LinkedList<>();
            for (int j = 0; j <= i - 1; j++) {
                String substring = s.substring(j, i);
                if (isPalindrome(substring)) {
                    List<List<String>> pre = dp[j];
                    for (List<String> list : pre) {
                        List<String> clone = new ArrayList<>(list);
                        clone.add(substring);
                        dp[i].add(clone);
                    }
                }
            }
        }
        return dp[s.length()];
    }

    public List<List<String>> partitionDPString(String s) {
        if (s == null || s.length() == 0) {
            return Collections.emptyList();
        }
        List<String>[] dp = new List[s.length() + 1];
        dp[0] = Collections.singletonList("");
        dp[1] = Collections.singletonList(s.substring(0, 1));
        for (int i = 2; i <= s.length(); i++) {
            dp[i] = new LinkedList<>();
            for (int j = 0; j <= i - 1; j++) {
                String substring = s.substring(j, i);
                if (isPalindrome(substring)) {
                    List<String> pre = dp[j];
                    for (String anAnswer : pre) {
                        dp[i].add(anAnswer + "," + substring);
                    }
                }
            }
        }
        List<List<String>> res = new ArrayList<>(dp[s.length()].size());
        for (String anAnswer : dp[s.length()]) {
            if (anAnswer.startsWith(",")) {
                res.add(Arrays.asList(anAnswer.substring(1).split(",")));
            } else {
                res.add(Arrays.asList(anAnswer.split(",")));
            }
        }
        return res;
    }


}
