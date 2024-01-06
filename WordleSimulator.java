import java.io.*;
import java.util.ArrayList;

public class WordleSimulator {
    static String word;
    static int run = 1;
    static Word w;
    static String filePath = "WordSets/wordle.txt";

    public static ArrayList<String> words=new ArrayList<String>();

    public static boolean wordCheck(String word, String recommendedWord){
        for (int i = 0; i < recommendedWord.length(); i++){
            boolean grey[] = new boolean[5];
            for (int j = 0; j < word.length(); j++){
                if (recommendedWord.charAt(i) == word.charAt(j)){

                    if(i == j && !w.green.contains(word.charAt(j)) )
                        w.green.set(j, word.charAt(j));

                    else{

                    int index;

                    if(w.yellow.contains(recommendedWord.charAt(i)))
                        index = w.yellow.indexOf(recommendedWord.charAt(i));

                    else{
                        index = w.yellow.size();
                        w.yellow.add(recommendedWord.charAt(i));
                    }

                    w.yellowPos[index][i] = true;
                    }
                }
                else{
                    grey[j] = true;
                }
            }
            boolean allGrey = true;
            for(boolean b : grey)
                if(!b)
                    allGrey = false;
            if(allGrey)
                w.grey.add(recommendedWord.charAt(i));
        }
        return recommendedWord.equals(word);
    }
    public static void main(String args[]){
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
            for(int i = 0; i < ((int)(Math.random()*linecount+1)); i++){
                word = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Word Chosen: "+word);
        boolean solved = false;
        boolean firstRun = true;
        do{
            String recommendedWord = w.wordCheck(!firstRun);
            firstRun = false;
            System.out.println(recommendedWord);
            solved = wordCheck(word, recommendedWord);
        } while (!solved);
    }
}
