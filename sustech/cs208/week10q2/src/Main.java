import quick_read.input_reader;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

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
        int d = in.nextInt();
        int n = in.nextInt();
        if (1 == n) {
            int[] points = new int[d];
            for (int i = 0; i < d; i++) {
                points[i] = in.nextInt();
            }
            int begin = 0;
            int end = 0;
            long min = Long.MAX_VALUE;
            Arrays.sort(points);
            for (int i = 0; i < d - 1; i++) {
                final long next_min = countDistanceOne(points[i], points[i + 1]);
                if (min > next_min) {
                    min = next_min;
                    begin = points[i];
                    end = points[i + 1];
                }
            }
            if (begin > end) {
                int temp = begin;
                begin = end;
                end = temp;
            }
            out.println(begin);
            out.println(end);
        } else if (2 == n) {
            point[] points = new point[d];
            for (int i = 1; i <= d; i++) {
                points[i - 1] = new point(in.nextLong(), in.nextLong());
            }
            Arrays.sort(points, new comparator_x_two());//x坐标,小到大排列
            long[] prints = DoubleDemison(points, 0, d - 1);
            //out.println(prints[0] + " " + prints[1] + " " + prints[2]);
            if (points[(int) prints[1]].x < points[(int) prints[2]].x) {
                out.println(points[(int) prints[1]].x + " " + points[(int) prints[1]].y);
                out.println(points[(int) prints[2]].x + " " + points[(int) prints[2]].y);
            } else if (points[(int) prints[1]].x > points[(int) prints[2]].x) {
                out.println(points[(int) prints[2]].x + " " + points[(int) prints[2]].y);
                out.println(points[(int) prints[1]].x + " " + points[(int) prints[1]].y);
            } else {
                if (points[(int) prints[1]].y < points[(int) prints[2]].y) {
                    out.println(points[(int) prints[1]].x + " " + points[(int) prints[1]].y);
                    out.println(points[(int) prints[2]].x + " " + points[(int) prints[2]].y);
                } else {
                    out.println(points[(int) prints[2]].x + " " + points[(int) prints[2]].y);
                    out.println(points[(int) prints[1]].x + " " + points[(int) prints[1]].y);
                }
            }
            //
        } else if (3 == n) {
            point3[] points = new point3[d];
            for (int i = 1; i <= d; i++) {
                points[i - 1] = new point3(in.nextLong(), in.nextLong(), in.nextLong());
            }
            Arrays.sort(points, new comparator_x_three());
            long[] prints = TribleDemison(points, 0, d - 1);
            if (points[(int) prints[1]].x < points[(int) prints[2]].x) {
                out.println(points[(int) prints[1]].x + " " + points[(int) prints[1]].y + " " + points[(int) prints[1]].z);
                out.println(points[(int) prints[2]].x + " " + points[(int) prints[2]].y + " " + points[(int) prints[2]].z);
            } else if (points[(int) prints[1]].x > points[(int) prints[2]].x) {
                out.println(points[(int) prints[2]].x + " " + points[(int) prints[2]].y + " " + points[(int) prints[2]].z);
                out.println(points[(int) prints[1]].x + " " + points[(int) prints[1]].y + " " + points[(int) prints[1]].z);
            } else {
                if (points[(int) prints[1]].y < points[(int) prints[2]].y) {
                    out.println(points[(int) prints[1]].x + " " + points[(int) prints[1]].y + " " + points[(int) prints[1]].z);
                    out.println(points[(int) prints[2]].x + " " + points[(int) prints[2]].y + " " + points[(int) prints[2]].z);
                } else if (points[(int) prints[1]].y > points[(int) prints[2]].y) {
                    out.println(points[(int) prints[2]].x + " " + points[(int) prints[2]].y + " " + points[(int) prints[2]].z);
                    out.println(points[(int) prints[1]].x + " " + points[(int) prints[1]].y + " " + points[(int) prints[1]].z);
                } else {
                    if (points[(int) prints[1]].z < points[(int) prints[2]].z) {
                        out.println(points[(int) prints[1]].x + " " + points[(int) prints[1]].y + " " + points[(int) prints[1]].z);
                        out.println(points[(int) prints[2]].x + " " + points[(int) prints[2]].y + " " + points[(int) prints[2]].z);
                    } else {
                        out.println(points[(int) prints[2]].x + " " + points[(int) prints[2]].y + " " + points[(int) prints[2]].z);
                        out.println(points[(int) prints[1]].x + " " + points[(int) prints[1]].y + " " + points[(int) prints[1]].z);
                    }
                }
            }
        }

    }

    public static long[] DoubleDemison(point[] points, int begin, int end) {
        //返回值[0] : 长度平方, [1],[2]:
        long[] willreturn = new long[3];
        int temp;
        if (begin > end) {
            temp = end;
            end = begin;
            begin = temp;
        }
        if (0 == end - begin) {
            long tempLength_1 = Long.MAX_VALUE;
            long tempLength_2 = Long.MAX_VALUE;
            if (end < points.length - 1) {
                tempLength_1 = countDistanceTwo(points[end], points[end + 1]);
            }
            if (begin > 0) {
                tempLength_2 = countDistanceTwo(points[begin - 1], points[begin]);
            }
            if (begin > 0 && end < points.length - 1) {
                if (tempLength_1 < tempLength_2) {
                    willreturn[0] = tempLength_1;
                    willreturn[1] = end;
                    willreturn[2] = end + 1;
                } else {
                    willreturn[0] = tempLength_2;
                    willreturn[1] = begin - 1;
                    willreturn[2] = begin;
                }
            } else if (begin > 0 && end >= points.length - 1) {
                willreturn[0] = tempLength_2;
                willreturn[1] = begin - 1;
                willreturn[2] = begin;
            } else {
                willreturn[0] = tempLength_1;
                willreturn[1] = end;
                willreturn[2] = end + 1;
            }
            return willreturn;
        } else if (1 == end - begin) {
            willreturn[0] = countDistanceTwo(points[begin], points[end]);
            willreturn[1] = begin;
            willreturn[2] = end;
            return willreturn;
        } else if (2 == end - begin) {
            long tempLength_1 = countDistanceTwo(points[begin], points[begin + 1]);
            long tempLength_2 = countDistanceTwo(points[begin], points[end]);
            long tempLength_3 = countDistanceTwo(points[begin + 1], points[end]);
            if (tempLength_1 < tempLength_2 && tempLength_1 < tempLength_3) {
                willreturn[0] = tempLength_1;
                willreturn[1] = begin;
                willreturn[2] = begin + 1;
            } else if (tempLength_2 < tempLength_3 && tempLength_2 < tempLength_1) {
                willreturn[0] = tempLength_2;
                willreturn[1] = begin;
                willreturn[2] = end;
            } else {
                willreturn[0] = tempLength_3;
                willreturn[1] = begin + 1;
                willreturn[2] = end;
            }
            return willreturn;
        } else {
            int middle = (end - begin) / 2 + begin;
            long[] array1 = DoubleDemison(points, begin, middle);
            if (array1[1] > array1[2]) {
                temp = (int) array1[2];
                array1[2] = array1[1];
                array1[1] = temp;
            }
            long[] array2 = DoubleDemison(points, middle + 1, end);
            if (array2[1] > array2[2]) {
                temp = (int) array2[2];
                array2[2] = array2[1];
                array2[1] = temp;
            }
            willreturn = array1[0] < array2[0] ? array1 : array2;
            int leftside = middle - 1;
            int rightside = middle + 1;
            double temp_Min = Math.sqrt(willreturn[0]);
            double middle_x = (points[middle].x + points[middle + 1].x) / 2;
            while (leftside > begin && middle_x - points[leftside].x < temp_Min) {
                leftside--;
            }
            while (rightside < end && points[rightside].x - middle_x < temp_Min) {
                rightside++;
            }
            int arrayLength = rightside - leftside + 1;
            point[] pos = new point[arrayLength];
            System.arraycopy(points, leftside, pos, 0, arrayLength);
            Arrays.sort(pos, new comparator_y_two());
            boolean isChange = false;
            if (pos.length <= 6) {
                for (int i = 0; i < pos.length - 1; i++) {
                    for (int j = i + 1; j <= pos.length - 1; j++) {
                        long temp_2 = countDistanceTwo(pos[i], pos[j]);
                        if (willreturn[0] > temp_2) {
                            isChange = true;
                            willreturn[0] = temp_2;
                            willreturn[1] = i;
                            willreturn[2] = j;
                        }
                    }
                }
            } else {
                for (int i = 0; i < pos.length - 6; i++) {
                    for (int j = i + 1; j <= i + 6; j++) {
                        long temp_3 = countDistanceTwo(pos[i], pos[j]);
                        if (willreturn[0] > temp_3) {
                            isChange = true;
                            willreturn[0] = temp_3;
                            willreturn[1] = i;
                            willreturn[2] = j;
                        }
                    }
                }
                for (int i = pos.length - 6; i < pos.length - 1; i++) {
                    for (int j = i + 1; j <= pos.length - 1; j++) {
                        long temp_4 = countDistanceTwo(pos[i], pos[j]);
                        if (willreturn[0] > temp_4) {
                            isChange = true;
                            willreturn[0] = temp_4;
                            willreturn[1] = i;
                            willreturn[2] = j;
                        }
                    }
                }
            }
            if (isChange) {
                for (int i = leftside; i <= rightside; i++) {
                    if (points[i].x == pos[(int) willreturn[1]].x && points[i].y == pos[(int) willreturn[1]].y) {
                        willreturn[1] = i;
                        break;
                    }
                }
                for (int i = leftside; i <= rightside; i++) {
                    if (points[i].x == pos[(int) willreturn[2]].x && points[i].y == pos[(int) willreturn[2]].y) {
                        willreturn[2] = i;
                        break;
                    }
                }

            }
        }
        return willreturn;
    }

    public static long[] TribleDemison(point3[] points, int begin, int end) {
        long[] willreturn = new long[3];
        int temp;
        if (begin > end) {
            temp = end;
            end = begin;
            begin = temp;
        }
        if (0 == end - begin) {
            long tempLength_1 = Long.MAX_VALUE;
            long tempLength_2 = Long.MAX_VALUE;
            if (end < points.length - 1) {
                tempLength_1 = countDistanceThree(points[end], points[end + 1]);
            }
            if (begin > 0) {
                tempLength_2 = countDistanceThree(points[begin - 1], points[begin]);
            }
            if (begin > 0 && end < points.length - 1) {
                if (tempLength_1 < tempLength_2) {
                    willreturn[0] = tempLength_1;
                    willreturn[1] = end;
                    willreturn[2] = end + 1;
                } else {
                    willreturn[0] = tempLength_2;
                    willreturn[1] = begin - 1;
                    willreturn[2] = begin;
                }
            } else if (begin > 0 && end >= points.length - 1) {
                willreturn[0] = tempLength_2;
                willreturn[1] = begin - 1;
                willreturn[2] = begin;
            } else {
                willreturn[0] = tempLength_1;
                willreturn[1] = end;
                willreturn[2] = end + 1;
            }
            return willreturn;
        } else if (1 == end - begin) {
            willreturn[0] = countDistanceThree(points[begin], points[end]);
            willreturn[1] = begin;
            willreturn[2] = end;
            return willreturn;
        } else if (2 == end - begin) {
            long tempLength_1 = countDistanceThree(points[begin], points[begin + 1]);
            long tempLength_2 = countDistanceThree(points[begin], points[end]);
            long tempLength_3 = countDistanceThree(points[begin + 1], points[end]);
            if (tempLength_1 < tempLength_2 && tempLength_1 < tempLength_3) {
                willreturn[0] = tempLength_1;
                willreturn[1] = begin;
                willreturn[2] = begin + 1;
            } else if (tempLength_2 < tempLength_3 && tempLength_2 < tempLength_1) {
                willreturn[0] = tempLength_2;
                willreturn[1] = begin;
                willreturn[2] = end;
            } else {
                willreturn[0] = tempLength_3;
                willreturn[1] = begin + 1;
                willreturn[2] = end;
            }
            return willreturn;
        } else {
            int middle = (end - begin) / 2 + begin;
            long[] array1 = TribleDemison(points, begin, middle);
            if (array1[1] > array1[2]) {
                temp = (int) array1[2];
                array1[2] = array1[1];
                array1[1] = temp;
            }
            long[] array2 = TribleDemison(points, middle + 1, end);
            if (array2[1] > array2[2]) {
                temp = (int) array2[2];
                array2[2] = array2[1];
                array2[1] = temp;
            }
            willreturn = array1[0] < array2[0] ? array1 : array2;
            int leftside = middle - 1;
            int rightside = middle + 1;
            double temp_Min = Math.sqrt(willreturn[0]);
            double middle_x = (points[middle].x + points[middle + 1].x) / 2;
            while (leftside > begin && middle_x - points[leftside].x < temp_Min) {
                leftside--;
            }
            while (rightside < end && points[rightside].x - middle_x < temp_Min) {
                rightside++;
            }
            int arrayLength = rightside - leftside + 1;
            point3[] pos = new point3[arrayLength];
            System.arraycopy(points, leftside, pos, 0, arrayLength);
            Arrays.sort(pos, new comparator_y_three());
            boolean isChange = false;
            if (pos.length <= 15) {
                for (int i = 0; i < pos.length - 1; i++) {
                    for (int j = i + 1; j <= pos.length - 1; j++) {
                        long temp_2 = countDistanceThree(pos[i], pos[j]);
                        if (willreturn[0] > temp_2) {
                            isChange = true;
                            willreturn[0] = temp_2;
                            willreturn[1] = i;
                            willreturn[2] = j;
                        }
                    }
                }
            } else {
                for (int i = 0; i < pos.length - 15; i++) {
                    for (int j = i + 1; j <= i + 15; j++) {
                        long temp_3 = countDistanceThree(pos[i], pos[j]);
                        if (willreturn[0] > temp_3) {
                            isChange = true;
                            willreturn[0] = temp_3;
                            willreturn[1] = i;
                            willreturn[2] = j;
                        }
                    }
                }
                for (int i = pos.length - 15; i < pos.length - 1; i++) {
                    for (int j = i + 1; j <= pos.length - 1; j++) {
                        long temp_4 = countDistanceThree(pos[i], pos[j]);
                        if (willreturn[0] > temp_4) {
                            isChange = true;
                            willreturn[0] = temp_4;
                            willreturn[1] = i;
                            willreturn[2] = j;
                        }
                    }
                }
            }
            if (isChange) {
                for (int i = leftside; i <= rightside; i++) {
                    if (points[i].x == pos[(int) willreturn[1]].x && points[i].y == pos[(int) willreturn[1]].y) {
                        willreturn[1] = i;
                        break;
                    }
                }
                for (int i = leftside; i <= rightside; i++) {
                    if (points[i].x == pos[(int) willreturn[2]].x && points[i].y == pos[(int) willreturn[2]].y) {
                        willreturn[2] = i;
                        break;
                    }
                }

            }
        }
        return willreturn;
    }

    public static long countDistanceOne(int x1, int x2) {
        return (x1 > x2 ? x1 - x2 : x2 - x1);
    }

    public static long countDistanceTwo(point p1, point p2) {
        long diffx = (p1.x > p2.x ? p1.x - p2.x : p2.x - p1.x);
        long diffy = (p1.y > p2.y ? p1.y - p2.y : p2.y - p1.y);
        return diffx * diffx + diffy * diffy;
    }

    public static long countDistanceThree(point3 p1, point3 p2) {
        long diffx = (p1.x > p2.x ? p1.x - p2.x : p2.x - p1.x);
        long diffy = (p1.y > p2.y ? p1.y - p2.y : p2.y - p1.y);
        long diffz = (p1.z > p2.z ? p1.z - p2.z : p2.z - p1.z);

        return diffx * diffx + diffy * diffy + diffz * diffz;
    }

    public static class point {
        public final long x, y;

        point(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class point3 {
        public final long x, y, z;

        point3(long x, long y, long z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    static class comparator_x_two implements Comparator<point> {
        @Override
        public int compare(point p1, point p2) {
            return Long.compare(p1.x, p2.x);
        }
    }//小 → 大

    static class comparator_x_three implements Comparator<point3> {
        @Override
        public int compare(point3 p1, point3 p2) {
            return Long.compare(p1.x, p2.x);
        }
    }//小 → 大

    static class comparator_y_two implements Comparator<point> {
        @Override
        public int compare(point p1, point p2) {
            return Long.compare(p1.y, p2.y);
        }
    }//小 → 大

    static class comparator_y_three implements Comparator<point3> {
        @Override
        public int compare(point3 p1, point3 p2) {
            return Long.compare(p1.y, p2.y);
        }
    }//小 → 大
}