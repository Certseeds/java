// SPDX-License-Identifier: AGPL-3.0-or-later

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lab14_Q1 {
    public static void main(String[] args) {
        byte[] temp = "10100".getBytes();
        //        2019 年 4 月 8 日星期一
        //String string_pattern_old = "^\\s*(\\w+\\s*)年(\\s*\\w+\\s*)月(\\s*\\w+\\s*)日(\\S+)$";
        String string_pattern = "^\\s*(.+)\\s*年\\s*(.+)\\s*月\\s*(.+)\\s*日(\\S*)$";
        Scanner input = new Scanner(System.in);
        Pattern p = Pattern.compile(string_pattern);
        while (true) {
            String recieve_input = input.nextLine();
            Matcher m = p.matcher(recieve_input);
            if (m.matches()) {
                for (int i = 1; i < 4; i++) {
                    System.out.println(m.group(i));
                }
            } else {
                System.out.println("NO MATCH");
            }
        }

    }
}
