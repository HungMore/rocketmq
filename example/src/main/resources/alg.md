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
然后弹出优先级队列（或者TreeSet），组装返回值。
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
1. 优先级队列和TreeSet这两个数据结构有何异同呢？
2. 看官方答案，有提到用桶排序来做，有空了解下桶排序。

4.4