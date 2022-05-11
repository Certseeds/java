import quick_read.input_reader;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        input_reader in = new input_reader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        int u = 121;
        solve(in, out);
        out.close();
    }

    static void solve(input_reader in, PrintWriter out) {
        int t = in.nextInt();
        for (int i = 1; i <= t; i++) {
            int n = in.nextInt();
            int x = in.nextInt();
            int y = in.nextInt();
            out.println(getBuses(n, x, y));
        }
    }

    public static long getBuses(int n, int x, int y) {
        long count = 0;
        if (x == 0 && y == 0) {
            return n;
        }
        if (2 * n > y) {
            if (y % 2 == 1) {
                count = count + y / 2;
                // 先剩一个小货物
                n = n - y / 2;
                y = 1;
                if (n > x) {
                    count = count + x;
                    n = n - x;
                    //不需要加1,因为n还大于0
                    return count + n;
                } else if (n == x) {
                    count = count + x;
                    x = 0;
                    return count + 1;
                } else if (n < x) {
                    if (n % 2 == 0) {
                        if (x % 2 == 0) {
                            count = count + n;
                            x = x - n;
                            return count + 1 + x / 2;
                        } else {
                            count = count + n;
                            x = x - n + 1;
                            return count + x / 2;
                        }
                    }
                    if (n % 2 == 1) {
                        if (x % 2 == 0) {
                            count = count + n;
                            x = x - n + 1;
                            return count + x / 2;
                        } else {
                            count = count + n;
                            x = x - n;
                            n = 0;
                            return count + 1 + x / 2 + (x % 2 == 0 ? 0 : 1);
                        }
                    }
                }
            } else {
                count = count + y / 2;
                n = n - y / 2;
                if (n >= x) {
                    return count + n;
                } else if (n < x) {
                    count = count + n;
                    x = x - n;
                    n = 0;
                    return count + x / 2 + (x % 2 == 0 ? 0 : 1);
                }
            }
        } else if (2 * n == y) {
            count = count + n;
            n = 0;
            y = 0;
            return count + x / 2 + (x % 2 == 0 ? 0 : 1);
        } else if (2 * n < y) {
            if ((y - 2 * n) % 3 == 2 && x % 2 == 1) {
                count = count + n + 2;
                x = x - 1;
                y = y - 2 * n;
                return count + x / 2 + y / 3;
            } else if ((y - 2 * n) % 3 == 1 && x % 2 == 1) {
                count = count + n;
                x = x - 1;
                y = y - 2 * n + 2;
                return count + x / 2 + y / 3;
            }
            count = count + n;
            y = y - n * 2;
            return count + x / 2 + (x % 2 == 0 ? 0 : 1) + y / 3 + (y % 3 == 0 ? 0 : 1);
        }
        return 0;
    }
}
