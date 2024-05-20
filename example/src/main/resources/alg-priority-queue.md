## 优先队列相关问题

优先队列的底层实现：堆
堆是一颗二叉树，底层可以使用数组实现（索引i的子节点就是2i+1和2i+2，父节点就是(i-1)/2）。

JDK的优先队列实现是`PriorityQueue`，它默认是小顶堆，可以通过自定义比较器的方式来实现大顶堆。

###### 问题347：top k frequent elements

给你一个整数数组nums和一个整数k，请你返回其中出现频率前k高的元素。你可以按任意顺序返回答案。
示例 1:
输入: nums = [1,1,1,2,2,3], k = 2
输出: [1,2]
提示：
1 <= nums.length <= 10^5
k 的取值范围是 [1, 数组中不相同的元素的个数]
题目数据保证答案唯一，换句话说，数组中前 k 个高频元素的集合是唯一的
进阶：你所设计算法的时间复杂度 必须 优于 O(n log n) ，其中 n 是数组大小。
```java
public int[] topKFrequent(int[] nums, int k);
```

这题有点类似于`问题451`，也是按频次降序排序，但是这题只需要排前k个，`问题451`要排全部。
我们首先使用`问题451`的解法来做一遍。（更快速的方法可能是类似于TOP K问题的快排或者桶排序）。
我们定义Pair类，包含数字及其出现次数两个属性，定义Pair的比较器：次数大的Pair对象更大。
遍历一遍数组，将数组及其次数存入HashMap。
然后遍历HashMap，将entry转化为Pair对象，并存入优先队列中。
从优先队列中弹出k个元素即为频次最高的k个元素。
整体复杂度为O(nlogn)。
代码：
```java
public int[] topKFrequent(int[] nums, int k) {
    HashMap<Integer, Integer> num2Times = new HashMap<>();
    for (int num : nums) {
        num2Times.merge(num, 1, Integer::sum);
    }
    PriorityQueue<Pair> priorityQueue = new PriorityQueue<>();
    for (Map.Entry<Integer, Integer> entry : num2Times.entrySet()) {
        priorityQueue.add(new Pair(entry.getKey(), entry.getValue()));
    }
    int[] res = new int[k];
    for (int i = 0; i < k; i++) {
        res[i] = priorityQueue.poll().num;
    }
    return res;
}

public static class Pair implements Comparable<Pair> {
    int num;
    int times;

    public Pair(int num, int times) {
        this.num = num;
        this.times = times;
    }

    @Override
    public int compareTo(Pair o) {
        return o.times - this.times;
    }
}
```

再使用桶排序的思想做一遍。
首先和前面的解法一样，我们先遍历一遍数组，将数字及其出现次数存入HashMap中，并在遍历过程中记录频次的最大值和最小值（用作后续构建桶）。
然后我们构建从频次最小值到频次最大值的多个桶，其中每个桶表示频次，桶中存该频次数字，我们可以使用二维数组来保存桶，一维下标表示频次（由于我们只需要找前k个高频数字，所以二维的长度设置为k就可以了）。
整体的复杂度为O(n)（但是放到leetcode上运行，它的执行速度反而更慢。。。）
代码：
```java
public int[] topKFrequent(int[] nums, int k) {
    HashMap<Integer, Integer> num2Times = new HashMap<>();
    int max = Integer.MIN_VALUE;
    int min = Integer.MAX_VALUE;
    for (int num : nums) {
        Integer times = num2Times.getOrDefault(num, 0);
        times++;
        num2Times.put(num, times);
        max = Integer.max(max, times);
        min = Integer.min(min, times);
    }
    int len = max - min + 1;
    ArrayList<Integer>[] bucket = new ArrayList[len];
    for (Map.Entry<Integer, Integer> entry : num2Times.entrySet()) {
        int index = entry.getValue() - min;
        if (bucket[index] == null) {
            bucket[index] = new ArrayList<>(k);
        }
        if (bucket[index].size() < k) {
            bucket[index].add(entry.getKey());
        }
    }
    int count = 0;
    int[] res = new int[k];
    for (int i = len - 1; i >= 0 && count < k; i--) {
        if (bucket[i] == null) {
            continue;
        }
        for (int j = 0; j < bucket[i].size() && count < k; j++) {
            res[count++] = bucket[i].get(j);
        }
    }
    return res;
}
```