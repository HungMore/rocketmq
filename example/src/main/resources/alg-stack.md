## 栈相关问题

JDK提供的Stack类是线程安全的，性能不高。如果我们不需要考虑线程安全问题，可以使用Deque的实现类来完成栈的操作。（同理，Vector是线程安全的ArrayList，但整体性能不高，如果考虑线程安全问题也可以使用CopyOnWriteArrayList或者Collections.synchronizedList()代替）  

sonar给出的替换方案（不需要考虑线程安全问题的前提下）：
- ArrayList or LinkedList instead of Vector
- Deque instead of Stack
- HashMap instead of Hashtable
- StringBuilder instead of StringBuffer

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


6.2