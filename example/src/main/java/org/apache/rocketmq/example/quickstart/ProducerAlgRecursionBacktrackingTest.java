package org.apache.rocketmq.example.quickstart;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author mo
 * @Description 生产者测试
 * @createTime 2024年06月03日 09:58
 */
@Slf4j
public class ProducerAlgRecursionBacktrackingTest {

    public static void main(String[] args) throws Exception {
        ProducerAlgRecursionBacktrackingTest test = new ProducerAlgRecursionBacktrackingTest();
        System.out.println();
//        List<String> stringList = test.restoreIpAddresses("101023");
//        System.out.println(stringList);

//        long t1 = 0, t2 = 0, t3 = 0;
//        for (int i = 0; i < 100; i++) {
//            String s = RandomStringUtils.randomAscii(200);
//            long l1 = System.currentTimeMillis();
//            test.partition(s);
//            long l2 = System.currentTimeMillis();
//            test.partitionDP(s);
//            long l3 = System.currentTimeMillis();
//            test.partitionDPString(s);
//            long l4 = System.currentTimeMillis();
//            t1 += l2 - l1;
//            t2 += l3 - l2;
//            t3 += l4 - l3;
//        }
//        System.out.println(t1);
//        System.out.println(t2);
//        System.out.println(t3);

//        System.out.println(test.combine(4, 2));

//        System.out.println(test.combinationSum(new int[]{2, 3, 6, 7}, 7));

        System.out.println(test.combinationSum2(new int[]{1, 1, 1, 3, 3, 5}, 8));

//        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
//        producer.setNamesrvAddr("127.0.0.1:9876");
//        producer.start();
//        Message message2 = new Message("order_topic",
//                null,
//                "key1",
//                JSON.toJSONString("").getBytes(StandardCharsets.UTF_8));
//        producer.send(message2, new SelectMessageQueueByHash(), "12");


    }

    public List<String> letterCombinations(String digits) {
        LinkedList<String> res = new LinkedList<>();
        combinations(digits, 0, new StringBuilder(), res);
        return res;
    }

    private void combinations(String digits, int digitIndex, StringBuilder pre, List<String> res) {
        if (digitIndex == digits.length()) {
            if (pre.length() != 0) {
                res.add(pre.toString());
            }
            return;
        }
        List<Character> myLetters = getMyLetters(digits.charAt(digitIndex));
        for (Character myLetter : myLetters) {
            // 拼接上字母
            pre.append(myLetter);
            combinations(digits, digitIndex + 1, pre, res);
            // 回溯的关键：去掉拼接的字母
            pre.deleteCharAt(digitIndex);
        }
    }

    private List<Character> getMyLetters(char digit) {
        if (digit == '2') {
            return Arrays.asList('a', 'b', 'c');
        } else if (digit == '3') {
            return Arrays.asList('d', 'e', 'f');
        } else if (digit == '4') {
            return Arrays.asList('g', 'h', 'i');
        } else if (digit == '5') {
            return Arrays.asList('j', 'k', 'l');
        } else if (digit == '6') {
            return Arrays.asList('m', 'n', 'o');
        } else if (digit == '7') {
            return Arrays.asList('p', 'q', 'r', 's');
        } else if (digit == '8') {
            return Arrays.asList('t', 'u', 'v');
        } else if (digit == '9') {
            return Arrays.asList('w', 'x', 'y', 'z');
        } else {
            throw new RuntimeException("unsupported digit:" + digit);
        }
    }

    public List<String> restoreIpAddresses(String s) {
        LinkedList<String> res = new LinkedList<>();
        ip(s, 0, 0, "", res);
        return res;
    }

    private void ip(String s, int index, int segmentNumber, String pre, List<String> res) {
        if (index == s.length() || segmentNumber == 4) {
            if (index == s.length() && segmentNumber == 4) {
                res.add(pre.substring(0, pre.length() - 1));
            }
            return;
        }
        for (int i = 1, endIndex; i < 4 && (endIndex = index + i) <= s.length(); i++) {
            String segment = s.substring(index, endIndex);
            if (isValidSegment(segment)) {
                ip(s, endIndex, segmentNumber + 1, pre + segment + ".", res);
            } else {
                // 提前剪枝
                break;
            }
        }
    }

