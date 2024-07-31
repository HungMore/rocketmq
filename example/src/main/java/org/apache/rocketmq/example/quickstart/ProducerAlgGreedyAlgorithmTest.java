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
        int[][] a = {
                {1, 2}, {2, 3}, {3, 4}, {1, 3}
        };
        System.out.println(test.eraseOverlapIntervals(a));
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

    public int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, (interval1, interval2) -> interval1[0] == interval2[0] ? interval1[1] - interval2[1] : interval1[0] - interval2[0]);
        int res = 0;
        int i = 0, j = i + 1;
        while (j < intervals.length) {
            // 有交集
            if (intervals[j][0] < intervals[i][1]) {
                res++;
                if (intervals[j][1] <= intervals[i][1]) {
                    i = j;
                    j = i + 1;
                } else {
                    j++;
                }
                // 没有交集
            } else {
                i = j;
                j = i + 1;
            }
        }
        return res;
    }

    public String getSmallestString(String s, int k) {
        char[] charArray = s.toCharArray();
        for (int i = 0; i < charArray.length && k > 0; i++) {
            int positiveDistance = 26 - charArray[i] + 'a';
            int negativeDistance = charArray[i] - 'a';
            int minDistance = Math.min(positiveDistance, negativeDistance);
            if (minDistance <= k) {
                // 剩余步数可以走到’a‘
                charArray[i] = 'a';
                k -= minDistance;
            } else {
                // 剩余步数走不到’a‘,必然只能反向操作。正向操作只会往'z'靠拢
                charArray[i] = (char) (charArray[i] - k);
                k = 0;
            }
        }
        return new String(charArray);
    }


}
