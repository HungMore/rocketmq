## 二叉树相关问题

递归结构的两部分：
1. 递归终止条件
2. 递归过程

###### 问题104：maximum depth of binary tree

给定一个二叉树root，返回其最大深度。
二叉树的最大深度是指从根节点到最远叶子节点的最长路径上的节点数。
```java
public int maxDepth(TreeNode root);
```

这题用递归就很简单啦！
首先递归终止条件：如果根节点是空，返回0
递归过程：当前二叉树的最大深度 = 1 + max(左子树的最大深度, 右子树的最大深度)
代码：
```java
public int maxDepth(TreeNode root) {
    if (root == null) {
        return 0;
    }
    return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
}
```

###### 问题111：minimum depth of binary tree

给定一个二叉树，找出其最小深度。
最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
说明：叶子节点是指没有子节点的节点。
```java
public int minDepth(TreeNode root);
```

这题和`问题104`类似呀，用递归就可以解决。
递归终止条件：如果根节点为空，返回0
递归逻辑：当前根节点的最小深度=1+min(左子树的最小深度, 右子树的最小深度)
代码：
```java
public int minDepth(TreeNode root) {
    if (root == null) {
        return 0;
    }
    return 1 + Math.min(minDepth(root.left), minDepth(root.right));
}
```
嘻嘻，提交以上代码，人傻了吧，并没有AC！
我们走进了一个陷阱：最小深度是根节点到叶子节点的最短路径，叶子节点要求左右子树都为空，而不是左右子树任意一个为空！
我们依然可以使用递归来解决，只是要重新定义递归关系：
递归终止条件：如果根节点为空，返回0
递归逻辑：如果根节点的左子节点为空，那么当前二叉树的最小深度=1+右子树的最小深度；如果根节点的右子节点为空，当前二叉树的最小深度=1+左子树的最小深度；如果左右子树都不为空，当前二叉树的最小深度=1+min(左子树的最小深度, 右子树的最小深度)
代码：
```java
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
```
看我的提交记录，我还写过一个层序遍历（即BFS）的解法，可以参考下：
```java
/**
 * 使用 labuladong的BFS思路来做做看
 *
 * @param root
 * @return
 */
public int minDepth(TreeNode root) {
    if (root == null) {
        return 0;
    }
    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);
    int step = 1;
    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            TreeNode remove = queue.remove();
            if (isTarget(remove)) {
                return step;
            }
            if (remove.left != null) {
                queue.add(remove.left);
            }
            if (remove.right != null) {
                queue.add(remove.right);
            }
        }
        step++;
    }
    throw new RuntimeException("unreachable!");
}
// 判读是否是叶子节点
private boolean isTarget(TreeNode node) {
    return node != null && node.left == null && node.right == null;
}
```

###### 问题226：invert binary tree

给你一棵二叉树的根节点root，翻转这棵二叉树，并返回其根节点。
```java
public TreeNode invertTree(TreeNode root);
```

这题用递归就可以做了吧。
递归终止条件：如果根节点为空，直接返回当前根节点。
递归逻辑：将左子树、右子树翻转，然后将根节点的左子节点指向翻转后的右子树，将根节点的右子节点指向翻转后的左子树。（整体逻辑就是在后序遍历[自底向上]的框架中处理）
代码：
```java
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
```
也用先序遍历（自顶向下）的框架做一遍：
```java
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
```

###### 问题100：same tree

###### 问题101：symmetric tree

###### 问题222：count complete tree nodes

###### 问题110：balanced binary tree


7.3
