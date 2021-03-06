/*
 * @lc app=leetcode id=433 lang=java
 *
 * [433] Minimum Genetic Mutation
 *
 * https://leetcode.com/problems/minimum-genetic-mutation/description/
 *
 * algorithms
 * Medium (38.66%)
 * Likes:    350
 * Dislikes: 42
 * Total Accepted:    27.8K
 * Total Submissions: 68.8K
 * Testcase Example:  '"AACCGGTT"\n"AACCGGTA"\n["AACCGGTA"]'
 *
 * A gene string can be represented by an 8-character long string, with choices
 * from "A", "C", "G", "T".
 * 
 * Suppose we need to investigate about a mutation (mutation from "start" to
 * "end"), where ONE mutation is defined as ONE single character changed in the
 * gene string.
 * 
 * For example, "AACCGGTT" -> "AACCGGTA" is 1 mutation.
 * 
 * Also, there is a given gene "bank", which records all the valid gene
 * mutations. A gene must be in the bank to make it a valid gene string.
 * 
 * Now, given 3 things - start, end, bank, your task is to determine what is
 * the minimum number of mutations needed to mutate from "start" to "end". If
 * there is no such a mutation, return -1.
 * 
 * Note:
 * Starting point is assumed to be valid, so it might not be included in the
 * bank.
 * If multiple mutations are needed, all mutations during in the sequence must
 * be valid.
 * You may assume start and end string is not the same.
 * 
 * 
 * Example 1:
 * start: "AACCGGTT"
 * end:   "AACCGGTA"
 * bank: ["AACCGGTA"]
 * return: 1
 * 
 * Example 2:
 * start: "AACCGGTT"
 * end:   "AAACGGTA"
 * bank: ["AACCGGTA", "AACCGCTA", "AAACGGTA"]
 * return: 2
 * 
 * Example 3:
 * start: "AAAAACCC"
 * end:   "AACCCCCC"
 * bank: ["AAAACCCC", "AAACCCCC", "AACCCCCC"]
 * return: 3
 */

// @lc code=start
class Solution {
    public int minMutation(String start, String end, String[] bank) {
        char[] gene = new char[]{'A', 'C', 'G', 'T'};        
        Set<String> set = new HashSet<>(Arrays.asList(bank));
        Map<String, Integer> map = new HashMap<>();
        Queue<String> q = new LinkedList<>();
        q.offer(start);
        map.put(start, 0);
        
        while (!q.isEmpty()) {
            String cur = q.poll();
            int level = map.get(cur);
            if (cur.equals(end)) return level;
            for (int i = 0; i < cur.length(); i++) {
                for (char c : gene) {
                    String mut = cur.substring(0, i) + c + cur.substring(i + 1, cur.length());
                    if (set.contains(mut) && !map.containsKey(mut)) {
                        q.offer(mut);
                        map.put(mut, level + 1);
                    }
                }
            }
        }
        
        return -1;
    }
}
// @lc code=end
