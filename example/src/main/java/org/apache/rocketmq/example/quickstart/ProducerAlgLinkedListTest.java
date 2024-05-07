package org.apache.rocketmq.example.quickstart;

/**
 * @author mo
 * @Description 生产者测试
 * @createTime 2024年04月23日 17:25
 */
public class ProducerAlgLinkedListTest {

    public static void main(String[] args) throws Exception {
        ProducerAlgLinkedListTest test = new ProducerAlgLinkedListTest();
        int[] arr = {1, 2, 3, 4, 5};
        ListNode head = createLinkedList(arr);
        displayLinkedList(head);
        ListNode reverseBetween = test.reverseBetween(head, 2, 5);
        displayLinkedList(reverseBetween);


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