import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordleSimulator {
    static String word;
    static int run = 1;
    static Word w;
    static String filePath = "WordSets/wordleU.txt";
    static Map<Character, List<Integer>> charPos;


    public static boolean wordCheck(String word, String recommendedWord){
        // boolean grey[] = new boolean[5];
        // for (int i = 0; i < recommendedWord.length(); i++){
        //     if(charPos.containsKey(recommendedWord.charAt(i))){
        //         for (int j = 0; j < word.length(); j++){
                    
        //             if (charPos.get(recommendedWord.charAt(i)).contains(j)){
        //                 w.addGreen(word.charAt(i), i);
        //                 break;
        //             }else {
        //                 w.addYellow(recommendedWord.charAt(i), j);
        //                 break;
        //             }
        //         }
        //     } else{
        //         w.addGrey(recommendedWord.charAt(i));
        //     }
        // }

        // return recommendedWord.equals(word);


        for (int i = 0; i < recommendedWord.length(); i++){
            
            if (recommendedWord.charAt(i) == word.charAt(i)){
                w.addGreen(word.charAt(i), i);
            }

            boolean grey[] = new boolean[5];

            for (int j = 0; j < word.length(); j++){
                if (charPos.containsKey(recommendedWord.charAt(i)) && ! charPos.get(recommendedWord.charAt(i)).contains(j)){

                    w.addYellow(recommendedWord.charAt(i), j);
                    
                }
                else{
                    grey[j] = true;
                }
            }

            boolean allGrey = true;

            for(boolean b : grey){
                if(!b){
                    allGrey = false;
                }
            }

            if(allGrey){
                w.addGrey(recommendedWord.charAt(i));
            }
        }

        return recommendedWord.equals(word);
    }
    public static int runGame(){
        charPos = new HashMap<>();
        w = new Word();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            int linecount = 0;
            while (true) {
                String line = br.readLine();
                if(line == null)
                    break;
                linecount++;
            }
            br.close();
            br = new BufferedReader(new FileReader(filePath));

            int wordIndex = (int)(Math.random()*(linecount+1));

            for(int i = 0; i < wordIndex; i++){
                word = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 5 ; i++) {
            charPos.putIfAbsent(word.charAt(i), new ArrayList<Integer>());

            charPos.get(word.charAt(i)).add(i);
        }

        // System.out.println("Word Chosen: "+word);
        boolean solved = false;
        int count = 0;
        do{
            String recommendedWord = w.wordCheck(word);
            // System.out.println(recommendedWord);
            solved = wordCheck(word, recommendedWord);
            count++;
        } while (!solved);
        return count;
    }
}
