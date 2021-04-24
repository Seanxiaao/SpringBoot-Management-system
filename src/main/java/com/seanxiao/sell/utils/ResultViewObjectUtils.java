package com.seanxiao.sell.utils;

import com.seanxiao.sell.viewobject.ProductViewObject;
import com.seanxiao.sell.viewobject.ResultViewObject;

import java.util.List;
import java.util.Map;

public class ResultViewObjectUtils {

    public static ResultViewObject success(Object object) {
        ResultViewObject resultViewObject = new ResultViewObject<>();
        resultViewObject.setCode(0);
        resultViewObject.setMessage("successful");
        resultViewObject.setData(object);
        return resultViewObject;
    }

    public static ResultViewObject success() {
        return success(null);
    }

    public static ResultViewObject error(Integer code, String message) {
        ResultViewObject resultViewObject = new ResultViewObject<>();
        resultViewObject.setCode(code);
        resultViewObject.setMessage(message);
        return resultViewObject;
    }
}
