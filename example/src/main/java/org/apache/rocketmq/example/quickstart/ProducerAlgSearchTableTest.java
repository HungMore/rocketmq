package org.apache.rocketmq.example.quickstart;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author mo
 * @Description 生产者测试
 * @createTime 2024年04月23日 17:25
 */
public class ProducerAlgSearchTableTest {

    public static void main(String[] args) throws Exception {
        ProducerAlgSearchTableTest producerAlgSearchTableTest = new ProducerAlgSearchTableTest();
//        System.out.println(producerAlgSearchTableTest.containsNearbyAlmostDuplicate4(new int[]{-3, 3}, 2, 4));
//        System.out.println(-3 / 4);
//        System.out.println(-3.0 / 4);
//        System.out.println(Math.floor(-3.0 / 4));
//        System.out.println((int) Math.floor(-3.0 / 4));
//        System.out.println((int) (-3.0 / 4));
//        System.out.println(producerAlgTest.threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
//        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
//        producer.setNamesrvAddr("127.0.0.1:9876");
//        producer.start();
//        Message message2 = new Message("order_topic",
//                null,
//                "key1",
//                JSON.toJSONString("").getBytes(StandardCharsets.UTF_8));
//        producer.send(message2, new SelectMessageQueueByHash(), "12");
        System.out.println(producerAlgSearchTableTest.minimumOperationsWithDP("2245047"));
        System.out.println(producerAlgSearchTableTest.minimumOperationsWithDP("2908305"));
        System.out.println(producerAlgSearchTableTest.minimumOperationsWithDP("10"));
        System.out.println(producerAlgSearchTableTest.minimumOperationsWithDP("275"));
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

    public boolean isHappy(int n) {
        Set<Long> longSet = new HashSet<>();
        long temp = n;
        longSet.add(temp);
        while (true) {
            temp = transform(temp);
            if (temp == 1) {
                return true;
            }
            if (!longSet.add(temp)) {
                return false;
            }
        }
    }

    private long transform(long n) {
        String longString = Long.toString(n);
        long res = 0;
        for (int i = 0; i < longString.length(); i++) {
            res += (int) Math.pow((longString.charAt(i) - '0'), 2);
        }
        return res;
    }

    public boolean wordPattern(String pattern, String s) {
        String[] split = s.split(" ");
        if (split.length != pattern.length()) {
            return false;
        }
        Map<Character, String> map1 = new HashMap<>();
        Map<String, Character> map2 = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            String word = map1.get(pattern.charAt(i));
            if (word == null) {
                Character character = map2.get(split[i]);
                if (character != null) {
                    return false;
                } else {
                    map1.put(pattern.charAt(i), split[i]);
                    map2.put(split[i], pattern.charAt(i));
                }
            } else if (!word.equals(split[i])) {
                return false;
            }
        }
        return true;
    }

