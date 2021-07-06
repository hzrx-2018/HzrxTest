package com.hhf.forum.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @version 0.1
 * @ahthor haifeng
 * @date 2021/6/8 21:52
 */
public class MyAbstractRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DbContectHolder.getDbType();
    }
}
