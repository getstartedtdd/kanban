package com.kanban.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by 碧濤 on 2015/6/22.
 */
public class JSONResult {
    public static JSONObject buildMessage(final Object message) {
        return new JSONObject() {{
            put("message", message);
        }};
    }
}
