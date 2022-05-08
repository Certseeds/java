import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner cin = new Scanner(System.in);) {
            int times = cin.nextInt();
            for (int i = 0; i < times; i++) {
                int wordNumber = cin.nextInt();
                cin.nextLine();
                String[] words = new String[wordNumber];
                for (int j = 0; j < wordNumber; j++) {
                    String temp = cin.nextLine();
                    temp = temp.toLowerCase();
                    words[j] = temp;
                }
                int number = cin.nextInt();
                cin.nextLine();
                String sentence = cin.nextLine();
                sentence = sentence.toLowerCase();
                //System.out.println(sentence);
                String[] wordsInTheSentence = sentence.split(" ");
                boolean iscould = false;
                for (int j = 0; j < wordNumber; j++) {
                    for (int k = 0; k < number; k++) {
                        if (words[j].equals(wordsInTheSentence[k])) {
                            iscould = true;
                            break;
                        }
                    }
                }
                if (iscould) {
                    System.out.println("Appeared");
                } else {
                    System.out.println("Not appeared");
                }
            }
        }
    }
}
