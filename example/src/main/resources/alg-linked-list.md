## 链表相关问题

链表的相关问题，主要有以下的一些解题思路或者解题技巧：
1. dummy节点。当需要构建一个新的链表的时候，我们都可以考虑使用dummy虚拟头节点，使用头插法或者尾插法完成链表的构建。
2. 快慢指针。快慢指针在链表中也是使用得很频繁，快慢指针也有很多不同的形式，比如相隔指定距离的快慢指针、步长翻倍的快慢指针等等。其中步长翻倍的快慢指针可以快速找到链表的中点。
3. “穿针引线”。“穿针引线”毫无疑问是很重要的一个部分，尤其是针对不能改变节点内部的值，只能改变next指针的情形。
4. 抽象数据结构辅助。链表加上特定抽象数据结构（如栈、ArrayList等），一般都有助于我们解决问题（虽然空间复杂度会有所提高）。
5. 如果题目要求重新修改链表的结构，一定要记得断开新链表的末尾（解题时经常要debug才能发现这个问题）。
6. 如果题目要求可以修改节点的内部值，那么节点的拷贝/删除就很简单。（参考问题237）
7. 函数的副作用一定要考虑好，如果函数的逻辑修改了输入参数的内部结构，这个要做到心里有底，在实际的开发中尽量避免。

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

###### 问题25：reverse nodes in k-group

给你链表的头节点head，每k个节点一组进行翻转，请你返回修改后的链表。
k是一个正整数，它的值小于或等于链表的长度。如果节点总数不是k的整数倍，那么请将最后剩余的节点保持原有顺序。
你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。
示例 1：
输入：head = [1,2,3,4,5], k = 2
输出：[2,1,4,3,5]
示例 2：
输入：head = [1,2,3,4,5], k = 3
输出：[3,2,1,4,5]
```java
public ListNode reverseKGroup(ListNode head, int k);
```

问题24是这题的一个特例。不过这题还是可以参考问题24的思路：k个节点一组，所以我们需要使用两个指针记录组外的前继节点和后继节点。同时，为了保证组内翻转后，能成功连上组外的后继节点，我们需要一个变量来保存组内原先的第一个节点(其实就是代码中的head节点)。题目还强调如果节点数不是k的整数倍，剩余最后一组要保持原顺序，所以我们不能简单地对每一组都使用头插法翻转，而是先遍历一遍够不够k个，够就头插法翻转，不够就原序接上。
代码：
```java
public ListNode reverseKGroup(ListNode head, int k) {
    ListNode dummy = new ListNode(-1, head);
    ListNode groupPre = dummy;
    ListNode groupPost = null;
    while (head != null) {
        ListNode groupTail = getGroupTail(head, k);
        // 后面剩余有完整的一组，剩余节点大于等于k个
        if (groupTail != null) {
            groupPost = groupTail.next;
            groupPre.next = reverseK(head, k);
            head.next = groupPost;
            groupPre = head;
            head = groupPost;
            // 后面不足一组
        } else {
            groupPre.next = head;
            head = null;
        }
    }
    return dummy.next;
}

/**
 * 返回以head为组首的组内最后一个元素
 * 如果剩余节点数不足k个，无法构成一组，返回空
 *
 * @param head
 * @param k
 * @return
 */
private ListNode getGroupTail(ListNode head, int k) {
    int count = 0;
    while (head != null && count < k - 1) {
        count++;
        head = head.next;
    }
    return head;
}

/**
 * 翻转k个节点（链表确保有k个节点或以上），并返回新的头节点，组内最后一个节点指向null的！
 *
 * @param head
 * @param k
 * @return
 */
private ListNode reverseK(ListNode head, int k) {
    ListNode dummy = new ListNode(-1);
    int count = 0;
    while (count < k) {
        ListNode insert = head;
        head = head.next;
        insert.next = dummy.next;
        dummy.next = insert;
        count++;
    }
    return dummy.next;
}
```

###### 问题147：insertion sort list

给定单个链表的头head，使用插入排序对链表进行排序，并返回排序后链表的头。
插入排序算法的步骤:
插入排序是迭代的，每次只移动一个元素，直到所有元素可以形成一个有序的输出列表。
每次迭代中，插入排序只从输入数据中移除一个待排序的元素，找到它在序列中适当的位置，并将其插入。
重复直到所有输入数据插入完为止。
```java
public ListNode insertionSortList(ListNode head);
```

插入排序很熟悉了，直接写代码吧。
代码：
```java
public ListNode insertionSortList(ListNode head) {
    ListNode dummy = new ListNode(Integer.MIN_VALUE);
    while (head != null) {
        ListNode pre = dummy;
        while (pre.next != null && pre.next.val <= head.val) {
            pre = pre.next;
        }

        ListNode insert = head;
        head = head.next;
        insert.next = pre.next;
        pre.next = insert;
    }
    return dummy.next;
}
```

