package backend.academy.fractals.service.utils;

import java.security.SecureRandom;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ListUtil {

    private static final ThreadLocal<SecureRandom> SECURE_RANDOM = ThreadLocal.withInitial(SecureRandom::new);

    public static <T> T random(List<T> list) {
        int index = SECURE_RANDOM.get().nextInt(list.size());
        return list.get(index);
    }
}
