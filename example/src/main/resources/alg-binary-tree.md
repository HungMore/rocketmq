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


7.2
