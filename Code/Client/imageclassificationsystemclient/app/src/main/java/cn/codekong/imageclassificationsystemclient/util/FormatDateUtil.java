package cn.codekong.imageclassificationsystemclient.util;

import java.util.List;

/**
 * Created by szh on 2017/5/26.
 * 格式化数据工具类
 */

public class FormatDateUtil {
    /**
     * 将list转化为"|"分割的String
     * @return
     */
    public static String convertListToString(List<String> list){
        if (list.size() == 0){
            return "";
        }

        StringBuilder result = new StringBuilder();
        for (String str : list) {
            result.append(str).append("|");
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }
}
