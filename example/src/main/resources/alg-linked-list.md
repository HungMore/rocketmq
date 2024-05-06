## 链表相关问题

###### 问题206：reverse linked list 

给你单链表的头节点head，请你反转链表，并返回反转后的链表。
```java
public ListNode reverseList(ListNode head); 
```

这题的解法有很多，比如我们可以使用栈的数据结构来将其反转，但是这样的空间复杂度就为O(n)。
现在我们尝试下使用O(1)的空间复杂度来完成这道题。
代码：
```java
public ListNode reverseList(ListNode head) {
    ListNode pre = null;
    ListNode cur = head;
    // next指针是依托于cur的，所以也可以在cur!=null以后再去声明
    ListNode next = null;
    while (cur != null) {
        next = cur.next;
        cur.next = pre;
        pre = cur;
        cur = next;
    }
    return pre;
}
```
labuladong的构造dummy节点的解法也挺有意思，这个方法应该是最优的了，思路也很清晰（这个方法叫头插法）：
```java
public ListNode reverseList(ListNode head) {
    // 这个小技巧真不错呢，需要新建一个链表的，都用个dummy节点！
    ListNode dummy = new ListNode(-1);
    ListNode cur = head;
    while (cur != null) {
        ListNode insert = cur;
        cur = cur.next;
        insert.next = dummy.next;
        dummy.next = insert;
    }
    return dummy.next;
}
```
利用递归栈来翻转的解法也挺有意思，而且这可以和二叉树的后序遍历联系起来（其实我没理解这个解法，后续再学习吧`todo`）：
```java
public ListNode reverseList(ListNode head) {
    if (head == null || head.next == null) {
        return head;
    }
    ListNode newHead = reverseList(head.next);
    head.next.next = head;
    head.next = null;
    return newHead;
}
```

###### 问题92：reverse linked list ii

给你单链表的头指针head和两个整数left和right，其中left<=right。请你反转从位置left到位置right的链表节点，返回反转后的链表。
```java
public ListNode reverseBetween(ListNode head, int left, int right);
```

首先使用dummy节点来表示头节点的前一个节点（因为头节点也可能参与翻转）
需要一个指针记录翻转区间的前一个节点，然后使用问题206的头插法方式将翻转区间内的节点逐个连接到这个节点后面，进而实现翻转
最后，需要一个指针记录翻转区间的第一个节点，翻转完成后它要指向翻转区间外的后序节点
代码：
```java
public ListNode reverseBetween(ListNode head, int left, int right) {
    ListNode dummy = new ListNode(-1, head);
    ListNode pre = dummy;
    ListNode cur = head;
    int index = 1;
    while (index < left) {
        pre = cur;
        cur = cur.next;
        index++;
    }
    ListNode beforeLeftNode = pre;
    ListNode leftNode = cur;
    while (index <= right) {
        ListNode insert = cur;
        cur = cur.next;
        insert.next = beforeLeftNode.next;
        beforeLeftNode.next = insert;
        index++;
    }
    leftNode.next = cur;
    return dummy.next;
}
```

5.2