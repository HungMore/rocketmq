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
labuladong的构造dummy节点的解法也挺有意思，这个方法应该是最优的了，思路也很清晰（这个方法叫头插法）（其实上面那个解法也是头插法，只是没用dummy节点，它的思路也是每次将cur插到pre的前面）：
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

###### 问题83：remove duplicates from sorted list

给定一个已排序的链表的头head，删除所有重复的元素，使每个元素只出现一次。返回已排序的链表。
```java
public ListNode deleteDuplicates(ListNode head);
```

这题感觉不难，定义快慢两个指针，初始化时都指向头节点
然后判断快指针指向的元素值是否等于慢指针的，如果是，快指针往前走一步
如果不是，将慢指针的next指针指向快指针的元素，慢指针指向快指针，快指针往前走一步
当快指针走向null，将慢指针的next指向null。
代码：
```java
public ListNode deleteDuplicates(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null) {
        if (fast.val != slow.val) {
            slow.next = fast;
            slow = fast;
        }
        fast = fast.next;
    }
    if (slow != null) {
        slow.next = null;
    }
    return head;
}
```

###### 问题86：partition list

给你一个链表的头节点head和一个特定值x，请你对链表进行分隔，使得所有小于x的节点都出现在大于或等于x的节点之前。
你应当保留两个分区中每个节点的初始相对位置。
```java
public ListNode partition(ListNode head, int x);
```

这题也不难，定义两个新的链表，链表1记录小于x的元素，链表2记录大于等于x的元素
然后遍历题目给出的链表，如果元素的值小于x，追加到链表1上，否则追加到链表2上
代码：
```java
public ListNode partition(ListNode head, int x) {
    ListNode dummy1 = new ListNode(-1);
    ListNode dummy2 = new ListNode(-2);
    ListNode temp1 = dummy1;
    ListNode temp2 = dummy2;
    ListNode cur = head;
    while (cur != null) {
        if (cur.val < x) {
            temp1.next = cur;
            temp1 = temp1.next;
        } else {
            temp2.next = cur;
            temp2 = temp2.next;
        }
        cur = cur.next;
    }
    temp1.next = dummy2.next;
    temp2.next = null;
    return dummy1.next;
}
```

###### 问题328：odd even linked list

给定单链表的头节点head，将所有索引为奇数的节点和索引为偶数的节点分别组合在一起，然后返回重新排序的列表。
第一个节点的索引被认为是奇数，第二个节点的索引为偶数，以此类推。
请注意，偶数组和奇数组内部的相对顺序应该与输入时保持一致。
你必须在 O(1) 的额外空间复杂度和 O(n) 的时间复杂度下解决这个问题。
```java
public ListNode oddEvenList(ListNode head);
```

这题和问题86分隔链表是一样的，只是一个按照值的大小分割，一个按照索引号分割，直接写代码吧。
代码：
```java
public ListNode oddEvenList(ListNode head) {
    ListNode dummy1 = new ListNode(-1);
    ListNode dummy2 = new ListNode(-2);
    ListNode temp1 = dummy1;
    ListNode temp2 = dummy2;
    ListNode cur = head;
    int index = 1;
    while (cur != null) {
        if (index % 2 == 1) {
            temp1.next = cur;
            temp1 = temp1.next;
        } else {
            temp2.next = cur;
            temp2 = temp2.next;
        }
        cur = cur.next;
        index++;
    }
    temp1.next = dummy2.next;
    temp2.next = null;
    return dummy1.next;
}
```
查看了下我对这题的题解记录，我还写过一个"穿针引线"的解法，也挺不错，可以参考下。（但是我觉得定义两个新的链表然后连上去的解法可能更好写、更通用一点）
```java
public ListNode oddEvenList(ListNode head) {
    if(head == null){
        return head;
    }
    ListNode endOdd = head; // 奇指针
    ListNode beginEven = head.next; // 偶头指针
    ListNode endEven = head.next; // 偶尾指针
    while(endOdd.next != null && endEven.next != null){
        endOdd.next = endEven.next;
        endOdd = endOdd.next;
        endEven.next = endOdd.next;
        endEven = endEven.next;
    }
    endOdd.next = beginEven;
    return head;
}
```

###### 问题2：add two numbers

给你两个非空的链表，表示两个非负的整数。它们每位数字都是按照逆序的方式存储的，并且每个节点只能存储一位数字。
请你将两个数相加，并以相同形式返回一个表示和的链表。
你可以假设除了数字 0 之外，这两个数都不会以0开头。
示例 1：
输入：l1 = [2,4,3], l2 = [5,6,4]
输出：[7,0,8]
解释：342 + 465 = 807.
注意l1和l2的长度可能并不一样
```java
public ListNode addTwoNumbers(ListNode l1, ListNode l2);
```

这题也比较简单，记录一个进位就好啦。
代码：
```java
public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(-1);
    ListNode res = dummy;
    int carry = 0;
    while (l1 != null || l2 != null || carry != 0) {
        int sum = carry;
        if (l1 != null) {
            sum += l1.val;
            l1 = l1.next;
        }
        if (l2 != null) {
            sum += l2.val;
            l2 = l2.next;
        }
        if (sum > 9) {
            sum = sum % 10;
            carry = 1;
        } else {
            carry = 0;
        }
        res.next = new ListNode(sum);
        res = res.next;
    }
    return dummy.next;
}
```

###### 问题445：add two numbers ii

给你两个非空链表来代表两个非负整数。数字最高位位于链表开始位置。它们的每个节点只存储一位数字。将这两数相加会返回一个新的链表。
你可以假设除了数字0之外，这两个数字都不会以零开头。
示例1：
输入：l1 = [7,2,4,3], l2 = [5,6,4]
输出：[7,8,0,7]
```java
public ListNode addTwoNumbers(ListNode l1, ListNode l2);
```

