package online.goudan;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * @author 刘苟淡
 * @description 各种排序算法
 * @date 2020/9/27 11:02
 */
public class _04Sort各种排序算法 {
    private int n;
    private int[] arr;

    @Before
    public void init() {
        arr = new int[]{45, 21, 63, 2, 1, 65, 33, 22, 71, 34, 20, 93, 26, 98, 32, 23, 21, 66, 9};
        n = arr.length;
        System.out.printf("排序前:%s%n", Arrays.toString(arr));
    }

    @After
    public void destory() {
        System.out.printf("排序后:%s%n", Arrays.toString(arr));
    }


    /**
     * 冒泡排序(Bubble Sort)
     * 原理:
     * （1）比较前后相邻的二个数据，如果前面数据大于后面的数据，就将这二个数据交换。
     * （2）这样对数组的第 0 个数据到 N-1 个数据进行一次遍历后，最大的一个数据就“沉”到数组第
     * N-1 个位置。
     * （3）N=N-1，如果 N 不为 0 就重复前面二步，否则排序完成。
     */
    @Test
    public void bubbleSort() {
        int temp;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (arr[i] > arr[j]) {
                    temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }

    /**
     * 插入排序(Insert Sort)
     * 原理:
     * 通过构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，找到相应的位置并插入。
     */
    @Test
    public void insertSort() {

        for (int i = 1; i < n; i++) {
            int insertVar = arr[i];
            int index = i - 1;
            while (index >= 0 && insertVar < arr[index]) {
                arr[index + 1] = arr[index];
                index--;

            }
            arr[index + 1] = insertVar;
        }
    }

    /**
     * 快速排序(Quick Sort)
     * 原理:
     * 选择一个关键值作为基准值。比基准值小的都在左边序列（一般是无序的），
     * 比基准值大的都在右边（一般是无序的）。一般选择序列的第一个元素。
     * 操作:
     * 从右往左找,找到比基准值小的索引,从左往右找,找到比基准值大的索引,然后交换位置,
     * 交换完成后,再接着从右往左找,找到比基准值小的索引,从左往右找,找到比基准值大的索引,交换位置...。
     * 最后,将基准值放在中间,再用递归分别排序基准左右边的
     */
    @Test
    public void quickSort() {
        _04Util.qucikSort(arr, 0, n - 1);
    }

    /**
     * 希尔排序(Shell Sort)
     * 是对插入排序的一种优化
     * 增量(步长)变化是 arr.length/2 arr.length/2^2 arr.length/2^3 ... 1 最终为1;
     * 原理:
     * 先将整个待排序的记录序列分割成为若干子序列分别进行直接插入排序，待整个序列
     * 中的记录“基本有序”时，再对全体记录进行依次直接插入排序。
     * 操作:
     * 1. 选择一个增量序列 t1，t2，…，tk，其中 ti>tj，tk=1；
     * 2. 按增量序列个数 k，对序列进行 k 趟排序；
     * 3. 每趟排序，根据对应的增量 ti，将待排序列分割成若干长度为 m 的子序列，分别对各子表进
     * 行直接插入排序。仅增量因子为1 时，整个序列作为一个表来处理，表长度即为整个序列的长
     * 度。
     */
    @Test
    public void shellSort() {

        int step = n / 2;
        int index;
        while (step > 0) {
            index = step;
            while (index < n) {
                int insertVar = arr[index];
                int innerIndex = index - step;

                while (innerIndex >= 0 && insertVar < arr[innerIndex]) {
                    arr[innerIndex + step] = arr[innerIndex];
                    innerIndex -= step;

                }

                arr[innerIndex + step] = insertVar;
                index++;
            }
            step /= 2;
        }

        //使用递归的实现
        _04Util.shellSort(arr, n / 2);

    }

    /**
     * 归并排序（Merge Sort)
     * 原理：
     * 将两个（或两个以上）有序表合并成一个新的有序表，即把待排序序列
     * 分为若干个子序列，每个子序列是有序的。然后再把有序子序列合并为整体有序序列。
     */
    @Test
    public void mergeSort() {
        _04Util.mergeSort(arr, 0, n - 1);
    }

    /**
     * 桶排序(Bucket Sort)
     * 原理:
     * 把数组 arr 划分为 n 个大小相同子区间（桶），每个子区间各自排序，最
     * 后合并。计数排序是桶排序的一种特殊情况，可以把计数排序当成每个桶里只有一个元素的情况。
     * 操作:
     * 1.找出待排序数组中的最大值 max、最小值 min
     * 2.我们使用 动态数组 ArrayList 作为桶，桶里放的元素也用 ArrayList 存储。桶的数量为(max-min)/arr.length+1
     * 3.遍历数组 arr，计算每个元素 arr[i] 放的桶
     * 4.每个桶各自排序
     */
    @Test
    public void bucketSort() {
        int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            max = Math.max(max, arr[i]);
            min = Math.min(min, arr[i]);
        }
        int bucketNum = (max - min) / n + 1;
        List<List<Integer>> bucket = new ArrayList<>(bucketNum);
        for (int i = 0; i < bucketNum; i++) {
            bucket.add(new ArrayList<>());
        }
        for (int i = 0; i < n; i++) {
            int index = (arr[i] - min) / n;
            bucket.get(index).add(arr[i]);
        }

        for (int i = 0; i < bucketNum; i++) {
            Collections.sort(bucket.get(i));
        }

        int dd = 0;
        for (int i = 0; i < bucketNum; i++) {
            for (int j = 0; j < bucket.get(i).size(); j++) {
                arr[dd++] = bucket.get(i).get(j);
            }
        }
    }


}
