import lombok.AllArgsConstructor;

import java.io.*;
import java.util.*;
/*
Accept Successfully
* */

public class Main {
    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
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
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        solve(in, out);
        out.close();
    }

    static void solve(InputReader in, PrintWriter out) {
        int n = in.nextInt();
        int[] begin = new int[n + 1];
        int[] end = new int[n + 1];
        pair[] friends = new pair[n + 1];
        int beginTime = Integer.MAX_VALUE / 4;
        int endTime = 0;
        int minTime = Integer.MAX_VALUE / 4;
        for (int i = 1; i <= n; i++) {
            begin[i] = in.nextInt();
            beginTime = Math.min(begin[i], beginTime);
            end[i] = in.nextInt();
            endTime = Math.max(end[i], endTime);
            minTime = Math.min(minTime, end[i] - begin[i]);
            friends[i] = new pair(begin[i], end[i]);
        }
        minTime = Math.min(minTime, (endTime - beginTime + 1) / n);
        int maxValue = minTime + 1;
        int minValue = 0;
        int middleValue;
        while (minValue < maxValue) {
            middleValue = minValue + (maxValue - minValue) / 2;
            boolean peopleNumebr = getNumebr(friends, middleValue, beginTime, endTime);
            if (peopleNumebr) {
                minValue = middleValue + 1;
            } else {
                maxValue = middleValue;
            }
        }
        out.println(minValue - 1);

    }

    public static boolean getNumebr(pair[] friends, int time, int beginTime, int endTime) {
        if (time == 0) {
            return true;
        }
        boolean[] used = new boolean[endTime - beginTime + 1];
        //@Override
        final Comparator<pair> comparatorOfFriends = Comparator.comparingInt(o -> o.end);
        final Queue<pair> friendsQueue = new PriorityQueue<pair>(comparatorOfFriends);
        friendsQueue.addAll(Arrays.asList(friends).subList(1, friends.length));
        while (!friendsQueue.isEmpty()) {
            pair tempPair = friendsQueue.poll();
            final int tempBegin = tempPair.begin;
            final int tempEnd = tempPair.end;
            int tempCount = 0;
            for (int j = tempBegin - beginTime; j <= tempEnd - beginTime; j++) {
                if (tempCount == time) {
                    break;
                }
                if (!used[j]) {
                    used[j] = true;
                    tempCount++;
                }
            }
            if (tempCount != time) {
                return false;
            }
        }
        return true;
    }

    @AllArgsConstructor
    private static final class pair {
        final int begin, end;
    }
}