这题比问题2稍难一点，因为需要将链表翻转再逐位求和，将l1、l2翻转我们可以使用栈数据结构，结果的保存我们可以使用头插法。
代码：
```java
public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    Stack<Integer> stack1 = new Stack<>();
    Stack<Integer> stack2 = new Stack<>();
    while (l1 != null) {
        stack1.push(l1.val);
        l1 = l1.next;
    }
    while (l2 != null) {
        stack2.push(l2.val);
        l2 = l2.next;
    }
    ListNode dummy = new ListNode(-1);
    int carry = 0;
    while (!stack1.isEmpty() || !stack2.isEmpty() || carry != 0) {
        int sum = carry;
        if (!stack1.isEmpty()) {
            sum += stack1.pop();
        }
        if (!stack2.isEmpty()) {
            sum += stack2.pop();
        }
        if (sum > 9) {
            sum = sum % 10;
            carry = 1;
        } else {
            carry = 0;
        }
        dummy.next = new ListNode(sum, dummy.next);
    }
    return dummy.next;
}
```

###### 问题203：remove linked list elements

给你一个链表的头节点head和一个整数val，请你删除链表中所有满足Node.val==val的节点，并返回新的头节点。
示例 1：
输入：head = [1,2,6,3,4,5,6], val = 6
输出：[1,2,3,4,5]
```java
public ListNode removeElements(ListNode head, int val);
```

这题挺简单的，定义dummy虚拟头节点，然后遍历链表，如果节点的值不等于val，将节点连接到dummy链表的后面。
代码：
```java
public ListNode removeElements(ListNode head, int val) {
    ListNode dummy = new ListNode(-1);
    ListNode tail = dummy;
    while (head != null) {
        if (head.val != val) {
            tail.next = head;
            tail = tail.next;
        }
        head = head.next;
    }
    tail.next = null;
    return dummy.next;
}
```

###### 问题82：remove duplicates from sorted list ii

给定一个已排序的链表的头head，删除原始链表中所有重复数字的节点，只留下不同的数字。返回已排序的链表。
示例 1：
输入：head = [1,2,3,3,4,4,5]
输出：[1,2,5]
```java
public ListNode deleteDuplicates(ListNode head);
```

因为要返回新的链表，所以定义dummy节点链表。
然后使用快慢指针遍历head链表（快慢指针初始化指向两个相邻的节点），如果快指针的值等于慢指针的值，快指针往前走一步，并使用一个标志变量标识**当前慢指针是重复的**（标志变量初始化为不重复）；如果快指针不等于慢指针，判断标志位是否重复，如果是，说明`[慢指针, 快指针)`这个区间是重复的，将慢指针指向快指针，快指针往前走一步，并重置标志变量为不重复；如果标志位不是重复，说明快慢指针不同，将慢指针的节点连接到dummy链表的后面，慢指针指向快指针，快指针往前走一步。当快指针走到head链表的末尾的时候，还有判断下最后一个slow节点，如果当前标志位为重复，则将dummy链表的末尾置为空；如果标志位不为重复，dummy链表追加上最后这个slow节点。（经常都是要debug才知道dummy链表的末尾没有处理。。。）
代码：
```java
public ListNode deleteDuplicates(ListNode head) {
    ListNode dummy = new ListNode(-1);
    ListNode tail = dummy;
    if (head != null) {
        ListNode slow = head;
        ListNode fast = head.next;
        boolean isDuplicate = false;
        while (fast != null) {
            if (fast.val == slow.val) {
                isDuplicate = true;
            } else if (isDuplicate) {
                slow = fast;
                isDuplicate = false;
            } else {
                tail.next = slow;
                tail = tail.next;
                slow = fast;
            }
            fast = fast.next;
        }
        if (isDuplicate) {
            tail.next = null;
        } else {
            tail.next = slow;
        }
    }
    return dummy.next;
}
```

###### 问题21：merge two sorted lists

将两个升序链表合并为一个新的升序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
示例 1：
输入：l1 = [1,2,4], l2 = [1,3,4]
输出：[1,1,2,3,4,4]
```java
public ListNode mergeTwoLists(ListNode list1, ListNode list2);
```

这题就是归并的过程，很简单啦。因为要产生一个新的链表，所以也是使用dummy节点。
代码：
```java
public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
    ListNode dummy = new ListNode(-1);
    ListNode tail = dummy;
    while (list1 != null && list2 != null) {
        if (list1.val <= list2.val) {
            tail.next = list1;
            list1 = list1.next;
        } else {
            tail.next = list2;
            list2 = list2.next;
        }
        tail = tail.next;
    }
    if (list1 != null) {
        tail.next = list1;
    } else {
        tail.next = list2;
    }
    return dummy.next;
}
```

###### 问题24：swap nodes in pairs

给你一个链表，两两交换其中相邻的节点，并返回交换后链表的头节点。你必须在不修改节点内部的值的情况下完成本题（即，只能进行节点交换）。
示例 1：
输入：head = [1,2,3,4]
输出：[2,1,4,3]
```java
public ListNode swapPairs(ListNode head);
```

试试用穿针引线的方式来做一做。我们需要两两一对进行内部交换，交换后需要连接上其他的“对”，所以需要两个指针记录“对”的前继节点、后继节点。
代码：
```java
public ListNode swapPairs(ListNode head) {
    ListNode dummy = new ListNode(-1, head);
    ListNode pairPre = dummy;
    ListNode pairPost = null;
    // head和head.next构建一对
    while (head != null && head.next != null) {
        pairPost = head.next.next;
        pairPre.next = head.next;
        head.next.next = head;
        head.next = pairPost;
        pairPre = head;
        head = pairPost;
    }
    return dummy.next;
}
```
