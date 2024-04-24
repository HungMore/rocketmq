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

###### 问题290：word pattern。

###### 问题205：isomorphic strings。

###### 问题451：sort characters by frequency。

4.4