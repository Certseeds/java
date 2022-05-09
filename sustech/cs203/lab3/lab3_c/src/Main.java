import quick_read.input_reader;

import java.io.PrintWriter;
import java.util.LinkedList;

public class Main {
    static PrintWriter out;
    static input_reader in;

    public static void main(String[] args) {
        out = new PrintWriter(System.out);
        in = new input_reader(System.in);
        int bookNumber = -1;
        while ((bookNumber = in.nextInt()) != 0) {
            int bookNumber2 = bookNumber;
            String temp = in.next();
            int length = temp.length();
            //int[] number = new int[length];
            LinkedList<Integer> numberList = new LinkedList<>();
            for (int i = 0; i < length; i++) {
                //number[i] = (int) temp.charAt(i) - 64;
                numberList.add((int) temp.charAt(i) - 64);
            }
            LinkedList<Integer> costomerList = new LinkedList<>();
            LinkedList<Integer> removeList = new LinkedList<>();
            for (int i = 0; i < length; i++) {
                boolean temp2 = false;
                int temp3 = numberList.getFirst();
                if (costomerList.size() >= bookNumber) {
                    if (!costomerList.contains(temp3)) {
                        temp2 = true;
                        if (!removeList.contains(temp3)) {
                            removeList.add(temp3);
                        }
                        numberList.removeLastOccurrence(temp3);
                        length--;
                    }
                }
                if (costomerList.contains(temp3) && !temp2) {
                    temp2 = true;
                    costomerList.removeFirstOccurrence(temp3);
                    bookNumber2++;
                }
                if (costomerList.size() < bookNumber && !temp2) {
                    temp2 = true;
                    costomerList.add(temp3);
                    bookNumber2--;
                }
                numberList.removeFirst();
            }
            out.println(removeList.size());
        }
        out.close(); // Don't forget this line, otherwise you will output nothing. This sentence
        // flush the buffer.
    }

}
