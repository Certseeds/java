package week05;

import java.util.List;

/**
 * @Github: https://github.com/Certseeds
 * @Organization: SUSTech
 * @Author: nanoseeds
 * @Date: 2020-03-17 15:35:46
 * @LastEditors : nanoseeds
 * @LastEditTime : 2020-03-17 15:35:46
 */
public class week05 {
    public static void main(String[] args) {
        final List<String> stringList = List.of("aa", "bb", "", "d", "", "f", "g", "h", "j");
        final List<String> emptys = stringList.stream()
                .filter(string -> !string.isEmpty()).toList();
        for (String s : emptys) {
            System.out.println(s);
        }
    }
}
