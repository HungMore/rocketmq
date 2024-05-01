## 查找表相关问题

查找问题主要分两类：
1. 查找有无，使用的是set
2. 查找键值对，使用的是map

需要注意的是，map/set的实现可以分两种：
1. HashMap/HashSet，哈希表，能在O(1)的复杂度下查找某个指定元素
2. TreeMap/TreeSet，使用搜索树实现的map，能在O(logn)的复杂度下查找某个元素，且支持顺序性，能在O(logn)的复杂度下快速查找最大/最小元素、某个元素的前驱/后继等

我们要根据题目要求选择合适的数据结构，提高算法的性能。

###### 问题349：intersection of two arrays。

取两个数组的交集，有多个相同的元素只需返回一个。
```java
public int[] intersection(int[] nums1, int[] nums2);
```

这题挺简单的，将nums2存入一个set中，然后对nums1中的元素进行逐一判断，判断是否存在于nums2的set中，存在就存入结果集。
代码：
```java
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
```

###### 问题350：intersection of two arrays ii。

取两个数组的交集，如果同一个元素出现多次，结果集中也要包含多个相同元素。
```java
public int[] intersect(int[] nums1, int[] nums2);
```

这题由于需要记录数字出现的次数，所以改用map数据结构。
将nums2存入一个map中，其中key为数组元素的值，value为数值出现的次数。
然后对nums1中的元素进行逐一遍历，判断元素是否存在于nums2的map中，如果存在且value>0，将元素存入结果集，并将nums2的map中对应的entry的value减一。
代码：
```java
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
```

###### 问题242：valid anagram。

给定两个字符串s和t，编写一个函数来判断t是否是s的字母异位词。
注意：若s和t中每个字符出现的次数都相同，则称s和t互为字母异位词。
```java
public boolean isAnagram(String s, String t);
```

因为需要记录每个字符出现的次数，所以用map（HashMap即可）的结构。
逐个遍历字符串s的字符，将每个字符存入map中，其中map的key为字符值，value为字符出现的次数。
然后逐个遍历字符串t的字符，判断每个字符是否存在于map中，如果map中不存在该字符返回false；如果map中存在该字符，还需要判断该字符的出现次数是否>0，如果<=0，说明s中没有那么多个该字符，返回false；否则将出现次数减一，接着判断下一个字符。
遍历完t的所有字符后，查看map的每一个entry，如果全部entry的value都是0，返回true，否则返回false。
代码：
```java
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
```

###### 问题202：happy number。

编写一个算法来判断一个数n是不是快乐数。
「快乐数」定义为：
对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和。
然后重复这个过程直到这个数变为1，也可能是无限循环但始终变不到1。
如果这个过程结果为1，那么这个数就是快乐数。
如果n是快乐数就返回true；不是，则返回false。
```java
public boolean isHappy(int n);
```

这题比较简单，首先将n放入一个set中（用于判断是否出现了无限循环）。
将n替换为它每个位置上的数字的平方和，然后判断替换后是否等于1，如果是，返回true；
如果不是，尝试将结果放入set中，如果set中已经包含这个数，说明出现无限循环，返回false；否则继续判断替换后的平方和，直至等于1或者出现无限循环。
代码：
```java
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
```

###### 问题290：word pattern。

给定一种规律pattern和一个字符串s，判断s是否遵循相同的规律。
这里的遵循指完全匹配，例如，pattern里的每个字母和字符串s中的每个非空单词之间存在着双向连接的对应规律。
需要注意，不同的字母不可以连接到相同的单词，也就是说字母和单词都只能相互单一连接。
示例1:
输入: pattern = "abba", s = "dog cat cat dog"
输出: true
```java
public boolean wordPattern(String pattern, String s);
```

这题也不难，使用两个map，其中map1用来存pattern中的每个字母和s中每个单词的连接关系，map2用来存单词和字母的连接关系（因为题目要求字母和单词都只能相互单一连接）。
逐个遍历pattern中的每个字母，判断字母是否存在于map1中，如果不存在，尝试建立字母与当前单词的单一连接关系，如果单词已经被其他字母连接，连接失败，返回false，否则建立单一连接关系；如果存在，判断当前单词与map中的单词是否一致，如果不一致，返回false；如果一致，往下判断下一个字母。
代码：
```java
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
```

###### 问题205：isomorphic strings。