    private boolean isValidSegment(String segment) {
        if (segment.startsWith("0")) {
            return "0".equals(segment);
        } else {
            int i = Integer.parseInt(segment);
            return i >= 0 && i <= 255;
        }
    }

    public List<List<String>> partition(String s) {
        List<List<String>> res = new LinkedList<>();
        partitionHelper(s, 0, new LinkedList<>(), res);
        return res;
    }

    private void partitionHelper(String s, int index, LinkedList<String> pre, List<List<String>> res) {
        if (index == s.length()) {
            res.add(new ArrayList<>(pre));
            return;
        }
        for (int i = index + 1; i <= s.length(); i++) {
            String substring = s.substring(index, i);
            if (isPalindrome(substring)) {
                pre.addLast(substring);
                partitionHelper(s, i, pre, res);
                pre.removeLast();
            }
        }
    }

    private boolean isPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }
        int i = 0, j = s.length() - 1;
        while (i < j) {
            if (s.charAt(i) == s.charAt(j)) {
                i++;
                j--;
            } else {
                return false;
            }
        }
        return true;
    }

    public List<List<String>> partitionDP(String s) {
        if (s == null || s.length() == 0) {
            return Collections.emptyList();
        }
        List<List<String>>[] dp = new List[s.length() + 1];
        dp[0] = Collections.singletonList(Collections.emptyList());
        dp[1] = Collections.singletonList(Collections.singletonList(s.substring(0, 1)));
        for (int i = 2; i <= s.length(); i++) {
            dp[i] = new LinkedList<>();
            for (int j = 0; j <= i - 1; j++) {
                String substring = s.substring(j, i);
                if (isPalindrome(substring)) {
                    List<List<String>> pre = dp[j];
                    for (List<String> list : pre) {
                        List<String> clone = new ArrayList<>(list);
                        clone.add(substring);
                        dp[i].add(clone);
                    }
                }
            }
        }
        return dp[s.length()];
    }

    public List<List<String>> partitionDPString(String s) {
        if (s == null || s.length() == 0) {
            return Collections.emptyList();
        }
        List<String>[] dp = new List[s.length() + 1];
        dp[0] = Collections.singletonList("");
        dp[1] = Collections.singletonList(s.substring(0, 1));
        for (int i = 2; i <= s.length(); i++) {
            dp[i] = new LinkedList<>();
            for (int j = 0; j <= i - 1; j++) {
                String substring = s.substring(j, i);
                if (isPalindrome(substring)) {
                    List<String> pre = dp[j];
                    for (String anAnswer : pre) {
                        dp[i].add(anAnswer + "," + substring);
                    }
                }
            }
        }
        List<List<String>> res = new ArrayList<>(dp[s.length()].size());
        for (String anAnswer : dp[s.length()]) {
            if (anAnswer.startsWith(",")) {
                res.add(Arrays.asList(anAnswer.substring(1).split(",")));
            } else {
                res.add(Arrays.asList(anAnswer.split(",")));
            }
        }
        return res;
    }

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new LinkedList<>();
        Queue<Integer> numQueue = new LinkedList<>();
        for (int num : nums) {
            numQueue.add(num);
        }
        dfs(numQueue, new LinkedList<>(), res);
        return res;
    }

    private void dfs(Queue<Integer> numQueue, LinkedList<Integer> pre, List<List<Integer>> res) {
        if (numQueue.isEmpty()) {
            res.add(new ArrayList<>(pre));
            return;
        }
        for (int i = 0; i < numQueue.size(); i++) {
            Integer remove = numQueue.remove();
            pre.addLast(remove);
            dfs(numQueue, pre, res);
            pre.removeLast();
            numQueue.add(remove);
        }
    }

    public List<List<Integer>> permuteUnique(int[] nums) {
        // 避免排序带来的副作用，copy一份数组
        int[] copyOf = Arrays.copyOf(nums, nums.length);
        Arrays.sort(copyOf);
        LinkedList<Integer> numList = new LinkedList<>();
        for (int i : copyOf) {
            numList.addLast(i);
        }
        List<List<Integer>> res = new LinkedList<>();
        dfsUnique(numList, new LinkedList<>(), res);
        return res;
    }

    private void dfsUnique(LinkedList<Integer> numList, LinkedList<Integer> pre, List<List<Integer>> res) {
        if (numList.isEmpty()) {
            res.add(new ArrayList<>(pre));
            return;
        }
        int size = numList.size();
        for (int i = 0; i < size; i++) {
            Integer removeFirst = numList.removeFirst();
            if (i == 0 || !removeFirst.equals(numList.peekLast())) {
                pre.addLast(removeFirst);
                dfsUnique(numList, pre, res);
                pre.removeLast();
            }
            numList.addLast(removeFirst);
        }
    }

    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new LinkedList<>();
        if (n >= k) {
            combineHelper(n, k, new LinkedList<>(), res);
        }
        return res;
    }

    private void combineHelper(int n, int k, LinkedList<Integer> pre, List<List<Integer>> res) {
        if (k == 0) {
            res.add(new ArrayList<>(pre));
            return;
        }
        pre.addLast(n);
        combineHelper(n - 1, k - 1, pre, res);
        pre.removeLast();
        if (n > k) {
            combineHelper(n - 1, k, pre, res);
        }
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new LinkedList<>();
        combinationSumHelper(candidates, 0, target, new LinkedList<>(), res);
        return res;
    }

    private void combinationSumHelper(int[] candidates, int startIndex, int target, LinkedList<Integer> pre, List<List<Integer>> res) {
        if (target < 0) {
            return;
        }
        if (target == 0) {
            res.add(new ArrayList<>(pre));
            return;
        }
        for (int i = startIndex; i < candidates.length; i++) {
            pre.addLast(candidates[i]);
            combinationSumHelper(candidates, i, target - candidates[i], pre, res);
            pre.removeLast();
        }
    }

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        // 为避免函数副作用，使用拷贝去排序
        int[] copyOf = Arrays.copyOf(candidates, candidates.length);
        Arrays.sort(copyOf);
        List<List<Integer>> res = new LinkedList<>();
        combinationSum2Helper(copyOf, 0, target, new LinkedList<>(), res);
        return res;
    }

    private void combinationSum2Helper(int[] candidates, int startIndex, int target, LinkedList<Integer> pre, List<List<Integer>> res) {
        if (target == 0) {
            res.add(new ArrayList<>(pre));
            return;
        }
        /// 这一句可以去掉，下面的for循环会判断 i < candidates.length
//        if (startIndex == candidates.length) {
//            return;
//        }
        for (int i = startIndex; i < candidates.length && candidates[i] <= target; i++) {
            // 包含相同元素的已经遍历过，跳过
            if (i != startIndex && candidates[i] == candidates[i - 1]) {
                continue;
            }
            pre.addLast(candidates[i]);
            combinationSum2Helper(candidates, i + 1, target - candidates[i], pre, res);
            pre.removeLast();
        }
    }

    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> res = new LinkedList<>();
        combinationSum3Helper(k, n, 1, new LinkedList<>(), res);
        return res;
    }

    /**
     * 从 [start, 9]找k个数字组成n
     *
     * @param k
     * @param n
     * @param start
     * @param pre
     * @param res
     */
    private void combinationSum3Helper(int k, int n, int start, LinkedList<Integer> pre, List<List<Integer>> res) {
        if (k == 0) {
            if (n == 0) {
                res.add(new ArrayList<>(pre));
            }
            return;
        }
        for (int i = start; i < 10 && i <= n; i++) {
            pre.addLast(i);
            combinationSum3Helper(k - 1, n - i, i + 1, pre, res);
            pre.removeLast();
        }
    }

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new LinkedList<>();
        for (int i = 0; i <= nums.length; i++) {
            res.addAll(subsets(nums, i));
        }
        return res;
    }

    /**
     * C(n,k)
     *
     * @param nums
     * @param k
     * @return
     */
    private List<List<Integer>> subsets(int[] nums, int k) {
        List<List<Integer>> res = new LinkedList<>();
        subsetsHelper(nums, 0, k, new LinkedList<>(), res);
        return res;
    }

    private void subsetsHelper(int[] nums, int startIndex, int k, LinkedList<Integer> pre, List<List<Integer>> res) {
        if (k == 0) {
            res.add(new ArrayList<>(pre));
            return;
        }
        // 这个长度判断可以优化下
//        for (int i = startIndex; i < nums.length && nums.length - i >= k; i++) {
        for (int i = startIndex; i <= nums.length - k; i++) {
            pre.addLast(nums[i]);
            subsetsHelper(nums, i + 1, k - 1, pre, res);
            pre.removeLast();
        }
    }

}
