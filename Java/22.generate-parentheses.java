/*
 * @lc app=leetcode id=22 lang=java
 *
 * [22] Generate Parentheses
 *
 * https://leetcode.com/problems/generate-parentheses/description/
 *
 * algorithms
 * Medium (56.06%)
 * Likes:    3473
 * Dislikes: 206
 * Total Accepted:    411.2K
 * Total Submissions: 712.5K
 * Testcase Example:  '3'
 *
 * 
 * Given n pairs of parentheses, write a function to generate all combinations
 * of well-formed parentheses.
 * 
 * 
 * 
 * For example, given n = 3, a solution set is:
 * 
 * 
 * [
 * ⁠ "((()))",
 * ⁠ "(()())",
 * ⁠ "(())()",
 * ⁠ "()(())",
 * ⁠ "()()()"
 * ]
 * 
 */

// @lc code=start
class Solution {
    /* runtime / space : O(4^n / sqrt(n)) */
    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        helper(n, 0, new StringBuilder(), res);
        return res;
    }
    
    private void helper(int l, int r, StringBuilder cur, List<String> res) {
        if (l == 0 && r == 0) {
            res.add(cur.toString());
            return;
        }
        if (l > 0) {
            cur.append("(");
            helper(l - 1, r + 1, cur, res);
            cur.deleteCharAt(cur.length() - 1);
        }
        if (r > 0) {
            cur.append(")");
            helper(l, r - 1, cur, res);
            cur.deleteCharAt(cur.length() - 1);
        }
    }
}
// @lc code=end