给定两个字符串s和t，判断它们是否是同构的。
如果s中的字符可以按某种映射关系替换得到t，那么这两个字符串是同构的。
每个出现的字符都应当映射到另一个字符，同时不改变字符的顺序。不同字符不能映射到同一个字符上，相同字符只能映射到同一个字符上，字符可以映射到自己本身。
```java
public boolean isIsomorphic(String s, String t);
```

其实这题和290一样啦，290是字母和单词的单一连接，这题是字母和字母的单一连接，解法上一模一样。
代码：
```java
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
```

###### 问题451：sort characters by frequency。

给定一个字符串s，根据字符出现的频率对其进行降序排序。一个字符出现的频率是它出现在字符串中的次数。
返回已排序的字符串。如果有多个答案，返回其中任何一个。
示例 1:
输入: s = "tree"
输出: "eert"
解释: 'e'出现两次，'r'和't'都只出现一次。
因此'e'必须出现在'r'和't'之前。此外，"eetr"也是一个有效的答案。
```java
public String frequencySort(String s);
```

这题强调了降序，所以要用HashMap+TreeSet/PriorityQueue了。
其中HashMap存的是字母到次数的映射。
定义类Pair，Pair包含两个属性，字母以及字母出现的次数，Pair的排序规则为先按照次数降序排序，再按照字母升序排序。
逐个遍历s中的字符，将字符及其次数存入HashMap中。
然后逐个遍历HashMap中的entry，生成对应的Pair对象，并将Pair对象存入优先级队列（或者TreeSet）中。
然后弹出优先级队列（或者遍历TreeSet），组装返回值。
代码：
```java
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
```
疑问：
1. 优先级队列PriorityQueue和TreeSet这两个数据结构有何异同呢？
复杂度上来说，它们的差别不大，主要是功能有差别。优先级队列底层是堆，元素可重复，可获取最大值，无序，要有序输出只能不断地弹出堆顶销毁堆；TreeSet底层是二叉搜索树（严格来说是红黑树），不可重复，有序。
2. 看官方答案，有提到用桶排序来做，有空了解下桶排序。
定义桶，每个桶表示频次，桶内元素表示该频次下的字母，然后从大到小遍历桶构造返回值即可。在这题中需要先用HashMap记录字母的频次，然后再构建桶。

###### 问题1：two sum。

给定一个整数数组nums和一个整数目标值target，请你在该数组中找出和为目标值target的那两个整数，并返回它们的数组下标。
你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
你可以按任意顺序返回答案。
示例 1：
输入：nums = [2,7,11,15], target = 9
输出：[0,1]
解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
```java
public int[] twoSum(int[] nums, int target);
```

这题因为需要返回数组的下标，所以使用map（HashMap）来存数值到下标的映射。
遍历数组的每个元素num，判断map中是否存在target-num，如果存在，说明找到了和为target的两个整数，返回它们的下标即可；如果不存在，将num及其下标存入map中，遍历下一个元素。
因为题目说明问题有且仅有一个解，所以如果遍历完数组依然没有找到解，就抛出异常吧。
代码：
```java
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
```
这题还有一个值得扩散的地方，如果题目中给出的数组是有序的，那我们就可以用指针对撞的方式来做，空间复杂度将进一步降低。

###### 问题15：3sum。

给你一个整数数组nums，判断是否存在三元组[nums[i], nums[j], nums[k]]，满足i!=j、i!=k且j!=k，同时还满足nums[i]+nums[j]+nums[k]==0。请你返回所有和为0且不重复的三元组。
注意：答案中不可以包含重复的三元组。
示例 1：
输入：nums = [-1,0,1,2,-1,-4]
输出：[[-1,-1,2],[-1,0,1]]
解释：
nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0 。
nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0 。
nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0 。
不同的三元组是 [-1,0,1] 和 [-1,-1,2] 。
注意，输出的顺序和三元组的顺序并不重要。
```java
public List<List<Integer>> threeSum(int[] nums);
```

题目要求i\j\k互不相等，所以我们需要考虑i\j\k的所有组合情况。
我们可以定义三层循环，i从0到nums.length-1，j从i+1到nums.length-1，k从j+1到nums.length-1，判断nums[i]+nums[j]+nums[k]是否等于0，如果等于0，加入结果集，直至三层循环遍历完成。
但是这题还有一个要求：答案中不能包含重复的三元组，所以我们还要对三元组进行去重。这样做复杂度就很高了，三层循环复杂度来到O(n^3)，去重又消耗额外的性能，无法成功ac。在这题中我们要换种解法。

