/*
 * @lc app=leetcode id=703 lang=java
 *
 * [703] Kth Largest Element in a Stream
 *
 * https://leetcode.com/problems/kth-largest-element-in-a-stream/description/
 *
 * algorithms
 * Easy (46.76%)
 * Likes:    407
 * Dislikes: 198
 * Total Accepted:    46.5K
 * Total Submissions: 98.5K
 * Testcase Example:  '["KthLargest","add","add","add","add","add"]\n[[3,[4,5,8,2]],[3],[5],[10],[9],[4]]'
 *
 * Design a class to find the kth largest element in a stream. Note that it is
 * the kth largest element in the sorted order, not the kth distinct element.
 * 
 * Your KthLargest class will have a constructor which accepts an integer k and
 * an integer array nums, which contains initial elements from the stream. For
 * each call to the method KthLargest.add, return the element representing the
 * kth largest element in the stream.
 * 
 * Example:
 * 
 * 
 * int k = 3;
 * int[] arr = [4,5,8,2];
 * KthLargest kthLargest = new KthLargest(3, arr);
 * kthLargest.add(3);   // returns 4
 * kthLargest.add(5);   // returns 5
 * kthLargest.add(10);  // returns 5
 * kthLargest.add(9);   // returns 8
 * kthLargest.add(4);   // returns 8
 * 
 * 
 * Note: 
 * You may assume that nums' length ≥ k-1 and k ≥ 1.
 * 
 */

// @lc code=start
class KthLargest {
    private PriorityQueue<Integer> minQ;
    private int largest;
    
    public KthLargest(int k, int[] nums) {
        minQ = new PriorityQueue<>();
        this.largest = k;
        for (int i : nums) {
            minQ.offer(i);
            if (minQ.size() > k) {
                minQ.poll();
            }
        }
    }
    
    public int add(int val) {
        if (this.minQ.size() < largest) {
            this.minQ.offer(val);        
        } else if (val > this.minQ.peek()) {
            this.minQ.poll();
            this.minQ.offer(val);
        }
        return this.minQ.peek();
    }
}

/**
 * Your KthLargest object will be instantiated and called as such:
 * KthLargest obj = new KthLargest(k, nums);
 * int param_1 = obj.add(val);
 */
// @lc code=end

