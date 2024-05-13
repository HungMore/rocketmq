package org.apache.rocketmq.example.quickstart;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

/**
 * @author mo
 * @Description 生产者测试
 * @createTime 2024年04月23日 17:25
 */
public class ProducerAlgLinkedListTest {

    public static void main(String[] args) throws Exception {
        ProducerAlgLinkedListTest test = new ProducerAlgLinkedListTest();
        int[] arr = {1, 2, 3, 4};
        ListNode head = createLinkedList(arr);
        System.out.println("before: ");
        displayLinkedList(head);
        test.reorderList(head);
        System.out.println("after: ");
        displayLinkedList(head);


//        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
//        producer.setNamesrvAddr("127.0.0.1:9876");
//        producer.start();
//        Message message2 = new Message("order_topic",
//                null,
//                "key1",
//                JSON.toJSONString("").getBytes(StandardCharsets.UTF_8));
//        producer.send(message2, new SelectMessageQueueByHash(), "12");
    }

    public ListNode reverseList(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        ListNode next = null;
        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

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

    // 1 2 1
    // -1 1 1
    // -1 2
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

    public ListNode addTwoNumbersII(ListNode l1, ListNode l2) {
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

    public ListNode deleteDuplicatesII(ListNode head) {
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

    public void deleteNode(ListNode node) {
        ListNode next = node.next;
        node.val = next.val;
        node.next = next.next;
    }

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

    public static ListNode createLinkedList(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        ListNode head = new ListNode(arr[0]);
        ListNode cur = head;
        for (int i = 1; i < arr.length; i++) {
            cur.next = new ListNode(arr[i]);
            cur = cur.next;
        }
        return head;
    }

    public static void displayLinkedList(ListNode head) {
        ListNode cur = head;
        while (cur != null) {
            System.out.print(cur.val + " -> ");
            cur = cur.next;
        }
        System.out.println("null");
    }

}

class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}