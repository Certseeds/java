import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Random;


public class TestSortRunningTimeSurvey {
    //    Task Name           Function Name      run times upper
    static final String max_value_str_N2 = Integer.toString(get_numbers(5));
    static final String max_value_str_logN = Integer.toString(get_numbers(8));
    static final int minimum_sort_range = 7;
    static String[][] taskList = {
            {"InsertionSortTest", "insertionSortTime", max_value_str_N2},
            {"BubbleSortTest", "bubbleSortTime", max_value_str_N2},
            {"SelectionSortTest", "selectionSortTime", max_value_str_N2},
            {"QuickSortTest", "quickSortTime", max_value_str_logN},
            {"MergeSortTest", "mergeSortTime", max_value_str_logN},
            {"HeapSortTest", "heapSortTime", max_value_str_logN}
    };
    static String[] function_list =
            {"insertionSort", "bubbleSort", "selectionSort",
                    "quickSort", "mergeSort", "heapSort",};
    static String[] dataList = {"AscendingSequence", "DescendingSequence",
            "RandomSequence", "OutOfOrderSequence"};
    static int max_length = get_numbers(8) + 10;
    static int[] data = new int[max_length];
    static Random random_produce1 = new Random(System.currentTimeMillis() % 1000000 + 2);

    public static int get_numbers(int num) {
        assert (num >= 0 && num <= 10);
        int will_return = 1;
        for (int i = 0; i < num; i++) {
            will_return *= 10;
        }
        return will_return;
    }

    public static void swap_sort(int[] list, int x, int y) {
        int temp = list[x];
        list[x] = list[y];
        list[y] = temp;
    }

