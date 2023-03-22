package shortUrl;

import config.Redissons;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;

public class IncreIdLab {

    //这里最后一位用-比较好，因为/比较特殊
    private static final String baseDigits = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ+-";
    private static final int BASE = baseDigits.length();
    //通过余数获取对应的64进制表示
    private static final char[] digitsChar = baseDigits.toCharArray();
    //这里预留了足够的空位122位
    private static final int FAST_SIZE = 'z';
    //这个是为了存放字母对应的值，比如-对应63，但是-是45，也就是 digitsIndex[45]=63
    //[digitsChar[-]会自动转变成45，这样子十六进制转十进制，就可以获取到前面的数字了。
    private static final int[] digitsIndex = new int[FAST_SIZE + 1];

    public static void main(String[] args) {
        RedissonClient client = Redissons.getRedissionClient();
        RAtomicLong atomicLong = client.getAtomicLong("incre");
        atomicLong.addAndGet(10000);

        long current = atomicLong.incrementAndGet();
        String encode = encode(current);
        System.out.println(encode);
    }


    //十进制转64进制
    public static String encode(long number) {
        if (number < 0)
            System.out.println("Number(Base64) must be positive: " + number);
        if (number == 0)
            return "0";
        StringBuilder buf = new StringBuilder();
        while (number != 0) {
            //获取余数
            buf.append(digitsChar[(int) (number % BASE)]);
            //剩下的值
            number /= BASE;
        }
        //反转
        return buf.reverse().toString();
    }
}
