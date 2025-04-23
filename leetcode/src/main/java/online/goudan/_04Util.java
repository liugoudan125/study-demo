package online.goudan;

import java.util.Arrays;

/**
 * @author chenglongliu
 * @date 2020/10/15 14:20
 * @desc 04的附件
 */
public class _04Util {

    /**
     * 快速排序
     *
     * @param arr   需要排序的数组
     * @param start 开始时最左边的索引=0
     * @param end   开始时最右边的索引=arr.length-1
     */
    public static void qucikSort(int[] arr, int start, int end) {
        if (start >= end) {
            return ;
        }
        int temp, left, right, key;
        //从左到右的指针
        left = start;
        //从右到左的指针
        right = end;
        //基准
        key = arr[start];

        //循环完成后,比基准值小的都在左边了,比基准值大的都在右边了
        while (left < right) {
            //从右往左找到第一个比基准值小的数
            while (left < right && key <= arr[right]) {
                right--;
            }
            //从左往右找到第一个比基准值大的数
            while (left < right && key >= arr[left]) {
                left++;
            }
            //将这两个数交换位置
            if (left < right) {
                temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
            }
        }

        //将基准值交换到中间
        arr[start] = arr[left];
        arr[left] = key;
        qucikSort(arr,0,left);
        qucikSort(arr,left+1,end);
    }


    /**
     * 希尔排序(递归)
     *
     * @param arr
     * @param step
     */
    public static void shellSort(int[] arr, int step) {
        if (step == 0) {
            return;
        }
        int n = arr.length;
        int index = step;
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
        shellSort(arr, step / 2);
    }


    /**
     * 归并排序
     * 分解
     *
     * @param arr   待排序数组
     * @param left  0
     * @param right arr.length - 1
     */
    public static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            //分解
            int middle = (left + right) / 2;
            //治理
            mergeSort(arr, left, middle);
            mergeSort(arr, middle + 1, right);
            //合并
            mSort(arr, left, middle, right);
        }
    }

    /**
     * 归并排序
     * 合并
     *
     * @param arr
     * @param left
     * @param middle
     * @param right
     */
    private static void mSort(int[] arr, int left, int middle, int right) {
        int[] tempArr = new int[arr.length];
        int leftIndex = left;
        int rightIndex = middle + 1;
        int index = left;
        while (leftIndex <= middle && rightIndex <= right) {
            if (arr[leftIndex] > arr[rightIndex]) {
                tempArr[index++] = arr[rightIndex++];
            } else {
                tempArr[index++] = arr[leftIndex++];
            }
        }

        //如果左边还有数据未添加到中间数组
        while (leftIndex <= middle) {
            tempArr[index++] = arr[leftIndex++];
        }
        //如果右边还有数据未添加到中间数组
        while (rightIndex <= right) {
            tempArr[index++] = arr[rightIndex++];
        }

        //将这段有序的数组合并到
        while (left <= right) {
            arr[left] = tempArr[left++];
        }

    }


}
