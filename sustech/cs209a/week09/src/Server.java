// SPDX-License-Identifier: AGPL-3.0-or-later

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Server {
    private static final String csv_path = "resources/StudentsGrade.csv";
    private static final int server_port = 23333;
    private static HashMap<String, Integer> name_grade;

    public static void main(String[] args) {
        if (!get_name_grade(csv_path)) {
            System.out.println("Error on reading file");
            return;
        }
        recieve();
    }

    public static boolean get_name_grade(String path) {
        name_grade = new HashMap<>();
        try (var reader = Files.lines(Path.of(path))) {
            reader.skip(1).forEach(x -> {
                        if (x != null) {
                            if (null == name_grade.get(x.split(",")[0])) {
                                name_grade.put(x.split(",")[0], Integer.valueOf(x.split(",")[1]));
                            }
                        }
                    }
            );
            //System.out.println(12);
        } catch (FileNotFoundException ffe) {
            System.out.println(path + " not found");
            return false;
        } catch (IOException ioe) {
            System.out.println("IO exception");
            return false;
        }
        return true;
    }

    public static void recieve() {
        try {
            ServerSocket ss1 = new ServerSocket(server_port);
            System.out.println("Server begin");
            while (true) {
                try (Socket client_socket = ss1.accept();
                     PrintWriter out = new PrintWriter(client_socket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
                ) {
                    String line = in.readLine();
                    System.out.println("Received " + line);
                    String[] recieve_command = line.split(" ");
                    String back_sentence = "";
                    switch (recieve_command[0]) {
                        case "NAME" -> {
                            back_sentence = String.valueOf(get_name_to_grade(line.substring(recieve_command[0].length() + 1)));
                            if ("-1".equals(back_sentence)) {
                                back_sentence = "Can not find this";
                            }
                        }
                        case "GRADE" -> {
                            if (3 == recieve_command.length) {
                                back_sentence = get_grade_range(Integer.parseInt(recieve_command[1]), Integer.parseInt(recieve_command[2]));
                            }
                        }
                        case "TOP" -> {
                            if (2 == recieve_command.length) {
                                back_sentence = get_rank_range(Integer.parseInt(recieve_command[1]));
                            }
                        }
                        default -> {
                            back_sentence = "Command not found";
                        }
                    }
                    System.out.println("Command processed");
                    out.println(back_sentence);
                }
            }
        } catch (IOException ioe) {
            System.out.println("Unknown io exception");
        }
    }

    public static int get_name_to_grade(String name) {
        return name_grade.getOrDefault(name, -1);
    }

    public static String get_grade_range(int begin, int end) {
        final StringBuilder will_return = new StringBuilder();
        if (begin > end) {
            int temp = begin;
            begin = end;
            end = temp;
            will_return.append("Please ensure the number order \n");
        }
        for (String key : name_grade.keySet()) {
            final var element = name_grade.get(key);
            if (begin <= element && element < end) {
                will_return.append(key).append(",").append(name_grade.get(key)).append("\n");
            }
        }
        return will_return.toString();
    }

    public static String get_rank_range(int range) {
        final StringBuilder will_return = new StringBuilder();
        final List<Integer> scores = new ArrayList<Integer>();
        for (int i : name_grade.values()) {
            if (!scores.contains(i)) {
                scores.add(i);
            }
        }
        scores.sort(Collections.reverseOrder());
        for (int i = 0; i < Math.min(range, scores.size()); i++) {
            for (String key : name_grade.keySet()) {
                if (name_grade.get(key).equals(scores.get(i))) {
                    will_return.append(key).append(",").append(scores.get(i)).append("\n");
                }
            }
        }
        return will_return.toString();
    }
}
