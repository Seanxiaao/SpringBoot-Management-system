package com.seanxiao.sell.utils;

import com.seanxiao.sell.enums.CodeEnum;

public class EnumUtils  {

    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T each : enumClass.getEnumConstants()) {
            if (code.equals((each.getStatus()))) {
                return each;
            }
        }
        return null;
    }
}