    public boolean isIsomorphic(String s, String t) {
        Map<Character, Character> map1 = new HashMap<>();
        Map<Character, Character> map2 = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            Character s2tChar = map1.get(s.charAt(i));
            if (s2tChar == null) {
                Character t2sChar = map2.get(t.charAt(i));
                if (t2sChar != null) {
                    return false;
                } else {
                    map1.put(s.charAt(i), t.charAt(i));
                    map2.put(t.charAt(i), s.charAt(i));
                }
            } else if (t.charAt(i) != s2tChar) {
                return false;
            }
        }
        return true;
    }

    public String frequencySort(String s) {
        HashMap<Character, Integer> char2Times = new HashMap<>();
        TreeSet<Pair> treeSet = new TreeSet<>();
        for (int i = 0; i < s.length(); i++) {
            char2Times.merge(s.charAt(i), 1, Integer::sum);
        }
        for (Map.Entry<Character, Integer> entry : char2Times.entrySet()) {
            treeSet.add(new Pair(entry.getKey(), entry.getValue()));
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Pair pair : treeSet) {
            for (int i = 0; i < pair.times; i++) {
                stringBuilder.append(pair.c);
            }
        }
        return stringBuilder.toString();
    }

    public static class Pair implements Comparable<Pair> {
        char c;
        int times;

        @Override
        public int compareTo(Pair p) {
            int gap = p.times - this.times;
            if (gap == 0) {
                return this.c - p.c;
            }
            return gap;
        }

        public Pair(char c, int times) {
            this.c = c;
            this.times = times;
        }
    }

    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> value2Index = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            Integer index = value2Index.get(target - nums[i]);
            if (index != null) {
                return new int[]{i, index};
            }
            value2Index.put(nums[i], i);
        }
        throw new RuntimeException("no answer: " + Arrays.toString(nums) + ", " + target);
    }

    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new LinkedList<>();
        for (int i = 0; i < nums.length; i++) {
            // 小小优化点，nums[i] > 0，后面肯定无解了
            if (nums[i] > 0) {
                break;
            }
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int j = i + 1, k = nums.length - 1;
            while (j < k) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    j++;
                    continue;
                }
                if (k < nums.length - 1 && nums[k] == nums[k + 1]) {
                    k--;
                    continue;
                }
                int sum = -nums[j] - nums[k];
                if (sum == nums[i]) {
                    ArrayList<Integer> integers = new ArrayList<>(3);
                    integers.add(nums[i]);
                    integers.add(nums[j]);
                    integers.add(nums[k]);
                    res.add(integers);
                    j++;
                    k--;
                } else if (sum < nums[i]) {
                    k--;
                } else {
                    j++;
                }
            }
        }
        return res;
    }

    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> res = new LinkedList<>();
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            for (int j = i + 1; j < nums.length; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                long sum = (long) nums[i] + nums[j];
                int k = j + 1, l = nums.length - 1;
                while (k < l) {
                    if (k > j + 1 && nums[k] == nums[k - 1]) {
                        k++;
                        continue;
                    }
                    if (l < nums.length - 1 && nums[l] == nums[l + 1]) {
                        l--;
                        continue;
                    }
                    if (nums[k] + nums[l] + sum == target) {
                        ArrayList<Integer> integers = new ArrayList<>(4);
                        integers.add(nums[i]);
                        integers.add(nums[j]);
                        integers.add(nums[k]);
                        integers.add(nums[l]);
                        res.add(integers);
                        k++;
                        l--;
                    } else if (nums[k] + nums[l] + sum < target) {
                        k++;
                    } else {
                        l--;
                    }
                }
            }
        }
        return res;
    }

    public int threeSumClosest(int[] nums, int target) {
        long res = Integer.MAX_VALUE + target;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    long sum = (long) nums[i] + nums[j] + nums[k];
                    if (Math.abs(sum - target) < Math.abs(res - target)) {
                        res = sum;
                    }
                }
            }
        }
        return (int) res;
    }

    public int threeSumClosest2(int[] nums, int target) {
        Arrays.sort(nums);
        long res = Integer.MAX_VALUE + (long) target;
        for (int i = 0; i < nums.length; i++) {
            // 优化点，去重
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int j = i + 1, k = nums.length - 1;
            while (j < k) {
                // 这里要用if，不要用while，差点就用成while了。。。
                // 用while可能导致j越界的
                // 也是优化点，去重
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    j++;
                    continue;
                }
                if (k < nums.length - 1 && nums[k] == nums[k + 1]) {
                    k--;
                    continue;
                }
                long sum = (long) nums[i] + nums[j] + nums[k];
                if (Math.abs(sum - target) < Math.abs(res - target)) {
                    res = sum;
                }
                // 优化点，等于target一定是最接近的，无需再判断了
                if (sum == target) {
                    return (int) res;
                } else if (sum > target) {
                    k--;
                } else {
                    j++;
                }
            }
        }
        return (int) res;
    }

    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        int res = 0;
        HashMap<Long, List<List<Integer>>> map1 = buildMap(nums1, nums2);
        HashMap<Long, List<List<Integer>>> map2 = buildMap(nums3, nums4);
        for (Map.Entry<Long, List<List<Integer>>> entry : map1.entrySet()) {
            // 从map2中查找和为-entry.getKey()的二元组集合，然后让它们的集合长度相乘
            List<List<Integer>> sumList = map2.get(-entry.getKey());
            if (sumList != null) {

                res += sumList.size() * entry.getValue().size();
            }
        }
        return res;
    }

    private HashMap<Long, List<List<Integer>>> buildMap(int[] nums1, int[] nums2) {
        HashMap<Long, List<List<Integer>>> sum2TwoIndexUnitList = new HashMap<>();
        int n = nums1.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                long sum = (long) nums1[i] + nums2[j];
                List<List<Integer>> sumList = sum2TwoIndexUnitList.getOrDefault(sum, new LinkedList<>());
                ArrayList<Integer> integers = new ArrayList<>(2);
                integers.add(i);
                integers.add(j);
                sumList.add(integers);
                sum2TwoIndexUnitList.put(sum, sumList);
            }
        }
        return sum2TwoIndexUnitList;
    }

    public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            String key = serialize(str);
            List<String> stringList = map.getOrDefault(key, new LinkedList<>());
            stringList.add(str);
            map.put(key, stringList);
        }
        ArrayList<List<String>> res = new ArrayList<>(map.size());
        res.addAll(map.values());
        return res;
    }

    private String serialize(String str) {
        int[] temp = new int[26];
        for (int i = 0; i < str.length(); i++) {
            temp[str.charAt(i) - 'a']++;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            if (temp[i] != 0) {
                stringBuilder.append((char) (i + 'a')).append(temp[i]);
            }
        }
        return stringBuilder.toString();
    }

    public int numberOfBoomerangs(int[][] points) {
        int res = 0;
        for (int i = 0; i < points.length; i++) {
            // 偷个懒，其实我们不需要保存具体的点，只需要记录点的个数
            HashMap<Integer, Integer> distance2PointNumber = new HashMap<>();
            for (int j = 0; j < points.length; j++) {
                if (j == i) {
                    continue;
                }
                int distance = distance(points, i, j);
                distance2PointNumber.merge(distance, 1, Integer::sum);
            }
            for (Map.Entry<Integer, Integer> entry : distance2PointNumber.entrySet()) {
                if (entry.getValue() > 1) {
                    res += entry.getValue() * (entry.getValue() - 1);
                }
            }
        }
        return res;
    }

    /**
     * 计算points[i]和points[j]的距离的平方
     * 偷下懒，不需要计算开根，因为距离的平方相等那么距离肯定就相等
     *
     * @param points
     * @param i
     * @param j
     * @return
     */
    private int distance(int[][] points, int i, int j) {
        int[] pointsi = points[i];
        int[] pointsj = points[j];
        return (int) (Math.pow(pointsi[0] - pointsj[0], 2) + Math.pow(pointsi[1] - pointsj[1], 2));
    }

    public int maxPoints(int[][] points) {
        if (points == null || points.length == 0) {
            return 0;
        }
        if (points.length == 1) {
            return 1;
        }
        int res = 2;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                // 由points[i]、points[j]两点构成的直线上，一开始只有两个点
                int myNumber = 2;
                for (int k = j + 1; k < points.length; k++) {
                    if (isOnALine(points, i, j, k)) {
                        myNumber++;
                    }
                }
                if (myNumber > res) {
                    res = myNumber;
                }
            }
        }
        return res;
    }

    /**
     * 判断i\j\k三点是否在一条直线上
     *
     * @param points
     * @param i
     * @param j
     * @param k
     * @return
     */
    private boolean isOnALine(int[][] points, int i, int j, int k) {
        int[] pointi = points[i];
        int[] pointj = points[j];
        int[] pointk = points[k];
        return (pointk[1] - pointj[1]) * (pointi[0] - pointj[0]) == (pointk[0] - pointj[0]) * (pointi[1] - pointj[1]);
    }

    public int maxPoints2(int[][] points) {
        if (points == null || points.length == 0) {
            return 0;
        }
        if (points.length == 1) {
            return 1;
        }
        int res = 2;
        for (int i = 0; i < points.length; i++) {
            // slope斜率->该斜率下的点的数目
            HashMap<String, Integer> slope2PointNumber = new HashMap<>();
            for (int j = i + 1; j < points.length; j++) {
                String key = calculateSlope(points, i, j);
                slope2PointNumber.merge(key, 1, Integer::sum);
            }
            for (Integer pointNumber : slope2PointNumber.values()) {
                // 加一是因为还要加上points[i]这个点
                if (res < pointNumber + 1) {
                    res = pointNumber + 1;
                }
            }
        }
        return res;
    }

    /**
     * 计算i\j两个点构成的斜率，要化简为最简分数！
     *
     * @param points
     * @param i
     * @param j
     * @return
     */
    private String calculateSlope(int[][] points, int i, int j) {
        int dy = points[j][1] - points[i][1];
        int dx = points[j][0] - points[i][0];
        int gcd = gcd(dy, dx);
        return (dy / gcd) + "/" + (dx / gcd);
    }

    /**
     * 求最大公约数，这个算法一时还写不出来
     * 欧几里得算法，辗转相除法
     *
     * @param a
     * @param b
     * @return
     */
    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        HashMap<Integer, Integer> value2Index = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            Integer index = value2Index.get(nums[i]);
            if (index != null) {
                if (Math.abs(index - i) <= k) {
                    return true;
                }
            }
            value2Index.put(nums[i], i);
        }
        return false;
    }

    public boolean containsNearbyDuplicate2(int[] nums, int k) {
        // 容量为k+1的窗口
        int size = k + 1;
        HashSet<Integer> window = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (window.size() == size) {
                window.remove(nums[i - size]);
            }
            if (!window.add(nums[i])) {
                return true;
            }
        }
        return false;
    }

    public int lengthOfLongestSubstring(String s) {
        int res = 0;
        HashSet<Character> set = new HashSet<>();
        int i = 0, j = 0;
        while (j < s.length()) {
            while (!set.add(s.charAt(j))) {
                set.remove(s.charAt(i++));
            }
            res = Math.max(res, set.size());
            j++;
        }
        return res;
    }

    public boolean containsDuplicate(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (!set.add(num)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsNearbyAlmostDuplicate(int[] nums, int indexDiff, int valueDiff) {
        int size = indexDiff + 1;
        TreeSet<Integer> window = new TreeSet<>();
        for (int i = 0; i < nums.length; i++) {
            // 窗口已满，要先移除nums[i-size]
            if (window.size() == size) {
                window.remove(nums[i - size]);
            }
            if (!window.add(nums[i])) {
                // 窗口内出现重复元素，最小差值为0，返回true
                return true;
            }
            // 获取最小差值
            if (valueDiff != 0) {
                int minValueDiff = getMinValueDiff(window);
                if (minValueDiff <= valueDiff) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * O(k)的复杂度找到最小差值
     *
     * @param treeSet
     * @return
     */
    private int getMinValueDiff(TreeSet<Integer> treeSet) {
        int[] numbers = new int[treeSet.size()];
        int i = 0;
        for (Integer integer : treeSet) {
            numbers[i++] = integer;
        }
        int minValueDiff = Integer.MAX_VALUE;
        for (i = 0; i < numbers.length - 1; i++) {
            minValueDiff = Math.min(minValueDiff, numbers[i + 1] - numbers[i]);
        }
        return minValueDiff;
    }

    public boolean containsNearbyAlmostDuplicate2(int[] nums, int indexDiff, int valueDiff) {
        int size = indexDiff + 1;
        TreeSet<Integer> window = new TreeSet<>();
        TreeMap<Integer, Set<Integer>> gap2Numbers = new TreeMap<>();
        for (int i = 0; i < nums.length; i++) {
            // 窗口已满，要先移除nums[i-size]
            if (window.size() == size) {
                int removeNumber = nums[i - size];
                Integer lower = window.lower(removeNumber);
                Integer higher = window.higher(removeNumber);
                window.remove(removeNumber);
                if (valueDiff != 0) {
                    if (higher != null) {
                        int gap = higher - removeNumber;
                        Set<Integer> numbers = gap2Numbers.get(gap);
                        numbers.remove(removeNumber);
                        if (numbers.isEmpty()) {
                            gap2Numbers.remove(gap);
                        }
                    }
                    if (lower != null) {
                        int gap = removeNumber - lower;
                        Set<Integer> numbers = gap2Numbers.get(gap);
                        numbers.remove(lower);
                        if (numbers.isEmpty()) {
                            gap2Numbers.remove(gap);
                        }
                    }
                    if (higher != null && lower != null) {
                        int gap = higher - lower;
                        Set<Integer> numbers = gap2Numbers.getOrDefault(gap, new HashSet<>());
                        numbers.add(lower);
                        gap2Numbers.put(gap, numbers);
                    }
                }
            }
            if (!window.add(nums[i])) {
                // 窗口内出现重复元素，最小差值为0，返回true
                return true;
            }
            if (valueDiff != 0) {
                Integer higher = window.higher(nums[i]);
                if (higher != null) {
                    int gap = higher - nums[i];
                    Set<Integer> numbers = gap2Numbers.getOrDefault(gap, new HashSet<>());
                    numbers.add(nums[i]);
                    gap2Numbers.put(gap, numbers);
                }
                Integer lower = window.lower(nums[i]);
                if (lower != null) {
                    int gap = nums[i] - lower;
                    Set<Integer> numbers = gap2Numbers.getOrDefault(gap, new HashSet<>());
                    numbers.add(lower);
                    gap2Numbers.put(gap, numbers);
                }
                if (higher != null && lower != null) {
                    int gap = higher - lower;
                    Set<Integer> numbers = gap2Numbers.get(gap);
                    numbers.remove(lower);
                    if (numbers.isEmpty()) {
                        gap2Numbers.remove(gap);
                    }
                }
                if (!gap2Numbers.isEmpty()) {
                    int minValueDiff = gap2Numbers.firstKey();
                    if (minValueDiff <= valueDiff) {
                        return true;
                    }
                }

            }
        }
        return false;
    }

    public boolean containsNearbyAlmostDuplicate3(int[] nums, int indexDiff, int valueDiff) {
        int size = indexDiff + 1;
        TreeSet<Integer> window = new TreeSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (window.size() == size) {
                window.remove(nums[i - size]);
            }
            if (!window.isEmpty()) {
                Integer ceiling = window.ceiling(nums[i]);
                if (ceiling != null && ceiling - nums[i] <= valueDiff) {
                    return true;
                }
                Integer floor = window.floor(nums[i]);
                if (floor != null && nums[i] - floor <= valueDiff) {
                    return true;
                }
            }
            window.add(nums[i]);
        }
        return false;
    }

    public boolean containsNearbyAlmostDuplicate4(int[] nums, int indexDiff, int valueDiff) {
        // 桶，key为商，value为该商的最新的一个数值
        HashMap<Integer, Integer> bucket = new HashMap<>();
        // 滑动窗口的大小，桶的总容量
        int size = indexDiff + 1;
        for (int i = 0; i < nums.length; i++) {
            if (bucket.size() == size) {
                int bucketNo = getBucketNo(nums[i - size], valueDiff);
                bucket.remove(bucketNo);
            }
            int bucketNo = getBucketNo(nums[i], valueDiff);
            Integer value = bucket.get(bucketNo);
            if (value != null) {
                return true;
            }
            Integer nextValue = bucket.get(bucketNo + 1);
            if (nextValue != null && Math.abs(nextValue - nums[i]) <= valueDiff) {
                return true;
            }
            Integer preValue = bucket.get(bucketNo - 1);
            if (preValue != null && Math.abs(preValue - nums[i]) <= valueDiff) {
                return true;
            }
            bucket.put(bucketNo, nums[i]);
        }
        return false;
    }

    private int getBucketNo(int num, int valueDiff) {
        if (num >= 0) {
            return num / (valueDiff + 1);
        } else {
            // 负数为何这么算？
            return (num + 1) / (valueDiff + 1) - 1;
        }
    }

    /**
     * 找出最长等值子数组
     * [1,3,2,3,1,3]
     *
     * @param nums
     * @param k
     * @return
     */
    public int longestEqualSubarray(List<Integer> nums, int k) {
        // key为nums[i]，value为nums[i]对应的下标集合
        Map<Integer, List<Integer>> num2IndexList = new HashMap<>();
        for (int i = 0; i < nums.size(); i++) {
            num2IndexList.computeIfAbsent(nums.get(i), key -> new ArrayList<>()).add(i);
        }
        // 1-> 0,4
        // 2-> 2
        // 3-> 1,3,5
        int res = 1;
        for (List<Integer> indexList : num2IndexList.values()) {
            if (indexList.size() == 1) {
                continue;
            }
            int leftCursor = 0;
            for (int rightCursor = 1; rightCursor < indexList.size(); rightCursor++) {
                while (leftCursor < rightCursor && indexList.get(rightCursor) - indexList.get(leftCursor) + 1 - (rightCursor - leftCursor + 1) > k) {
                    leftCursor++;
                }
                res = Math.max(res, rightCursor - leftCursor + 1);
            }
        }
        return res;
    }


    /**
     * 直接暴力求解吧，两层循环，O(n^2)复杂度
     *
     * @param nums
     * @return
     */
    public int countBeautifulPairs(int[] nums) {
        int[][] firstNumberAndLastNumber = getNumberFirstNumberAndLastNumber(nums);
        int[] firstNumber = firstNumberAndLastNumber[0];
        int[] lastNumber = firstNumberAndLastNumber[1];
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (isPrime(firstNumber[i], lastNumber[j])) {
                    res++;
                }
            }
        }
        return res;
    }

    private int[][] getNumberFirstNumberAndLastNumber(int[] nums) {
        int[][] res = new int[2][nums.length];
        for (int i = 0; i < nums.length; i++) {
            int temp = nums[i];
            int lastNumber = temp % 10;
            res[1][i] = lastNumber;
            while (temp >= 10) {
                temp /= 10;
            }
            res[0][i] = temp;
        }
        return res;
    }

    private boolean isPrime(int i, int j) {
        int max, min;
        if (i > j) {
            max = i;
            min = j;
        } else {
            max = j;
            min = i;
        }
        if (min == 1) {
            return true;
        }
        if (min == 2) {
            return max == 3 || max == 5 || max == 7 || max == 9;
        }
        if (min == 3) {
            return max == 4 || max == 5 || max == 7 || max == 8;
        }
        if (min == 4) {
            return max == 5 || max == 7 || max == 9;
        }
        if (min == 5) {
            return max != 5;
        }
        if (min == 6) {
            return max == 7;
        }
        if (min == 7) {
            return max == 8 || max == 9;
        }
        if (min == 8) {
            return max == 9;
        }
        return false;
    }


    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        // email -> index，用于判重
        HashMap<String, Integer> hashMap3 = new HashMap<>();
        // index -> name
        HashMap<Integer, String> hashMap1 = new HashMap<>();
        // index -> emailList
        HashMap<Integer, TreeSet<String>> hashMap2 = new HashMap<>();
        int index = 0;
        for (List<String> accountList : accounts) {
            String name = accountList.get(0);
            for (int i = 1; i < accountList.size(); i++) {
                String email = accountList.get(i);
                if (hashMap3.containsKey(email)) {
                    Integer preIndex = hashMap3.get(email);
                    if (preIndex != index) {
                        hashMap1.remove(index);
                        hashMap2.remove(index);
                        for (int j = 1; j < accountList.size(); j++) {
                            String anEmail = accountList.get(j);
                            // 需要更新index！！！
                            hashMap3.put(anEmail, preIndex);
                            hashMap2.get(preIndex).add(anEmail);
                        }
                        break;
                    }
                } else {
                    hashMap3.put(email, index);
                    hashMap1.computeIfAbsent(index, key -> name);
                    hashMap2.computeIfAbsent(index, key -> new TreeSet<>()).add(email);
                }
            }
            index++;
        }
        List<List<String>> res = new LinkedList<>();
        for (Map.Entry<Integer, String> entry : hashMap1.entrySet()) {
            LinkedList<String> list = new LinkedList<>();
            list.add(entry.getValue());
            TreeSet<String> emailList = hashMap2.get(entry.getKey());
            list.addAll(emailList);
            res.add(list);
        }
        return res;
    }

    public List<Integer> relocateMarbles(int[] nums, int[] moveFrom, int[] moveTo) {
        Set<Integer> hasStonePositions = new HashSet<>();
        for (int num : nums) {
            hasStonePositions.add(num);
        }
        for (int i = 0; i < moveFrom.length; i++) {
            hasStonePositions.remove(moveFrom[i]);
            hasStonePositions.add(moveTo[i]);
        }
        return hasStonePositions.stream().sorted().collect(Collectors.toList());
    }

    public int minimumOperations(String num) {
        int count = 0;
        LinkedList<String> list = new LinkedList<>();
        list.addLast(num);
        Set<String> set = new HashSet<>();
        set.add(num);
        while (!list.isEmpty()) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                String removeFirst = list.removeFirst();
                if (is25Times(removeFirst)) {
                    return count;
                }
                for (int j = 0; j < removeFirst.length(); j++) {
                    String removeIndex = removeIndex(removeFirst, j);
                    if (set.add(removeIndex)) {
                        list.addLast(removeIndex);
                    }
                }
            }
            count++;
        }
        return count;
    }

    /**
     * 移除指定下标的字符
     *
     * @param s
     * @param index
     * @return
     */
    private String removeIndex(String s, int index) {
        char[] toCharArray = s.toCharArray();
        char[] chars = new char[s.length() - 1];
        System.arraycopy(toCharArray, 0, chars, 0, index);
        System.arraycopy(toCharArray, index + 1, chars, index, s.length() - index - 1);
        return new String(chars);
    }

    /**
     * 是否能被25整除
     *
     * @param num
     * @return
     */
    private boolean is25Times(String num) {
        if (num == null || num.length() == 0) {
            return true;
        }
        if (num.length() == 1) {
            return "0".equals(num);
        }
        return num.endsWith("00") || num.endsWith("25") || num.endsWith("75") || num.endsWith("50");
    }

    public int minimumOperationsWithDP(String num) {
        int[] dp = new int[num.length() + 1];
        dp[0] = 0;
        // 记录上一个0的下标
        int lastZeroIndex = -1;
        // 记录上一个5的下标
        int lastFiveIndex = -1;
        // 记录上一个2的下标
        int lastTwoIndex = -1;
        // 记录上一个7的下标
        int lastSevenIndex = -1;
        for (int i = 1; i < dp.length; i++) {
            // 不保留当前字符
            dp[i] = 1 + dp[i - 1];
            char c = num.charAt(i - 1);
            if (c == '0') {
                int maxIndex = Math.max(lastZeroIndex, lastFiveIndex);
                if (maxIndex != -1) {
                    // 保留当前字符，并且前面有0或者5
                    dp[i] = Math.min(dp[i], i - 1 - maxIndex - 1);
                } else {
                    // 保留当前字符，并且前面没有0和5，删除前面所有字符
                    dp[i] = Math.min(dp[i], i - 1);
                }
                lastZeroIndex = i - 1;
            } else if (c == '5') {
                int maxIndex = Math.max(lastTwoIndex, lastSevenIndex);
                if (maxIndex != -1) {
                    dp[i] = Math.min(dp[i], i - 1 - maxIndex - 1);
                }
                lastFiveIndex = i - 1;
            } else if (c == '2') {
                lastTwoIndex = i - 1;
            } else if (c == '7') {
                lastSevenIndex = i - 1;
            }
        }
        return dp[num.length()];
    }


}
