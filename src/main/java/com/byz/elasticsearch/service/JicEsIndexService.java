package com.byz.elasticsearch.service;

import com.byz.elasticsearch.EsRuntimeException;

/**
 * 索引接口
 *
 * @author baiyizhong
 * @Description:
 * @date 2020-10-21 14:32
 */
public interface JicEsIndexService<T> {

    /**
     * 创建索引
     *
     * @param clazz
     * @throws EsRuntimeException
     */
    void createIndex(Class<T> clazz) throws EsRuntimeException;

    /**
     * 删除索引
     *
     * @param clazz
     * @throws EsRuntimeException
     */
    void dropIndex(Class<T> clazz) throws EsRuntimeException;

    /**
     * 切换Alias写入index
     *
     * @param clazz
     * @param writeIndex
     * @throws EsRuntimeException
     */
    void switchAliasWriteIndex(Class<T> clazz, String writeIndex) throws EsRuntimeException;

    /**
     * 创建Alias
     *
     * @param clazz
     * @throws EsRuntimeException
     */
    void createAlias(Class<T> clazz) throws EsRuntimeException;


    /**
     * 索引是否存在
     *
     * @param clazz
     * @return
     * @throws EsRuntimeException
     */
    boolean exists(Class<T> clazz) throws EsRuntimeException;

    /**
     * 滚动索引
     *
     * @param clazz
     * @param async 是否异步
     * @throws EsRuntimeException
     */
    void rollover(Class<T> clazz, boolean async) throws EsRuntimeException;
}
