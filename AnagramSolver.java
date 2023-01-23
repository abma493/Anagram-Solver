/* 
 *  AnagramSolver class solves all possible anagrams in a given word or phrase.
 *  
 *  Based on a program by Stuart Reges, written by Abraham Martinez.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

public class AnagramSolver {

  private TreeMap<String, LetterInventory> wordsInventory;
  private TreeMap<String, LetterInventory> filtered;
  private LetterInventory wordPhrase;

  /*
   * pre: list != null
   * 
   * @param list Contains the words to form anagrams from.
   */
  public AnagramSolver(Set<String> dictionary) {
    if (dictionary == null) {
      throw new IllegalArgumentException("Dictionary is empty.");
    }
    wordsInventory = new TreeMap<>();
    Iterator<String> itr = dictionary.iterator();
    while (itr.hasNext()) {
      String key = itr.next();
      wordsInventory.put(key, new LetterInventory(key));
    }
  }

  /*
   * pre: maxWords >= 0, s != null, s contains at least one
   * English letter.
   * Return a list of anagrams that can be formed from s with
   * no more than maxWords, unless maxWords is 0 in which case
   * there is no limit on the number of words in the anagram
   */
  public List<List<String>> getAnagrams(String s, int maxWords) {
    if (maxWords < 0 || s == null || !(s.matches(".*[a-zA-Z]+.*"))) {
      throw new IllegalArgumentException("Invalid word/phrase or max");
    }
    wordPhrase = new LetterInventory(s);
    filterDictionary(wordPhrase);
    Set<Stack<String>> getAnagrams = new HashSet<>();
    ArrayList<List<String>> results = new ArrayList<>();
    getAnagrams(new LetterInventory(""), new Stack<>(), getAnagrams, maxWords);
    results.addAll(getAnagrams);
    Collections.sort(results, new AnagramComparator());
    return results;
  }

  // Helper method for getAnagrams(), employs recursion to find anagrams.
  private void getAnagrams(LetterInventory candidate, Stack<String> currAnagram, Set<Stack<String>> results, int max) {
    if (candidate.equals(wordPhrase) && currAnagram.size() <= max) {
      System.out.println(candidate.toString() + "\n");
      Stack<String> safeCopy = deepCopy(currAnagram);
      Collections.sort(safeCopy);
      results.add(safeCopy);
    } else {
      for (String word : filtered.keySet()) {
        LetterInventory possible = candidate.add(filtered.get(word));
        boolean isCandidate = isCandidate(possible);
        if (isCandidate) {
          // System.out.println(filtered.get(word));
          currAnagram.push(word);
          getAnagrams(possible, currAnagram, results, max);
          currAnagram.pop();
        }
      }
    }
  }

  // Helper method for recursive getAnagrams()
  private boolean isCandidate(LetterInventory possible) {
    return wordPhrase.subtract(possible) != null && wordPhrase.subtract(possible).size() != 1;
  }

  // Helper method for recursive method. Creates a deep copy for the current
  // Anagram to prevent data from being overwritten.
  private Stack<String> deepCopy(Stack<String> currAnagram) {
    Stack<String> result = new Stack<>();
    for (String elem : currAnagram) {
      result.push(elem);
    }
    return result;
  }

  // filters the dictionary to allow only words with relevant letters from
  // wordPhrase.
  private void filterDictionary(LetterInventory wordPhrase) {
    filtered = new TreeMap<>();
    for (String word : wordsInventory.keySet()) {
      LetterInventory itsInventory = wordsInventory.get(word);
      if (wordPhrase.subtract(itsInventory) != null) {
        filtered.put(word, itsInventory);
      }
    }
  }

  // An inner class to allow comparison for List of Lists.
  private static class AnagramComparator implements Comparator<List<String>> {
    @Override
    public int compare(List<String> a1, List<String> a2) {
      if (a1.size() < a2.size()) {
        return -1;
      } else if (a1.size() > a2.size()) {
        return 1;
      } else {
        return a1.get(0).compareTo(a2.get(0));
      }
    }
  }

}
