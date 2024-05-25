import java.io.*; 
import java.util.*;
import java.util.Map.Entry;

class Word{
    Set<Character> grey;
    Map<Character,List<Integer>> yellows;
    Map<Character, Integer> greens;

    public static ArrayList<String> words;
    {
        words = new ArrayList<String>();

        try {
            BufferedReader br = new BufferedReader(new FileReader("WordSets/wordle.txt"));
            while (true) {
                String line = br.readLine();
                if(line == null)
                    break;
                words.add(line);
            }
            br.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

    }
    int best;
    int worst;

    public Word(){
        grey = new HashSet<Character>();
        yellows = new HashMap<>(5);
        greens = new HashMap<>(5);
    }

    private boolean grayCheck(String word){
        for (int i = 0; i < 5; i++)
            if (grey.contains(word.charAt(i))) {
                return false;
            }

        return true;
    }

    private boolean yellowCheck(String word){
        for(Entry<Character, List<Integer>> entry: yellows.entrySet()){
            char key = entry.getKey();
            List<Integer> value = entry.getValue();
            boolean charFound = false;

            for (int i = 0; i < 5; i++) {
                if (word.charAt(i) == key) {
                    charFound = true;

                    if (value.contains(i)) {
                        return false;
                    }
                }
            }

            if(!charFound)
                return false;
        }
        return true;
    }

    private boolean greenCheck(String word){
        for (int i = 0; i < 5; i++)
            if (i != greens.getOrDefault(word.charAt(i), i))
                return false;

        return true;
    }


    String wordCheck(boolean debug){
        int charCount[] = new int[26];
        int charCountPos[][] = new int[26][5];

        ArrayList<String> prevWords = words;
        words = new ArrayList<String>();
        for (String word : prevWords) {

            if (debug)
                System.out.print("");


            if(grayCheck(word) && yellowCheck(word) && greenCheck(word))
                words.add(word);
            
        }

        for(String word: words) {
            for (int i = 0; i < 5; i++) {
                charCount[word.charAt(i) - 97]++;
                charCountPos[word.charAt(i) - 97][i]++;
            }
        }

        double bestWordScore = Integer.MIN_VALUE;
        double worstWordScore = Integer.MAX_VALUE;

        for(int i = 0; i < words.size(); i++){
            double wordScore = 0;
            char[] prevChars = new char[5];
            for(int j = 0; j < 5; j++){
                boolean check = true;
                char currentChar = words.get(i).charAt(j);
                for(int k = 0; k < j; k++){
                    if(prevChars[k] == currentChar)
                        check = false;
                }
                if (check) {
                    wordScore += ((charCountPos[currentChar - 97][j])*2.5 + charCount[currentChar -97]);
                    prevChars[j] = currentChar;
                }
            }
            if(wordScore > bestWordScore){
                bestWordScore = wordScore;
                best = i;
            }else if (wordScore < worstWordScore) {
                worstWordScore = wordScore;
                worst = i;
            }
        }
        return words.get(best);
    }
}