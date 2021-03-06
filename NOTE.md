
## String

### 3. Longest Substring Without Repeating Characters
#### Problem
```text
Given a string, find the length of the longest substring without repeating characters.

Example 1:
Input: "abcabcbb"
Output: 3 
Explanation: The answer is "abc", with the length of 3. 

Example 2:
Input: "bbbbb"
Output: 1
Explanation: The answer is "b", with the length of 1.

Example 3:
Input: "pwwkew"
Output: 3
Explanation: The answer is "wke", with the length of 3. 
             Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
```
#### Solution
Basic idea, use `sliding window` to slide through the whole string.   
Used a low pointer to log where current window starts, and use a map to log where each letters appeared fot the last time.  

**Caution**: when resetting the low pointer, we should take the max of `lo` and `dic[c] + 1`.  
E.g.`abcaab`, when the window slides to the last b, low pointer is at a with index of -2 and current window is `a`, but the former b with index of 1 is still in the hashmap. If we use `dic[c] + 1` at this point, the window will become `caab` instead of `ab`.
```python
class Solution:
    def lengthOfLongestSubstring(self, s: str) -> int:
        res = 0
        lo = 0
        dic = {}
        for i, c in enumerate(s):
            if c in dic:
                lo = max(lo, dic[c] + 1)  # caution: abcaab
            dic[c] = i
            res = max(i - lo + 1, res)
        return res
```


### 5. Longest Palindromic Substring
#### Problem
```text
Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.

Example 1:
Input: "babad"
Output: "bab"
Note: "aba" is also a valid answer.

Example 2:
Input: "cbbd"
Output: "bb"
```
#### Solution
Since a palindrome mirrors around its center, and there are totally `2n - 1` such centers, we could expand around these centers to find the longest palindrome. `Time complexity: O(n^2)`  

**Note**: use `//` in python for floor division.
```python
class Solution:
    def longestPalindrome(self, s: str) -> str:
        start, end = 0, 0
        for i in range(len(s)):
            cur_len = max(self.expand(s, i, i), self.expand(s, i, i + 1))
            if cur_len > end - start:
                start = i - (cur_len - 1) // 2  # trick here, remember to -1 since it could expand at center of two char
                end = i + cur_len // 2
        return s[start:end + 1]

    def expand(self, s, left, right):
        l, r = left, right
        while l >= 0 and r < len(s) and s[l] == s[r]:
            l -= 1
            r += 1
        return r - l - 1
```
`TODO: DP, Manacher's Algorithm`


### 7. Reverse Integer
#### Problem
```text
Given a 32-bit signed integer, reverse digits of an integer.

Example 1:
Input: 123
Output: 321

Example 2:
Input: -123
Output: -321

Example 3:
Input: 120
Output: 21

Note:
Assume we are dealing with an environment which could only store integers within the 32-bit signed integer range: [−231,  231 − 1]. For the purpose of this problem, assume that your function returns 0 when the reversed integer overflows.
```
#### Solution
The most challenge part in this problem is not letting the result overflow, so the result has to be checked each time during calculation.
```java
class Solution {
    public int reverse(int x) {
        int result = 0;
        while (x != 0) {
            int tail = x % 10;
            x /= 10;
            if (result > Integer.MAX_VALUE / 10 || result == Integer.MAX_VALUE / 10 && tail > 7) return 0;
            if (result < Integer.MIN_VALUE / 10 || result == Integer.MIN_VALUE / 10 && tail < -8) return 0;
            result = result * 10 + tail;
        }
        return result;
    }
}
```
Another brilliant solution from discussion.  
I'm not sure if it's a good solution, because the integer could overflow during calculation (even though it is the intention).   
During calculation, the temp result is `result * 10 + tail`, but if it's smaller than `-2^31`or larger than `2^31 - 1`, it will overflow and return a number that is not correct.
```java
class Solution {
    public int reverse(int x) {
        int result = 0;
        while (x != 0) {
            int tail = x % 10;
            // overflow here, brilliant here
            if (((result * 10 + tail) - tail) / 10 != result) return 0;
            result = result * 10 + tail;
            x /= 10;
        }
        return result;
    }
}
```


