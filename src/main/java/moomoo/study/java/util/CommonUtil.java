package moomoo.study.java.util;

import java.util.ArrayList;
import java.util.List;

public class CommonUtil {

    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for(T e : list) {
            if (predicate.check(e)) {
                result.add(e);
            }
        }
        return result;
    }
}
