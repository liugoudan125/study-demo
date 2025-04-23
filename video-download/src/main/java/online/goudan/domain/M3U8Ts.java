package online.goudan.domain;

/**
 * @author 刘成龙
 * @date 2021/6/24 10:40
 * @desc M3U8Ts
 */
public class M3U8Ts {
    private String name;
    private float seconds;

    public M3U8Ts(String name, float seconds) {
        this.name = name;
        this.seconds = seconds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSeconds() {
        return seconds;
    }

    public void setSeconds(float seconds) {
        this.seconds = seconds;
    }

    @Override
    public String toString() {
        return name + " (" + seconds + " sec)";
    }
}
