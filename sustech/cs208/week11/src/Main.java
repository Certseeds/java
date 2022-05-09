import lombok.AllArgsConstructor;

import java.io.*;
import java.util.*;

public class Main{
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
        int u = 121;
        solve(in, out);
        out.close();
    }

    static void solve(InputReader in, PrintWriter out) {
        int n = in.nextInt();
        int p = in.nextInt();
        int[] array = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            array[i] = (in.nextInt() % p);
        }
        int rootNumber = getRoot(p);
        int[][] rootArray = RootArray(rootNumber, p);

        int[] rootArray1 = rootArray[0];
        int[] rootArray2 = rootArray[1];
        int[] numberArray = new int[p];
        for (int i = 0; i < p; i++) {
            numberArray[i] = 0;
        }
        for (int i = 1; i <= n; i++) {
            if (array[i] % p != 0) {
                numberArray[rootArray1[array[i]]]++;
                //此处如果
            }
        }

        complex[] com1 = new complex[3 * p];
        complex[] com2 = new complex[3 * p];
        for (int i = 0; i < 3 * p; i++) {
            com1[i] = new complex(0, 0);
            com2[i] = new complex(0, 0);
        }
        for (int i = 0; i < p; i++) {
            com1[i] = new complex(numberArray[i], 0);
            com2[i] = new complex(numberArray[i], 0);
        }
        int length = 1;
        int temp = 0;
        while (length <= 2 * p - 2) {
            length = length * 2;
            temp++;
        }
        int[] reverse = new int[length + 1];
        for (int i = 0; i < length; i++) {
            reverse[i] = (reverse[i/2]/2) + (int) ((i % 2) * Math.pow(2,temp -1));
        }

        FFT(com1, 1, reverse, length);
        FFT(com2, 1, reverse, length);
        for (int i = 0; i < 3 * p; i++) {
            com1[i] = com1[i].multi(com2[i]);
        }
        FFT(com1, -1, reverse, length);
        int[] result = new int[2 * p - 1];
        for (int i = 0; i <= 2 * p - 2; i++) {
            result[i] = (int) (com1[i].real / length + 0.5);
        }
        int[] Willoutput = new int[p];
        for (int i = 0; i <2*p-1; i++) {
            Willoutput[rootArray2[i % (p - 1)]] += result[i];
        }
        Willoutput[1] = Willoutput[0];
        Willoutput[0] = n * n;
        for (int i = 1; i < p; i++) {
            Willoutput[0] -= Willoutput[i];
        }
        for (int i = 0; i < p; i++) {
            out.println(Willoutput[i]);
        }
    }

    public static void FFT(complex[] array, int number, int[] reverse, int length) {
        for (int i = 0; i < length; i++) {
            if (i < reverse[i]) {
                complex temp2 = array[i];
                array[i] = array[reverse[i]];
                array[reverse[i]] = temp2;
            }
        }
        complex temp3 = new complex(0,0);
        for (int i = 1; i < length; i *= 2) {
            double temp4 = Math.PI / i;
            temp3 = new complex(Math.cos(temp4), number * Math.sin(temp4));
            for (int j = 0; j < length; j = j + 2 * i) {
                complex temp5 = new complex(1, 0);
                for (int k = 0; k < i; k++) {
                    complex temp6 = array[j + k];
                    complex temp7 = temp5.multi(array[j + k + i]);
                    array[j + k] = temp6.add(temp7);
                    array[j + k + i] = temp6.minus(temp7);
                    temp5 = temp5.multi(temp3);
                }
            }
        }

    }

    @AllArgsConstructor
    public static class complex {
        final double real,image;

        public complex add(complex c1) {
            return new complex(this.real + c1.real, this.image + c1.image);
        }

        public complex minus(complex c1) {
            return new complex(this.real - c1.real, this.image - c1.image);
        }

        public complex multi(complex c1) {
            return new complex(this.real * c1.real - this.image * c1.image, this.real * c1.image + this.image * c1.real);
        }
    }

    public static int getRoot(int p) {
        int[] primes = getPrime(p - 1);
        int lengthOfPrime = primes.length;
        // System.out.println(Arrays.toString(primes));
        int minRootNumber = 1;
        for (int i = 2; i <= p - 1; i++) {
            boolean isRootNumber = true;
            for (int j = 1; j <= lengthOfPrime - 1; j++) {
                if (judgeMod1(i, (p-1)/primes[j], p) == 1) {
                    isRootNumber = false;
                    break;
                }
            }
            if (isRootNumber) {
                minRootNumber = i;
                break;
            }
        }
        return minRootNumber;
    }

    public static int[][] RootArray(int root, int p) {
        int[][] willreturn = new int[2][p];
        int n = root;
        for (int i = 1; i <= p - 1; i++) {
            willreturn[0][n % p] = i;
            willreturn[1][i] = n % p;
            n = ((n % p) * root) % p;
        }
        return willreturn;
    }

    public static long judgeMod1(int a, int mi, int p) {
        if (mi == 1) {
            return a % p;
        } else if (mi == 2) {
            return ((a % p) * (a % p)) % p;
        } else {
            int middle = (mi - 1) / 2 + 1;
            if (middle == mi - middle) {
                long leftOne = judgeMod1(a, middle, p);
                return (leftOne * leftOne) % p;
            } else if (middle >= mi - middle) {
                long rightOne = judgeMod1(a, mi - middle, p);
                long leftOne = (rightOne * judgeMod1(a, middle * 2 - mi, p)) % p;
                return (leftOne * rightOne) % p;
            } else {
                long leftOne = judgeMod1(a, middle, p);
                long rightOne = (leftOne * judgeMod1(a, mi - 2 * middle, p)) % p;
                return (leftOne * rightOne) % p;
            }
        }
    }

    public static int[] getPrime(int x) {
        ArrayList<Integer> willreturn = new ArrayList<>();
        while (x > 1) {
            for (int i = 2; i <= x; i++) {
                if (x % i == 0) {
                    willreturn.add(i);
                    x = x / i;
                    break;
                }
            }
        }
        int[] primes = new int[willreturn.size() + 1];
        for (int i = 1; i < primes.length; i++) {
            primes[i] = willreturn.get(i - 1);
        }
        return primes;
    }
}