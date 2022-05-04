// SPDX-License-Identifier: AGPL-3.0-or-later


import javafx.util.Pair;
import org.junit.jupiter.api.*;
import org.mockito.stubbing.Answer;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServerStudentMockTest {
    private static final int numbers = 645;
    Random ran1 = new Random(System.currentTimeMillis());
    private ServerStudent ss;
    private ClientMock client;
    private StringBuilder grade_60_70 = new StringBuilder();
    private StringBuilder name_Ahmad_Fawaz = new StringBuilder();
    private StringBuilder top_3 = new StringBuilder();

    @BeforeEach
    public void prepare() {
        ss = new ServerStudent();
        assertEquals(0, ss.gradeMap.size());
        assertEquals(0, ss.orderedList.size());
        try {
            ss.readFile();
        } catch (Exception e) {
            fail("exception in test read file");
        }
        HashSet<Integer> scores_set = new HashSet<>();
        for (Map.Entry<String, Integer> entry : ss.gradeMap.entrySet()) {
            int grade = entry.getValue();
            if (60 <= grade && grade <= 70) {
                grade_60_70.append(entry.getKey() + "," + grade + "\n");
            }
            if (entry.getKey().equals("Ahmad Fawaz")) {
                name_Ahmad_Fawaz.append(entry.getValue() + "\n");
            }
            scores_set.add(grade);
        }
        ArrayList<Integer> scores_list = new ArrayList<>(scores_set);
        scores_list.sort(Collections.reverseOrder());
        for (Map.Entry<String, Integer> entry : ss.gradeMap.entrySet()) {
            int grade = entry.getValue();
            for (int i = 0; i < 3; i++) {
                if (grade == scores_list.get(i)) {
                    top_3.append(entry.getKey()).append(",").append(entry.getValue()).append("\n");
                    break;
                }
            }
        }
    }

    @Test
    @Order(1)
    public void testReadFile() {
        assertTrue(ss.gradeMap.size() > 0);
        assertTrue(ss.orderedList.size() > 0);
        assertEquals(ss.gradeMap.size(), numbers);
        assertEquals(ss.orderedList.size(), numbers);
        for (Pair<String, Integer> p : ss.orderedList) {
            assertEquals(p.getValue(), ss.gradeMap.get(p.getKey()));
        }
        Integer last = -1;
        for (Pair<String, Integer> p : ss.orderedList) {
            assertTrue(last <= p.getValue());
            last = p.getValue();
        }
    }

    @Test
    @Order(2)
    public void testHandleNameCommand() {
        assertEquals(
                ss.handleNameCommand("Aguiar Armani"),
                "87");
        assertEquals(
                ss.handleNameCommand("Li Suo"),
                "Student does not exist");
        File file = new File(ServerStudent.FILE_NAME);
        try (BufferedReader reader =
                     new BufferedReader(
                             new InputStreamReader(
                                     new FileInputStream(file),
                                     StandardCharsets.UTF_8));) {
            reader.readLine();
            String s = reader.readLine();
            while ((s = reader.readLine()) != null) {
                assertEquals(
                        s.split(",")[1],
                        ss.handleNameCommand(s.split(",")[0])
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 100; ++i) {
            assertEquals(
                    ss.handleNameCommand(UUID.randomUUID().toString()),
                    "Student does not exist");
        }
    }

    @Test
    @Order(3)
    public void testHandleGradeCommand() {
        for (int i = 0; i < numbers; ++i) {
            int less = ran1.nextInt(101);
            int greater = ran1.nextInt(101);
            String will_send = less + " " + greater;
            String result = ss.handleGradeCommand(will_send);
            if (less > greater) {
                assertEquals(
                        ServerStudent.INVALID,
                        result
                );
            } else {
                assertNotEquals(
                        ServerStudent.INVALID,
                        result
                );
            }
        }
        for (int i = 0; i < numbers; ++i) {
            int less = (ran1.nextDouble() > 0.5 ? 1 : -1) * ran1.nextInt(101);
            int greater = (ran1.nextDouble() > 0.3 ? 1 : -1) * ran1.nextInt(1024);
            String will_send = less + " " + greater;
            String result = ss.handleGradeCommand(will_send);
            if (less > greater || less < 0) {
                assertEquals(
                        ServerStudent.INVALID,
                        result
                );
            } else {
                assertNotEquals(
                        ServerStudent.INVALID,
                        result
                );
            }
        }
        for (int i = 0; i < numbers; ++i) {
            assertEquals(
                    ServerStudent.INVALID,
                    ss.handleGradeCommand(UUID.randomUUID().toString())
            );
            assertEquals(
                    ServerStudent.INVALID,
                    ss.handleGradeCommand(UUID.randomUUID().toString() + " " + UUID.randomUUID().toString())
            );
        }
    }

    @Test
    @Order(4)
    public void testHandleTopCommand() {
        assertEquals(ServerStudent.INVALID, ss.handleTopCommand("0"));
        for (int i = 1; i < 200; i++) {
            assertNotEquals(ServerStudent.INVALID, ss.handleTopCommand(String.valueOf(i)));
        }
        for (int i = 1; i < numbers; i++) {
            assertEquals(
                    ServerStudent.INVALID,
                    ss.handleTopCommand(UUID.randomUUID().toString()));
        }
    }

    @Test
    @Order(5)
    public void testHandleCommand() {
        // NAME begin
        StringBuilder sb = new StringBuilder();
        sb.append("NAME");
        for (int i = 0; i < numbers; i++) {
            assertEquals(
                    ServerStudent.INVALID,
                    ss.handleCommand(sb.toString()));
            sb.append(" ");
        }
        sb.delete(0, sb.length());
        File file = new File(ServerStudent.FILE_NAME);
        try (BufferedReader reader =
                     new BufferedReader(
                             new InputStreamReader(
                                     new FileInputStream(file),
                                     StandardCharsets.UTF_8));) {
            reader.readLine();
            String s = reader.readLine();
            while ((s = reader.readLine()) != null) {
                assertEquals(
                        s.split(",")[1],
                        ss.handleCommand("NAME " + s.split(",")[0])
                );
                assertEquals(
                        "Student does not exist",
                        ss.handleCommand("NAME   " + s.split(",")[0])
                );
                assertEquals(
                        "Student does not exist",
                        ss.handleCommand("NAME " + s.split(",")[0] + UUID.randomUUID().toString())
                );
                assertEquals(
                        "Student does not exist",
                        ss.handleCommand("NAME   " + s.split(",")[0] + UUID.randomUUID().toString())
                );
                assertEquals(
                        "Student does not exist",
                        ss.handleCommand("NAME   " + s.split(",")[0] + UUID.randomUUID().toString())
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // NAME end GRADE begin
        for (int i = 0; i < numbers; ++i) {
            int less = ran1.nextInt(101);
            int greater = ran1.nextInt(101);
            String will_send = "GRADE " + less + " " + greater;
            String result = ss.handleCommand(will_send);
            if (less > greater) {
                assertEquals(
                        ServerStudent.INVALID,
                        result
                );
            } else {
                assertNotEquals(
                        ServerStudent.INVALID,
                        result
                );
            }
        }
        assertEquals(
                ServerStudent.INVALID,
                ss.handleCommand("GRADE")
        );
        assertEquals(
                ServerStudent.INVALID,
                ss.handleCommand("GRADE ")
        );
        for (int i = 0; i < numbers; ++i) {
            int less = (ran1.nextDouble() > 0.5 ? 1 : -1) * ran1.nextInt(101);
            int greater = (ran1.nextDouble() > 0.3 ? 1 : -1) * ran1.nextInt(1024);
            String will_send = "GRADE " + less + " " + greater;
            String result = ss.handleCommand(will_send);
            if (less > greater || less < 0) {
                assertEquals(
                        ServerStudent.INVALID,
                        result
                );
            } else {
                assertNotEquals(
                        ServerStudent.INVALID,
                        result
                );
            }
            assertEquals(ServerStudent.INVALID,
                    ss.handleCommand("GRADE " + UUID.randomUUID().toString()));
        }
        for (int i = 0; i < numbers; ++i) {
            assertEquals(ServerStudent.INVALID,
                    ss.handleCommand("GRADE " + sb.toString()));
            sb.append(UUID.randomUUID().toString());
        }
        sb.delete(0, sb.length());
        assertEquals(
                ServerStudent.INVALID,
                ss.handleCommand("TOP"));
        assertEquals(
                ServerStudent.INVALID,
                ss.handleCommand("TOP "));
        assertEquals(
                ServerStudent.INVALID,
                ss.handleCommand("TOP 0"));
        for (int i = 1; i < 200; i++) {
            assertNotEquals(
                    ServerStudent.INVALID,
                    ss.handleCommand("TOP " + i));
            assertEquals(
                    ServerStudent.INVALID,
                    ss.handleCommand("TOP  " + i));
        }
        for (int i = 1; i < numbers; i++) {
            sb.append(UUID.randomUUID().toString());
            assertEquals(
                    ServerStudent.INVALID,
                    ss.handleTopCommand("TOP " + sb.toString()));
            sb.append(" ");
        }
        sb.delete(0, sb.length());
        sb.append(UUID.randomUUID().toString());
        for (int i = 1; i < numbers; i++) {
            assertEquals(
                    ServerStudent.INVALID,
                    ss.handleTopCommand(sb.toString()));
            sb.append(" ").append(UUID.randomUUID().toString());
        }
    }

    @Test
    @Order(6)
    public void test_end_to_end() {
        Thread thread = new Thread(() -> {
            try {
                ServerStudent.main(new String[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        String[] commands = {"TOP 3", "GRADE 60 70", "NAME Ahmad Fawaz"};
        for (String command : commands) {
            try (Socket socket = new Socket("localhost", 6324);
                 PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader reader = new BufferedReader(
                         new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            ) {
                writer.println(command);
                StringBuilder res = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    res.append(line);
                }
                assertNotEquals(
                        ServerStudent.INVALID,
                        res.toString()
                );
                assertTrue(res.length() > 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        client = mock(ClientMock.class);
        when(client.sendGradeCommand(anyString())).thenAnswer((Answer<String>) invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            String a = (String) args[0];
            return send_recieve("GRADE " + a);
        });
        when(client.sendNameCommand(anyString())).thenAnswer((Answer<String>) invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            String a = (String) args[0];
            return send_recieve("NAME " + a);
        });
        when(client.sendTopCommand(anyString())).thenAnswer((Answer<String>) invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            String a = (String) args[0];
            return send_recieve("TOP " + a);
        });
        when(client.sendUndefinedCommand(anyString())).thenAnswer((Answer<String>) invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            String a = (String) args[0];
            return send_recieve(UUID.randomUUID() + " " + a);
        });
        assertEquals(client.sendGradeCommand("60 70").trim(), ss.handleCommand("GRADE 60 70").trim());
        assertEquals(client.sendGradeCommand("60 70").trim(), ss.handleGradeCommand("60 70").trim());
        assertEquals(grade_60_70.toString().trim(), ss.handleGradeCommand("60 70").trim());
        assertEquals(client.sendNameCommand("Ahmad Fawaz").trim(), ss.handleCommand("NAME Ahmad Fawaz").trim());
        assertEquals(client.sendNameCommand("Ahmad Fawaz").trim(), ss.handleNameCommand("Ahmad Fawaz").trim());
        assertEquals(name_Ahmad_Fawaz.toString().trim(), ss.handleNameCommand("Ahmad Fawaz").trim());
        assertEquals(client.sendTopCommand("3").trim(), ss.handleCommand("TOP 3").trim());
        assertEquals(client.sendTopCommand("3").trim(), ss.handleTopCommand("3").trim());
        assertEquals(top_3.toString().trim().length(), ss.handleTopCommand("3").trim().length());
    }

    public String send_recieve(String input) throws IOException {
        StringBuilder res = new StringBuilder();
        try (Socket socket = new Socket("localhost", 6324);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        ) {
            writer.println(input);
            String line;
            while ((line = reader.readLine()) != null) {
                res.append(line).append("\n");
            }
        }
        return res.toString();
    }
}
