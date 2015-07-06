package com.kanban.core.exception;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * Created by 碧濤 on 2015/6/22.
 */
public class ThikiExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        try {
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json; charset=utf-8");

            httpServletResponse.setStatus((e instanceof BizException) ? 400 : 500);
            PrintWriter writer = httpServletResponse.getWriter();
            JSONObject result = new JSONObject();
            result.put("success", false);
            result.put("message", e.getMessage());
            writer.write(result.toJSONString());
            writer.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }
}