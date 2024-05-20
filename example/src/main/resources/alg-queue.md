## 队列相关问题

队列的基本应用：广度优先遍历
- 树：层次遍历
- 图：无权图的最短路径

###### 问题102：binary tree level order traversal

给你二叉树的根节点root，返回其节点值的层序遍历。（即逐层地，从左到右访问所有节点）。
示例 1：
输入：root = [3,9,20,null,null,15,7]
输出：[[3],[9,20],[15,7]]
```java
public List<List<Integer>> levelOrder(TreeNode root);
```

树的层次遍历相当于图的广度优先遍历，使用的是队列数据结构。
首先我们将根节点添加到队列中，然后遍历队列中的节点，将节点的非空左右子节点添加到队列当中。
需要注意的是，我们在遍历队列的过程中，每一层的节点要单独放到一个List中，所以我们需要每次记录当前层的节点数目，分层次进行循环遍历。
代码：
```java
public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> res = new LinkedList<>();
    Queue<TreeNode> queue = new LinkedList<>();
    if (root != null) {
        queue.add(root);
    }
    while (!queue.isEmpty()) {
        int size = queue.size();
        List<Integer> aLevelList = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            TreeNode remove = queue.remove();
            aLevelList.add(remove.val);
            if (remove.left != null) {
                queue.add(remove.left);
            }
            if (remove.right != null) {
                queue.add(remove.right);
            }
        }
        res.add(aLevelList);
    }
    return res;
}
```

###### 问题107：binary tree level order traversal ii

给你二叉树的根节点root，返回其节点值自底向上的层序遍历。（即按从叶子节点所在层到根节点所在的层，逐层从左向右遍历）
示例 1：
输入：root = [3,9,20,null,null,15,7]
输出：[[15,7],[9,20],[3]]
```java
public List<List<Integer>> levelOrderBottom(TreeNode root);
```

这题和`问题102`是一模一样的，我们只需要修改`res.add(aLevelList)`为`res.addFirst(aLevelList)`即可。
代码：
```java
public List<List<Integer>> levelOrderBottom(TreeNode root) {
    LinkedList<List<Integer>> res = new LinkedList<>();
    Queue<TreeNode> queue = new LinkedList<>();
    if (root != null) {
        queue.add(root);
    }
    while (!queue.isEmpty()) {
        int size = queue.size();
        List<Integer> aLevelList = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            TreeNode remove = queue.remove();
            aLevelList.add(remove.val);
            if (remove.left != null) {
                queue.add(remove.left);
            }
            if (remove.right != null) {
                queue.add(remove.right);
            }
        }
        res.addFirst(aLevelList);
    }
    return res;
}
```

###### 问题103：binary tree zigzag level order traversal

给你二叉树的根节点root，返回其节点值的锯齿形层序遍历。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
示例 1：
输入：root = [3,9,20,null,null,15,7]
输出：[[3],[20,9],[15,7]]
```java
public List<List<Integer>> zigzagLevelOrder(TreeNode root);
``` 

这题也和`问题102`差不多，只不过我们需要用一个布尔值保存当前层是从左到右还是从右到左，每遍历完一层就需要对该布尔值取反。
另外，其实res.size就记录了当前是第几层！
代码：
```java
public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
    // 另外，其实我们也不需要定义变量来保存当前属于第几层了，res.size 就可以记录当前是第几层
    List<List<Integer>> res = new LinkedList<>();
    Queue<TreeNode> queue = new LinkedList<>();
    if (root != null) {
        queue.add(root);
    }
    boolean fromLeftToRight = true;
    while (!queue.isEmpty()) {
        int size = queue.size();
        LinkedList<Integer> aLevelList = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            TreeNode remove = queue.remove();
            if (fromLeftToRight) {
                aLevelList.add(remove.val);
            } else {
                aLevelList.addFirst(remove.val);
            }
            if (remove.left != null) {
                queue.add(remove.left);
            }
            if (remove.right != null) {
                queue.add(remove.right);
            }
        }
        res.add(aLevelList);
        fromLeftToRight = !fromLeftToRight;
    }
    return res;
}
```

###### 问题199：binary tree right side view 

给定一个二叉树的根节点root，想象自己站在它的右侧，按照从顶部到底部的顺序，返回从右侧所能看到的节点值。
示例 1:
输入: [1,2,3,null,5,null,4]
输出: [1,3,4]
```java
public List<Integer> rightSideView(TreeNode root);
```

第一反应是不断获取根节点的右子节点就可以了，但是仔细一想右子节点可能为空的。所以这题其实就是要求每一层最右侧的节点，我们还是可以使用层次遍历，然后拿到层次遍历中每一层的最右边的节点就可以了。
代码：
```java
public List<Integer> rightSideView(TreeNode root) {
    List<Integer> res = new LinkedList<>();
    Queue<TreeNode> queue = new LinkedList<>();
    if (root != null) {
        queue.add(root);
    }
    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 1; i < size; i++) {
            TreeNode remove = queue.remove();
            if (remove.left != null) {
                queue.add(remove.left);
            }
            if (remove.right != null) {
                queue.add(remove.right);
            }
        }
        TreeNode remove = queue.remove();
        res.add(remove.val);
        if (remove.left != null) {
            queue.add(remove.left);
        }
        if (remove.right != null) {
            queue.add(remove.right);
        }
    }
    return res;
}
```

6.5 这一节讲的都是通过无权图的广度优先遍历寻找最短路径的问题，后面单独看bobo老师的图论课程吧。`todo`

###### 问题279：perfect squares

查看了我对这题的题解记录，用动态规划会更简单！

###### 问题127：word ladder

###### 问题126：word ladder ii


