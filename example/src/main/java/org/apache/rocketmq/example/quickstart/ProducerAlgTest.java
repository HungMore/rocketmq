package org.apache.rocketmq.example.quickstart;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.selector.SelectMessageQueueByHash;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author mo
 * @Description 生产者测试
 * @createTime 2024年04月23日 17:25
 */
public class ProducerAlgTest {

    public static void main(String[] args) throws Exception {
        ProducerAlgTest producerAlgTest = new ProducerAlgTest();

        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();
        Message message2 = new Message("order_topic",
                null,
                "key1",
                JSON.toJSONString("").getBytes(StandardCharsets.UTF_8));
        producer.send(message2, new SelectMessageQueueByHash(), "12");
    }

    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> resultSet = new HashSet<>();
        Set<Integer> nums2Set = new HashSet<>();
        for (int num : nums2) {
            nums2Set.add(num);
        }
        for (int num : nums1) {
            if (nums2Set.contains(num)) {
                resultSet.add(num);
            }
        }
        int[] result = new int[resultSet.size()];
        int i = 0;
        for (Integer num : resultSet) {
            result[i++] = num;
        }
        return result;
    }

    public int[] intersect(int[] nums1, int[] nums2) {
        List<Integer> resultList = new LinkedList<>();
        Map<Integer, Integer> nums2Map = new HashMap<>();
        for (int num : nums2) {
            nums2Map.merge(num, 1, Integer::sum);
        }
        for (int num : nums1) {
            Integer times = nums2Map.get(num);
            if (times != null && times > 0) {
                resultList.add(num);
                nums2Map.merge(num, -1, Integer::sum);
            }
        }
        int[] res = new int[resultList.size()];
        int i = 0;
        for (Integer num : resultList) {
            res[i++] = num;
        }
        return res;
    }

    public boolean isAnagram(String s, String t) {
        Map<Character, Integer> sChar2Times = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            sChar2Times.merge(s.charAt(i), 1, Integer::sum);
        }
        for (int i = 0; i < t.length(); i++) {
            Integer times = sChar2Times.get(t.charAt(i));
            if (times == null || times <= 0) {
                return false;
            }
            sChar2Times.put(t.charAt(i), times - 1);
        }
        for (Map.Entry<Character, Integer> entry : sChar2Times.entrySet()) {
            if (entry.getValue() != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isHappy(int n) {
        Set<Long> longSet = new HashSet<>();
        long temp = n;
        longSet.add(temp);
        while (true) {
            temp = transform(temp);
            if (temp == 1) {
                return true;
            }
            if (!longSet.add(temp)) {
                return false;
            }
        }
    }

    private long transform(long n) {
        String longString = Long.toString(n);
        long res = 0;
        for (int i = 0; i < longString.length(); i++) {
            res += (int) Math.pow((longString.charAt(i) - '0'), 2);
        }
        return res;
    }

    public boolean wordPattern(String pattern, String s) {
        String[] split = s.split(" ");
        if (split.length != pattern.length()) {
            return false;
        }
        Map<Character, String> map1 = new HashMap<>();
        Map<String, Character> map2 = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            String word = map1.get(pattern.charAt(i));
            if (word == null) {
                Character character = map2.get(split[i]);
                if (character != null) {
                    return false;
                } else {
                    map1.put(pattern.charAt(i), split[i]);
                    map2.put(split[i], pattern.charAt(i));
                }
            } else if (!word.equals(split[i])) {
                return false;
            }
        }
        return true;
    }

    public boolean isIsomorphic(String s, String t) {
        Map<Character, Character> map1 = new HashMap<>();
        Map<Character, Character> map2 = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            Character s2tChar = map1.get(s.charAt(i));
            if (s2tChar == null) {
                Character t2sChar = map2.get(t.charAt(i));
                if (t2sChar != null) {
                    return false;
                } else {
                    map1.put(s.charAt(i), t.charAt(i));
                    map2.put(t.charAt(i), s.charAt(i));
                }
            } else if (t.charAt(i) != s2tChar) {
                return false;
            }
        }
        return true;
    }

    public String frequencySort(String s) {
        HashMap<Character, Integer> char2Times = new HashMap<>();
        TreeSet<Pair> treeSet = new TreeSet<>();
        for (int i = 0; i < s.length(); i++) {
            char2Times.merge(s.charAt(i), 1, Integer::sum);
        }
        for (Map.Entry<Character, Integer> entry : char2Times.entrySet()) {
            treeSet.add(new Pair(entry.getKey(), entry.getValue()));
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Pair pair : treeSet) {
            for (int i = 0; i < pair.times; i++) {
                stringBuilder.append(pair.c);
            }
        }
        return stringBuilder.toString();
    }

    private class Pair implements Comparable<Pair> {
        char c;
        int times;

        @Override
        public int compareTo(Pair p) {
            int gap = p.times - this.times;
            if (gap == 0) {
                return this.c - p.c;
            }
            return gap;
        }

        public Pair(char c, int times) {
            this.c = c;
            this.times = times;
        }
    }

}
