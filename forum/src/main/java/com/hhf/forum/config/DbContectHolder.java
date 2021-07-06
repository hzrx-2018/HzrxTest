package com.hhf.forum.config;

/**
 * @version 0.1
 * @ahthor haifeng
 * @date 2021/6/8 18:14
 */
public class DbContectHolder {

    public static final String WRITE = "write";
    public static final String READ = "read";

    private static ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setDbType(String db) {
        contextHolder.set(db);
    }

    public static String getDbType() {
        return contextHolder.get();
    }

    public static void clearDbType() {
        contextHolder.remove();
    }
}