###### 问题148：sort list

给你链表的头结点head，请将其按升序排列并返回排序后的链表。
示例 1：
输入：head = [4,2,1,3]
输出：[1,2,3,4]
进阶：你可以在O(nlogn)时间复杂度和常数级空间复杂度下，对链表进行排序吗？
```java
public ListNode sortList(ListNode head);
```

bobo老师说用归并排序，递归版本的归并排序确实可以实现O(nlogn)的时间复杂度，但是无法实现常数级的空间复杂度，所以这题的要求就是用迭代版本的归并排序。
找寻划分归并排序的中间节点有个小技巧，就是使用快慢指针，慢指针走一步，快指针走两步，当快指针到达尾部时，慢指针就在中间。
迭代版暂时写不出来，先不做吧。`todo`
代码：
```java

```
 
###### 问题237：delete node in a linked list

有一个单链表的head，我们想删除它其中的一个节点node。
给你一个需要删除的节点node。你将无法访问第一个节点head。
链表的所有值都是唯一的，并且保证给定的节点node不是链表中的最后一个节点。
删除给定的节点。注意，删除节点并不是指从内存中删除它。这里的意思是：
    给定节点的值不应该存在于链表中。
    链表中的节点数应该减少 1。
    node前面的所有值顺序相同。
    node后面的所有值顺序相同。
自定义测试：
对于输入，你应该提供整个链表head和要给出的节点node。node不应该是链表的最后一个节点，而应该是链表中的一个实际节点。
我们将构建链表，并将节点传递给你的函数。
输出将是调用你函数后的整个链表。
示例 1：
输入：head = [4,5,1,9], node = 5
输出：[4,1,9]
解释：指定链表中值为 5 的第二个节点，那么在调用了你的函数之后，该链表应变为 4 -> 1 -> 9
```java
public void deleteNode(ListNode node);
```

这题的题目有点绕，简单来说，就是通过修改node节点的值的方式来删除一个节点，而不是通过修改引用的指向的方式来删除。
整体思路也很简单，我们将待删除节点的后一个节点的值拷贝到待删除节点，然后将待删除节点的后一个节点的next指针也拷贝到待删除节点的next指针就可以。
相当于把next节点完完整整地拷贝到current节点，current节点的值就消失了，但是从实际的内存上来说，消失的是next节点。（我替代了你，消失的既是你，也是我！）
代码：
```java
public void deleteNode(ListNode node) {
    ListNode next = node.next;
    node.val = next.val;
    node.next = next.next;
}
```

这题也说明了链表的题并不都是“穿针引线”，有的时候也可以通过修改值来实现。‘

###### 问题19：remove Nth node from end of list

给你一个链表，删除链表的倒数第n个结点，并且返回链表的头结点。
示例 1：
输入：head = [1,2,3,4,5], n = 2
输出：[1,2,3,5]
```java
public ListNode removeNthFromEnd(ListNode head, int n);
```

这题是老熟人了，定义快慢两个指针
快指针先走n步，然后慢指针起步，快指针每走一步，慢指针也跟着走一步，快慢指针之间的距离总是维持在n
当快指针到达链表的末尾，值为null时，慢指针指向的就是倒数第n个节点。
这题因为要删除倒数第n个节点，所以我们要找的是倒数第n+1个节点，然后将倒数第n+1的节点的next指针指向倒数第n个节点的next节点
我们初始化慢指针的时候，可以让它指向dummy节点（dummy.next=head），这样慢指针最终走到的就是倒数第n+1个节点，并且这样设定也解决了移除头节点的特例
代码：
```java
public ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode fast = head;
    int i = 0;
    while (i < n) {
        fast = fast.next;
        i++;
    }
    ListNode dummy = new ListNode(-1, head);
    ListNode slow = dummy;
    while (fast != null) {
        fast = fast.next;
        slow = slow.next;
    }
    slow.next = slow.next.next;
    return dummy.next;
}
```

###### 问题61：rotate list

给你一个链表的头节点head，旋转链表，将链表每个节点向右移动k个位置。
示例 1：
输入：head = [1,2,3,4,5], k = 2
输出：[4,5,1,2,3]
```java
public ListNode rotateRight(ListNode head, int k);
```

