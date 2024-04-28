## 查找表相关问题

查找问题主要分两类：
1. 查找有无，使用的是set
2. 查找键值对，使用的是map

需要注意的是，map的实现可以分两种：
1. HashMap，哈希表，能在O(1)的复杂度下查找某个指定元素
2. TreeMap，使用搜索树实现的map，能在O(logn)的复杂度下查找某个元素，且支持顺序性，能在O(logn)的复杂度下快速查找最大/最小元素、某个元素的前驱/后继等

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
