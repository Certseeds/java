<!--
 * @Github: https://github.com/Certseeds
 * @Organization: SUSTech
 * @Author: nanoseeds
 * @Date: 2020-02-27 21:41:31
 * @LastEditors: nanoseeds
 * @LastEditTime: 2020-03-02 16:14:57
 -->

# Analysis of six kind of sorting Algorithm

## first of all, those are the datas.

#### increasing array

![Picture 01](./picture_01.png)

#### decreasing array

![Picture 02](./picture_02.png)

#### absolutely random

![Picture 03](./picture_03.png)

#### one tenth random

![Picture 04](./picture_04.png)
### 1. analysis based on sort Algorithm:

+ insert sort
  1. the insert sort is faster in increaing array(part of array is less to greater also helpful), it even have the same performance with the O(Nlog(N))'s Algorithm(if the array is increasing). but in the decreaing array it shows it's a O(N^2) Algorithm , in the random case, it have the same order of magnitude with others O(N^2) Algorithm.
  2. increasing faster than one tenth random , faster than absolutely random, faster than decreasing.
+ Bubblesort and selection sort
  1. Bubblesort and selection sort have the same bad perfermance in all arrays, which is actually tell us that they are O(N^2) Algorithm(but bubble is badder than selection sort),in the absolutely random array, it's perfermance is much more badder.
  2. increasing faster than one tenth random , faster than decreasing, faster than absolutely random.
+ Merge sort
  1. The Merge sort is stable(unless the array is unsorted at all) in time of different array and faster than heapsort, it is allmost the fastest.
  2. increasing ≈ one tenth random ≈ decreasing, faster than absolutely random.
+ HeapSort
  1. The HeapSort is Special becuase it have a few badder than th other O(Nlog(N))'s Algorithm in servel times.unless the array is unsorted at all, it is stable in different kind of arrays.
  2. increasing ≈ one tenth random ≈ decreasing, faster than absolutely random.
+ quick sort
  1.  The quick sort sometimes is as faster as merge sort, but sometimes it is slow as the heapsort. it is unstable for different kinds of array(escapially the unsorted array.)
  2.  but in average,it should be increasing ≈ one tenth random ≈ decreasing, faster than absolutely random.

### 2. Analysis base on different array.

+ In  O(N^2) Algorithm
  1. if part of the array is increaing(it the same for one tenth randomly). insert sort is faster, bubble and selection sort have the same bad performance(but selection sort is a few faster than bubble sort).
  2. If part of the array is decreaing,all of them have the same order of magnitude(but the rank is still insertion sort faster than selection sort faster than bubble sort).
  3. other random array, the insert sort is slow but bubble sort is the badest, insert sort and selection sort have the same order of magnitude, both of them faster than bubble sort.the rank is still insertion sort faster than selection sort faster than bubble sort
  4. after all, the rank is always insertion sort faster than selection sort faster than bubble sort. but the fastest and badtest is different.
+ In O(log(N)) Algorithm: ~~Actually they dont care about that~~
  1. If the array in increaing,decreasing,or one tenth randomly,quick sort ≈ mergesort faster than heapsort.
  2. if the array is absolutely random, they are all same order of magnitude and slower than other cases.