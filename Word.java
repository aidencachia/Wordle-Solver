import java.io.*; import java.util.*;
class Word{
    ArrayList<Character> grey= new ArrayList<Character>();
    ArrayList<Character> yellow= new ArrayList<Character>();
    ArrayList<Character> green= new ArrayList<Character>();
    boolean[][] yellowPos = new boolean[5][5];
    int best;
    int worst;
    public Word(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("WordSets/wordle.txt"));
            while (true) {
                String line = br.readLine();
                if(line == null)
                    break;
                WordleSimulator.words.add(line);
            }
            br.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        green.add('\0');
        green.add('\0');
        green.add('\0');
        green.add('\0');
        green.add('\0');
    }

    boolean grayCheck(String word){
        for (int i = 0; i < 5; i++)
            if (grey.contains(word.charAt(i))) {
                return false;
            }

        return true;
    }

    boolean yellowCheck(String word){
        for (int i = 0; i < yellow.size(); i++) {
            boolean charFound = false;

            for (int j = 0; j < 5; j++) {
                if (word.charAt(j) == yellow.get(i)) {
                    charFound = true;

                    if (yellowPos[i][j]) {
                        return false;
                    }
                }
            }

            if(!charFound)
                return false;
        }
        return true;
    }

    boolean greenCheck(String word){
        boolean apply = true;

        for (int i = 0; i < 5; i++) {
            if (word.charAt(i) != green.get(i) && green.get(i) != '\0') {
                apply = false;
                break;
            }
        }

        return apply;
    }
    String wordCheck(boolean debug){
        int charCount[] = new int[26];
        int charCountPos[][] = new int[26][5];

        ArrayList<String> prevWords = WordleSimulator.words;
        WordleSimulator.words = new ArrayList<String>();
        for (String word : prevWords) {
            boolean apply = true;

            if (debug)
                System.out.print("");

            apply = grayCheck(word);

            if(apply) {

                apply = yellowCheck(word);

                if(apply) {

                    apply = greenCheck(word);

                    if (apply)
                        WordleSimulator.words.add(word);
                }
            }
        }

        for(String word: WordleSimulator.words) {
            for (int i = 0; i < 5; i++) {
                charCount[word.charAt(i) - 97]++;
                charCountPos[word.charAt(i) - 97][i]++;
            }
        }

        double bestWordScore = 0;
        double worstWordScore = 99999999;

        for(int i = 0; i < WordleSimulator.words.size(); i++){
            double wordScore = 0;
            char[] prevChars = new char[5];
            for(int j = 0; j < 5; j++){
                boolean check = true;
                char currentChar = WordleSimulator.words.get(i).charAt(j);
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
        return WordleSimulator.words.get(best);
    }
}