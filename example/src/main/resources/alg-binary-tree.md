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

7.2
