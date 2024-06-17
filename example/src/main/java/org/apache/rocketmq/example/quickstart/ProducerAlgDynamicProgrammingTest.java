package org.apache.rocketmq.example.quickstart;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mo
 * @Description 生产者测试
 * @createTime 2024年06月03日 09:58
 */
@Slf4j
public class ProducerAlgDynamicProgrammingTest {

    public static void main(String[] args) throws Exception {
        ProducerAlgDynamicProgrammingTest test = new ProducerAlgDynamicProgrammingTest();
        System.out.println();

//        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
//        producer.setNamesrvAddr("127.0.0.1:9876");
//        producer.start();
//        Message message2 = new Message("order_topic",
//                null,
//                "key1",
//                JSON.toJSONString("").getBytes(StandardCharsets.UTF_8));
//        producer.send(message2, new SelectMessageQueueByHash(), "12");

//        test.fib(5);
//        System.out.println(test.count);
//
//        test.count = 0;
//        test.fibMemo(5);
//        System.out.println(test.count);

//        Integer[] ints0 = {2};
//        Integer[] ints1 = {3, 4};
//        Integer[] ints2 = {6, 5, 7};
//        Integer[] ints3 = {4, 1, 8, 3};
//        LinkedList<List<Integer>> list = new LinkedList<>();
//        list.add(Arrays.asList(ints0));
//        list.add(Arrays.asList(ints1));
//        list.add(Arrays.asList(ints2));
//        list.add(Arrays.asList(ints3));
//        System.out.println(test.minimumTotal(list));

        System.out.println(test.integerBreak(10));
        System.out.println(test.integerBreak(2));
    }

    // 计算执行fib函数的次数
    int count = 0;

    public int fib(int n) {
        count++;
        if (n == 0 || n == 1) {
            return n;
        }
        return fib(n - 1) + fib(n - 2);
    }

    Map<Integer, Integer> memo = new HashMap<>();

    public int fibMemo(int n) {
        count++;
        if (n == 0 || n == 1) {
            return n;
        }
        Integer res = memo.get(n);
        if (res == null) {
            res = fibMemo(n - 1) + fibMemo(n - 2);
            memo.put(n, res);
        }
        return res;
    }

    public int climbStairs(int n) {
        int[] dp = new int[Math.max(n + 1, 3)];
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    public int minimumTotal(List<List<Integer>> triangle) {
        int row = 0;
        int[][] dp = new int[triangle.size()][triangle.size()];
        for (List<Integer> rowList : triangle) {
            int col = 0;
            for (Integer i : rowList) {
                if (col == 0) {
                    if (row == 0) {
                        dp[row][col] = i;
                    } else {
                        dp[row][col] = dp[row - 1][col] + i;
                    }
                } else if (col == row) {
                    dp[row][col] = i + dp[row - 1][col - 1];
                } else {
                    dp[row][col] = i + Math.min(dp[row - 1][col], dp[row - 1][col - 1]);
                }
                col++;
            }
            row++;
        }
        int res = Integer.MAX_VALUE;
        for (int temp : dp[dp.length - 1]) {
            res = Math.min(res, temp);
        }
        return res;
    }

    public int minPathSum(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];
        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }
        return dp[m - 1][n - 1];
    }

    public int integerBreak(int n) {
        int[] dp = new int[n + 1];
        for (int i = 2; i <= n; i++) {
            int res = Integer.MIN_VALUE;
            for (int j = 1; j < i; j++) {
                int temp = Math.max(j * (i - j), j * dp[i - j]);
                res = Math.max(temp, res);
            }
            dp[i] = res;
        }
        return dp[n];
    }

    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int res = Integer.MAX_VALUE;
            for (int j = 1; j * j <= i; j++) {
                res = Math.min(res, 1 + dp[i - j * j]);
            }
            dp[i] = res;
        }
        return dp[n];
    }

    public int numDecodings(String s) {
        int[] dp = new int[s.length() + 1];
        dp[0] = 1;
        dp[1] = s.charAt(0) == '0' ? 0 : 1;
        for (int i = 2; i <= s.length(); i++) {
            dp[i] = (s.charAt(i - 1) == '0' ? 0 : dp[i - 1]) + (s.substring(i - 2, i).compareTo("10") >= 0 && s.substring(i - 2, i).compareTo("26") <= 0 ? dp[i - 2] : 0);
        }
        return dp[s.length()];
    }

    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int j = 0; j < n; j++) {
            dp[0][j] = 1;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length, n = obstacleGrid[0].length;
        int[][] dp = new int[m][n];
        dp[0][0] = obstacleGrid[0][0] == 1 ? 0 : 1;
        for (int i = 1; i < m; i++) {
            dp[i][0] = obstacleGrid[i][0] == 1 ? 0 : dp[i - 1][0];
        }
        for (int j = 1; j < n; j++) {
            dp[0][j] = obstacleGrid[0][j] == 1 ? 0 : dp[0][j - 1];
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = obstacleGrid[i][j] == 1 ? 0 : dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }

}
