// SPDX-License-Identifier: AGPL-3.0-or-later

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.HashMap;

public class Server {
    private static final String csv_path = "resource/StudentsGrade.csv";
    private static final int server_port = 23333;
    private static HashMap<String, Integer> name_grade;
    private static Connection con = null;

    public static void main(String[] args) throws IOException {
        final String db_path2 = File.createTempFile("StudentDB", ".sqlite").getAbsolutePath();
        System.out.println(db_path2);
        openDB(db_path2);
        create_table();
        if (!get_name_grade(csv_path)) {
            System.out.println("Error on reading file");
            return;
        }
        recieve();

    }

    private static void openDB(String dbPath) {
        try {
            // CLASSPATH must be properly set, for instance on
            // a Linux system or a Mac:
            // $ export CLASSPATH=.:sqlite-jdbc-version-number.jar
            // Alternatively, run the program with
            // $ java -cp .:sqlite-jdbc-version-number.jar BasicJDBC
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            System.err.println("Cannot find the driver.");
            System.exit(1);
        }
        try {
            con = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            con.setAutoCommit(false);
            System.err.println("Successfully connected to the database.");
        } catch (Exception e) {
            System.err.println("openDB" + e.getMessage());
            System.exit(1);
        }
    }

    private static void closeDB() {
        if (con != null) {
            try {
                con.close();
                con = null;
            } catch (Exception e) {
                // Forget about it
            }
        }
    }

    private static void create_table() {
        final String sqlDelete = "DROP TABLE IF EXISTS StudentsGrade";
        final String sql = "CREATE TABLE IF NOT EXISTS StudentsGrade " +
                "( NAME TEXT NOT NULL, " +
                " GRADE INT NOT NULL)";
        try (
                PreparedStatement statementDelete = con.prepareStatement(sqlDelete);
                PreparedStatement statement = con.prepareStatement(sql);
        ) {
            statementDelete.executeUpdate();
            statement.setQueryTimeout(30);
            statement.executeUpdate();
            con.commit();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public static boolean get_name_grade(String path) {
        name_grade = new HashMap<String, Integer>();
        final String SQL = "INSERT INTO StudentsGrade(NAME,GRADE) VALUES(?, ?)";
        try (final var lines = Files.lines(Path.of(path))) {
            PreparedStatement statement = con.prepareStatement(SQL);
            lines.skip(1).forEach(
                    line -> {
                        if (null == name_grade.get(line.split(",")[0])) {
                            try {
                                statement.setString(1, line.split(",")[0]);
                                statement.setInt(2, Integer.parseInt(line.split(",")[1]));
                                statement.addBatch();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );
            statement.executeBatch();
            con.commit();
        } catch (FileNotFoundException ffe) {
            System.out.println(path + " not found");
            return false;
        } catch (IOException ioe) {
            System.out.println("IO exception");
            return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static void recieve() {
        try {
            ServerSocket ss1 = new ServerSocket(server_port);
            System.out.println("Server begin");
            while (true) {
                try (final Socket client_socket = ss1.accept();
                     final PrintWriter out = new PrintWriter(client_socket.getOutputStream(), true);
                     final BufferedReader in = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
                ) {
                    final String line = in.readLine();
                    System.out.println("Received " + line);
                    final String[] recieve_command = line.split(" ");
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
                        default -> back_sentence = "Command not found";
                    }
                    System.out.println("Command processed");
                    out.println(back_sentence);
                }
            }
        } catch (IOException ioe) {
            System.out.println("Unknown io exception");
        } finally {
            closeDB();
        }
    }

    public static int get_name_to_grade(String name) {
        final String sql1 = "select COUNT(*) from StudentsGrade WHERE NAME = " + "\'" + name + "\'";
        final String sql2 = "select GRADE from StudentsGrade WHERE NAME = " + "\'" + name + "\'";
        int will_return = 0;
        try (PreparedStatement statement = con.prepareStatement(sql1);
             ResultSet rs1 = statement.executeQuery();) {
            if (rs1.getInt(1) > 0) {
                try (PreparedStatement statement2 = con.prepareStatement(sql2);
                     ResultSet rs2 = statement2.executeQuery();) {
                    will_return = rs2.getInt(1);
                }
                con.commit();
                return will_return;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public static String get_grade_range(int begin, int end) {
        final StringBuilder will_return = new StringBuilder();
        if (begin > end) {
            int temp = begin;
            begin = end;
            end = temp;
            will_return.append("Please ensure the number order \n");
        }
        final String SQL = "select * from StudentsGrade WHERE GRADE >= ? AND GRADE < ?";
        try (PreparedStatement pstmt = con.prepareStatement(SQL);) {
            pstmt.setInt(1, begin);
            pstmt.setInt(2, end);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                will_return.append(rs.getString("NAME") + "," + rs.getInt("GRADE") + "\n");
            }
            rs.close();
            con.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return will_return.toString();
    }

    public static String get_rank_range(int range) {
        final StringBuilder will_return = new StringBuilder();
        final String SQL = "select * from " +
                "(select *,DENSE_RANK () OVER (ORDER BY GRADE DESC) AS orders" +
                "                    from StudentsGrade)  where orders <= ?";
        try (PreparedStatement pstmt = con.prepareStatement(SQL);) {
            pstmt.setInt(1, range);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                will_return.append(rs.getString("NAME") + "," + rs.getInt("GRADE") + "\n");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return will_return.toString();
    }
}
