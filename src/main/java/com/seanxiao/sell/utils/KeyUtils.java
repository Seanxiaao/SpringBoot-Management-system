package com.seanxiao.sell.utils;

import java.util.Random;

public class KeyUtils {
    /*
    * generate the key for multi thread situation
    * */
    public static synchronized String genKey() {
        Random random = new Random();
        return System.currentTimeMillis() + String.valueOf(random.nextInt(90000) + 10000);
    }
}