### 9. Palindrome Number
#### Problem
```text
Determine whether an integer is a palindrome. An integer is a palindrome when it reads the same backward as forward.

Example 1:

Input: 121
Output: true
Example 2:

Input: -121
Output: false
Explanation: From left to right, it reads -121. From right to left, it becomes 121-. Therefore it is not a palindrome.
Example 3:

Input: 10
Output: false
Explanation: Reads 01 from right to left. Therefore it is not a palindrome.
Follow up:

Coud you solve it without converting the integer to a string?
```
#### Solution
My first thought, instead of convert it into string, it's to compare the first and last digit continuously.  
The problem is, how could I know I've compared half of the digits?  
Thanks to the official solution, they have a very similar approach by reverse the left half of the number, and when the right half is no longer larger than the right half, we know it is the end.
```java
class Solution {
    public boolean isPalindrome(int x) {
        // take care of edge cases        
        if (x < 0 || x % 10 == 0 && x != 0) return false;
        int left = x;
        int right = 0;
        // = only in case of x = 0
        while (left >= right) {
            right = right * 10 + left % 10;
            left /= 10;
            if (right == left || right / 10 == left) return true;
        }
        return false;
    }
}
```


### 20. Valid Parentheses
#### Problem
```text
Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.

An input string is valid if:
    1. Open brackets must be closed by the same type of brackets.
    2. Open brackets must be closed in the correct order.
Note that an empty string is also considered valid.

Example 1:
Input: "()"
Output: true

Example 2:
Input: "()[]{}"
Output: true

Example 3:
Input: "(]"
Output: false

Example 4:
Input: "([)]"
Output: false

Example 5:
Input: "{[]}"
Output: true
```
#### Solution
```java
public class Solution {
    public boolean isValid(String s) {
        Map<Character, Character> map = new HashMap<>();
        map.put('(', ')');
        map.put('{', '}');
        map.put('[', ']');
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char cur = s.charAt(i);
            if (map.containsKey(cur)) {
                stack.push(cur);
            } else if (stack.isEmpty() || map.get(stack.pop()) != cur) { 
                return false;
            }
        }
        return stack.isEmpty();
    }
}
```


## Array

### 15. 3Sum
#### Problem
```text
Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.

Note:
The solution set must not contain duplicate triplets.

Example:
Given array nums = [-1, 0, 1, 2, -1, -4],

A solution set is:
[
  [-1, 0, 1],
  [-1, -1, 2]
]
```
#### Solution
A very classical problem.  
Basic idea is to sort the array and using `two pointer` to find the complementary two sum.  
```java
public class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (i == 0 || nums[i] != nums[i - 1]) {
                int lo = i + 1, hi = nums.length - 1;
                // dont need to check the length of input array, if its length < 3, lo < hi will not stand
                while (lo < hi) {
                    int sum = nums[i] + nums[lo] + nums[hi];
                    if (sum == 0) {
                        // hey!
                        res.add(Arrays.asList(nums[i], nums[lo], nums[hi]));
                        while (lo < hi && nums[lo] == nums[lo + 1]) lo++;
                        while (lo < hi && nums[hi] == nums[hi - 1]) hi--;
                        lo++;
                        hi--;
                    } else if (sum < 0) {
                        lo++;
                    } else {
                        hi--;
                    }
                }
            }
        }
        return res;
    }
}
```


### 215. Kth Largest Element in an Array
#### Problem
```text
Find the kth largest element in an unsorted array. Note that it is the kth largest element in the sorted order, not the kth distinct element.

Example 1:
Input: [3,2,1,5,6,4] and k = 2
Output: 5

Example 2:
Input: [3,2,3,1,2,4,5,5,6] and k = 4
Output: 4

Note: 
You may assume k is always valid, 1 ≤ k ≤ array's length.
```
#### Solution
Naive approach, simply sort the array. Time complexity: `O(nlogn)`
```java
class Solution {
    public int findKthLargest(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }
}
```
Priority queue approach, use a max-heap to keep the first k largest element. Time complexity: `O(nlogk)`  
**Note**: min-heap comparator: `(n1, n2) -> n2 - n1` or `Collections.reverseOrder()` 
```java
class Solution {
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>((n1, n2) -> n1 - n2);
        for (int i : nums) {
            heap.add(i);
            if (heap.size() > k) {
                heap.poll();
            }
        }
        return heap.poll();
    }
}
```
And...Python Hack!
```python
class Solution:
    def findKthLargest(self, nums: List[int], k: int) -> int:
        return heapq.nlargest(k, nums)[-1]
```
`TODO: Quickselect`