做完`问题19：删除倒数第N个节点`以后，这题的思路就比较明确了。
我们要找到倒数第k个节点以及倒数第k+1个节点，将倒数第k+1个节点的next指针置为空，然后将倒数第k个节点作为头节点
这题因为要将链表的最后一个节点连接上链表的第一个节点，所以我们希望快指针不要走到null，而是走到链表的最后一个节点。
我们初始化快指针为head，快指针先走k-1步，慢指针起步，当快指针走到链表的最后一个节点时，慢指针就是倒数第k个节点。
如果将慢指针初始化为dummy节点（dummy.next=head），那么慢指针就是倒数第k+1个节点。
代码：
```java
public ListNode rotateRight(ListNode head, int k) {
    int len = 0;
    ListNode cur = head;
    while (cur != null) {
        len++;
        cur = cur.next;
    }
    if (len == 0) {
        return head;
    }
    // 如果k大于等于链表的长度，需要取余
    if (k >= len) {
        k = k % len;
    }
    // 如果k等于0，相当于不旋转，直接返回
    if (k == 0) {
        return head;
    }
    ListNode fast = head;
    int i = 1;
    while (i < k) {
        fast = fast.next;
        i++;
    }
    ListNode dummy = new ListNode(-1, head);
    ListNode slow = dummy;
    while (fast.next != null) {
        fast = fast.next;
        slow = slow.next;
    }
    ListNode next = slow.next;
    slow.next = null;
    fast.next = head;
    return next;
}
```
官方题解也不错的，可以看看：[官方题解](https://leetcode.cn/problems/rotate-list/solutions/681812/xuan-zhuan-lian-biao-by-leetcode-solutio-woq1/)

###### 问题143：reorder list

给定一个单链表L的头节点head，单链表L表示为：
L0 → L1 → … → Ln - 1 → Ln
请将其重新排列后变为：
L0 → Ln → L1 → Ln - 1 → L2 → Ln - 2 → …
不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
示例 1：
输入：head = [1,2,3,4]
输出：[1,4,2,3]
```java
public void reorderList(ListNode head);
```

这个重排就类似于高斯的等差数列求和，首尾不断组合
我们可以使用一个栈，将链表的节点全部压入栈（以实现逆序）
然后一个指针指向头节点，记作遍历指针，一个指针指向栈顶，记作栈顶指针
遍历指针指向的节点插入链表，往后走一步
栈顶指针指向的节点插入链表，弹栈
直至两个指针到达链表的中心。如果链表的元素总数是偶数个，两个指针各走一半即可；如果为奇数，中间的节点再连接到链表最后。
代码：
```java
public void reorderList(ListNode head) {
    ListNode cur = head;
    Deque<ListNode> stack = new LinkedList<>();
    while (cur != null) {
        stack.push(cur);
        cur = cur.next;
    }
    int len = stack.size();
    ListNode dummy = new ListNode(-1);
    ListNode tail = dummy;
    cur = head;
    for (int i = 0; i < len / 2; i++) {
        tail.next = cur;
        cur = cur.next;
        tail = tail.next;
        tail.next = stack.pop();
        tail = tail.next;
    }
    if (len % 2 == 1) {
        tail.next = cur;
        tail.next.next = null;
    } else {
        tail.next = null;
    }
}
```
[官方解答](https://leetcode.cn/problems/reorder-list/solutions/452867/zhong-pai-lian-biao-by-leetcode-solution/)还提供了两种解法
第一种是将ListNode的节点按顺序存入到一个ArrayList中，然后通过下标的方式直接获取首尾的元素，也就是i=0，j=size-1，然后i、j不断靠近，直至i>=j
第二种是`寻找链表中点 + 链表逆序 + 合并链表`，首先使用快慢指针（快指针走两步慢指针走一步）找到链表的中点，然后将链表的后半部分逆序，然后再与前半部分归并。


###### 问题234：palindrome linked list

给你一个单链表的头节点head，请你判断该链表是否为回文链表。如果是，返回true；否则，返回false。
示例 1：
输入：head = [1,2,2,1]
输出：true
```java
public boolean isPalindrome(ListNode head);
```

使用栈来做的话就很简单啦（不断比较栈顶指针和遍历指针是否相等就可以），不过做完`问题143 重排链表`以后，这题也可以使用`寻找中心节点+链表逆序`的方式来判断是否回文。
将后半段链表逆序，然后判断是否一致。
代码：
```java
public boolean isPalindrome(ListNode head) {
    ListNode slow = head;
    ListNode fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }
    ListNode secondHalf = fast == null ? slow : slow.next;
    ListNode reverseNode = reverseNode(secondHalf);
    while (reverseNode != null) {
        if (head.val != reverseNode.val) {
            return false;
        }
        reverseNode = reverseNode.next;
        head = head.next;
    }
    return true;
}

private ListNode reverseNode(ListNode head) {
    ListNode dummy = new ListNode(-1);
    while (head != null) {
        ListNode insert = head;
        head = head.next;
        insert.next = dummy.next;
        dummy.next = insert;
    }
    return dummy.next;
}
```

但是这样写这个函数是有副作用的，人家只是希望判断下是否是回文，你却在函数内部将链表的内部结构都给改了，这是不太对的，所以官方解答也提议要将链表还原回去。不过，即便还原回去了，这个方法在并发执行的时候可能还会出现各式各样的问题，解题学习可以这么做，实际工作就不推荐了。