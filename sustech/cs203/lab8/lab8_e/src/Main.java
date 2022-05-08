import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;


public class Main {
    static PrintWriter out;
    static InputReader in;
    static int count = 1;

    public static void main(String[] args) {
        out = new PrintWriter(System.out);
        in = new InputReader(System.in);
        for (int t = in.nextInt(); t > 0; t--) {
            int row = in.nextInt();
            int column = in.nextInt();
            int[][] matrix = new int[row][column];
            boolean[][] isVisited = new boolean[row][column];
            boolean[][] hadAdden = new boolean[row][column];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    matrix[i][j] = in.nextInt();
                    isVisited[i][j] = false;
                    hadAdden[i][j] = false;
                }
            }
            int countNumbers = 0;
            int countOfBolcks = 0;
            int colorNow = matrix[0][0];
            isVisited[0][0] = true;
            hadAdden[0][0] = true;
            Queue<Integer> queuex = new LinkedList<>();
            Queue<Integer> queuey = new LinkedList<>();
            Queue<Integer> queueAnotherx = new LinkedList<>();
            Queue<Integer> queueAnothery = new LinkedList<>();

            queuex.add(0);
            queuey.add(0);
            while ((queuex.size() != 0 && queuey.size() != 0)) {
                // (queueAnotherx.size() != 0 && queueAnothery.size() != 0)?
                int tempx = queuex.poll();
                int tempy = queuey.poll();
//                int up = (tempx-1) == -1? row-1:tempx -1;
//                int down = (tempx +1) == row? 0:tempx + 1;
//                int left = (tempy -1);
//                boolean hasLeftOne = (left !=-1);
//                int right = (tempy+1);
//                boolean hasRightOne = (right != column-1);
                //还以为是上下通
                int up = (tempx - 1);
                boolean hasUpOne = (up != -1);
                int down = (tempx + 1);
                boolean hasDownOne = (down != row);
                int left = (tempy - 1 == -1 ? column - 1 : tempy - 1);
                int right = (tempy + 1 == column ? 0 : tempy + 1);
                boolean hasRightOne = (right != column - 1);
                //999+1 % 1000 = 0 && (0-1) % 999,说明上下联通
                if (hasUpOne) {
                    if (matrix[up][tempy] == colorNow && !isVisited[up][tempy]) {
                        isVisited[up][tempy] = true;
                        hadAdden[up][tempy] = true;
                        queuex.add(up);
                        queuey.add(tempy);
                        countNumbers++;
                    } else if (!isVisited[up][tempy] && !hadAdden[up][tempy]) {
                        queueAnotherx.add(up);
                        queueAnothery.add(tempy);
                        hadAdden[up][tempy] = true;
                    }
                }
                if (hasDownOne) {
                    if (matrix[down][tempy] == colorNow && !isVisited[down][tempy]) {
                        isVisited[down][tempy] = true;
                        hadAdden[down][tempy] = true;
                        queuex.add(down);
                        queuey.add(tempy);
                        countNumbers++;
                    } else if (!isVisited[down][tempy] && !hadAdden[down][tempy]) {
                        hadAdden[down][tempy] = true;
                        queueAnotherx.add(down);
                        queueAnothery.add(tempy);
                    }
                }
                if (matrix[tempx][left] == colorNow && !isVisited[tempx][left]) {
                    isVisited[tempx][left] = true;
                    hadAdden[tempx][left] = true;
                    queuex.add(tempx);
                    queuey.add(left);
                    countNumbers++;
                } else if (!isVisited[tempx][left] && !hadAdden[tempx][left]) {
                    hadAdden[tempx][left] = true;
                    queueAnotherx.add(tempx);
                    queueAnothery.add(left);
                }
                if (matrix[tempx][right] == colorNow && !isVisited[tempx][right]) {
                    isVisited[tempx][right] = true;
                    hadAdden[tempx][right] = true;
                    queuex.add(tempx);
                    queuey.add(right);
                    countNumbers++;
                } else if (!isVisited[tempx][right] && !hadAdden[tempx][right]) {
                    hadAdden[tempx][right] = true;
                    queueAnotherx.add(tempx);
                    queueAnothery.add(right);
                }


            }
            countOfBolcks = 1;
            while ((queueAnotherx.size() != 0 && queueAnothery.size() != 0) && countNumbers != row * column) {

                int tempx2 = queueAnotherx.poll();
                int tempy2 = queueAnothery.poll();
                if (isVisited[tempx2][tempy2]) {
                    // out.print("jump");
                } else {
                    isVisited[tempx2][tempy2] = true;
                    hadAdden[tempx2][tempy2] = true;
                    queuex.add(tempx2);
                    queuey.add(tempy2);
                    colorNow = matrix[tempx2][tempy2];
                    while ((queuex.size() != 0 && queuey.size() != 0)) {
                        // (queueAnotherx.size() != 0 && queueAnothery.size() != 0)?
                        int tempx = queuex.poll();
                        int tempy = queuey.poll();
                        //  out.println(tempx + " " + tempy);
                        int up = (tempx - 1);
                        boolean hasUpOne = (up != -1);
                        int down = (tempx + 1);
                        boolean hasDownOne = (down != row);
                        int left = (tempy - 1 == -1 ? column - 1 : tempy - 1);
                        int right = (tempy + 1 == column ? 0 : tempy + 1);
                        boolean hasRightOne = (right != column - 1);
                        //999+1 % 1000 = 0 && (0-1) % 999,说明上下联通
                        if (hasUpOne) {
                            if (matrix[up][tempy] == colorNow && !isVisited[up][tempy]) {
                                isVisited[up][tempy] = true;
                                hadAdden[up][tempy] = true;
                                queuex.add(up);
                                queuey.add(tempy);
                                countNumbers++;
                            } else if (!isVisited[up][tempy] && !hadAdden[up][tempy]) {
                                queueAnotherx.add(up);
                                queueAnothery.add(tempy);
                                hadAdden[up][tempy] = true;
                            }
                        }
                        if (hasDownOne) {
                            if (matrix[down][tempy] == colorNow && !isVisited[down][tempy]) {
                                isVisited[down][tempy] = true;
                                hadAdden[down][tempy] = true;
                                queuex.add(down);
                                queuey.add(tempy);
                                countNumbers++;
                            } else if (!isVisited[down][tempy] && !hadAdden[down][tempy]) {
                                hadAdden[down][tempy] = true;
                                queueAnotherx.add(down);
                                queueAnothery.add(tempy);
                            }
                        }
                        if (matrix[tempx][left] == colorNow && !isVisited[tempx][left]) {
                            isVisited[tempx][left] = true;
                            hadAdden[tempx][left] = true;
                            queuex.add(tempx);
                            queuey.add(left);
                            countNumbers++;
                        } else if (!isVisited[tempx][left] && !hadAdden[tempx][left]) {
                            hadAdden[tempx][left] = true;
                            queueAnotherx.add(tempx);
                            queueAnothery.add(left);
                        }
                        if (matrix[tempx][right] == colorNow && !isVisited[tempx][right]) {
                            isVisited[tempx][right] = true;
                            hadAdden[tempx][right] = true;
                            queuex.add(tempx);
                            queuey.add(right);
                            countNumbers++;
                        } else if (!isVisited[tempx][right] && !hadAdden[tempx][right]) {
                            hadAdden[tempx][right] = true;
                            queueAnotherx.add(tempx);
                            queueAnothery.add(right);
                        }

//                        out.println(Arrays.toString(isVisited[0]));
//                        out.println(Arrays.toString(isVisited[1]));
//                        out.println(Arrays.toString(isVisited[2]));
//                        out.println(Arrays.toString(hadAdden[0]));
//                        out.println(Arrays.toString(hadAdden[1]));
//                        out.println(Arrays.toString(hadAdden[2]));
//                        out.println(queuex.toString());
//                        out.println(queuey.toString());
//                        out.println(queueAnotherx.toString());
//                        out.println(queueAnothery.toString());
                    }
                    countOfBolcks++;
                }
            }
            out.print(countOfBolcks);
            if (t != 1) {
                out.println();
            }
        }
        out.close();
    }


    private static final class InputReader {
        public BufferedReader br;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            br = new BufferedReader(new InputStreamReader(stream), 327680);
            tokenizer = null;
        }

        public int nextInt() {
            try {
                int c = br.read();
                while (c <= 32) {
                    c = br.read();
                }
                boolean negative = false;
                if (c == '-') {
                    negative = true;
                    c = br.read();
                }
                int x = 0;
                while (c > 32) {
                    x = x * 10 + c - '0';
                    c = br.read();
                }
                return negative ? -x : x;
            } catch (IOException e) {
                return -1;
            }
        }


    }
}
