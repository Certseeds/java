import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class Main{

    public static void main(String[] args) throws NumberFormatException, IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int times = Integer.parseInt(br.readLine());
        StringTokenizer st;
        for (int i = 0; i < times; i ++) {
            st = new StringTokenizer(br.readLine());
            int lengthOfIndex = Integer.parseInt(st.nextToken());
            int [] menList  = new int [lengthOfIndex];
            int lengthOfSword = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine());
            for (int j = 0;j < lengthOfIndex;j++) {
                menList [j] =  Integer.parseInt(st.nextToken());
                //System.out.println(menList[j]);
            }
//          int begin = 0;
//          int finalNumber = lengthOfIndex -1;
//          int middle = 0;
            int sword = 0;
            //int lengNeeded = lengthOfSword - menList[j];
            for (int j = 0; j < lengthOfIndex;j++) {
                int begin = 0;
                int finalNumber = lengthOfIndex -1;
                int middle = 0;
                int lengNeeded = lengthOfSword - menList[j];
                //System.out.println(lengNeeded + ",");
                a1:while (begin <= finalNumber) {
                    middle = begin +(finalNumber - begin)/2;
                    if (menList[middle] > lengNeeded) {
                        //System.out.println(menList[middle]);
                        finalNumber = middle -1;
                    }
                    else if (menList[middle] < lengNeeded) {
                        //System.out.println(menList[middle]);
                        begin = middle + 1;
                    }
                    else if (menList[middle] == lengNeeded) {
                        //System.out.println(menList[middle]);
                        sword = sword +1;
                        //System.out.println("Sword++");
                        break a1;
                    }
                }

            }
            sword = sword /2 ;
            if (i+1 == times) {
                System.out.print(sword);
            }
            else {
                System.out.println(sword);
            }
        }

    }




}