    @Test
    @Disabled// maybe that worth a single workflow
    public void mainTest() {

        long timeStart = System.currentTimeMillis();
        String osName = System.getProperty("os.name");
        System.out.println(osName);
        try {
            File xlsFile = new File("RunningTimeSurvey.xls");
            // Create a workbook
            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(xlsFile);
            Class<?> me = Class.forName(Thread.currentThread().getStackTrace()[1].getClassName());

            for (int s = 0; s < 4; s++) {
                // Create a worksheet
                WritableSheet sheet = workbook.createSheet("RunningTime" + s, s);
                // the first row
                for (int j = 1, n = 1; j <= 6; j++) {
                    n = 10 * n;
                    sheet.addCell(new Label(j + 1, 0, "n = " + n));
                }
                for (int i = 0; i < taskList.length; i++) {
                    // col row data
                    sheet.addCell(new Label(0, i + 1, taskList[i][0]));
                    sheet.addCell(new Label(1, i + 1, taskList[i][1]));
                }
                for (int i = 1; i <= 8; i++) {
                    Method methodGenSeq = me.getMethod(dataList[s], int.class);
                    methodGenSeq.invoke(null, (int) Math.pow(10, i));
                    for (int j = 0; j < taskList.length; j++) {
                        String[] taskInfo = taskList[j];
                        // "origin" Method method = me.getMethod(taskInfo[1], int.class);
                        Method functions = me.getMethod(function_list[j], int.class, int[].class);
                        int upper = Integer.parseInt(taskInfo[2]);
                        //System.out.println(upper);
                        int n = (int) Math.pow(10, i);
                        if (n > upper) {
                            continue;
                        }
                        // run a sort program, get the run time
                        // "origin"  Long time = (Long) method.invoke(null, n);
                        // add the run time to the sheet
                        // col row data
                        Long times = (Long) count(n, functions);
                        //System.out.println(time + " " + times);
                        sheet.addCell(new Label(i + 1, j + 1, times.toString()));
                    }

                }
                System.out.println(s + " is finished");
            }
            workbook.write();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        long timeEnd = System.currentTimeMillis();
        double timeCost = timeEnd - timeStart;

        System.out.println(timeCost / 100);
    }

    public static void AscendingSequence(int n) {
        for (int i = 0; i < n; i++) {
            data[i] = i;
        }
        System.out.println("AscendingSequence " + n + "elements");
    }

    public static void DescendingSequence(int n) {
        for (int i = 0; i < n; i++) {
            data[i] = n - i;
        }
        System.out.println("DescendingSequence " + n + "elements");

    }

    public static void RandomSequence(int n) {
        for (int i = 0; i < n; i++) {
            data[i] = i;
        }
        // shuffle
        Random rnd = new Random();
        for (int i = n; i > 1; i--) {
            int j = rnd.nextInt(i);
            int temp = data[j];
            data[j] = data[i - 1];
            data[i - 1] = temp;
        }
        System.out.println("RandomSequence " + n + " elements");

    }

    public static void OutOfOrderSequence(int n) {
        for (int i = 0; i < n; i++) {
            data[i] = i;
        }
        // shuffle
        Random rnd = new Random(System.currentTimeMillis() % 100000 + 2);
        for (int t = 0; t < n * 0.1; t++) {
            int i = rnd.nextInt(n);
            int j = rnd.nextInt(n);
            int temp = data[j];
            data[j] = data[i];
            data[i] = temp;
        }

        System.out.println("OutOfOrderSequence " + n + " elements");

    }

    public static long count(int n, Method d) {
        int[] list = new int[n + 1];
        System.arraycopy(data, 0, list, 0, n);
        long timeStart = System.currentTimeMillis();
        try {
            d.invoke(null, n, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis() - timeStart;
    }

    // the input value x means the range is [0,n] n+1 values
    public static void insertionSort(int n, int[] list) {
        // UNTODO :add your code here
        // reference: http://math.hws.edu/eck/cs124/javanotes8/c7/s4.html 7.4.3
        System.out.println("use insert sort");
        int key = 0;
        for (int i = 1; i <= n; i++) {
            key = list[i];
            int j = i - 1;
            for (; j >= 0 && list[j] > key; j--) {
                list[j + 1] = list[j];
            }
            list[j + 1] = key;
        }
    }

    public static void bubbleSort(int n, int[] list) {
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n - i - 1; j++) {
                if (list[j] > list[j + 1]) {
                    swap_sort(list, j, j + 1);
                }
            }
        }
        System.out.println("use bubble sort");
    }

    public static void selectionSort(int n, int[] list) {
        // UNTODO :add your code here
        // reference: http://math.hws.edu/eck/cs124/javanotes8/c7/s4.html 7.4.4
        System.out.println("use selection Sort");
        for (int i = 0; i <= n; i++) {
            int min_value = 0x7FFFFFF;
            int position = -1;
            for (int j = i; j <= n; j++) {
                if (min_value > list[j]) {
                    min_value = list[j];
                    position = j;
                }
            }
            swap_sort(list, i, position);
        }
        System.out.println("use selection Sort");
    }

    public static void quickSort(int n, int[] list) {
        // UNTODO :add your code here
        // Adam's ppt
        System.out.println("use quicksort Sort");
        quickSort_rec(list, 0, n);
    }

    public static void quickSort_rec(int[] list, int begin, int end) {
        //System.out.println(begin + " : " + end);
        if (begin >= end) {
            return;
        } else if (end - begin <= minimum_sort_range) {
            Arrays.sort(list, begin, end + 1);
            return;
        }
        int middle = begin + (end - begin) / 2;
        int position = random_produce1.nextInt(end - begin) + begin;
        int pivot = list[position];
        //System.out.println(pivot + " " + position);
        swap_sort(list, position, end);
        int count = 0;
        for (int i = begin; i < end; i++) {
            if (list[i] < pivot) {
                swap_sort(list, begin + count, i);
                count++;
            }
        }
        swap_sort(list, begin + count, end);
        quickSort_rec(list, begin, begin + count - 1);
        quickSort_rec(list, begin + count + 1, end);
    }

    public static void mergeSort(int n, int[] list) {
        // UnTODO :add your code here
        int[] zeros = new int[n + 1];
        // https://introcs.cs.princeton.edu/java/42sort/ Mergesort
        System.out.println("use merge Sort");
        mergesort_rec(list, zeros, 0, n);
    }

    public static void mergesort_rec(int[] list, int[] zeros, int begin, int end) {
        //System.out.println(begin + " " + end);
        if (begin >= end) {
            return;
        } else if (end - begin <= minimum_sort_range) {
            Arrays.sort(list, begin, end + 1);
            return;
        }
        int middle = begin + (end - begin) / 2;
        mergesort_rec(list, zeros, begin, middle);
        mergesort_rec(list, zeros, middle + 1, end);
        int pos = begin;
        int start1 = begin;
        int start2 = middle + 1;
        while (start1 <= middle && start2 <= end) {
            if (list[start1] < list[start2]) {
                zeros[pos] = list[start1];
                start1++;
            } else {
                zeros[pos] = list[start2];
                start2++;
            }
            pos++;
        }
        while (start1 <= middle) {
            zeros[pos] = list[start1];
            pos++;
            start1++;
        }
        while (start2 <= end) {
            zeros[pos] = list[start2];
            pos++;
            start2++;
        }
        System.arraycopy(zeros, begin, list, begin, end - begin + 1);
//        for (int i = begin; i <= end; i++) {
//            list[i] = zeros[i];
//        }
    }

    public static void heapSort(int n, int[] list) {
        // UNTODO :add your code here
        System.out.println("use heap Sort");
        int beginIndex = ((n + 1) / 2) - 1;
        for (int i = beginIndex; i >= 0; i--) {
            heapSort_headpify(list, i, n);
        }
        for (int i = n; i > 0; i--) {
            swap_sort(list, 0, i);
            heapSort_headpify(list, 0, i - 1);
        }
    }

    public static void heapSort_headpify(int[] list, int index, int length) {
        int left = (index << 1) + 1;
        int right = left + 1;
        int son_max = left;
        if (left > length) {
            return;
        }
        if (right <= length && list[right] > list[left]) {
            son_max = right;
        }
        if (list[son_max] > list[index]) {
            swap_sort(list, son_max, index);
            heapSort_headpify(list, son_max, length);
        }
    }
}
