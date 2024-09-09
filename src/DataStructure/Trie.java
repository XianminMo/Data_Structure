package DataStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trie {
    private static class TrieNode {
        private final Map<Character, TrieNode> children;
        private boolean isEndOfWord;

        public TrieNode() {
            this.children = new HashMap<>();
            this.isEndOfWord = false;
        }
    }

    private final TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            current = current.children.computeIfAbsent(c, k -> new TrieNode());
        }
        current.isEndOfWord = true;
    }

    // 插入一个单词到 Trie 中
    public boolean search(String word) {
        TrieNode node = searchWordLastNode(word);
        return node != null && node.isEndOfWord;
    }

    // 判断 Trie 中是否包含以给定前缀开头的单词
    public boolean startWith(String prefix) {
        return searchWordLastNode(prefix) != null;
    }

    // 返回存储的所有单词
    public List<String> collect() {
        List<String> results = new ArrayList<>();
        collectAllWords(root, new StringBuilder(), results);
        return results;
    }

    // 查找所有以给定前缀开头的单词
    public List<String> keysWithPrefix(String prefix) {
        List<String> results = new ArrayList<>();
        TrieNode node = searchWordLastNode(prefix);
        if (node != null) {
            collectAllWords(node, new StringBuilder(prefix), results);
        }
        return results;
    }

    // 查找给定字符串的最长前缀
    public String longestPrefixOf(String word) {
        TrieNode current = root;
        int length = 0;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            TrieNode node = current.children.get(c);
            if (node == null) {
                break;
            }
            current = node;
            if (current.isEndOfWord) {
                length = i + 1;
            }
        }
        return word.substring(0, length);
    }

    // Helper method: 从给定节点开始收集所有单词，递归＋回溯
    private void collectAllWords(TrieNode node, StringBuilder word, List<String> results) {
        if (node.isEndOfWord) {
            results.add(word.toString()); // 如果当前节点是一个完整单词，添加到结果列表
        }
        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            word.append(entry.getKey());
            collectAllWords(entry.getValue(), word, results);
            word.deleteCharAt(word.length() - 1); // 回溯，移除最后一个字符
        }
    }

    // Helper method：查找并返回与该词的最后一个字符对应的节点
    private TrieNode searchWordLastNode(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            TrieNode node = current.children.get(c);
            if (node == null) {
                return null; // 如果前缀不匹配，返回 null
            }
            current = node;
        }
        return current;
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("sand");
        trie.insert("same");
        trie.insert("sam");
        trie.insert("awls");
        trie.insert("sad");
        trie.insert("sap");

        System.out.println(trie.collect());
        System.out.println(trie.keysWithPrefix("sa"));
        System.out.println(trie.longestPrefixOf("sample"));
    }
}
