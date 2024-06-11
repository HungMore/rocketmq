## 递归和回溯相关问题

回溯法主要用在树形问题上。
树形问题是分析的过程中从上向下遍历多种不同的可能性。
回溯法：当搜索到底了或者发现不是正确路径了（剪枝），就回头返回上一层。
回溯法一般可以使用递归进行代码实现。
回溯法的效率一般都不高，适合数据集比较小的场景。回溯法是暴力解法的一个主要实现手段，它可以枚举所有的可能性。

回溯法和DFS的区别是？

[github DFS和回溯算法区别](https://github.com/Rancho86/leetcode/blob/master/DFS%E5%92%8C%E5%9B%9E%E6%BA%AF%E7%AE%97%E6%B3%95%E5%8C%BA%E5%88%AB.md)

> DFS是一个劲地往某一个方向搜索。而回溯算法建立在DFS基础之上的，但不同的是在搜索过程中，达到结束条件后，恢复状态，回溯上一层，再次搜索。因此回溯算法与DFS的区别就是有无状态重置。
> 当问题需要“回头”，以此来查找出所有的解的时候，使用回溯算法。即满足结束条件或者发现不是正确路径的时候(走不通)，要撤销选择，回退到上一个状态，继续尝试，直到找出所有解为止。

[示例分析总结递归，回溯，DFS以及动态规划的各自特点与区别](https://www.cnblogs.com/xu-learn/p/16340255.html)

> 深度优先搜索适用于遍历或图或搜索树的算法，DFS是一个不断探查和回溯的过程。在探查的每一步，算法都有一个当前的顶点。最初的当前顶点，作为起始顶点。每一步探查过程中，首先对当前顶点V进行访问，并将该点的访问标志visited[v] = true.接着在v的所有邻接顶点中找出未被标志的过一个点，将其作为下一步的探查的当前顶点，倘若当前顶点的所有邻接顶点都被标志过，则退回一步，将前一步所访问的顶点重新取出，作为探查的当前顶点，重复上述过程，直到最初指定起始顶点的所有邻接顶点都被访问为止。
> 从DFS定义中不难发现，深度优先搜索算法也用到回溯，DFS与回溯关键区别在于DFS是一般应用于树和图的结构，回溯算法应用范围更广不限于固定数据结构，DFS搜索过程记录图或树的搜索完整路径，而回溯有剪枝功能在求解过程中不保留树或图的完整路径。

[辨析DFS 、回溯法和递归](https://liduos.com/backtracking.html)

> 回溯是一种通用的算法思想，把问题分步解决，在每一步都试验所有的可能（回溯本质上是一种穷举。），当发现已经找到一种方式或者目前这种方式不可能是结果的时候，退回上一步继续尝试其他可能。当每一步的处理都是一致的，这时候用递归来实现就很自然。
> 深度优先搜索（DFS）算法，即是一种搜索算法。他的特点是在搜索时会面临多个选择，当选择某一个情况后仍然会面临多个选择。那么，他每一次都选择一个情况时，会继续沿着这个“方向”搜索下去，直到遇到边界无法在搜索时，结束当前分支路径的搜索。接着，进行其他路径的搜索。以这种方式持续下去，直到将所有的路径搜索完毕。经典的案例，比如对于BST（二叉搜索树）的先序遍历、中序遍历和后序遍历。
> 回溯搜索是深度优先搜索（DFS）的一种，对于某一个搜索树来说（搜索树是起记录路径和状态判断的作用）。其主要的区别是，回溯法在求解过程中不保留完整的树结构，而深度优先搜索则记下完整的搜索树。在深度优先搜索中，用标志的方法记录访问过的状态，这种处理方法使得深度优先搜索法与回溯法没什么区别了。

自己理解：回溯和DFS都可以很方便地用递归的方式进行代码实现。回溯用到了DFS的思想（或者DFS用到了回溯的思想，两者都是自顶向下的遍历思想）。DFS可以用在树、图的搜索上；回溯不局限于具体的数据结构，只要能解析成树形问题，都可以使用回溯法。回溯法可以更广义地理解为一种算法思想，和动态规划、分治、贪心等思想同等级别。

回溯法的常见应用场景：
1. 排列问题（问题46、47）
2. 组合问题（问题77等）
3. flood-fill算法问题（问题200等）

###### 问题17：letter combinations of phone number

给定一个仅包含数字2-9的字符串，返回所有它能表示的字母组合。答案可以按任意顺序返回。
给出数字到字母的映射如下（与电话按键相同）。注意数字1不对应任何字母。
```text
2 -> [a,b,c]
3 -> [d,e,f]
4 -> [g,h,i]
5 -> [j,k,l]
6 -> [m,n,o]
7 -> [p,q,r,s]
8 -> [t,u,v]
9 -> [w,x,y,z]
```
示例 1：
输入：digits = "23"
输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]
```java
public List<String> letterCombinations(String digits);
```

这题就是很典型的树形问题。对于数字字符串`23`，我们首先看数字`2`，数字`2`可以映射到字母a\b\c三种选择，选择其中一个字母以后，我们继续看数字`3`，数字`3`可以映射到字母d\e\f三种选择，形成的树型结构如下：

[问题17的树形结构](./img/recursion-backtracking/q17_tree.png)

这题我们可以用回溯法来做，假定数字`2`我们选择了字母`a`，我们继续往下遍历数字`3`，遍历完`a`的所有结果以后，我们回溯到数字`2`继续选择字母`b`，以此类推遍历所有的情况。
我们可以定义递归函数`combinations(String digits, int digitIndex, String pre, List<String> res)`，其中：
digits表示数字字符串
digitIndex表示当前遍历的数字在digits中的下标
pre表示在digitIndex前的数字获取到字符串
res表示结果集  
该递归函数的递归结构：
1. 递归终止条件：当digitIndex来到digits字符串的末尾的时候，说明已经遍历完所有的数字，将pre加入到res结果集中，返回
2. 递归逻辑：对于当前的`digits[digitIndex]`，我们获取到它映射的多个字母x，分别向下递归`combinations(digits, digitIndex + 1, pre + x, res)`
代码：
```java
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
```

###### 问题93：restore ip address

有效IP地址正好由四个整数（每个整数位于0到255之间组成，且不能含有前导0），整数之间用'.'分隔。
例如："0.1.2.201"和"192.168.1.1"是有效IP地址，但是"0.011.255.245"、"192.168.1.312"和"192.168@1.1"是无效IP地址。
给定一个只包含数字的字符串s，用以表示一个IP地址，返回所有可能的有效IP地址，这些地址可以通过在s中插入'.'来形成。你不能重新排序或删除s中的任何数字。你可以按任何顺序返回答案。
示例1：
输入：s = "25525511135"
输出：["255.255.11.135","255.255.111.35"]
示例 2：
输入：s = "0000"
输出：["0.0.0.0"]
示例 3：
输入：s = "101023"
输出：["1.0.10.23","1.0.102.3","10.1.0.23","10.10.2.3","101.0.2.3"]
```java
public List<String> restoreIpAddresses(String s);
```

这题可以用回溯法来做。我们可以定义这样的一个辅助函数：`private void ip(String s, int index, int segmentNumber)`
其中：
1. s表示字符串s
2. index表示当前遍历的s的第index个字符
3. segmentNumber表示当前ip地址有多少个segment
以这个辅助函数来定义递归关系：
1. 当index等于s.length或者segmentNumber等于4，表示已经遍历完s的所有字符或者凑够ip地址所需的四个segment，这时我们判断是否index等于s.length且segmentNumber等于4，如果是，将ip加入结果集并返回，否则，此路走不通，直接返回。
2. 如果index不等于s.length且segmentNumber不等于4，说明还有字符需要分割，我们需要判断s[index...index]\s[index...index+1]\s[index...index+2]能否构成一个合法的segment，如果可以，递归往下遍历，如果不可以，跳过。
代码：
```java
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
```

###### 问题131：palindrome partitioning

给你一个字符串s，请你将s分割成一些子串，使每个子串都是回文串。返回s所有可能的分割方案。
示例 1：
输入：s = "aab"
输出：[["a","a","b"],["aa","b"]]
示例 2：
输入：s = "a"
输出：[["a"]]
```java
public List<List<String>> partition(String s);
```

首先我们需要一个函数来判断是否是回文串，这个很简单，用头尾双指针就行了。
代码：
```java
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
```
然后我们就可以用回溯法来不断遍历各种分割方案了。首先我们定义这个的辅助函数：`private void partitionHelper(String s, int index, List<String> pre, List<List<String>> res)`
其中：
1. s表示字符串s
2. index表示当前遍历的s的第index个字符
3. pre表示前面的s[0...index-1]构成的回文子串集合
4. res表示结果集
以这个辅助函数来定义递归关系：
1. 当index到达s的末尾，表示遍历完成，将pre加入res，返回
2. 如果index不等于s.length，循环判断`s[index...i]`（i<s.length）能否构成回文串，如何不可以，跳过，遍历下一个i，如果可以，递归往下遍历。（其实递归判断就是寻找子串`s[index...s.length-1]`的分割方案，会存在大量的重复计算！）
代码：
```java
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
```
很明显，这题存在子树重复计算的问题（很多种情况都可以递归到同一个index，这些就是重复计算），所以我们可以用记忆化搜索来优化。
然后回溯法+记忆化搜索，就等于动态规划啦！
所以我们可以定义动态规划的状态转移方程：
dp[index]表示s[0...index)构成的结果集。这题的答案就等于dp[s.length]
dp[index] = dp[i] + s[i...index)，0<=i<=index-1,s[i...index)为回文子串
dp[0] = [[]]
dp[1] = [[s[0,1)]]
代码：
```java
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
```
但是这样只击败了5%的用户，主要是集合需要重复不断地拷贝，很消耗性能。
我们可以考虑不使用集合来存储一个分割方案，而是将其“序列化”成字符串，比如`["aa","b"]`这一个方案我们就序列化成"aa,b"，使用逗号将其隔开。操作字符串的消耗就很小了，代码如下：
```java
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
```
这样也还是只击败了5%的用户，可能是s的长度太小了吧，我们可以随机生成一些更长的字符串来测试下。
随机生成了长度为200的字符串，这个方法确实是更快的！`partitionDPString` > `partitionDP` > `partition` ！
有空看看官解以及其他的解吧`todo`

###### 问题46：permutations

给定一个不含重复数字的数组nums，返回其所有可能的全排列。你可以按任意顺序返回答案。
示例 1：
输入：nums = [1,2,3]
输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
```java
public List<List<Integer>> permute(int[] nums);
```

这题其实就是一个典型的树形问题：

[问题46的分析树](./img/recursion-backtracking/q46_tree.png)

我们可以使用回溯法来解决这道题。
首先我们将数组存入到一个链表中，这样就很方便我们拿取其中的一个元素，并且拿取（弹出）了该元素以后，遍历完以该元素开头的全排列以后，重新将其放回到链表的末尾，又可以处理不以该元素开头的全排列，真的很方便！这一步也是解决排列问题的关键！
代码：
```java
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
    // 这里更稳妥的办法是将size存入变量中。毕竟在循环中会往numQueue中增删元素。这里一增一删刚好抵消。
    for (int i = 0; i < numQueue.size(); i++) {
        Integer remove = numQueue.remove();
        pre.addLast(remove);
        dfs(numQueue, pre, res);
        // 回溯的重点：恢复状态
        pre.removeLast();
        numQueue.add(remove);
    }
}
```
看了我以前提交的答案，注释得更明确一些：
```java
private List<List<Integer>> res;

public List<List<Integer>> permute(int[] nums) {
    res = new LinkedList<>();
    LinkedList<Integer> numberList = new LinkedList<>();
    for (int num : nums) {
        numberList.add(num);
    }
    dfs(numberList, new LinkedList<>());
    return res;
}

private void dfs(LinkedList<Integer> nums, LinkedList<Integer> pre) {
    if (nums.isEmpty()) {
        res.add(new LinkedList<>(pre));
        return;
    }
    int size = nums.size();
    for (int i = 0; i < size; i++) {
        // 拿出第一个元素
        Integer first = nums.removeFirst();
        pre.addLast(first);
        dfs(nums, pre);
        // 回溯
        pre.removeLast();
        // 将第一个元素放到尾部，这样在 0--size-1 的循环中，就不会再遇到这个元素了
        nums.addLast(first);
    }
}
```
印象中labuladong有使用数组位置交换的方式来替代numberList的，可以看看。`todo`

###### 问题47：permutations ii

给定一个可包含重复数字的序列nums，按任意顺序返回所有不重复的全排列。
示例 1：
输入：nums = [1,1,2]
输出：
[[1,1,2],
 [1,2,1],
 [2,1,1]]
```java
public List<List<Integer>> permuteUnique(int[] nums);
```

这题和问题46是类似的，区别就是该题可能包含重复元素。我们可以先对数组进行排序，然后使用和问题46一样的套路去解决，需要额外处理的是如果以元素A开头的全排列已经处理过，以下一个A开头的全排列就可以跳过了（也就是当i不等于0时，判断下当前元素是否等于前一个元素，如果是的话，跳过）。
代码：
```java
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
```

###### 问题77：combinations

给定两个整数n和k，返回范围[1, n]中所有可能的k个数的组合。
你可以按任何顺序返回答案。
示例 1：
输入：n = 4, k = 2
输出：
[
  [2,4],
  [3,4],
  [2,3],
  [1,2],
  [1,3],
  [1,4],
]
```java
public List<List<Integer>> combine(int n, int k);
```

组合问题有个公式，就很符合递归函数的定义：`C(n,k) = C(n-1,k) + C(n-1,k-1)`（相当于分两种情况：包含某个元素以及不包含某个元素）。
利用这个公式，我们可以定义递归辅助函数`combineHelper(int n, int k, List<Integer> pre)`
其中：
1. n表示数字的可选范围[1,n]
2. k表示组合的数字个数
3. pre表示递归前一步的临时结果
递归关系定义如下：
1. 递归终止条件：当k等于0时，表明pre已经凑够组合所需的数组个数，将pre加入结果集，返回
2. 递归关系：递归关系分两部分：
2.1 组合内包含数字n。将n接入到pre集合中，递归`C(n-1,k-1)`。（记得回溯状态，将n从pre中移除）
2.2 组合内不包含数字n。直接递归`C(n-1,k)`。这里递归需要注意：`n-1`要大于等于`k`才往下递归！
综上，代码如下：
```java
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
```
看来我的提交记录，以前写的一个解也还可以（整体的复杂度是一样的，主要是思考的方向略有一点点差别），可以参考下：
```java
private List<List<Integer>> res;

public List<List<Integer>> combine(int n, int k) {
    res = new LinkedList<>();
    dfs(1, n, k, new LinkedList<>());
    return res;
}

/**
 * 在 [begin, end] 的区间找 k 个数字
 *
 * @param begin
 * @param end
 * @param k
 * @param pre
 */
private void dfs(int begin, int end, int k, LinkedList<Integer> pre) {
    if (k == 0) {
        res.add((LinkedList<Integer>) pre.clone());
        return;
    }
    // 优化，i 不需要从 begin 到 end，从 begin 到 end - k + 1，就行，不然区间太短，无解
    for (int i = begin; i <= end - k + 1; i++) {
        pre.addLast(i);
        dfs(i + 1, end, k - 1, pre);
        pre.removeLast();
    }
}
```

###### 问题39：combination sum

给你一个无重复元素的整数数组candidates和一个目标整数target，找出candidates中可以使数字和为目标数target的所有不同组合，并以列表形式返回。你可以按任意顺序返回这些组合。
candidates中的同一个数字可以无限制重复被选取。如果至少一个数字的被选数量不同，则两种组合是不同的。
对于给定的输入，保证和为target的不同组合数少于150个。
示例 1：
输入：candidates = [2,3,6,7], target = 7
输出：[[2,2,3],[7]]
解释：
2 和 3 可以形成一组候选，2 + 2 + 3 = 7 。注意 2 可以使用多次。
7 也是一个候选， 7 = 7 。
仅有这两种组合。
```java
public List<List<Integer>> combinationSum(int[] candidates, int target);
```

这题用回溯法倒也不难，直接写代码吧。
辅助函数需要`startIndex`是为了避免出现不同顺序的相同结果，比如样例1的[2,2,3]和[2,3,2]。
代码：
```java
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
```


###### 问题40：combination sum ii

给定一个候选人编号的集合candidates和一个目标数target，找出candidates中所有可以使数字和为target的组合。
candidates中的每个数字在每个组合中只能使用一次。
注意：解集不能包含重复的组合。
示例 1:
输入: candidates = [10,1,2,7,6,1,5], target = 8,
输出:
[
[1,1,6],
[1,2,5],
[1,7],
[2,6]
]
```java
public List<List<Integer>> combinationSum2(int[] candidates, int target);
```

这题和问题39不同的是：
1. candidates可能包含重复的元素
2. 每个数字只能使用一次
对于可能包含重复元素的情况，我们还是沿用老套路：对数组进行排序。（问题47也是这个套路）。
定义递归辅助函数：`combinationSum2Helper(int[] candidates, int startIndex, int target, List<Integer> pre)`
其中：
1. candidates为candidates数组
2. startIndex表示当前遍历candidates数组元素的下标
3. target表示所需的和
4. pre表示当前已使用的数字集合
定义递归关系：
1. 递归终止条件：当target等于0时，表示已凑够，将pre加入结果集，返回；当startIndex到达数组末尾，表示无元素可使用，此路不通，返回；如果candidates[startIndex]大于target，由于数组已排序，所以表示此路不通，返回
2. 递归关系：文字描述就比较复杂了，看代码吧。
代码：
```java
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
```


###### 问题216：combination sum iii

找出所有相加之和为n的k个数的组合，且满足下列条件：
只使用数字1到9
每个数字最多使用一次
返回所有可能的有效组合的列表。该列表不能包含相同的组合两次，组合可以以任何顺序返回。
示例 1:
输入: k = 3, n = 7
输出: [[1,2,4]]
解释:
1 + 2 + 4 = 7
没有其他符合的组合了。
示例 2:
输入: k = 3, n = 9
输出: [[1,2,6], [1,3,5], [2,3,4]]
解释:
1 + 2 + 6 = 9
1 + 3 + 5 = 9
2 + 3 + 4 = 9
没有其他符合的组合了。
```java
public List<List<Integer>> combinationSum3(int k, int n);
```

这题也是回溯法可以做，直接写代码吧。
代码：
```java
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
```

###### 问题78：subsets

给你一个整数数组nums，数组中的元素互不相同。返回该数组所有可能的子集（幂集）。
解集不能包含重复的子集。你可以按任意顺序返回解集。
示例 1：
输入：nums = [1,2,3]
输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
示例 2：
输入：nums = [0]
输出：[[],[0]]
```java
public List<List<Integer>> subsets(int[] nums);
```

这题可以用到问题77的解答。问题77是`C(n,k)`，这题是`C(n,0) + C(n,1) + ... + C(n,n)`。
我们先写`C(n,k)`的解答，然后k从0到n循环调用`C(n,k)`。
代码：
```java
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
```
看了我以前的一个提交，效率更高一些，它避免了`res.addAll(subsets(nums, i));`这一步不断地添加临时结果集，而是每找到一个子集就直接加入到最终结果集。
```java
private List<List<Integer>> res;

public List<List<Integer>> subsets(int[] nums) {
    res = new LinkedList<>();
    for (int i = 0; i <= nums.length; i++) {
        combine(nums, i, 0, new LinkedList<>());
    }
    return res;
}

private void combine(int[] nums, int n, int checkIndex, LinkedList<Integer> pre) {
    if (n == 0) {
        res.add((LinkedList<Integer>) pre.clone());
        return;
    }
    for (int i = checkIndex; i < nums.length - n + 1; i++) {
        pre.addLast(nums[i]);
        combine(nums, n - 1, i + 1, pre);
        pre.removeLast();
    }
}
```

###### 问题90：subsets ii

给你一个整数数组nums，其中可能包含重复元素，请你返回该数组所有可能的子集（幂集）。
解集不能包含重复的子集。返回的解集中，子集可以按任意顺序排列。
示例 1：
输入：nums = [1,2,2]
输出：[[],[1],[1,2],[1,2,2],[2],[2,2]]
```java
public List<List<Integer>> subsetsWithDup(int[] nums);
```

这题和问题78基本一样，不同点就是这题允许数组存在重复元素，还是使用老套路，存在重复元素就先排序，然后如果元素A已经遍历过，下一个A就不需要继续判断。
代码：
```java
public List<List<Integer>> subsetsWithDup(int[] nums) {
    int[] copyOf = Arrays.copyOf(nums, nums.length);
    Arrays.sort(copyOf);
    List<List<Integer>> res = new LinkedList<>();
    for (int i = 0; i <= nums.length; i++) {
        res.addAll(subsetsWithDup(copyOf, i));
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
private List<List<Integer>> subsetsWithDup(int[] nums, int k) {
    List<List<Integer>> res = new LinkedList<>();
    subsetsWithDupHelper(nums, 0, k, new LinkedList<>(), res);
    return res;
}

private void subsetsWithDupHelper(int[] nums, int startIndex, int k, LinkedList<Integer> pre, List<List<Integer>> res) {
    if (k == 0) {
        res.add(new ArrayList<>(pre));
        return;
    }
    for (int i = startIndex; i <= nums.length - k; i++) {
        if (i != startIndex && nums[i] == nums[i - 1]) {
            continue;
        }
        pre.addLast(nums[i]);
        subsetsWithDupHelper(nums, i + 1, k - 1, pre, res);
        pre.removeLast();
    }
}
```

###### 问题401：binary watch

二进制手表顶部有4个LED代表小时（0-11），底部的6个LED代表分钟（0-59）。每个LED代表一个0或1，最低位在右侧。
给你一个整数turnedOn，表示当前亮着的LED的数量，返回二进制手表可以表示的所有可能时间。你可以按任意顺序返回答案。
小时不会以零开头：
例如，"01:00"是无效的时间，正确的写法应该是"1:00"。
分钟必须由两位数组成，可能会以零开头：
例如，"10:2" 是无效的时间，正确的写法应该是"10:02"。
示例 1：
输入：turnedOn = 1
输出：["0:01","0:02","0:04","0:08","0:16","0:32","1:00","2:00","4:00","8:00"]
```java
public List<String> readBinaryWatch(int turnedOn);
```

这题其实也不难，就是找出`C(10, turnedOn)`的所有组合情况，然后将组合转换为时间的格式。
代码：
```java
public List<String> readBinaryWatch(int turnedOn) {
    List<String> res = new LinkedList<>();
    readBinaryWatchHelper(0, turnedOn, new LinkedList<>(), res);
    return res;
}

/**
 * 从 [start, 9] 中亮起 turnedOn 个灯泡
 *
 * @param start
 * @param turnedOn
 * @param pre
 * @param res
 */
private void readBinaryWatchHelper(int start, int turnedOn, LinkedList<Integer> pre, List<String> res) {
    if (turnedOn == 0) {
        String time = transform2Time(pre);
        if (time != null) {
            res.add(time);
        }
        return;
    }
    for (int i = start; i < 10; i++) {
        pre.addLast(i);
        readBinaryWatchHelper(i + 1, turnedOn - 1, pre, res);
        pre.removeLast();
    }
}

/**
 * 将亮的灯泡转换为时间
 *
 * @param indexList 亮的灯泡的下标，0~9
 * @return
 */
private String transform2Time(List<Integer> indexList) {
    char[] base = new char[]{'0', '0', '0', '0', '0', '0', '0', '0', '0', '0'};
    for (Integer index : indexList) {
        base[index] = '1';
    }
    String hour = new String(base, 0, 4);
    String minute = new String(base, 4, 6);
    int hourInt = Integer.parseInt(hour, 2);
    int minuteInt = Integer.parseInt(minute, 2);
    if (hourInt > 11 || minuteInt > 59) {
        return null;
    }
    return hourInt + ":" + (minuteInt < 10 ? "0" : "") + minuteInt;
}
```
leetcode官解直接使用了枚举这样的“流氓”方法，也挺有意思，所以面对小的数据集，我们完全可以枚举所有的可能性来求解！代码还是讲究一个kiss原则！
```java
/**
 * 看官方答案，直接枚举出来
 *
 * @param turnedOn
 * @return
 */
public List<String> readBinaryWatch(int turnedOn) {
    LinkedList<String> res = new LinkedList<>();
    for (int hour = 0; hour < 12; hour++) {
        for (int minute = 0; minute < 60; minute++) {
            if (Integer.bitCount(hour) + Integer.bitCount(minute) == turnedOn) {
                res.add(hour + ":" + String.format("%02d", minute));
            }
        }
    }
    return res;
}
```

###### 问题79：word search 

给定一个m x n二维字符网格board和一个字符串单词word。如果word存在于网格中，返回true；否则，返回false。
单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。
```java
public boolean exist(char[][] board, String word);
```

这题就是典型的回溯法来遍历搜索啦，直接写代码吧。
代码：
```java
public boolean exist(char[][] board, String word) {
    boolean[][] used = new boolean[board.length][board[0].length];
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[0].length; j++) {
            if (existHelper(board, i, j, word, 0, used)) {
                return true;
            }
        }
    }
    return false;
}

private boolean existHelper(char[][] board, int i, int j, String word, int index, boolean[][] used) {
    if (index == word.length()) {
        return true;
    }
    if (i == -1 || i == board.length || j == -1 || j == board[0].length) {
        return false;
    }
    if (used[i][j]) {
        return false;
    }
    if (board[i][j] != word.charAt(index)) {
        return false;
    }
    used[i][j] = true;
    boolean res = existHelper(board, i - 1, j, word, index + 1, used) ||
            existHelper(board, i + 1, j, word, index + 1, used) ||
            existHelper(board, i, j - 1, word, index + 1, used) ||
            existHelper(board, i, j + 1, word, index + 1, used);
    used[i][j] = false;
    return res;
}
```
bobo老师在这一题中提到了一个代码小技巧，使用一个for循环来实现向上、下、左、右四个方向移动：
```java
int x = 0, y = 0;
// (x,y) 向四个方向移动的坐标偏移量
int[][] d = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
for (int i = 0; i < d.length; i++) {
    int newX = x + d[i][0];
    int newY = y + d[i][1];
}
```

###### 问题200：number of islands

给你一个由'1'（陆地）和'0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
此外，你可以假设该网格的四条边均被水包围。
示例 1：
输入：grid = [
  ["1","1","1","1","0"],
  ["1","1","0","1","0"],
  ["1","1","0","0","0"],
  ["0","0","0","0","0"]
]
输出：1
示例 2：
输入：grid = [
  ["1","1","0","0","0"],
  ["1","1","0","0","0"],
  ["0","0","1","0","0"],
  ["0","0","0","1","1"]
]
输出：3
```java
public int numIslands(char[][] grid);
```

这题就是很典型的flood fill问题！我们不断地遍历二维网格，每找到一块陆地，就“flood fill”标记其他属于同一个岛屿的其他陆地。每当找到一个没有被标记过的陆地，就表示出现了一个新的岛屿，结果集加一。
代码：
```java
public int numIslands(char[][] grid) {
    int res = 0;
    for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid[0].length; j++) {
            if (grid[i][j] == '1') {
                numIslandsFloodFill(grid, i, j);
                res++;
            }
        }
    }
    return res;
}

/**
 * 将 grid[i][j] 赋值为 2 表示已经标记过
 *
 * @param grid
 * @param i
 * @param j
 */
private void numIslandsFloodFill(char[][] grid, int i, int j) {
    if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length) {
        return;
    }
    if (grid[i][j] == '0' || grid[i][j] == '2') {
        return;
    }
    grid[i][j] = '2';
    numIslandsFloodFill(grid, i - 1, j);
    numIslandsFloodFill(grid, i + 1, j);
    numIslandsFloodFill(grid, i, j - 1);
    numIslandsFloodFill(grid, i, j + 1);
}
```
bobo老师有提到说有个观点认为上面这个解法并不是回溯法，因为它没有回溯回来重置（恢复）某个状态的过程，所以这只能算是一个DFS算法。
其实这样理解也没问题，确实是没有恢复状态的过程，但是对于DFS递归算法来说，你只有在“后序遍历”框架里随便执行一条指令，也可以认为是回溯的过程。这里不用细究，DFS与回溯的关系本来就是你中有我我中有你。

###### 问题130：surrounded regions

###### 问题417：pacific atlantic water flow


8.8