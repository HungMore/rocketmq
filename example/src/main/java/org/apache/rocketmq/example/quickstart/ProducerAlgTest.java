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

}
