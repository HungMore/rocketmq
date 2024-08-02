package org.apache.rocketmq.example.quickstart;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author mo
 * @Description 生产者测试
 * @createTime 2024年05月14日 10:07
 */
public class ProducerAlgArrayTest {

    public static void main(String[] args) throws Exception {
        ProducerAlgArrayTest test = new ProducerAlgArrayTest();

//        System.out.println(producerAlgTest.threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
//        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
//        producer.setNamesrvAddr("127.0.0.1:9876");
//        producer.start();
//        Message message2 = new Message("order_topic",
//                null,
//                "key1",
//                JSON.toJSONString("").getBytes(StandardCharsets.UTF_8));
//        producer.send(message2, new SelectMessageQueueByHash(), "12");

//        String res = test.convert("PAYPALISHIRING", 3);
//        System.out.println(res);
//        System.out.println(res.equals("PAHNAPLSIIGYIR"));

        System.out.println(test.getGoodIndices(new int[][]{{2, 3, 3, 10}, {3, 3, 3, 1}, {6, 1, 1, 4}}, 2));
        System.out.println(test.getGoodIndices(new int[][]{{39, 3, 1000, 1000}}, 17));
        System.out.println(test.getGoodIndices(new int[][]{{1, 2, 3, 1}}, 0));
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] merge = new int[nums1.length + nums2.length];
        int index = 0, cursor1 = 0, cursor2 = 0;
        while (cursor1 < nums1.length && cursor2 < nums2.length) {
            if (nums1[cursor1] <= nums2[cursor2]) {
                merge[index++] = nums1[cursor1++];
            } else {
                merge[index++] = nums2[cursor2++];
            }
        }
        while (cursor1 < nums1.length) {
            merge[index++] = nums1[cursor1++];
        }
        while (cursor2 < nums2.length) {
            merge[index++] = nums2[cursor2++];
        }
        if (merge.length % 2 == 0) {
            int mid = merge.length / 2;
            return ((double) (merge[mid] + merge[mid - 1])) / 2;
        } else {
            return merge[merge.length / 2];
        }
    }

    public String convert(String s, int numRows) {
        List<Character>[] array = new List[numRows];
        for (int i = 0; i < numRows; i++) {
            array[i] = new LinkedList<>();
        }
        int index = 0;
        while (index < s.length()) {
            for (int i = 0; i < numRows && index < s.length(); i++, index++) {
                array[i].add(s.charAt(index));
            }
            // 这里需要注意，往上走的时候，从倒数第二个走到第二个就可以了。然后第二轮就从第一个开始走
            for (int i = numRows - 2; i >= 1 && index < s.length(); i--, index++) {
                array[i].add(s.charAt(index));
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numRows; i++) {
            for (Character character : array[i]) {
                sb.append(character);
            }
        }
        return sb.toString();
    }

    public List<Integer> getGoodIndices(int[][] variables, int target) {
        List<Integer> res = new LinkedList<>();
        int index = 0;
        for (int[] variable : variables) {
            int cal = 1;
            for (int i = 0; i < variable[1]; i++) {
                cal = cal * variable[0];
                if (cal >= 10) {
                    cal = cal % 10;
                }
            }
            int base = cal;
            cal = 1;
            for (int i = 0; i < variable[2]; i++) {
                cal = cal * base;
                if (cal >= variable[3]) {
                    cal = cal % variable[3];
                }
            }
            if (cal == target) {
                res.add(index);
            }
            index++;
        }
        return res;
    }

    public int minRectanglesToCoverPoints(int[][] points, int w) {
        Arrays.sort(points, Comparator.comparingInt(p -> p[0]));
        int res = 1;
        int start = points[0][0];
        for (int i = 1; i < points.length; i++) {
            if (points[i][0] > start + w) {
                start = points[i][0];
                res++;
            }
        }
        return res;
    }

    public long numberOfRightTriangles(int[][] grid) {
        // 记录每行、每列具有的1的个数
        int[] rowCount = new int[grid.length];
        int[] colCount = new int[grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    rowCount[i]++;
                    colCount[j]++;
                }
            }
        }
        long res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    res += (long) (rowCount[i] - 1) * (colCount[j] - 1);
                }
            }
        }
        return res;
    }


}
