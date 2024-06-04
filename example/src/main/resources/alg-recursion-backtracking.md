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

8.3