package com.example.test.utils;

import java.util.Random;

/**
 * 随机码
 */
public class VerifyUtil {

    // 随机码字符集
    private static final char[] CHARS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    /**
     * 生成随机码
     * @param size
     * @return
     */
    public String createRandomCode(int size){
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i=0; i<size; i++){
            int charsInt = random.nextInt(CHARS.length);
            sb.append(CHARS[charsInt]);
        }
        return sb.toString();
    }

}
