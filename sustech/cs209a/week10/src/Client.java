// SPDX-License-Identifier: AGPL-3.0-or-later

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String ip = "127.0.0.1";
    private static final int client_port = 23333;

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("Please send a command");
            String command = input.nextLine();
            try (final Socket s = new Socket(ip, client_port);
                 final PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                 final BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            ) {
                out.println(command);
                String line;
                while (null != (line = in.readLine())) {
                    System.out.println(line);
                }
            }
        }
    }
}
