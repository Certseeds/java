import lombok.AllArgsConstructor;

import java.util.Comparator;
        import java.util.PriorityQueue;
        import java.util.Queue;
        import java.util.StringTokenizer;
        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
import java.io.OutputStream;
        import java.io.PrintWriter;

public class Main {
    
    private static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;
        public InputReader(InputStream stream) {
            tokenizer = null;
        }
        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }
        public int nextInt() {
            return Integer.parseInt(next());
        }
    }
    
    public static void main(String[] args) {
        final InputStream inputStream = System.in;
        final OutputStream outputStream = System.out;
        final InputReader in = new InputReader(inputStream);
        final PrintWriter out = new PrintWriter(outputStream);
        solve(in, out);
        out.close();
    }
    
    static void solve(InputReader in, PrintWriter out) {
        int n = in.nextInt();
        //@Override
        final Comparator<edge> comparatorOfEdges = (o1, o2) -> {
            int o1length = o1.length;
            int o2length = o2.length;
            return Integer.compare(o1length, o2length);
        };
        
        boolean[] isVisited = new boolean[n + 1];
        for (int i = 0; i <= n; i++) {
            isVisited[i] = false;
        }
        int[][] matrix = new int[n + 1][n + 1];
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j <= n; j++) {
                matrix[i][j] = in.nextInt();
                matrix[j][i] = matrix[i][j];
            }
        }
        long  lengthOfMinTree = 0;
        int beginKey = 0;
        isVisited[0] = true;
        edge NetTemp = new edge(1,matrix[0][1]);
        Queue<edge> edges = new PriorityQueue<>(comparatorOfEdges);
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                if (!isVisited[j] && j != beginKey){
                    edges.add(new edge(j,matrix[beginKey][j]));
                }
            }
            while (!edges.isEmpty() && isVisited[edges.peek().end]){
                edges.poll();
            }
            if (!edges.isEmpty()){
                NetTemp = edges.poll();
            }
            beginKey = NetTemp.end;
            isVisited[beginKey] = true;
            lengthOfMinTree += NetTemp.length;
        }
        out.println(lengthOfMinTree);
    }

    @AllArgsConstructor
    private static class edge {
        int end,length;
    }
}
