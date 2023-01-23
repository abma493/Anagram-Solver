/*
 * 
 * LetterInventory class for the Anagram Assignment. This class stores the number
 * of times each English letter 'a' through 'z' occur in a word or phrase.
 * 
 * Written by Abraham Martinez 
 */
public class LetterInventory {

  private int[] lettersFreq;
  private static final int ALPHABET = 26;
  private String phrase;

  public LetterInventory(String phrase) {
    if (phrase == null) {
      throw new IllegalArgumentException("Word/Phrase is null");
    }
    lettersFreq = new int[ALPHABET];
    String lowerCase = phrase.toLowerCase();
    this.phrase = lowerCase;
    for (int ch = 0; ch < lowerCase.length(); ch++) {
      char letter = lowerCase.charAt(ch);
      if (letter >= 'a' && letter <= 'z') {
        int index = (letter - 'a');
        lettersFreq[index]++;
      }
    }
  }

  /*
   * pre: char must be an English letter (A-Z)/(a-z)
   * post: get the frequency number of a valid letter.
   */
  public int get(char letter) {
    if ((letter < 'a' && letter > 'z') || (letter < 'A' && letter > 'Z')) {
      throw new IllegalArgumentException("Letter not in the English Alphabet");
    }
    char base = (letter < 'a') ? 'A' : 'a';
    int index = (letter - base);
    return lettersFreq[index];
  }

  /*
   * Returns the total number of letters in this LetterInventory.
   */
  public int size() {
    int size = 0;
    for (int i = 0; i < lettersFreq.length; i++) {
      size += lettersFreq[i];
    }
    return size;
  }

  /*
   * Checks if the size of this inventory is 0.
   */
  public boolean isEmpty() {
    int size = this.size();
    return size == 0;
  }

  /*
   * Returns a string representation of the Letter inventory array.
   * It is ordered alphabetically, thus the letters return sorted.
   */
  public String toString() {
    String result = "";
    int index = 0;
    while (index < lettersFreq.length) {
      char letter = (char) (index + 'a');
      int times = 0;
      while (times < lettersFreq[index]) {
        result += letter;
        times++;
      }
      index++;
    }
    return result;
  }

  /*
   * param(s): Another LetterInventory Object
   * pre: other != null
   * post: new LetterInventory created by addingthe frequencies from the calling
   * object (this) and (other) object.
   */
  public LetterInventory add(LetterInventory other) {
    if (other == null) {
      throw new IllegalArgumentException("LetterInventory object is null.");
    }
    LetterInventory result = new LetterInventory(this.phrase + other.phrase);
    return result;
  }

  /*
   * param(s): Another LetterInventory Object
   * pre: other != null
   * post: new LetterInventory Obj. created by subtracting the letter frequencies
   * of the explicit parameter from the calling object's letter frequencies.
   * 
   */
  public LetterInventory subtract(LetterInventory other) {
    if (other == null) {
      throw new IllegalArgumentException("LetterInventory object is null.");
    }
    LetterInventory result = new LetterInventory(this.phrase);
    for (int i = 0; i < other.phrase.length(); i++) {
      char let = other.phrase.charAt(i);
      if (let != ' ' && let != '-' && let != '"') {
        int index = (int) (let - 'a');
        if (result.phrase.contains("" + let)) {
          result.lettersFreq[index]--;
          if (result.lettersFreq[index] < 0) {
            return null;
          }
        } else if (result.lettersFreq[index] == 0) { // doesn't contain letter
          return null;
        }
      }
    }
    return result;
  }

  /*
   * Determines if this LinkedList is equal to other.
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof LetterInventory) || obj == null) {
      return false;
    } else if (obj == this) {
      return true;
    } else {
      LetterInventory other = (LetterInventory) obj;
      for (int freq = 0; freq < lettersFreq.length; freq++) {
        if (this.lettersFreq[freq] != other.lettersFreq[freq]) {
          return false;
        }
      }
      return true;
    }
  }

}
