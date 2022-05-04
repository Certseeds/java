// SPDX-License-Identifier: AGPL-3.0-or-later

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
* Suppose you are working for an express company, you accept a new requirement. You
need to automatically extract province, city, district, street, zip code, telephone number
and other information from user input address information
Input:
    广东省深圳市南山区学苑大道南山智园 A7 栋，518055，13422221111，赵老师收
Output:
    Province: 广东
    City: 深圳
    District: 南山
    Street: 学苑大道
    Zip code: 518055
    Telephone number: 13422221111
* */
public class Lab14_Q42 {
    public static void main(String[] args) {
        String string_pattern = "^\\s*(.+)\\s*省\\s*(.+)\\s*市\\s*(.+)\\s*区\\w*(.+[道路街]).+(\\d{6}).+([1]([3-9])[0-9]{9}).*$";
        Scanner input = new Scanner(System.in);
        Pattern p = Pattern.compile(string_pattern);
        while (true) {
            String recieve_input = input.nextLine();
            Matcher m = p.matcher(recieve_input);
            if (m.find()) {
                System.out.println("Province: " + m.group(1));
                System.out.println("City: " + m.group(2));
                System.out.println("District: " + m.group(3));
                System.out.println("Street: " + m.group(4));
                System.out.println("Zip code: " + m.group(5));
                System.out.println("Telephone number: " + m.group(6));
            } else {
                System.out.println("NO MATCH");
            }
        }
    }
}
