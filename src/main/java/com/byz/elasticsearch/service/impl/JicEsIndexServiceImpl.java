package com.byz.elasticsearch.service.impl;

import com.byz.elasticsearch.EsRuntimeException;
import com.byz.elasticsearch.service.JicEsIndexService;
import org.springframework.stereotype.Service;
import org.zxp.esclientrhl.index.ElasticsearchIndex;

import javax.annotation.Resource;

/**
 * @author baiyizhong
 * @Description:
 * @date 2020-10-21 14:42
 */
@Service
public class JicEsIndexServiceImpl<T> implements JicEsIndexService<T> {


    public JicEsIndexServiceImpl() {
        super();
    }

    @Resource
    private ElasticsearchIndex elasticsearchIndex;


    @Override
    public void createIndex(Class clazz) throws EsRuntimeException {
        try {
            elasticsearchIndex.createIndex(clazz);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public void dropIndex(Class clazz) throws EsRuntimeException {
        try {
            elasticsearchIndex.dropIndex(clazz);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public void switchAliasWriteIndex(Class clazz, String writeIndex) throws EsRuntimeException {
        try {
            elasticsearchIndex.switchAliasWriteIndex(clazz, writeIndex);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public void createAlias(Class clazz) throws EsRuntimeException {
        try {
            elasticsearchIndex.createAlias(clazz);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public boolean exists(Class clazz) throws EsRuntimeException {
        try {
            return elasticsearchIndex.exists(clazz);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public void rollover(Class clazz, boolean async) throws EsRuntimeException {
        try {
            elasticsearchIndex.rollover(clazz, async);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

}