### 973. K Closest Points to Origin
#### Problem
```text
We have a list of points on the plane.  Find the K closest points to the origin (0, 0).

(Here, the distance between two points on a plane is the Euclidean distance.)

You may return the answer in any order.  The answer is guaranteed to be unique (except for the order that it is in.)

Example 1:
Input: points = [[1,3],[-2,2]], K = 1
Output: [[-2,2]]
Explanation: 
The distance between (1, 3) and the origin is sqrt(10).
The distance between (-2, 2) and the origin is sqrt(8).
Since sqrt(8) < sqrt(10), (-2, 2) is closer to the origin.
We only want the closest K = 1 points from the origin, so the answer is just [[-2,2]].

Example 2:
Input: points = [[3,3],[5,-1],[-2,4]], K = 2
Output: [[3,3],[-2,4]]
(The answer [[-2,4],[3,3]] would also be accepted.)
 

Note:
1 <= K <= points.length <= 10000
-10000 < points[i][0] < 10000
-10000 < points[i][1] < 10000
```
#### Solution
这个题真的写的要被java气死了  
arraylist里存int[]都研究好半天，List<Integer[]>还不行，只能写List<int[]>  
这就算了，总算是存进去了，以为就这样结束了  
结果List<int[]> 转成int[][]又出现了问题: )  
行，建个array一个个读了再存进去行吧  
结果这个k closest和上面那个kth还不一样，这个是前k个  
那现在array初始化又有问题  
太气了，这题还是用python写了
```java
class Solution {
    public int[][] kClosest(int[][] points, int K) {
        Map<Integer, List<int[]>> map = new HashMap<>();
        for (int[] point : points) {
            int dist = point[0] * point[0] + point[1] * point[1];
            List<int[]> tmp = map.getOrDefault(dist, new ArrayList<int[]>());
            tmp.add(point);
            map.put(dist, tmp);
        }
        PriorityQueue<Integer> pq = new PriorityQueue<>((n1, n2) -> n2 - n1);
        for (int i : map.keySet()) {
            pq.add(i);
            if (pq.size() > K) {
                pq.poll();
            }
        }
        // 辣鸡java 这个arraylist转int[][]写的我想砸电脑
        // int res[][] = new int[points.length][2];  // 这样写是答案是不对的，会多出好多[0, 0]
        // 哦我以为相同距离是不算的，大半夜脑子不清醒了，那直接初始化长度k就行了
        int res[][] = new int[K][2];
        int index = 0;
        while (!pq.isEmpty()) {
            List<int[]> list = map.get(pq.poll());
            for (int i = 0; index < K && i < list.size(); i++) {
                res[index] = list.get(i);
            }
            index++;
        }
        return res;
    }
}
```
python用这个思路写不要再简单  
不过写到这里也发现按照上面kth那题的思路写确实有问题  
用map存的话取前k个只能粗暴的根据res的长度来判断要不要继续往res里加  
这样的话多了这么多麻烦确实还不如答案里的直接sort，复杂度也就是nlogn比上nlogk，省好多事呢
```python
class Solution:
    def kClosest(self, points: List[List[int]], K: int) -> List[List[int]]:
        map = {}
        for point in points:
            dist = point[0]**2 + point[1]**2
            tmp = map.get(dist, [])
            tmp.append(point)
            map[dist] = tmp
        res = []
        for i in heapq.nsmallest(K, list(map.keys())):
            # python没有flatten也有点难受
            for j in map[i]:
                if len(res) < K:
                    res.append(j)
                else:
                    return res
        return res
```
最后发现这个题做这么难受是题目的理解有问题  
最开始以为k closest是第k个，结果是前k个  
前k个的话就导致一个很严重的问题，我是按照第k个来想的，所以才用了hashmap存了所有点的距离  
如果是前k个的话，那pq在这里的作用就和上面kth的完全一样了，最后得到第k个点的距离就行  
下面是正确理解题意后的pq解法
```java
class Solution {
    public int[][] kClosest(int[][] points, int K) {
        PriorityQueue<Integer> pq = new PriorityQueue<>((n1, n2) -> n2 - n1);
        
        for (int[] point : points) {
            int dist = point[0] * point[0] + point[1] * point[1];
            pq.add(dist);
            if (pq.size() > K) {
                pq.poll();
            }
        }
        
        int kthDist = pq.poll();
        int[][] res = new int[K][2];    
        int x = 0;
        
        for (int i = 0; i < points.length; i++) {
            int[] point = points[i];
            if (point[0] * point[0] + point[1] * point[1] <= kthDist) {
                res[x++] = point;
            }
        }
        return res;
    }
}
```
接下来的是java的sort的写法的非常的dry的code
```java
class Solution {
    public int[][] kClosest(int[][] points, int K) {
        int[] dists = new int[points.length];

        for (int i = 0; i < points.length; i++) {
            int[] point = points[i];
            dists[i] = point[0] * point[0] + point[1] * point[1];
        }

        Arrays.sort(dists);
        int kthDist = dists[K - 1];
        int[][] res = new int[K][2];
        int x = 0;

        for (int i = 0; i < points.length; i++) {
            int[] point = points[i];
            if (point[0] * point[0] + point[1] * point[1] <= kthDist) {
                res[x++] = point;
            }
        }
        return res;
    }
}
```
然而lc里跑起来sort的解法比pq快？而且还快不少：）

