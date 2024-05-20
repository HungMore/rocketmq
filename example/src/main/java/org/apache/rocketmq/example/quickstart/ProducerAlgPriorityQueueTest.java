package org.apache.rocketmq.example.quickstart;

import java.util.*;

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
        int[] ints = test.topKFrequentWithBucket(new int[]{1, 1, 1, 2, 2, 3}, 2);
        System.out.println(Arrays.toString(ints));

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

    public int[] topKFrequentWithHeapBetter(int[] nums, int k) {
        HashMap<Integer, Integer> num2Times = new HashMap<>();
        for (int num : nums) {
            num2Times.merge(num, 1, Integer::sum);
        }
        // 小顶堆
        PriorityQueue<PairWithHeapBetter> priorityQueue = new PriorityQueue<>();
        for (Map.Entry<Integer, Integer> entry : num2Times.entrySet()) {
            if (priorityQueue.size() < k) {
                priorityQueue.add(new PairWithHeapBetter(entry.getKey(), entry.getValue()));
            } else {
                PairWithHeapBetter peek = priorityQueue.peek();
                if (peek.times < entry.getValue()) {
                    priorityQueue.poll();
                    priorityQueue.add(new PairWithHeapBetter(entry.getKey(), entry.getValue()));
                }
            }

        }
        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = priorityQueue.poll().num;
        }
        return res;
    }

    public static class PairWithHeapBetter implements Comparable<PairWithHeapBetter> {
        int num;
        int times;

        public PairWithHeapBetter(int num, int times) {
            this.num = num;
            this.times = times;
        }

        @Override
        public int compareTo(PairWithHeapBetter o) {
            return this.times - o.times;
        }
    }

    public int[] topKFrequentWithBucket(int[] nums, int k) {
        HashMap<Integer, Integer> num2Times = new HashMap<>();
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int num : nums) {
            Integer times = num2Times.getOrDefault(num, 0);
            times++;
            num2Times.put(num, times);
            max = Integer.max(max, times);
            min = Integer.min(min, times);
        }
        int len = max - min + 1;
        ArrayList<Integer>[] bucket = new ArrayList[len];
        for (Map.Entry<Integer, Integer> entry : num2Times.entrySet()) {
            int index = entry.getValue() - min;
            if (bucket[index] == null) {
                bucket[index] = new ArrayList<>(k);
            }
            if (bucket[index].size() < k) {
                bucket[index].add(entry.getKey());
            }
        }
        int count = 0;
        int[] res = new int[k];
        for (int i = len - 1; i >= 0 && count < k; i--) {
            if (bucket[i] == null) {
                continue;
            }
            for (int j = 0; j < bucket[i].size() && count < k; j++) {
                res[count++] = bucket[i].get(j);
            }
        }
        return res;
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


