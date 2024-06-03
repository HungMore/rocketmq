## 递归和回溯相关问题

回溯法主要用在树形问题上。
树形问题是分析的过程中从上向下遍历多种不同的可能性。
回溯法一般可以使用递归进行代码实现。

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
该递归函数的递归结果：
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
        pre.append(myLetter);
        combinations(digits, digitIndex + 1, pre, res);
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

