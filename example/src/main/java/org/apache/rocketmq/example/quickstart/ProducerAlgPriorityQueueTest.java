package org.apache.rocketmq.example.quickstart;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @author mo
 * @Description 生产者测试
 * @createTime 2024年05月14日 10:07
 */
public class ProducerAlgPriorityQueueTest {

    public static void main(String[] args) throws Exception {
        ProducerAlgPriorityQueueTest test = new ProducerAlgPriorityQueueTest();
        System.out.println();
        PriorityQueue<String> priorityQueue = new PriorityQueue<>();

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

    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> num2Times = new HashMap<>();
        for (int num : nums) {
            num2Times.merge(num, 1, Integer::sum);
        }
        PriorityQueue<Pair> priorityQueue = new PriorityQueue<>();
        for (Map.Entry<Integer, Integer> entry : num2Times.entrySet()) {
            priorityQueue.add(new Pair(entry.getKey(), entry.getValue()));
        }
        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = priorityQueue.poll().num;
        }
        return res;
    }

    public static class Pair implements Comparable<Pair> {
        int num;
        int times;

        public Pair(int num, int times) {
            this.num = num;
            this.times = times;
        }

        @Override
        public int compareTo(Pair o) {
            return o.times - this.times;
        }
    }


    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }


}


