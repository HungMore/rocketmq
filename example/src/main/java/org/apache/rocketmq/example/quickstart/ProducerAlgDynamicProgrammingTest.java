package org.apache.rocketmq.example.quickstart;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
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

//        System.out.println(test.integerBreak(10));
//        System.out.println(test.integerBreak(2));

//        System.out.println(test.canPartition(new int[]{1, 5, 11, 5}));

//        System.out.println(test.combinationSum4(new int[]{1, 2, 3}, 4));

        System.out.println(test.findMaxForm(new String[]{"10", "0", "1"}, 1, 1));
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

    public int robI(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int[] dp = new int[nums.length + 1];
        dp[0] = 0;
        dp[1] = nums[0];
        for (int i = 2; i <= nums.length; i++) {
            dp[i] = Math.max(nums[i - 1] + dp[i - 2], dp[i - 1]);
        }
        return dp[nums.length];
    }

    public int robII(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }

        return Math.max(robI(Arrays.copyOf(nums, nums.length - 1)), robI(Arrays.copyOfRange(nums, 1, nums.length)));
    }

    // memo1保存canRobRoot为true的子问题的解
    private Map<TreeNode, Integer> memo1;
    // memo2保存canRobRoot为false的子问题的解
    private Map<TreeNode, Integer> memo2;

    public int rob(TreeNode root) {
        memo1 = new HashMap<>();
        memo2 = new HashMap<>();
        return robHelper(root, true);
    }

    private int robHelper(TreeNode root, boolean canRobRoot) {
        if (root == null) {
            return 0;
        }
        Integer res;
        if (canRobRoot) {
            res = memo1.get(root);
            if (res == null) {
                res = Math.max(root.val + robHelper(root.left, false) + robHelper(root.right, false),
                        robHelper(root.left, true) + robHelper(root.right, true));
            }
            memo1.put(root, res);
        } else {
            res = memo2.get(root);
            if (res == null) {
                res = robHelper(root.left, true) + robHelper(root.right, true);
            }
            memo2.put(root, res);
        }
        return res;
    }

    public int maxProfit(int[] prices) {
        int[] dp = new int[prices.length + 1];
        dp[0] = 0;
        dp[1] = 0;
        for (int i = 2; i <= prices.length; i++) {
            int res = dp[i - 1];
            for (int j = 0; j < i - 1; j++) {
                res = Math.max(res, prices[i - 1] - prices[j] + (j >= 1 ? dp[j - 1] : 0));
            }
            dp[i] = res;
        }
        return dp[prices.length];
    }

    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum % 2 != 0) {
            return false;
        }
        int half = sum / 2;
        boolean[][] dp = new boolean[nums.length + 1][half + 1];
        for (int i = 0; i <= nums.length; i++) {
            dp[i][0] = true;
        }
        for (int j = 1; j <= half; j++) {
            dp[0][j] = false;
        }
        for (int i = 1; i <= nums.length; i++) {
            for (int j = 1; j <= half; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j >= nums[i - 1]) {
                    dp[i][j] = dp[i][j] || dp[i - 1][j - nums[i - 1]];
                }
            }
        }
        return dp[nums.length][half];
    }

    public int coinChange(int[] coins, int amount) {
        int[][] dp = new int[coins.length + 1][amount + 1];
        for (int i = 0; i <= coins.length; i++) {
            dp[i][0] = 0;
        }
        for (int j = 1; j <= amount; j++) {
            dp[0][j] = -1;
        }
        for (int i = 1; i <= coins.length; i++) {
            for (int j = 1; j <= amount; j++) {
                int num1 = dp[i - 1][j];
                int num2;
                if (coins[i - 1] > j || dp[i][j - coins[i - 1]] == -1) {
                    num2 = -1;
                } else {
                    num2 = dp[i][j - coins[i - 1]] + 1;
                }
                if (num1 != -1 && num2 != -1) {
                    dp[i][j] = Math.min(num1, num2);
                } else {
                    dp[i][j] = num1 == -1 ? num2 : num1;
                }
            }
        }
        return dp[coins.length][amount] == Integer.MAX_VALUE ? -1 : dp[coins.length][amount];
    }

    public int combinationSum4(int[] nums, int target) {
        int[][] dp = new int[nums.length + 1][target + 1];
        for (int i = 0; i <= nums.length; i++) {
            dp[i][0] = 1;
        }
        for (int j = 1; j <= target; j++) {
            dp[0][j] = 0;
        }
        for (int i = 1; i <= nums.length; i++) {
            for (int j = 1; j <= target; j++) {
                int res = 0;
                for (int k = 0; k < i; k++) {
                    if (nums[k] <= j) {
                        res += dp[i][j - nums[k]];
                    }
                }
                dp[i][j] = res;
            }
        }
        return dp[nums.length][target];
    }

    public int findMaxForm(String[] strs, int m, int n) {
        int[][][] dp = new int[strs.length + 1][m + 1][n + 1];
        for (int i = 1; i <= strs.length; i++) {
            int[] numberOfZeroAndOne = getNumberOfZeroAndOne(strs[i - 1]);
            for (int j = 0; j <= m; j++) {
                for (int k = 0; k <= n; k++) {
                    if (j >= numberOfZeroAndOne[0] && k >= numberOfZeroAndOne[1]) {
                        dp[i][j][k] = Math.max(dp[i - 1][j][k], 1 + dp[i - 1][j - numberOfZeroAndOne[0]][k - numberOfZeroAndOne[1]]);
                    } else {
                        dp[i][j][k] = dp[i - 1][j][k];
                    }
//                    System.out.println(String.format("strs的前 %d 个元素构成的最多 %d 个1、 %d 个0的最大子集长度是 %d", i, j, k, dp[i][j][k]));
                }
            }
        }
        return dp[strs.length][m][n];
    }

    private int[] getNumberOfZeroAndOne(String s) {
        int[] res = new int[2];
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '0') {
                res[0]++;
            } else {
                res[1]++;
            }
        }
        return res;
    }

    private Map<String, Boolean> memoForWordBreak = new HashMap<>();

    public boolean wordBreak(String s, List<String> wordDict) {
        if (s.length() == 0) {
            return true;
        }
        Boolean aBoolean = memoForWordBreak.get(s);
        if (aBoolean == null) {
            boolean res = false;
            for (String word : wordDict) {
                if (s.startsWith(word)) {
                    boolean recursion = wordBreak(s.substring(word.length()), wordDict);
                    if (recursion) {
                        res = true;
                        break;
                    }
                }
            }
            aBoolean = res;
            memoForWordBreak.put(s, aBoolean);
        }
        return aBoolean;
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
