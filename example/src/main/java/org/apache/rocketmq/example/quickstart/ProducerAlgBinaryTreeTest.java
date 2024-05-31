package org.apache.rocketmq.example.quickstart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author mo
 * @Description 生产者测试
 * @createTime 2024年05月14日 10:07
 */
public class ProducerAlgBinaryTreeTest {

    public static void main(String[] args) throws Exception {
        ProducerAlgBinaryTreeTest test = new ProducerAlgBinaryTreeTest();
        System.out.println();

//        System.out.println(producerAlgTest.threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
//        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
//        producer.setNamesrvAddr("127.0.0.1:9876");
//        producer.start();
//        Message message2 = new Message("order_topic",
//                null,
//                "key1",
//                JSON.toJSONString("").getBytes(StandardCharsets.UTF_8));
//        producer.send(message2, new SelectMessageQueueByHash(), "12");
        TreeNode t5 = new TreeNode(5);
        TreeNode t3 = new TreeNode(3);
        TreeNode t6 = new TreeNode(6);
        TreeNode t2 = new TreeNode(2);
        TreeNode t4 = new TreeNode(4);
        TreeNode t7 = new TreeNode(7);
        t5.left = t3;
        t5.right = t6;
        t3.left = t2;
        t3.right = t4;
        t6.right = t7;
        System.out.println(test.deleteNode(t5, 3));
    }

    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    public int minDepthWrong(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.min(minDepthWrong(root.left), minDepthWrong(root.right));
    }

    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null) {
            return 1 + minDepth(root.right);
        }
        if (root.right == null) {
            return 1 + minDepth(root.left);
        }
        return 1 + Math.min(minDepth(root.left), minDepth(root.right));
    }

    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return root;
        }
        TreeNode newLeft = invertTree(root.left);
        TreeNode newRight = invertTree(root.right);
        root.left = newRight;
        root.right = newLeft;
        return root;
    }

    public TreeNode invertTreePreorder(TreeNode root) {
        if (root == null) {
            return root;
        }
        TreeNode left = root.left;
        root.left = root.right;
        root.right = left;
        invertTree(root.left);
        invertTree(root.right);
        return root;
    }

    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p != null && q != null) {
            return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
        } else {
            return p == q;
        }
    }

    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isSymmetric(root.left, root.right);
    }

    private boolean isSymmetric(TreeNode p, TreeNode q) {
        if (p != null && q != null) {
            return p.val == q.val && isSymmetric(p.left, q.right) && isSymmetric(p.right, q.left);
        } else {
            return p == q;
        }
    }

    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + countNodes(root.left) + countNodes(root.right);
    }

    public boolean isBalanced(TreeNode root) {
        return isBalancedHeight(root) != -1;
    }

    /**
     * 辅助函数，判断二叉树是否是平衡二叉树，如果不是，返回-1，如果是，返回其高度
     *
     * @param root
     * @return -1 不是平衡二叉树
     */
    private int isBalancedHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int balancedHeightLeft = isBalancedHeight(root.left);
        int balancedHeightRight = isBalancedHeight(root.right);
        if (balancedHeightLeft == -1 || balancedHeightRight == -1) {
            return -1;
        } else if (Math.abs(balancedHeightLeft - balancedHeightRight) <= 1) {
            return 1 + Math.max(balancedHeightLeft, balancedHeightRight);
        } else {
            return -1;
        }
    }

    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        if (root.left == null && root.right == null) {
            return targetSum == root.val;
        }
        return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
    }

    public int sumOfLeftLeaves(TreeNode root) {
        return sumOfLeftLeavesHelper(root.left, true) + sumOfLeftLeavesHelper(root.right, false);
    }

    private int sumOfLeftLeavesHelper(TreeNode root, boolean isFromLeft) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return isFromLeft ? root.val : 0;
        } else {
            return sumOfLeftLeavesHelper(root.left, true) + sumOfLeftLeavesHelper(root.right, false);
        }
    }

    public List<String> binaryTreePaths(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        if (root.left == null && root.right == null) {
            return Collections.singletonList(String.valueOf(root.val));
        }
        List<String> leftPaths = binaryTreePaths(root.left);
        List<String> rightPaths = binaryTreePaths(root.right);
        List<String> res = new ArrayList<>(leftPaths.size() + rightPaths.size());
        leftPaths.forEach(path -> res.add(root.val + "->" + path));
        rightPaths.forEach(path -> res.add(root.val + "->" + path));
        return res;
    }


    public List<List<Integer>> pathSumII(TreeNode root, int targetSum) {
        List<List<Integer>> res = new LinkedList<>();
        pathSumIIHelper(root, targetSum, new LinkedList<>(), res);
        return res;
    }

    private void pathSumIIHelper(TreeNode root, int targetSum, LinkedList<Integer> pre, List<List<Integer>> res) {
        if (root == null) {
            return;
        }
        pre.addLast(root.val);
        targetSum = targetSum - root.val;
        if (root.left == null && root.right == null) {
            if (targetSum == 0) {
                res.add(cloneList(pre));
            }
            pre.removeLast();
            return;
        }
        pathSumIIHelper(root.left, targetSum, pre, res);
        pathSumIIHelper(root.right, targetSum, pre, res);
        pre.removeLast();
    }

    private List<Integer> cloneList(LinkedList<Integer> pre) {
        ArrayList<Integer> res = new ArrayList<>(pre.size());
        res.addAll(pre);
        return res;
    }

    public int sumNumbers(TreeNode root) {
        List<String> stringList = binaryTreePathsForSumNumbers(root);
        int mySum = 0;
        for (String s : stringList) {
            mySum += Integer.parseInt(s);
        }
        return mySum;
    }

    private List<String> binaryTreePathsForSumNumbers(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        if (root.left == null && root.right == null) {
            return Collections.singletonList(String.valueOf(root.val));
        }
        List<String> leftPaths = binaryTreePathsForSumNumbers(root.left);
        List<String> rightPaths = binaryTreePathsForSumNumbers(root.right);
        List<String> res = new ArrayList<>(leftPaths.size() + rightPaths.size());
        leftPaths.forEach(path -> res.add(root.val + path));
        rightPaths.forEach(path -> res.add(root.val + path));
        return res;
    }

    public int sumNumbersWithDFS(TreeNode root) {
        List<Integer> res = new LinkedList<>();
        res.add(0);
        sumNumbersWithDFSHelper(root, 0, res);
        return res.get(0);
    }

    private void sumNumbersWithDFSHelper(TreeNode root, int pre, List<Integer> res) {
        if (root == null) {
            return;
        }
        pre = pre * 10 + root.val;
        if (root.left == null && root.right == null) {
            res.set(0, res.get(0) + pre);
        } else {
            sumNumbersWithDFSHelper(root.left, pre, res);
            sumNumbersWithDFSHelper(root.right, pre, res);
        }
    }

    public int pathSum(TreeNode root, int targetSum) {
        return pathSumHelper(root, targetSum, false);
    }

    // 用long是避免溢出
    private int pathSumHelper(TreeNode root, long target, boolean includeParentNode) {
        if (root == null) {
            return 0;
        }
        if (includeParentNode) {
            return (root.val == target ? 1 : 0)
                    + pathSumHelper(root.left, target - root.val, true)
                    + pathSumHelper(root.right, target - root.val, true);
        } else {
            return (root.val == target ? 1 : 0)
                    + pathSumHelper(root.left, target - root.val, true)
                    + pathSumHelper(root.right, target - root.val, true)
                    + pathSumHelper(root.left, target, false)
                    + pathSumHelper(root.right, target, false);
        }
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (p.val < root.val && q.val < root.val) {
            return lowestCommonAncestor(root.left, p, q);
        }
        if (p.val > root.val && q.val > root.val) {
            return lowestCommonAncestor(root.right, p, q);
        }
        return root;
    }

    public boolean isValidBST(TreeNode root) {
        return isValidBSTHelper(root, null, null);
    }

    private boolean isValidBSTHelper(TreeNode root, Integer upperBound, Integer lowerBound) {
        if (root == null) {
            return true;
        }
        if (upperBound != null && root.val >= upperBound) {
            return false;
        }
        if (lowerBound != null && root.val <= lowerBound) {
            return false;
        }
        if (root.left != null && root.left.val >= root.val) {
            return false;
        }
        if (root.right != null && root.right.val <= root.val) {
            return false;
        }
        return isValidBSTHelper(root.left, root.val, lowerBound)
                && isValidBSTHelper(root.right, upperBound, root.val);
    }

    public TreeNode deleteNode(TreeNode root, int key) {
        TreeNode dummy = new TreeNode(Integer.MAX_VALUE, root, null);
        TreeNode temp = dummy;
        while (temp != null) {
            if (temp.left != null && temp.left.val == key) {
                temp.left = deleteNode(temp.left);
                temp = null;
            } else if (temp.right != null && temp.right.val == key) {
                temp.right = deleteNode(temp.right);
                temp = null;
            } else {
                temp = key > temp.val ? temp.right : temp.left;
            }
        }
        return dummy.left;
    }

    public TreeNode deleteNode(TreeNode node) {
        if (node == null) {
            return null;
        }
        if (node.left == null) {
            return node.right;
        }
        if (node.right == null) {
            return node.left;
        }
        TreeNode closestFather = node;
        TreeNode closest = node.left;
        while (closest.right != null) {
            closestFather = closest;
            closest = closest.right;
        }
        if (closestFather != node) {
            closestFather.right = closest.left;
            closest.left = node.left;
        }
        closest.right = node.right;
        return closest;
    }

    public TreeNode sortedArrayToBST(int[] nums) {
        return sortedArrayToBST(nums, 0, nums.length - 1);
    }

    private TreeNode sortedArrayToBST(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }
        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = sortedArrayToBST(nums, left, mid - 1);
        root.right = sortedArrayToBST(nums, mid + 1, right);
        return root;
    }

    int count = 0;

    public int kthSmallest(TreeNode root, int k) {
        if (root == null) {
            return -1;
        }
        int left = kthSmallest(root.left, k);
        if (left != -1) {
            return left;
        }
        count++;
        if (count == k) {
            return root.val;
        }
        return kthSmallest(root.right, k);
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

}


