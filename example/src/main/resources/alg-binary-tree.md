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

###### 问题404：sum of left leaves

给定二叉树的根节点root，返回所有左叶子之和。
```java
public int sumOfLeftLeaves(TreeNode root);
```

对于当前节点，我们需要判断它是不是叶子节点，不是叶子节点就跳过往下继续查找；如果是叶子节点，还要判断它是否是左节点，是左节点才参与求和。
所以我们需要一个辅助函数，入参不仅有当前节点，还得有它是否是左节点的标记。辅助函数的功能是求出以当前节点为根的左叶子之和。
该辅助函数的递归实现：
递归终止条件：如果当前节点为空，返回0；如果当前不为空，且为叶子节点，判断是否是左叶子，是的话参与求和，返回它的val，如果是右叶子，返回0
递归关系：如果当前节点不是叶子节点，往下递归它的左右子树，并按实际情况设置入参。
代码：
```java
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
```
我这个解法和官方解答等主流方法都不一样哦，可以了解下官方的解法，多扩展下思维！感觉我又遗忘了DFS\BFS的代码框架了······

###### 问题257：binary tree paths

给你一个二叉树的根节点root，按任意顺序，返回所有从根节点到叶子节点的路径。
叶子节点是指没有子节点的节点。
```java
public List<String> binaryTreePaths(TreeNode root);
```

这题没有思路，看了bobo老师的讲解，才明白递归思路的推导过程。
首先我们要找根节点A到叶子节点的路径，就等于找A的左右子节点到它们的叶子节点的路径，然后分别都在前面拼接上A就行了。
所以递归结构定义如下：
1. 递归逻辑：binaryTreePaths(A) =  (A + foreach(binaryTreePaths(A.left))) + (A + foreach(binaryTreePaths(A.right)))
2. 递归终止条件：如果A是叶子节点，返回它自己即可，如果A为空，返回空。
代码：
```java
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
```

这个解法只击败了9%的用户，估计还有更好的解法吧。


###### 问题113：path sum ii

给你二叉树的根节点root和一个整数目标和targetSum，找出所有从根节点到叶子节点路径总和等于给定目标和的路径。
叶子节点是指没有子节点的节点。
```java
public List<List<Integer>> pathSum(TreeNode root, int targetSum);
```

这题和`问题112`类似，所以同样可以使用递归来做啦，但是我们还需要一个变量来保存前面的路径，所以用深度优先遍历的框架来做。
```java
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
```

###### 问题129：sum root to leaf numbers

给你一个二叉树的根节点root，树中每个节点都存放有一个0到9之间的数字。
每条从根节点到叶节点的路径都代表一个数字：
例如，从根节点到叶节点的路径1->2->3表示数字123。
计算从根节点到叶节点生成的所有数字之和。
叶节点是指没有子节点的节点。
示例 1：
输入：root = [1,2,3]
输出：25
解释：
从根到叶子节点路径 1->2 代表数字 12
从根到叶子节点路径 1->3 代表数字 13
因此，数字总和 = 12 + 13 = 25
```java
public int sumNumbers(TreeNode root);
```

这题用`问题257`或者`问题113`的思路都可以解决啦，用问题257的递归来做吧，然后把字符串转数字。
代码：
```java
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
```
这个解法只击败了5%的用户，等下用深度优先搜索做一做。
```java
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
```

###### 问题437：path sum iii

给定一个二叉树的根节点root，和一个整数targetSum，求该二叉树里节点值之和等于targetSum的路径的数目。
路径不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。
```java
public int pathSum(TreeNode root, int targetSum);
```

这题可以使用递归求解。
对于当前根节点，路径可以包含根节点，也可以不包含根节点。
对于包含根节点的情况，找pathSum(root, target)就转化为了找pathSum(roo.left, target-root.val) + pathSum(roo.right, target-root.val)；特别地，如果target等于root.val，当前根节点就可以构成一个路径，结果+1
对于不包含根节点的情况，找pathSum(root, target)就转化为了找pathSum(roo.left, target) + pathSum(roo.right, target)。
综上，递归关系定义：
1. 递归逻辑：pathSum(root, target) = pathSum(roo.left, target-root.val) + pathSum(roo.right, target-root.val) + pathSum(roo.left, target) + pathSum(roo.right, target) + (root.val == target ? 1 : 0)。
2. 递归终止条件：当root为空时，返回0
代码：
```java
public int pathSum(TreeNode root, int targetSum) {
    if (root == null) {
        return 0;
    }
    int sum = root.val == targetSum ? 1 : 0;
    return sum
            + pathSum(root.left, targetSum - root.val)
            + pathSum(root.right, targetSum - root.val)
            + pathSum(root.left, targetSum)
            + pathSum(root.right, targetSum);
}
```
以上代码并没有成功AC！
因为递归关系找错了，对于包含根节点的情况，在子树中找target-root.val也必须要包含根节点，这样才能构成一条连续的路径，所以我们可以构造一个辅助函数pathSumHelper(TreeNode root, int targetSum, boolean includeParentNode)。
那么当前函数pathSum(root, target)就等于pathSumHelper(root, target, false)
pathSumHelper(root, targetSum, includeParentNode) 的递归关系就转化为：
pathSumHelper(root, targetSum, true) = pathSumHelper(root.left, target-root.val, true) 
    + pathSumHelper(root.right, target-root.val, true) 
    + (root.val == target ? 1 : 0)
pathSumHelper(root, targetSum, false) = pathSumHelper(root.left, target-root.val, true) 
    + pathSumHelper(root.right, target-root.val, true) 
    + pathSumHelper(root.left, target, false) 
    + pathSumHelper(root.right, target, false) 
    + (root.val == target ? 1 : 0)
代码：
```java
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
```

7.6
