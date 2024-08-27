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
//        int[][] a = {
//                {1, 2}, {2, 3}, {3, 4}, {1, 3}
//        };
//        System.out.println(test.eraseOverlapIntervals(a));
        System.out.println(test.maxmiumScore(new int[]{1, 2, 8, 9}, 3));
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

    /**
     * LCP 40: 心算挑战
     * <p>
     * 将 cards 从小到大排序后，先贪心的将后 cnt 个数字加起来，若此时 sum 为偶数，直接返回即可。
     * 若此时答案为奇数，有两种方案:
     * 1. 在数组前面找到一个最大的奇数与后 cnt 个数中最小的偶数进行替换；
     * 2. 在数组前面找到一个最大的偶数与后 cnt 个数中最小的奇数进行替换。
     * 两种方案选最大值即可。
     *
     * @param cards
     * @param cnt
     * @return
     */
    public int maxmiumScore(int[] cards, int cnt) {
        // 使用拷贝来排序，避免函数副作用
        int[] copy = Arrays.copyOf(cards, cards.length);
        Arrays.sort(copy);
        cards = copy;
        int res = 0;
        // 最小的奇数和最小的偶数
        int minOdd = -1, minEven = -1;
        for (int i = cards.length - 1; i >= cards.length - cnt; i--) {
            res += cards[i];
            if (cards[i] % 2 == 0) {
                minEven = cards[i];
            } else {
                minOdd = cards[i];
            }
        }
        if (res % 2 == 0) {
            return res;
        }
        // 剩余数字中的最大奇数和最大偶数
        int maxOdd = -1, maxEven = -1;
        for (int i = cards.length - cnt - 1; i >= 0; i--) {
            if (cards[i] % 2 == 0) {
                if (maxEven == -1) {
                    maxEven = cards[i];
                }
            } else {
                if (maxOdd == -1) {
                    maxOdd = cards[i];
                }
            }
            if (maxEven != -1 && maxOdd != -1) {
                break;
            }
        }
        int temp1 = -1;
        if (minOdd != -1 && maxEven != -1) {
            temp1 = res - minOdd + maxEven;
        }
        int temp2 = -1;
        if (minEven != -1 && maxOdd != -1) {
            temp2 = res - minEven + maxOdd;
        }
        if (temp1 == -1 && temp2 == -1) {
            return 0;
        }
        return Math.max(temp1, temp2);
    }

    public String largestNumber(int[] nums) {
        StringBuilder res = new StringBuilder();
        String[] stringArray = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            stringArray[i] = String.valueOf(nums[i]);
        }
        // 降序排序
        Arrays.sort(stringArray, (s1, s2) -> {
            String s1Start = s1 + s2;
            String s2Start = s2 + s1;
            return s2Start.compareTo(s1Start);
        });
        for (String s : stringArray) {
            res.append(s);
        }
        int i = 0;
        while (i < res.length() && res.charAt(i) == '0') {
            i++;
        }
        return i == res.length() ? "0" : res.substring(i);
    }


}
