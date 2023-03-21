package shortUrl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUrlLab {


    public static String[] ShortUrl(String url) throws NoSuchAlgorithmException {
        //可以自定义生成MD5加密字符传前的混合KEY
        String key = "Leejor";
        //要使用生成URL的字符
        String[] chars = new String[]{
                "a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p",
                "q", "r", "s", "t", "u", "v", "w", "x",
                "y", "z", "0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "A", "B", "C", "D",
                "E", "F", "G", "H", "I", "J", "K", "L",
                "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z"
        };

        //对传入网址进行MD5加密
        MessageDigest digest = MessageDigest.getInstance("md5");
        String hex = new String(digest.digest((key + url).getBytes()));

        String[] resUrl = new String[4];

        for (int i = 0; i < 4; i++) {
            //把加密字符按照8位一组16进制与0x3FFFFFFF进行位与运算
            int hexint = 0x3FFFFFFF & Integer.parseInt(hex.substring(i * 8, 8), 16);
            StringBuilder outChars = new StringBuilder();
            for (int j = 0; j < 6; j++) {
                //把得到的值与0x0000003D进行位与运算，取得字符数组chars索引
                int index = 0x0000003D & hexint;
                //把取得的字符相加
                outChars.append(chars[index]);
                //每次循环按位右移5位
                hexint = hexint >> 5;
            }
            //把字符串存入对应索引的输出数组
            resUrl[i] = outChars.toString();
        }
        return resUrl;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println(ShortUrl("www.baiodu.com"));
    }
}