首先回忆下我们在做two sum这道题的时候，我们说过如果数组有序，我们就可以用对撞指针低成本来做。
所以在这题中，我们先对数组进行排序。
排序完成以后，对应nums[i]，我们需要找到nums[j] + nums[k] = -nums[i]，而利用数组已经排好序的性质，我们可以使用对撞指针在O(n)的复杂度就找到这样的i和j。
另外，排好序以后，我们进行去重也很简单，如果nums[i+1] == nums[i]，那我们判断完nums[i]以后，完全就可以不再判断nums[i+1]了（nums[j]、nums[k]也是同理）。
代码：
```java
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
```

###### 问题18：4sum。

给你一个由n个整数组成的数组nums，和一个目标值target。
请你找出并返回满足下述全部条件且不重复的四元组[nums[a], nums[b], nums[c], nums[d]]（若两个四元组元素一一对应，则认为两个四元组重复）：
0 <= a, b, c, d < n
a、b、c 和 d 互不相同
nums[a] + nums[b] + nums[c] + nums[d] == target
你可以按任意顺序返回答案。
示例 1：
输入：nums = [1,0,-1,0,-2,2], target = 0
输出：[[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
```java
public List<List<Integer>> fourSum(int[] nums, int target);
```

3sum那题的解题思路可以用到这题上面。
首先我们将数组排序。
排序以后，对于nums[i]，我们需要选取一个nums[j]（j>i），然后通过对撞指针的方式找到nums[i] + nums[j] + nums[k] + nums[l] == target的四元组加入到结果集，其中i<j<k<l。
同样的，对于结果的去重，如果nums[i+1]==nums[i]，我们就可以跳过nums[i+1]，nums[j]、nums[k]、nums[l]也是同理，相等的元素就跳过，无需重复判断。
算法的复杂度为O(n^3)。
代码：
```java
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
            // 防止溢出
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
```

###### 问题16：3sum closest。

给你一个长度为n的整数数组nums和一个目标值target。请你从nums中选出三个整数，使它们的和与target最接近。 
返回这三个数的和。
假定每组输入只存在恰好一个解。
示例 1：
输入：nums = [-1,2,1,-4], target = 1
输出：2
解释：与 target 最接近的和是 2 (-1 + 2 + 1 = 2) 。
```java
public int threeSumClosest(int[] nums, int target);
```

这题没想到什么好的解法，只能考虑暴力解法了，定义三层循环遍历所有的三元组，找出其中最接近target的。
代码：
```java
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
```
看了官方的解答，这题还是可以排序后用对撞指针来做的，我是真没想到那个对撞指针收缩的公式。明天按照官方解答的思路写一写吧，也要尝试下自己用文字写出思路。

我们可以按照3sum那道题的思路，使用排序+对撞指针的方法来优化。
首先我们对数组nums进行排序，排序以后对于nums[i]，我们需要找到nums[j]、nums[k]（i<j<k），使得nums[i] + nums[j] + nums[k]的和最接近target。
在寻找nums[j]、nums[k]的过程中，我们可以定义对撞指针，使j指向i的右侧元素，k指向数组末尾，
如果此时nums[i] + nums[j] + nums[k] > target，表明三数之和过大，如果此时继续增大j，只会导致和更大（因为数组已经排好序），越发远离target，所以在这种情况下我们只需要考虑将k减小；（这个推导也是这个算法的精髓了）
同理，如果此时nums[i] + nums[j] + nums[k] < target，表明三数之和过小，如果此时继续减小k，只会导致和更小，越发远离target，所以在这种情况下我们只需要考虑将j增大。
综上，对于nums[i]，利用对撞指针，我们只需要在O(n)的复杂度下找出最接近target的nums[j]、nums[k]。
整个算法的复杂度就为O(n^2)。
代码：
```java
public int threeSumClosest(int[] nums, int target) {
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
```

###### 问题454：4sum II。

给你四个整数数组nums1、nums2、nums3和nums4，数组长度都是n，请你计算有多少个元组(i,j,k,l)能满足：
0<=i,j,k,l<n
nums1[i]+nums2[j]+nums3[k]+nums4[l]==0
示例 1：
输入：nums1 = [1,2], nums2 = [-2,-1], nums3 = [-1,2], nums4 = [0,2]
输出：2
解释：
两个元组如下：
1. (0, 0, 0, 1) -> nums1[0] + nums2[0] + nums3[0] + nums4[1] = 1 + (-2) + (-1) + 2 = 0
2. (1, 1, 0, 0) -> nums1[1] + nums2[1] + nums3[0] + nums4[0] = 2 + (-1) + (-1) + 0 = 0
```java
public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4);
```

这题说白了，就是从四个数组中，各找一个元素，使得四个元素加起来的和等于0。
如果是使用暴力解法，我们需要定义四层循环，分别遍历四元组的每一种情况，判断当前四元组之和是否等于0，整体的复杂度将是O(n^4)。
但是如果我们使用HashMap存储nums4的`值->下标集合`的映射关系，我们就可以只定义三层循环，循环遍历nums1、nums2、nums3，可以将复杂度降低到O(n^3)。
更进一步，如果我们使用两个HashMap，map1存储nums1和nums2的`和->下标二元组集合`的映射关系，map2存储nums3和nums4的`和->下标二元组集合`的映射关系，然后我们再使用二层循环循环遍历map1和map2，就可以在O(n^2)的复杂度下完成题解。
代码：
```java
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
```
以上的算法还是可以优化下的啦，因为题目只需要找到四元组的个数，而不需要知道每个四元组分别是什么，所以代码可以进一步优化成下面这样，HashMap只存`下标二元组集合`的长度，但是代码整体的复杂度还是O(n^2)：
```java
public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
    Map<Integer, Integer> map = new HashMap<>();
    int cnt = 0;

    for (int i : nums1) {
        for (int j : nums2) {
            map.merge(i + j, 1, Integer::sum);
        }
    }

    for (int i : nums3) {
        for (int j : nums4) {
            cnt += map.getOrDefault(-(i + j), 0);
        }
    }

    return cnt;
}
```

###### 问题49：group anagrams。

字母异位词分组。
给你一个字符串数组，请你将字母异位词组合在一起。可以按任意顺序返回结果列表。
字母异位词是由重新排列源单词的所有字母得到的一个新单词。
示例 1:
输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
```java
public List<List<String>> groupAnagrams(String[] strs);
```

这题要按照字母异位词进行分组，所以我们首先就会考虑使用一个HashMap存`字母异位词->单词集合`的映射关系，然后我们将HashMap的values返回即可。但是在这个方法中，我们还要想办法将多个字母异位词“序列化”成同一个key，这样才能分组成功。由于题目规定字符串只包含小写字母，那我们可以考虑这样一种字母异位词“序列化”方式：使用一个数组`int[26]`记录字母异位词，下标表示字母，值表示字母出现的次数，然后将该数组转换为`a3b4`（字母a出现3次字母b出现4次）这样的字符串，保证不同的字母异位词可以“序列化”成同一个字符串key。
代码：
```java
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
```

###### 问题447：number of boomerangs

给定平面上n对互不相同的点points，其中points[i]=[xi, yi]。回旋镖是由点(i, j, k)表示的元组，其中i和j之间的欧式距离和i和k之间的欧式距离相等（需要考虑元组的顺序）。
返回平面上所有回旋镖的数量。
示例 1：
输入：points = [[0,0],[1,0],[2,0]]
输出：2
解释：两个回旋镖为 [[1,0],[0,0],[2,0]] 和 [[1,0],[2,0],[0,0]]
```java
public int numberOfBoomerangs(int[][] points);
```

这题目读起来有点绕。首先明确下欧式距离的定义，欧式距离就是我们求解两点距离的公式：点(x1,y1)和点(x2,y2)的距离d=开根((x1-x2)^2+(y1-y2)^2)。
所以这道题，就是要求我们找出两个点到同一个点的距离相等的三元组，并且需要考虑元组的顺序。
对于点points[i]，我们可以定义一个HashMap，记录`其他点到points[i]的距离`->`该距离下的点的集合`的映射关系。
如果HashMap的entry.value的长度大于1，说明有多个点到points[i]距离等于entry.key，我们就可以构造`(entry.value.size)*(entry.value.size-1)`个满足条件的三元组（排列与组合中的排列）。
使用以上思路遍历完所有的点，就可以获得这道题的题解。整体的算法复杂度将是O(n^2)。
代码：
```java
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
```

###### 问题149：max points on a line

给你一个数组points，其中points[i]=[xi, yi]表示X-Y平面上的一个点。求最多有多少个点在同一条直线上。
示例 1：
输入：points = [[1,1],[2,2],[3,3]]
输出：3
```java
public int maxPoints(int[][] points);
```

正所谓两点确定一条直线。
对于点points[i]和点points[j]组合构成的直线，我们需要判断points[k]是否在该直线上（i<j<k），不断遍历k，记录在该直线上的点的个数。
所以我们可以定义i、j、k（i<j<k）的三层循环，不断地判断有多少个points[k]在由points[i]、points[j]构成的直线上，在遍历的过程中更新结果。整体复杂度为O(n^3)。
而判断points[k]是否在由points[i]、points[j]构成的直线上也很简单，只需要比较它们的斜率，如果(yk-yj)/(xk-xj)==(yi-yj)/(xi-xj)，即(yk-yj)*(xi-xj)==(xk-xj)*(yi-yj)（之所以要将除法转化为乘法，是为了规避除法的精度问题），那么这三点就在一条直线上。
代码：
```java
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
```
简单瞄了眼官方的答案，它有复杂度更低的解法，要去了解下并写下来。
~~官方的答案有点复杂，今晚再仔细阅读吧。~~

官方的答案和`宫水三叶`的思路是一样的，其实只要理解了就觉得并不难。。。
我们考虑points[i]这个点，我们可以使用一个HashMap来存`其他的点与它构成的直线的斜率`->`该斜率下的点的数目`的映射关系
（不难理解，若两个点和点points[i]构成的斜率一样，那么它们和points[i]就在同一条直线上）
对于points[i]这个点来说，HashMap的values中的最大值就是最多有多少个点和points[i]在同一条直线上。
我们遍历points中所有的点，都计算它们的最大值，即可获得这题的解。
另外，这题还有一点需要注意：HashMap中的key（斜率）一定要使用最简分数，确保相同的斜率定位到相同的entry，并且数据类型不要使用不精确的浮点数，一种可行方案是用如“3/4”这样的字符串形式来存（其实更严谨的，用“3/4”这样的字符串来存，还要考虑斜率为负的情况，保证“-3/4”要等于“3/-4”，这里比较讨巧的是求最大公约数这个方法让我们规避掉了这个问题）。
算法的整体复杂度是O(n^2).
代码：
```java
public int maxPoints(int[][] points) {
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
```

###### 问题219：contains duplicate ii

给你一个整数数组nums和一个整数k，判断数组中是否存在两个不同的索引i和j，满足nums[i]==nums[j]且abs(i-j)<=k。如果存在，返回true；否则，返回false。
示例 1：
输入：nums = [1,2,3,1], k = 3
输出：true
```java
public boolean containsNearbyDuplicate(int[] nums, int k);
```

这题很简单啦，使用一个HashMap存`数值`->`该数值的最近的一个下标`的映射关系。
从左往右遍历nums的过程中，对于当前元素nums[i]
判断HashMap中是否存在nums[i]对应的entry，如果不存在，则存入HashMap中；如果存在，判断两个下标的绝对值是否小于k，如果是，返回true，如果不是，更新nums[i]数值对应的最近一个下标（可以更新的原因是后续的元素的下标一定和当前的这个元素下标更接近）。
如果遍历完nums都没有这样的元素，返回false。
整体复杂度为O(n)。
代码：
```java
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
```
bobo老师的解法用的是滑动窗口，也可以写一写。（bobo老师说这题可以和问题3对比下，那题也是滑动窗口+查找表，只是窗口大小不固定）
使用滑动窗口的思路来做下这一道题
首先我们定义长度为k+1的滑动窗口（因为要判重，所以用set来充当窗口容器；长度为k+1是因为下标差等于k是有k+1个元素的）
然后逐个遍历数组，对于nums[i]，我们判断当前滑动窗口的大小是否等于k+1
如果等于，说明已满，我们要先移除nums[i-k-1]再尝试添加nums[i]；如果不等于k，说明窗口未满，直接尝试添加nums[i]到窗口中，如果添加失败，说明窗口中已经存在该元素，返回true。算法复杂度为O(n)。
代码：
```java
public boolean containsNearbyDuplicate(int[] nums, int k) {
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
```
然后我们再来看看问题3，对比下。

###### 问题3：无重复字符的最长子串

给定一个字符串s，请你找出其中不含有重复字符的最长子串的长度。
示例 1:
输入: s = "abcabcbb"
输出: 3 
解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
```java
public int lengthOfLongestSubstring(String s);
```

这题我们可以用滑动窗口+set(HashSet)的方法来做。
定义双指针i和j，分别指向char[i]和char[j]，然后使用set来存窗口[i,j]之间的字符集合。
i和j初始化为0，然后尝试将char[j]添加到set中，如果添加成功，说明[i,j]中暂未出现重复字符，更新返回值，j++；如果添加失败，说明set中已经有char[j]，不断地将char[i++]移出，直至将已经存在的char[j]移除，然后添加新的char[j]。
当j来到字符串的末尾，算法结束。
算法复杂度为O(n)。
代码：
```java
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
```
上面这个代码应该是我对这道题写过的最简洁的代码了。
这道题和问题219的两种滑动窗口确实值得回味，一个是固定长度的滑动窗口，判断窗口内是否有重复元素；一个是不固定长度的窗口，要保证窗口内部没有重复的元素。

###### 问题217：contains duplicate。

给你一个整数数组nums。如果任一值在数组中出现至少两次，返回true；如果数组中每个元素互不相同，返回false。
示例 1：
输入：nums = [1,2,3,1]
输出：true
```java
public boolean containsDuplicate(int[] nums);
```

这题就很简单啦，遍历数组，用个set判断是否重复。
代码：
```java
public boolean containsDuplicate(int[] nums) {
    HashSet<Integer> set = new HashSet<>();
    for (int num : nums) {
        if (!set.add(num)) {
            return true;
        }
    }
    return false;
}
```

###### 问题220：contains duplicate III

给你一个整数数组nums和两个整数indexDiff和valueDiff。
找出满足下述条件的下标对(i, j)：
i!=j,
abs(i-j)<=indexDiff
abs(nums[i]-nums[j])<=valueDiff
如果存在，返回true；否则，返回false。
示例 1：
输入：nums = [1,2,3,1], indexDiff = 3, valueDiff = 0
输出：true
解释：
    可以找出 (i, j) = (0, 3) 。
    满足下述 3 个条件：
    i != j --> 0 != 3
    abs(i - j) <= indexDiff --> abs(0 - 3) <= 3
    abs(nums[i] - nums[j]) <= valueDiff --> abs(1 - 1) <= 0
```java
public boolean containsNearbyAlmostDuplicate(int[] nums, int indexDiff, int valueDiff);
```

这题就是说要从数组中找出两个元素，使得它们的下标的差值不超过indexDiff，元素的值的差值不超过valueDiff。
参考问题219的解法，下标差不超过indexDiff，我们就可以定义固定长度为indexDiff+1的滑动窗口，然后判断窗口内的元素值的最小差值是否不超过valueDiff即可。现在的问题就转化为，我们如何快速地找到窗口内的最小差值。一般来说，查找最小差值，我们需要两两组合比较，复杂度是O(k^2)，但是如果窗口内的元素有序，我们就可以在O(k)的复杂度下找到最小差值，所以如果我们能将窗口的元素保持有序，就可以更快速地找到最小差值，所以我们可以使用TreeSet这样的数据结构来作为窗口的容器（另外，虽然TreeSet不允许存入重复元素，但是对于重复元素，它们的差值就是0，肯定不超过valueDiff的。所以窗口内出现重复元素我们可以直接返回true）。
整体复杂度为O(n*indexDiff)
代码：
```java
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
```
竟然超时了！其实`2<=nums.length<=10^5`、`1<=indexDiff<=nums.length`这两个数据量也提醒我们O(n*indexDiff)的复杂度是无法通过的，因为这约等于O(n^2)，最多将进行约10^10次运算，指定要超时啦。
我们再考虑下方案的优化。
其实我们没必要每次操作TreeSet都重新计算整个TreeSet中的最小差值，因为给TreeSet增/删一个元素，其实只会影响它相邻的元素。
我们可以定义两个容器，第一个为TreeSet，充当滑动窗口的容器，将窗口中的元素进行升序排列。第二个容器为TreeMap，
存`差值`->`该差值的元素的集合`的映射关系，即`gap`->`[nums[i]、...]`（我们给出如下定义，某个元素的差值等于它与它的下一个元素的差，即nums[i]的差值等于nums[i+1]-nums[i]）
那我们往滑动窗口添加元素的时候需要：
1. TreeSet中新增当前元素
2. TreeMap中更新当前元素的差值的entry（加上当前元素元素）、前一个元素原来的差值的entry（删除前一个元素）、前一个元素新的差值的entry（加上前一个元素）。
我们要找到最小的差值，直接从TreeMap中找第一个就可以了。
往滑动窗口删除元素时需要：
1. TreeSet中删除当前元素
2. TreeMap中更新当前元素的差值的entry（删除当前元素）、前一个元素原来的差值的entry（删除前一个元素）、前一个元素新的差值的entry（加上前一个元素）
整体复杂度O(n*log(indexDiff))
代码：
```java
public boolean containsNearbyAlmostDuplicate(int[] nums, int indexDiff, int valueDiff) {
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
```
这个解法只超过了8%的用户，还有更好的解法吧。。。
看了bobo老师的讲解，我被降维打击了。。。
其实根本就不用将当前元素加入到滑动窗口以后，再去查找滑动窗口里的最小差值。
我们可以每加入一个元素nums[i]前，就判断当前窗口是否有元素在[nums[i] - valueDiff, nums[i] + valueDiff]这个区间（使用nums[i]最接近的两个数字比较就可以了），如果有，就返回true；没有就遍历下一个元素。所以我们用一个TreeSet作为窗口的容器就可以了。妈的，我怎么给搞得这么复杂。
代码：
```java
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
```
看leetcode的答案，还有一种O(n)的基于桶分组的解法，有空了解下。

桶分组的讲解可以看这两个回答：
1. [C++ 利用桶分组, 详细解释](https://leetcode.cn/problems/contains-duplicate-iii/solutions/727120/c-li-yong-tong-fen-zu-xiang-xi-jie-shi-b-ofj6/)，这个答案分析得很详细、很好理解！
2. [【宫水三叶】一题双解：「滑动窗口 & 二分」&「桶排序」解法](https://leetcode.cn/problems/contains-duplicate-iii/solutions/726905/gong-shui-san-xie-yi-ti-shuang-jie-hua-d-dlnv/)，这个答案可以作为第一个的补充。

我个人觉得桶分组的答案是挺难想到的，它使用桶分组式的滑动窗口对TreeSet式的滑动窗口进行了优化。
在TreeSet的解法中我们使用TreeSet来作为滑动窗口的容器（窗口大小为indexDiff+1），所以我们可以在log(indexDiff)的复杂度下找到最接近当前元素的前后两个元素，进而判断它们的差值是否在valueDiff的范围内。
而使用桶分组的思维：如果两个数除以`valueDiff+1`的商相等（不考虑余数），那么它们的差值肯定就小于等于valueDiff；如果它们的商的差值大于1，它们的差值肯定就大于valueDiff；如果它们的商的差值等于1，就需要重新判断它们的差值。
所以我们可以使用数值除以`valueDiff+1`的商作为桶的编号，每个桶内存该商下的数值，其实每个桶只需要存该商下的最新的一个数值就可以了，因为如果商值相等，整个算法已经返回true了。
所有桶的总容量需要保证最大为indexDiff+1。
整体的算法思路为：
对于nums[i]，首先我们需要判断桶的总容量是否已经达到indexDiff+1，如果是的话，我们要移除nums[i-(indexDiff+1)]的桶；
然后计算nums[i]的桶的编号，判断当前桶内是否已经有元素，如果是的话，返回true
否则取出nums[i]的前、后一个桶的值，判断桶内的值和nums[i]的差值是否小于等于valueDiff，如果是，返回true，如果不是，存入nums[i]，遍历下一个元素。
代码：
```java
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
```
这个解法中关于负数获取其桶的编号的问题，可以看看leetcode的这个评论：
[Edward Elric的评论](https://leetcode.cn/problems/contains-duplicate-iii/solutions/726619/cun-zai-zhong-fu-yuan-su-iii-by-leetcode-bbkt/comments/895169)，很精彩，除法是向0方向舍入的，所以正数和负数的舍入方向相反！

