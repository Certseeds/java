// SPDX-License-Identifier: AGPL-3.0-or-later

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lab14_Q3 {
    public static void main(String[] args) {
        String string_pattern = "(?=.*[A-Z]+)(?=.*[a-z]+)(?=.*\\d+)(?=.*[\\~\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\|\\<\\>\\,\\.\\?\\/\\:\\;\\'\\[\\]\\{\\}\\\\]+).{8,16}";
        Scanner input = new Scanner(System.in);
        Pattern p = Pattern.compile(string_pattern);
        while (true) {
            String recieve_input = input.nextLine();
            Matcher m = p.matcher(recieve_input);
            if (m.matches()) {
                System.out.println("True");
            } else {
                System.out.println("False");
            }
        }
    }
}
