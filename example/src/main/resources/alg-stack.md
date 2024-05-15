## 栈相关问题

JDK提供的Stack类是线程安全的，性能不高。如果我们不需要考虑线程安全问题，可以使用Deque的实现类来完成栈的操作。（同理，Vector是线程安全的ArrayList，但整体性能不高，如果考虑线程安全问题也可以使用CopyOnWriteArrayList或者Collections.synchronizedList()代替）  

sonar给出的替换方案（不需要考虑线程安全问题的前提下）：
- ArrayList or LinkedList instead of Vector
- Deque instead of Stack
- HashMap instead of Hashtable
- StringBuilder instead of StringBuffer

栈和递归的关系是很紧密的，通过做题要深刻理解它们两者的联系。
我们可以使用栈数据结构来模拟系统的调用栈，进而写出非递归的程序。
应该说栈是函数调用的基础，能想出栈的人，确实是很天才！

###### 问题20：valid parentheses

给定一个只包括'('，')'，'{'，'}'，'['，']'的字符串s，判断字符串是否有效。
有效字符串需满足：
左括号必须用相同类型的右括号闭合。
左括号必须以正确的顺序闭合。
每个右括号都有一个对应的相同类型的左括号。
示例 1：
输入：s = "()"
输出：true
```java
public boolean isValid(String s);
```

这题很简单啦，使用栈的数据结构，遇到左括号就入栈，遇到右括号就弹栈，判断弹出的左括号是否和当前右括号匹配，如果不匹配，返回false；否则遍历下一个字符。
代码：
```java
public boolean isValid(String s) {
    Deque<Character> stack = new LinkedList<>();
    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (c == '(' || c == '[' || c == '{') {
            stack.push(c);
        } else if (stack.isEmpty() || !isMatch(stack.pop(), c)) {
            return false;
        }
    }
    return stack.isEmpty();
}

private boolean isMatch(char left, char right) {
    return (left == '[' && right == ']') || (left == '(' && right == ')') || (left == '{' && right == '}');
}
```

###### 问题150：evaluate reverse polish notation

给你一个字符串数组tokens，表示一个根据逆波兰表示法表示的算术表达式。
请你计算该表达式。返回一个表示表达式值的整数。
注意：
有效的算符为'+'、'-'、'*'和'/'。
每个操作数（运算对象）都可以是一个整数或者另一个表达式。
两个整数之间的除法总是向零截断。
表达式中不含除零运算。
输入是一个根据逆波兰表示法表示的算术表达式。
答案及所有中间计算结果可以用32位整数表示。
示例 1：
输入：tokens = ["2","1","+","3","*"]
输出：9
解释：该算式转化为常见的中缀算术表达式为：((2 + 1) * 3) = 9
```java
public int evalRPN(String[] tokens);
```

这题也不难啦，使用栈数据结构可以解决。
遍历字符串数组，如果当前字符串是数字，将数字压入栈；如果当前字符串是算术运算符，从栈中弹出两个元素，进行算术运算，并将结果压入栈中。
遍历完数组，栈中剩下的元素就是最终结果。
代码：
```java
public int evalRPN(String[] tokens) {
    Deque<Integer> stack = new LinkedList<>();
    for (String token : tokens) {
        if (token.equals("+")) {
            Integer num2 = stack.pop();
            Integer num1 = stack.pop();
            stack.push(num1 + num2);
        } else if (token.equals("-")) {
            Integer num2 = stack.pop();
            Integer num1 = stack.pop();
            stack.push(num1 - num2);
        } else if (token.equals("*")) {
            Integer num2 = stack.pop();
            Integer num1 = stack.pop();
            stack.push(num1 * num2);
        } else if (token.equals("/")) {
            Integer num2 = stack.pop();
            Integer num1 = stack.pop();
            stack.push(num1 / num2);
        } else {
            stack.push(Integer.parseInt(token));
        }
    }
    return stack.pop();
}
```

###### 问题71：simplify path

给你一个字符串path，表示指向某一文件或目录的Unix风格绝对路径（以'/'开头），请你将其转化为更加简洁的规范路径。
在Unix风格的文件系统中，一个点（.）表示当前目录本身；此外，两个点（..）表示将目录切换到上一级（指向父目录）；两者都可以是复杂相对路径的组成部分。任意多个连续的斜杠（即，'//'）都被视为单个斜杠'/'。对于此问题，任何其他格式的点（例如，'...'）均被视为文件/目录名称。
请注意，返回的规范路径必须遵循下述格式：
始终以斜杠'/'开头。
两个目录名之间必须只有一个斜杠'/'。
最后一个目录名（如果存在）不能以'/'结尾。
此外，路径仅包含从根目录到目标文件或目录的路径上的目录（即，不含'.'或'..'）。
返回简化后得到的规范路径。
示例 1：
输入：path = "/home/"
输出："/home"
解释：注意，最后一个目录名后面没有斜杠。 
示例 2：
输入：path = "/../"
输出："/"
解释：从根目录向上一级是不可行的，因为根目录是你可以到达的最高级。
示例 3：
输入：path = "/home//foo/"
输出："/home/foo"
解释：在规范路径中，多个连续斜杠需要用一个斜杠替换。
示例 4：
输入：path = "/a/./b/../../c/"
输出："/c"
提示：
1 <= path.length <= 3000
path 由英文字母，数字，'.'，'/' 或 '_' 组成。
path 是一个有效的 Unix 风格绝对路径。
```java
public String simplifyPath(String path);
```

这题也不难，使用栈数据结构可以解决（用栈存目录名）。
首先我们使用'/'对path进行分割，分割得到字符串数据。
遍历字符串数组，如果字符串为空串，直接跳过；如果字符串为'.'，直接跳过；如果字符串为'..'，我们从栈中弹出一个元素（如果栈为空就不弹）；否则将字符串压入栈。
遍历完字符串，栈中留存的就是自底向上的目录名，每弹出一个元素，在目录名前拼接上'/'就是最终的结果。
代码：
```java
public String simplifyPath(String path) {
    Deque<String> stack = new LinkedList<>();
    String[] split = path.split("/");
    for (String s : split) {
        if ("..".equals(s)) {
            if (!stack.isEmpty()) {
                stack.pop();
            }
        } else if (!"".equals(s) && !".".equals(s)) {
            stack.push(s);
        }
    }
    StringBuilder stringBuilder = new StringBuilder();
    if (stack.isEmpty()) {
        stringBuilder.append("/");
    } else {
        while (!stack.isEmpty()) {
            stringBuilder.insert(0, "/" + stack.pop());
        }
    }
    return stringBuilder.toString();
}
```
其实这题用双端队列会好一点啦，后进先出规范化的时候使用栈，拼接最后的结果的时候，使用队列从前往后遍历会方便一点。


6.2 & 6.3

###### 问题341：flatten nested list iterator

