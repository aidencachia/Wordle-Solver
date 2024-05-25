import java.util.*;
public class Wordle{
    public static void main(String args[]){

        Word w = new Word();
        Scanner input = new Scanner(System.in);
        char user = '0';
        boolean solved = false;

        do{
            System.out.print("Enter Grey Letters: ");
            String userStr = input.next();

            if(userStr.charAt(0) != '0'){
                for (char c : userStr.toCharArray()){
                    w.addGrey(c);
                }
            }

            do{
                System.out.print("Enter Orange Letter: ");
                user = input.next().charAt(0);

                if(user != '0'){
                    int position;

                    System.out.print("Enter Position: ");
                    position = input.nextInt()-1;

                    w.addYellow(user, position);
                }

            } while (user != '0');
            do{

                System.out.print("Enter Green Letter: ");
                user = input.next().charAt(0);

                if(user != '0'){
                    System.out.print("Enter Position: ");
                    int position = input.nextInt()-1;
                    w.addGreen(user, position);
                }

            } while (user != '0');

            System.out.println(w.wordCheck());
            System.out.println("Is this correct? (y/n)");
            user = input.next().charAt(0);

            if(user == 'y'){
                solved = true;
            }
            
        } while (!solved);
        input.close();
    }
}