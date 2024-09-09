package DataStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple Trie implementation for storing and retrieving strings.
 */
public class Trie {

    // Node structure for the Trie
    private static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>(); // Child nodes
        boolean isEndOfWord = false; // Marks end of a complete word
    }

    private final TrieNode root = new TrieNode(); // Root node of the Trie

    /**
     * Inserts a word into the Trie.
     * @param word the word to insert
     */
    public void insert(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            // Create a new node if the character doesn't exist
            current = current.children.computeIfAbsent(c, k -> new TrieNode());
        }
        current.isEndOfWord = true; // Mark the last node as the end of a word
    }

    /**
     * Searches for a word in the Trie (exact match).
     * @param word the word to search for
     * @return true if the word exists, false otherwise
     */
    public boolean search(String word) {
        TrieNode node = searchWordLastNode(word);
        return node != null && node.isEndOfWord; // Check if it's a valid end of a word
    }

    /**
     * Checks if there's any word in the Trie that starts with the given prefix.
     * @param prefix the prefix to check
     * @return true if any word starts with the prefix, false otherwise
     */
    public boolean startWith(String prefix) {
        return searchWordLastNode(prefix) != null; // Just check if the prefix exists
    }

    /**
     * Returns all words stored in the Trie.
     * @return a list of all words in the Trie
     */
    public List<String> collect() {
        List<String> results = new ArrayList<>();
        collectAllWords(root, new StringBuilder(), results); // Recursively collect words
        return results;
    }

    /**
     * Finds and returns all words in the Trie that start with the given prefix.
     * @param prefix the prefix to search for
     * @return a list of words that start with the given prefix
     */
    public List<String> keysWithPrefix(String prefix) {
        List<String> results = new ArrayList<>();
        TrieNode node = searchWordLastNode(prefix);
        if (node != null) {
            collectAllWords(node, new StringBuilder(prefix), results); // Collect words from the prefix node
        }
        return results;
    }

    /**
     * Finds the longest prefix of the given word that exists in the Trie.
     * @param word the word to search for
     * @return the longest matching prefix
     */
    public String longestPrefixOf(String word) {
        TrieNode current = root;
        int length = 0; // Track the length of the matching prefix
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            TrieNode node = current.children.get(c);
            if (node == null) {
                break; // Stop if the path breaks
            }
            current = node;
            if (current.isEndOfWord) {
                length = i + 1; // Update the length if it's a valid word
            }
        }
        return word.substring(0, length); // Return the longest prefix found
    }

    // Helper: Recursively collects all words starting from the given node
    private void collectAllWords(TrieNode node, StringBuilder word, List<String> results) {
        if (node.isEndOfWord) {
            results.add(word.toString()); // Add the word to results if it's complete
        }
        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            word.append(entry.getKey()); // Add the character
            collectAllWords(entry.getValue(), word, results); // Recurse into the next node
            word.deleteCharAt(word.length() - 1); // Backtrack after recursion
        }
    }

    // Helper: Finds the node corresponding to the last character of the word or prefix
    private TrieNode searchWordLastNode(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            TrieNode node = current.children.get(c);
            if (node == null) {
                return null; // Return null if the character doesn't exist
            }
            current = node;
        }
        return current; // Return the last node reached
    }

    // Main method to test the Trie functionality
    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("sand");
        trie.insert("same");
        trie.insert("sam");
        trie.insert("awls");
        trie.insert("sad");
        trie.insert("sap");

        System.out.println(trie.collect()); // Print all words
        System.out.println(trie.keysWithPrefix("sa")); // Print words with prefix "sa"
        System.out.println(trie.longestPrefixOf("sample")); // Print the longest prefix of "sample"
    }
}
