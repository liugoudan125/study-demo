package online.goudan;


import javax.swing.plaf.SliderUI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘苟淡
 * @description 给定一个排序数组，你需要在 原地 删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
 * <p>
 * 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
 * @date 2020/8/3 14:55
 */
public class _02删除排序数组的重复项 {
    public static void main(String[] args) {
        int[] arr = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        _02删除排序数组的重复项 demo = new _02删除排序数组的重复项();

        System.out.println(demo.myRemoveDuplicates(arr));
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 我自己的题解(没做出来)
     * 官方题解
     *
     * @param nums
     * @return
     */
    public int myRemoveDuplicates(int[] nums) {
        if (nums.length <= 1) {
            return nums.length;
        }
        int i = 0;
        for (int j = 1; j < nums.length; j++) {
            if (nums[i] != nums[j]) {
                i++;
                nums[i] = nums[j];
            }
        }
        return i + 1;
    }


}
