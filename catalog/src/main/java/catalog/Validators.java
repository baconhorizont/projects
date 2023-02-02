package catalog;

import java.util.List;

public class Validators {
    public static boolean isBlank(String s) {
        return s == null || s.isBlank();
    }

    public static boolean isEmpty(List<String> s) {
        return s == null || s.isEmpty();
    }
}