damn..看了一眼讨论区，还能这么干的吗..  
```java
class Solution {
    public int[][] kClosest(int[][] points, int K) {
        Arrays.sort(points, (p1, p2) -> p1[0] * p1[0] + p1[1] * p1[1] - p2[0] * p2[0] - p2[1] * p2[1]);
        return Arrays.copyOfRange(points, 0, K);
    }
}
```
java对不起

**quick select**
```text
The last solution is based on quick sort, we can also call it quick select. In the quick sort, we will always choose a pivot to compare with other elements. After one iteration, we will get an array that all elements smaller than the pivot are on the left side of the pivot and all elements greater than the pivot are on the right side of the pviot (assuming we sort the array in ascending order). So, inspired from this, each iteration, we choose a pivot and then find the position p the pivot should be. Then we compare p with the K, if the p is smaller than the K, meaning the all element on the left of the pivot are all proper candidates but it is not adequate, we have to do the same thing on right side, and vice versa. If the p is exactly equal to the K, meaning that we've found the K-th position. Therefore, we just return the first K elements, since they are not greater than the pivot.

Theoretically, the average time complexity is O(N) , but just like quick sort, in the worst case, this solution would be degenerated to O(N^2), and pratically, the real time it takes on leetcode is 15ms.

The advantage of this solution is it is very efficient.
The disadvatage of this solution are it is neither an online solution nor a stable one. And the K elements closest are not sorted in ascending order.
```
The short code shows as follows:
```java
class solution {
    public int[][] kClosest(int[][] points, int K) {
        int len =  points.length, l = 0, r = len - 1;
        while (l <= r) {
            int mid = helper(points, l, r);
            if (mid == K) break;
            if (mid < K) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return Arrays.copyOfRange(points, 0, K);
    }
    
    private int helper(int[][] A, int l, int r) {
        int[] pivot = A[l];
        while (l < r) {
            while (l < r && compare(A[r], pivot) >= 0) r--;
            A[l] = A[r];
            while (l < r && compare(A[l], pivot) <= 0) l++;
            A[r] = A[l];
        }
        A[l] = pivot;
        return l;
    }
    
    private int compare(int[] p1, int[] p2) {
        return p1[0] * p1[0] + p1[1] * p1[1] - p2[0] * p2[0] - p2[1] * p2[1];
    }
}
```

## Linked List

