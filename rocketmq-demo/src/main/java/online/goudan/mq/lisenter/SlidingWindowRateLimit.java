package online.goudan.mq.lisenter;

/**
 * @author chencc
 * @date 2022/7/30 22:07
 */
public class SlidingWindowRateLimit {
    /**
     * 窗口大小
     */
    private int windowSize;
    /**
     * 最大请求数
     */
    private int maxRequestNum;
    /**
     * 以1000毫秒为一个分割(qps)
     */
    private final int LIMIT_UNIT = 1000;
    /**
     * 窗口数组 模拟队列
     */
    private Window[] windowArr;
    /**
     * 窗口数组的长度
     */
    private int arrLen;
    /**
     * 尾下标
     */
    private int tailIndex = 0;
    /**
     * 头下标
     */
    private int headIndex = 0;
    /**
     * 当前请求数
     */
    private int currentRequestNum = 0;

    class Window {
        /**
         * 窗口的起点
         */
        private long start;
        /**
         * 在这个区间内的请求数
         */
        private int requestNum;

        public Window() {
        }

        public Window(int start, int requestNum) {
            this.start = start;
            this.requestNum = requestNum;
        }
    }

    public SlidingWindowRateLimit(int maxRequestNum) {
        this(100, maxRequestNum);
    }

    public SlidingWindowRateLimit(int windowSize, int maxRequestNum) {
        if (LIMIT_UNIT % windowSize != 0) {
            throw new IllegalArgumentException(LIMIT_UNIT + " % windowSize must is 0");
        }
        this.windowSize = windowSize;
        this.maxRequestNum = maxRequestNum;
        this.arrLen = getLen(LIMIT_UNIT / windowSize);
        this.windowArr = new Window[this.arrLen];
        // 预先初始化window对象
        initWindowArr(this.arrLen);
    }

    private void initWindowArr(int len) {
        for (int n = 0; n < len; n++) {
            windowArr[n] = new Window();
        }
    }

    /**
     * 判断当前这个请求是否能够通过
     * @return
     */
    public boolean entry() {
        // 获得当前时间
        long currentTime = System.currentTimeMillis();
        // 判断是否有窗口可以移出去
        while (enableRemoveWindow(currentTime)) {
            // 删除窗口
            removeTailWindow();
        }
        // 如果当前1s内的所有窗口的请求总和数量大于最大请求数量
        if (currentRequestNum >= maxRequestNum) {
            return false;
        }
        return doEntry(currentTime);
    }

    private boolean doEntry(long time) {
        if (headIndex == tailIndex) {
            setHeadWindowStartTime(time);
            return true;
        }
        // 获得当前最新的window对象
        Window currentHeadWindow = getCurrentHeadWindow();
        // 判断是否需要新生成窗口对象
        if (!check(currentHeadWindow, time)) {
            currentHeadWindow.requestNum++;
            currentRequestNum++;
            return true;
        }
        // 获得新窗口的开始时间
        long startTime = getStartTime(currentHeadWindow, time);
        setHeadWindowStartTime(startTime);
        return true;
    }

    /**
     * 当这个窗口和这个时间不在一个窗口内，获得这个时间应该在的窗口的startTime 又可能中间相差了好几个窗口 所以要计算startTime
     * @param window
     * @param time
     * @return
     */
    public long getStartTime(Window window, long time) {
        long start = window.start;
        long interval = time - start;
        // 获得相差窗口的个数
        long num = interval / windowSize;
        return num * windowSize + start;
    }


    private boolean check(Window currentHeadWindow, long time) {
        return currentHeadWindow.start + windowSize < time;
    }

    /**
     * 获得当前最新的window对象
     * @return
     */
    private Window getCurrentHeadWindow() {
        if (headIndex == 0) {
            return windowArr[arrLen - 1];
        }
        return windowArr[headIndex - 1];
    }

    private void incrementHeadIndex() {
        headIndex = (headIndex + 1) & (arrLen - 1);
    }

    private void incrementTailIndex() {
        tailIndex = (tailIndex + 1) & (arrLen - 1);
    }

    /**
     * 设置头下标对应窗口的值
     * @param startTime
     */
    private void setHeadWindowStartTime(long startTime) {
        Window window = windowArr[headIndex];
        window.start = startTime;
        window.requestNum = 1;
        // 总请求数量加一
        currentRequestNum++;
        // 移动头下标
        incrementHeadIndex();
    }

    /**
     * 移动尾下标 来表示移除掉窗口
     */
    private void removeTailWindow() {
        // 减去这个窗口的请求数
        currentRequestNum -= windowArr[tailIndex].requestNum;
        // 移动尾下标
        incrementTailIndex();
    }

    /**
     * 判断是否能够移除掉窗口
     * @param time
     * @return
     */
    private boolean enableRemoveWindow(long time) {
        if (tailIndex == headIndex) {
            return false;
        }
        return time - windowArr[tailIndex].start >= LIMIT_UNIT;
    }

    /**
     * 获得大于len的最小2的N次方的数
     * 参考 ArrayDeque 里面的 源码 基本拷贝的 哈哈
     * 为了 % 可以使用位运算
     * @param len
     * @return
     */
    private int getLen(int len) {
        len |= (len >>>  1);
        len |= (len >>>  2);
        len |= (len >>>  4);
        len |= (len >>>  8);
        len |= (len >>> 16);
        len++;
        if (len < 0) {
            len >>>= 1;
        }
        return len;
    }

}

