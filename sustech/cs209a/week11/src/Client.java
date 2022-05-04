// SPDX-License-Identifier: AGPL-3.0-or-later

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Client {
    private static final String ip = "localhost";
    private static final int client_port = 23333;

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        try (Socket s = new Socket(ip, client_port);
             PrintWriter out =
                     new PrintWriter(s.getOutputStream(), true);
             BufferedReader in =
                     new BufferedReader(new InputStreamReader(s.getInputStream()));) {
            String line = in.readLine();
            System.out.println(line);
            out.println(input.nextLine());
            String ok = in.readLine();
            System.out.println(ok);
            if (!"OK".equals(ok)) {
                return;
            }
            while (true) {
                System.out.println("Please send a command");
                String command = input.nextLine();
                while ("".equals(command)) {
                    command = input.nextLine();
                }
                out.println(command);
                while (s.isConnected()) {
                    line = in.readLine();
                    if ("end".equals(line.trim())) {
                        break;
                    }
                    System.out.println(line);
                }
            }
        } catch (SocketException se) {
            System.out.println(se.getMessage());
        }

    }
}
