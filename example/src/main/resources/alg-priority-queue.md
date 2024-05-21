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
我们定义Pair类，包含数字及其出现次数两个属性，定义Pair的比较器：次数大的Pair对象更大（大顶堆）。
遍历一遍数组，将数组及其次数存入HashMap。
然后遍历HashMap，将entry转化为Pair对象，并存入优先队列中。
从优先队列中弹出k个元素即为频次最高的k个元素（大顶堆）。
整体复杂度为O(nlogn)。
代码：
```java
public int[] topKFrequent(int[] nums, int k) {
    HashMap<Integer, Integer> num2Times = new HashMap<>();
    for (int num : nums) {
        num2Times.merge(num, 1, Integer::sum);
    }
    // 大顶堆，堆顶元素次数最大
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
    // 大顶堆，所以按逆序来
    @Override
    public int compareTo(Pair o) {
        return o.times - this.times;
    }
}
```
以上的优先队列解法我们还可以优化下，我们不需要对全部频次都进行排序，我们只需要前k个高频次，所以我们可以将优先队列的容量保持在k。
当优先队列的大小已经达到k，我们就判断下堆中的最小频次和准备入堆的频次哪个更小（所以要用小顶堆），如果堆中的最小频次更小，就将其出堆并将当前元素入堆；否则不对堆进行操作。
整体复杂度就为O(nlogk)。
代码：
```java
public int[] topKFrequent(int[] nums, int k) {
    HashMap<Integer, Integer> num2Times = new HashMap<>();
    for (int num : nums) {
        num2Times.merge(num, 1, Integer::sum);
    }
    // 小顶堆
    PriorityQueue<PairWithHeapBetter> priorityQueue = new PriorityQueue<>();
    for (Map.Entry<Integer, Integer> entry : num2Times.entrySet()) {
        if (priorityQueue.size() < k) {
            priorityQueue.add(new PairWithHeapBetter(entry.getKey(), entry.getValue()));
        } else {
            PairWithHeapBetter peek = priorityQueue.peek();
            if (peek.times < entry.getValue()) {
                priorityQueue.poll();
                priorityQueue.add(new PairWithHeapBetter(entry.getKey(), entry.getValue()));
            }
        }

    }
    int[] res = new int[k];
    for (int i = 0; i < k; i++) {
        res[i] = priorityQueue.poll().num;
    }
    return res;
}

public static class PairWithHeapBetter implements Comparable<PairWithHeapBetter> {
    int num;
    int times;

    public PairWithHeapBetter(int num, int times) {
        this.num = num;
        this.times = times;
    }
    // 小顶堆，所以按顺序来
    @Override
    public int compareTo(PairWithHeapBetter o) {
        return this.times - o.times;
    }
}
```
这个解法还有可以优化的点：如果k趋近于n，那么整体复杂度就接近于nlogn。其实，在k趋近于n的时候，我们可以反向保存：使用大顶堆保存n-k个低频的元素！
当堆的大小已经达到n-k，我们就判断准备入堆的元素的频次是否小于堆顶元素，如果是，将堆顶出堆并加入到返回值，并将当前元素入堆；如果不是，将当前元素加入返回值。
综合两者思路，如果k远小于n，我们就维持k个元素的小顶堆，保存高频元素；如果k趋近于n，我们就维持n-k个元素的大顶堆，保存低频元素！）

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

###### 问题23：merge k sorted lists

给你一个链表数组，每个链表都已经按升序排列。
请你将所有链表合并到一个升序链表中，返回合并后的链表。
示例 1：
输入：lists = [[1,4,5],[1,3,4],[2,6]]
输出：[1,1,2,3,4,4,5,6]
解释：链表数组如下：
[
  1->4->5,
  1->3->4,
  2->6
]
将它们合并到一个有序链表中得到。1->1->2->3->4->4->5->6。
```java
public ListNode mergeKLists(ListNode[] lists);
```

之前写的归并都是二分的，这题也同样可以转化为二分归并：链表1先和链表2归并，归并的结果再和链表3归并，然后再和链表4归并······直至归并完所有链表。
代码：
```java
public ListNode mergeKLists(ListNode[] lists) {
    ListNode temp = null;
    for (int i = 0; i < lists.length; i++) {
        temp = mergeTwoLists(temp, lists[i]);
    }
    return temp;
}

private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(-1);
    ListNode tail = dummy;
    while (l1 != null && l2 != null) {
        if (l1.val <= l2.val) {
            tail.next = l1;
            l1 = l1.next;
        } else {
            tail.next = l2;
            l2 = l2.next;
        }
        tail = tail.next;
    }
    if (l1 != null) {
        tail.next = l1;
    }
    if (l2 != null) {
        tail.next = l2;
    }
    return dummy.next;
}
```

这题也可以用小顶堆来做。我们假设链表一共有k个。我们定义一个小顶堆，将k个链表的第一个元素都加入到小顶堆中，那么堆顶的元素就是当前的最小元素，
我们将堆顶元素出堆并连接到结果链表的末尾，然后将堆顶元素的下一个节点加入到堆中，如此不断循环直至小顶堆为空（也就是k个链表都遍历到结尾）。
代码：
```java
public ListNode mergeKLists(ListNode[] lists) {
    // 小顶堆
    PriorityQueue<ListNode> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(l -> l.val));
    for (ListNode list : lists) {
        if (list != null) {
            priorityQueue.add(list);
        }
    }
    ListNode dummy = new ListNode(-1);
    ListNode tail = dummy;
    while (!priorityQueue.isEmpty()) {
        ListNode poll = priorityQueue.poll();
        tail.next = poll;
        tail = tail.next;
        if (poll.next != null) {
            priorityQueue.add(poll.next);
        }
    }
    return dummy.next;
}
```