### 23. Merge k Sorted Lists
#### Problem
```text
Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.

Example:
Input:
[
  1->4->5,
  1->3->4,
  2->6
]
Output: 1->1->2->3->4->4->5->6
```
#### Solution
A brilliant solution to solve this problem with a priority queue.  
Since all lists are sorted, instead of store all ListNodes into the queue, we could just store the first node into it.  
```java
class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        ListNode res = new ListNode(0);
        ListNode cur = res;
        PriorityQueue<ListNode> minQ = new PriorityQueue<>((l1, l2) -> l1.val - l2.val);
        for (ListNode l : lists) {
            if (l != null) {
                minQ.offer(l);
            }
        }
        while (!minQ.isEmpty()) {
            ListNode tmp = minQ.poll();
            cur.next = tmp;
            if (tmp.next != null) {
                minQ.offer(tmp.next);
            }
            cur = cur.next;
        }
        return res.next;
    }
}
```

### 206. Reverse Linked List
#### Problem
```text
Reverse a singly linked list.

Example:
Input: 1->2->3->4->5->NULL
Output: 5->4->3->2->1->NULL

Follow up:

A linked list can be reversed either iteratively or recursively. Could you implement both?
```
#### Solution
An elegant in-place solution by iteration 
```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode reverseList(ListNode head) {
        ListNode cur = head, pre = null;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }
}
```
`TODO: recursion` 


## Tree

### 94. Binary Tree Inorder Traversal
#### Problem
```text
Given a binary tree, return the inorder traversal of its nodes' values.

Example:

Input: [1,null,2,3]
   1
    \
     2
    /
   3

Output: [1,3,2]

Follow up: Recursive solution is trivial, could you do it iteratively?
```
#### Solution
First start with the recursive approach.
```python
# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None

class Solution:
    def inorderTraversal(self, root: TreeNode) -> List[int]:
        def trav(node, res):
            if node:
                trav(node.left, res)
                res.append(node.val)
                trav(node.right, res)
        res = []
        trav(root, res)
        return res
```
Since recursive solution is trivial :), let's do it iteratively!
```python
class Solution:
    def inorderTraversal(self, root: TreeNode) -> List[int]:
        stack, res = [], []
        cur = root
        while cur or stack:
            while cur:
                stack.append(cur)
                cur = cur.left
            cur = stack.pop()
            res.append(cur.val)
            cur = cur.right
        return res
```


### 144. Binary Tree Preorder Traversal
#### Problem
```text
Given a binary tree, return the preorder traversal of its nodes' values.

Example:

Input: [1,null,2,3]
   1
    \
     2
    /
   3

Output: [1,2,3]

Follow up: Recursive solution is trivial, could you do it iteratively?
```
#### Solution
Again, start with the trivial recursive approach.
```python
# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None

class Solution:
    def preorderTraversal(self, root: TreeNode) -> List[int]:
        def trav(node, res):
            if node:
                res.append(node.val)
                trav(node.left, res)
                trav(node.right, res)
        res = []
        trav(root, res)
        return res
```
Then the iterative approach.
```python
class Solution:
    def preorderTraversal(self, root: TreeNode) -> List[int]:
        stack, res = [root], []
        while stack:
            cur = stack.pop()
            if cur:
                res.append(cur.val)
                if cur.right:
                    stack.append(cur.right)
                if cur.left:
                    stack.append(cur.left)
        return res
```


### 145. Binary Tree Postorder Traversal
#### Problem
```text
Given a binary tree, return the postorder traversal of its nodes' values.

Example:

Input: [1,null,2,3]
   1
    \
     2
    /
   3

Output: [3,2,1]

Follow up: Recursive solution is trivial, could you do it iteratively?
```
#### Solution
Finally comes the postorder, let's straightforwardly go to the iterative approach.
```python
# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None

class Solution:
    def postorderTraversal(self, root: TreeNode) -> List[int]:
        stack, res = [root], []
        while stack:
            cur = stack.pop()
            if cur:
                res.append(cur.val)            
                if cur.left:
                    stack.append(cur.left)
                if cur.right:
                    stack.append(cur.right)
        return res[::-1]  # wow, awesome :D
```


