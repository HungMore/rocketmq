package org.apache.rocketmq.example.quickstart;

import java.util.Arrays;

/**
 * @author mo
 * @Description 生产者测试
 * @createTime 2024年05月14日 10:07
 */
public class ProducerAlgGreedyAlgorithmTest {

    public static void main(String[] args) throws Exception {
        ProducerAlgGreedyAlgorithmTest test = new ProducerAlgGreedyAlgorithmTest();


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

    public int findContentChildren(int[] g, int[] s) {
        int[] copyOfG = Arrays.copyOf(g, g.length);
        int[] copyOfS = Arrays.copyOf(s, s.length);
        Arrays.sort(copyOfG);
        Arrays.sort(copyOfS);
        int gCursor = copyOfG.length - 1, sCursor = copyOfS.length - 1;
        int res = 0;
        while (gCursor >= 0 && sCursor >= 0) {
            if (copyOfG[gCursor] <= copyOfS[sCursor]) {
                res++;
                sCursor--;
            }
            gCursor--;
        }
        return res;
    }

    public boolean isSubsequence(String s, String t) {
        int i = s.length() - 1, j = t.length() - 1;
        while (i >= 0 && j >= 0) {
            if (s.charAt(i) == t.charAt(j)) {
                i--;
                j--;
            } else {
                j--;
            }
        }
        return i == -1;
    }


}
