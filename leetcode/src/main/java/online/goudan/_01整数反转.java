package online.goudan;

/**
 * 　　　　　　　　┏┓　　　┏┓+ +
 * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃ ++ + + +
 * 　　　　　　 ████━████ ┃+
 * 　　　　　　　┃　　　　　　　┃ +
 * 　　　　　　　┃　　　┻　　　┃
 * 　　　　　　　┃　　　　　　　┃ + +
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃ + + + +
 * 　　　　　　　　　┃　　　┃　　　　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃ + 　　　　神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃　　+
 * 　　　　　　　　　┃　 　　┗━━━┓ + +
 * 　　　　　　　　　┃ 　　　　　　　┣┓
 * 　　　　　　　　　┃ 　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 *
 * @author 刘苟淡
 * @description 整数反转
 * 题目:
 * 给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
 * 注意:
 * 假设我们的环境只能存储得下 32 位的有符号整数，则其数值范围为 [−231,  231 − 1]。请根据这个假设，如果反转后整数溢出那么就返回 0。
 * @date 2020/7/16 17:06
 */
public class _01整数反转 {
    public static void main(String[] args) {
        int x = -14141;
        _01整数反转 整数反转 = new _01整数反转();
        整数反转.reverse(x);
    }

    /**
     * 我的题解
     *
     * @param x
     * @return
     */
    public int reverse(int x) {
        long a = x;
        long sum = 0;

        while (a != 0) {
            //sum 先乘10然后加上 原始数模10后的数(负数模运算后也是负数)
            sum = sum * 10 + a % 10;
            a = a / 10;
        }

        if (sum > Integer.MAX_VALUE || sum < Integer.MIN_VALUE) {
            return 0;
        }

        return (int) sum;
    }

    /**
     * 更优的题解
     *
     * @param x
     * @return
     */
    public int best_reverse(int x) {
        int num = 0;
        // int resnum=0;
        // int result = 0;
        while (x != 0) {
            int resnum = x % 10;
            int result = num * 10 + resnum;
            // System.out.println(result+"==="+x);
            if ((result - resnum) / 10 != num) {
                return 0;
            }
            num = result;
            x /= 10;
        }
        return num;
    }
}
