package org.apache.rocketmq.example.quickstart;

/**
 * @author mo
 * @Description 生产者测试
 * @createTime 2024年04月23日 17:25
 */
public class ProducerAlgLinkedListTest {

    public static void main(String[] args) throws Exception {
        ProducerAlgLinkedListTest test = new ProducerAlgLinkedListTest();
        ListNode node5 = new ListNode(5, null);
        ListNode node4 = new ListNode(4, node5);
        ListNode node3 = new ListNode(3, node4);
        ListNode node2 = new ListNode(2, node3);
        ListNode node1 = new ListNode(1, node2);
        ListNode reverseBetween = test.reverseBetween(node1, 1, 3);
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

    public static void displayLinkedList(ListNode head){
        ListNode cur = head;
        while (cur != null){
            System.out.print(cur.val + "->");
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