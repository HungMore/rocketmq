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