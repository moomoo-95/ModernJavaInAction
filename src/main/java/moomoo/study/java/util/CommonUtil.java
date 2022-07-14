package moomoo.study.java.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class CommonUtil {

    /**
     * list 내에 predicat.check 조건에 부합하는 요소만 리스트에 담아 반환하는 메서드
     */
    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for(T e : list) {
            if (predicate.test(e)) {
                result.add(e);
            }
        }
        return result;
    }
}
