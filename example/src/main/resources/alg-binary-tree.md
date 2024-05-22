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

给你两棵二叉树的根节点p和q，编写一个函数来检验这两棵树是否相同。
如果两个树在结构上相同，并且节点具有相同的值，则认为它们是相同的。
```java
public boolean isSameTree(TreeNode p, TreeNode q);
```

这题用递归很好做啦！
递归终止条件：如果p和q都是空，返回真
递归逻辑：如果p和q的节点值不相等或者p、q中其中一个为空另一个不为空，返回false；否则递归判断p的左子树和q的左子树是否相同以及p的右子树和q的右子树是否相同。
代码：
```java
public boolean isSameTree(TreeNode p, TreeNode q) {
    if (p != null && q != null) {
        return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    } else {
        return p == q;
    }
}
```

###### 问题101：symmetric tree

给你一个二叉树的根节点root，检查它是否轴对称。
```java
public boolean isSymmetric(TreeNode root);
```

编写一个辅助函数，用以判断两棵树是否是对称的，然后调用该辅助函数判断根节点的左右子树是否对称。
辅助函数可以使用递归的方式编写
递归逻辑：如果p、q两棵树都不为空，判断p、q的值是否相等，如果相等，递归判断p的左子树和q的右子树是否对称以及p的右子树和q的左子树是否对称；如果不相等，返回false；
如果p、q都为空，返回true，只有其中一个为空返回false。
代码：
```java
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
```

###### 问题222：count complete tree nodes

给你一棵完全二叉树的根节点root，求出该树的节点个数。
完全二叉树的定义如下：在完全二叉树中，除了最底层节点可能没填满外，其余每层节点数都达到最大值，并且最下面一层的节点都集中在该层最左边的若干位置。若最底层为第h层，则该层包含1~2h个节点。
```java
public int countNodes(TreeNode root);
```

这题用二叉树的任意一种遍历方式都可以做（比如前、中、后、层次遍历等）。
用前序遍历做一下吧。
```java
public int countNodes(TreeNode root) {
    if (root == null) {
        return 0;
    }
    return 1 + countNodes(root.left) + countNodes(root.right);
}
```
这题还有更优的解法，可以充分利用完全二叉树的特性，具体可参考leetcode的官方解答。


###### 问题110：balanced binary tree

给定一个二叉树，判断它是否是平衡二叉树。
平衡二叉树：左右子树都是平衡二叉树且左右子树的高度差不超过1。
树的高度：根节点到叶子节点的最大长度。
```java
public boolean isBalanced(TreeNode root);
```

平衡二叉树的定义其实就是一个递归关系：
1. 空树是一颗二叉树（递归终止条件）
2. 左右子树都是一颗平衡二叉树，且左右子树的高度差不超过1。（递归逻辑）
根据定义，我们很容易想到要使用后序遍历的框架，先判断左右子树是否是平衡二叉树，再判断它们的高度差。
如果我们单独写一个获取二叉树高度的函数，那么整体的复杂度就会很高（因为自底向上每往上走一层，都要重新自顶向下计算一遍当前高度【参考问题104的代码】，存在大量重复计算）。
其实一棵树的高度就等于1+max(左子树的高度, 右子树的高度)，所以我们完全可以在后序遍历的过程中，让递归函数顺带返回子树的高度。
综上，我们可以定义这样一个辅助函数：判断二叉树是否是平衡二叉树，如果不是，返回-1，如果是，返回其高度。
那么这题就转化为辅助函数的结果是否等于-1。
而这个辅助函数使用递归也是很好实现的：
递归终止条件：空树是一颗平衡二叉树，且高度为0，所以直接返回0
递归逻辑：如果左右子树其中一个是非平衡二叉树（返回值为-1），那么当前二叉树就是非平衡二叉树（返回-1）；
如果左右子树都是平衡二叉树（返回值为其高度），那么判断高度差是否不超过1，如果超过，当前二叉树是非平衡二叉树（返回-1）；如果没超过，当前二叉树的高度=1+max(左右子树的高度)
代码：
```java
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
```

###### 问题112：path sum

给你二叉树的根节点root和一个表示目标和的整数targetSum。判断该树中是否存在根节点到叶子节点的路径，这条路径上所有节点值相加等于目标和targetSum。如果存在，返回true；否则，返回false。
叶子节点是指没有子节点的节点。
```java
public boolean hasPathSum(TreeNode root, int targetSum);
```

这题第一想法是用深度优先遍历的思想来做，但仔细想想感觉不好写。反而用递归就很好做。
递归终止条件：当前节点是叶子节点，如果节点的值等于target，返回真；如果不等于，返回false。
递归逻辑：hasPathSum(root, target) = hasPathSum(root.left, target-root.val) || hasPathSum(root.right, target-root.val).
代码：
```java
public boolean hasPathSum(TreeNode root, int targetSum) {
    if (root == null) {
        return false;
    }
    if (root.left == null && root.right == null) {
        return targetSum == root.val;
    }
    return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
}
```

7.4