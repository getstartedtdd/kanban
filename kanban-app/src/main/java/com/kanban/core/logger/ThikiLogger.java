package com.kanban.core.logger;

import org.apache.log4j.Logger;

import java.text.MessageFormat;

/**
 * Created by 碧濤 on 2015/7/4.
 */
public class ThikiLogger {
    public Class clazz;

    public Logger logger;

    public ThikiLogger(Class clazz) {
        this.clazz = clazz;
        logger = Logger.getLogger(clazz);
    }

    public static ThikiLogger getLogger(Class clazz) {
        return new ThikiLogger(clazz);
    }

    public void info(String message, Object... data) {
        String logMessage = MessageFormat.format(message, data);
        logger.info(logMessage);
        System.out.println(logMessage);
    }
}
