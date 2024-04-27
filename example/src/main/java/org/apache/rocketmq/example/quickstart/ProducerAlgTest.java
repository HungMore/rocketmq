package org.apache.rocketmq.example.quickstart;

import java.util.*;

/**
 * @author mo
 * @Description 生产者测试
 * @createTime 2024年04月23日 17:25
 */
public class ProducerAlgTest {

    public static void main(String[] args) throws Exception {
        ProducerAlgTest producerAlgTest = new ProducerAlgTest();

        System.out.println(producerAlgTest.threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
//        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
//        producer.setNamesrvAddr("127.0.0.1:9876");
//        producer.start();
//        Message message2 = new Message("order_topic",
//                null,
//                "key1",
//                JSON.toJSONString("").getBytes(StandardCharsets.UTF_8));
//        producer.send(message2, new SelectMessageQueueByHash(), "12");
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

    private class Pair implements Comparable<Pair> {
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

}