### 235. Lowest Common Ancestor of a Binary Search Tree
#### Problem
```text
Given a binary search tree (BST), find the lowest common ancestor (LCA) of two given nodes in the BST.

According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes p and q as the lowest node in T that has both p and q as descendants (where we allow a node to be a descendant of itself).”

Given binary search tree:  root = [6,2,8,0,4,7,9,null,null,3,5]
```
![tree_image](https://assets.leetcode.com/uploads/2018/12/14/binarysearchtree_improved.png)
```
Example 1:
Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
Output: 6
Explanation: The LCA of nodes 2 and 8 is 6.

Example 2:
Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 4
Output: 2
Explanation: The LCA of nodes 2 and 4 is 2, since a node can be a descendant of itself according to the LCA definition.
```
#### Solution
Since it's a BST, the LCA is the split point of the two nodes, we could easily find it with this property. 
```python
# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None

class Solution:
    def lowestCommonAncestor(self, root: 'TreeNode', p: 'TreeNode', q: 'TreeNode') -> 'TreeNode':
        if not root:
            return None
        cur = root
        while cur:
            if cur.val < p.val and cur.val < q.val:
                cur = cur.right
            elif cur.val > p.val and cur.val > q.val:
                cur = cur.left
            else:
                return cur
        return None
```


### 236. Lowest Common Ancestor of a Binary Tree
#### Problem
```text
Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.

According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes p and q as the lowest node in T that has both p and q as descendants (where we allow a node to be a descendant of itself).”

Given the following binary tree:  root = [3,5,1,6,2,0,8,null,null,7,4]
```
![tree_image](https://assets.leetcode.com/uploads/2018/12/14/binarytree.png)
```text
Example 1:
Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
Output: 3
Explanation: The LCA of nodes 5 and 1 is 3.

Example 2:
Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
Output: 5
Explanation: The LCA of nodes 5 and 4 is 5, since a node can be a descendant of itself according to the LCA definition.
 
Note:
All of the nodes' values will be unique.
p and q are different and both values will exist in the binary tree.
```
#### Solution
Without the property of BST, we cannot easily find the LCA by a split point.  
The idea here is to use a map to point each nodes to their parent nodes.   
1. Iterate down through the root node until p and q was found, then we have all the ancestor nodes of p & q.   
2. Add all ancestors of p into a set, and finally iterate up through ancestors of q.  
Once a ancestor of q was found in the set, it is the LCA.  

```python
# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None

class Solution:
    def lowestCommonAncestor(self, root: 'TreeNode', p: 'TreeNode', q: 'TreeNode') -> 'TreeNode':
        parent = {root: None}
        stack = [root]
        # use a stack to traversal the tree and store parent nodes of each nodes in the parent dict
        while stack and (p not in parent or q not in parent):
            cur = stack.pop()
            if cur.right:
                parent[cur.right] = cur
                stack.append(cur.right)
            if cur.left:
                parent[cur.left] = cur
                stack.append(cur.left)
        ancestor = set()
        # add all ancestors of p into the ancestor set
        while p:
            ancestor.add(p)
            p = parent[p]
        # iterate through ancestors of q
        while q:
            if q in ancestor:
                return q
            q = parent[q]
        return None
```

### 98. Validate Binary Search Tree
#### Problem
```text
Given a binary tree, determine if it is a valid binary search tree (BST).

Assume a BST is defined as follows:

The left subtree of a node contains only nodes with keys less than the node's key.
The right subtree of a node contains only nodes with keys greater than the node's key.
Both the left and right subtrees must also be binary search trees.
 
Example 1:

    2
   / \
  1   3

Input: [2,1,3]
Output: true

Example 2:

    5
   / \
  1   4
     / \
    3   6

Input: [5,1,4,null,null,3,6]
Output: false
Explanation: The root node's value is 5 but its right child's value is 4.
```
#### Solution
Basic idea is to have a inorder traversal of the tree. If any of the pre node.val < cur node.val, then it's not a BST. 
```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public boolean isValidBST(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root, pre = null;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            if (pre != null && cur.val <= pre.val) {
                return false;
            }
            pre = cur;
            cur = cur.right;
        }
        return true;
    }
}
```
