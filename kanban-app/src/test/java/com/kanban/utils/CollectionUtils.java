package com.kanban.utils;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by L.x on 15-6-3.
 */
public class CollectionUtils {
    public static Set<String> asSet(String... items) {
        return new LinkedHashSet<String>(Arrays.asList(items));
    }
}
