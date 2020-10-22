package com.byz.elasticsearch.service.impl;


import com.byz.elasticsearch.EsRuntimeException;
import com.byz.elasticsearch.service.JicEsService;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.filter.FiltersAggregator;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.metrics.Stats;
import org.springframework.stereotype.Service;
import org.zxp.esclientrhl.enums.AggsType;
import org.zxp.esclientrhl.enums.SqlFormat;
import org.zxp.esclientrhl.repository.*;
import org.zxp.esclientrhl.repository.response.ScrollResponse;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author baiyizhong
 * @Description:
 * @date 2020-10-22 13:35
 */
@Service
public class JicEsServiceImpl<T, M> implements JicEsService<T, M> {

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public Response request(Request request) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.request(request);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public boolean save(T t) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.save(t);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public boolean save(T t, String routing) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.save(t, routing);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public BulkResponse save(List<T> list) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.save(list);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public BulkResponse[] saveBatch(List<T> list) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.saveBatch(list);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public BulkResponse bulkUpdate(List<T> list) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.bulkUpdate(list);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public BulkResponse[] bulkUpdateBatch(List<T> list) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.bulkUpdateBatch(list);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public boolean update(T t) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.update(t);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public BulkResponse batchUpdate(QueryBuilder queryBuilder, T t, Class clazz, int limitcount, boolean asyn) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.batchUpdate(queryBuilder, t, clazz, limitcount, asyn);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public boolean updateCover(T t) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.updateCover(t);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public boolean delete(T t) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.delete(t);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public boolean delete(T t, String routing) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.delete(t, routing);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public BulkByScrollResponse deleteByCondition(QueryBuilder queryBuilder, Class<T> clazz) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.deleteByCondition(queryBuilder, clazz);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(M id, Class<T> clazz) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.deleteById(id, clazz);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public SearchResponse search(SearchRequest searchRequest) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.search(searchRequest);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public List<T> search(QueryBuilder queryBuilder, Class<T> clazz) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.search(queryBuilder, clazz);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public List<T> search(QueryBuilder queryBuilder, Class<T> clazz, String... indexs) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.search(queryBuilder, clazz, indexs);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public List<T> searchMore(QueryBuilder queryBuilder, int limitSize, Class<T> clazz) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.searchMore(queryBuilder, limitSize, clazz);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public List<T> searchMore(QueryBuilder queryBuilder, int limitSize, Class<T> clazz, String... indexs) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.searchMore(queryBuilder, limitSize, clazz, indexs);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public List<T> searchUri(String uri, Class<T> clazz) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.searchUri(uri, clazz);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public String queryBySQL(String sql, SqlFormat sqlFormat) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.queryBySQL(sql, sqlFormat);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public long count(QueryBuilder queryBuilder, Class<T> clazz) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.count(queryBuilder, clazz);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public long count(QueryBuilder queryBuilder, Class<T> clazz, String... indexs) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.count(queryBuilder, clazz, indexs);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public PageList<T> search(QueryBuilder queryBuilder, PageSortHighLight pageSortHighLight, Class<T> clazz) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.search(queryBuilder, pageSortHighLight, clazz);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public PageList<T> search(QueryBuilder queryBuilder, PageSortHighLight pageSortHighLight, Class<T> clazz, String... indexs) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.search(queryBuilder, pageSortHighLight, clazz, indexs);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public PageList<T> search(QueryBuilder queryBuilder, Attach attach, Class<T> clazz) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.search(queryBuilder, attach, clazz);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public PageList<T> search(QueryBuilder queryBuilder, Attach attach, Class<T> clazz, String... indexs) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.search(queryBuilder, attach, clazz, indexs);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }


    @Override
    public ScrollResponse<T> createScroll(QueryBuilder queryBuilder, Class<T> clazz, Long time, Integer size) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.createScroll(queryBuilder, clazz, time, size);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public ScrollResponse<T> createScroll(QueryBuilder queryBuilder, Class<T> clazz, Long time, Integer size, String... indexs) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.createScroll(queryBuilder, clazz, time, size, indexs);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public ScrollResponse<T> queryScroll(Class<T> clazz, Long time, String scrollId) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.queryScroll(clazz, time, scrollId);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public List<T> searchTemplate(Map<String, Object> template_params, String templateName, Class<T> clazz) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.searchTemplate(template_params, templateName, clazz);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public List<T> searchTemplateBySource(Map<String, Object> template_params, String templateSource, Class<T> clazz) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.searchTemplateBySource(template_params, templateSource, clazz);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public Response saveTemplate(String templateName, String templateSource) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.saveTemplate(templateName, templateSource);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public List<String> completionSuggest(String fieldName, String fieldValue, Class<T> clazz) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.completionSuggest(fieldName, fieldValue, clazz);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public List<String> completionSuggest(String fieldName, String fieldValue, Class<T> clazz, String... indexs) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.completionSuggest(fieldName, fieldValue, clazz, indexs);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public List<String> phraseSuggest(String fieldName, String fieldValue, ElasticsearchTemplateImpl.PhraseSuggestParam param, Class<T> clazz) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.phraseSuggest(fieldName, fieldValue, param, clazz);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public List<String> phraseSuggest(String fieldName, String fieldValue, ElasticsearchTemplateImpl.PhraseSuggestParam param, Class<T> clazz, String... indexs) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.phraseSuggest(fieldName, fieldValue, param, clazz, indexs);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public T getById(M id, Class<T> clazz) throws EsRuntimeException {
        try {
            return (T) elasticsearchTemplate.getById(id, clazz);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public List<T> mgetById(M[] ids, Class<T> clazz) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.mgetById(ids, clazz);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public boolean exists(M id, Class<T> clazz) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.exists(id, clazz);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public Map aggs(String metricName, AggsType aggsType, QueryBuilder queryBuilder, Class<T> clazz, String bucketName) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.aggs(metricName, aggsType, queryBuilder, clazz, bucketName);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public Map aggs(String metricName, AggsType aggsType, QueryBuilder queryBuilder, Class<T> clazz, String bucketName, String... indexs) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.aggs(metricName, aggsType, queryBuilder, clazz, bucketName, indexs);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public double aggs(String metricName, AggsType aggsType, QueryBuilder queryBuilder, Class<T> clazz) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.aggs(metricName, aggsType, queryBuilder, clazz);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public double aggs(String metricName, AggsType aggsType, QueryBuilder queryBuilder, Class<T> clazz, String... indexs) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.aggs(metricName, aggsType, queryBuilder, clazz, indexs);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public List<Down> aggswith2level(String metricName, AggsType aggsType, QueryBuilder queryBuilder, Class<T> clazz, String[] bucketNames) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.aggswith2level(metricName, aggsType, queryBuilder, clazz, bucketNames);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public List<Down> aggswith2level(String metricName, AggsType aggsType, QueryBuilder queryBuilder, Class<T> clazz, String[] bucketNames, String... indexs) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.aggswith2level(metricName, aggsType, queryBuilder, clazz, bucketNames, indexs);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public Stats statsAggs(String metricName, QueryBuilder queryBuilder, Class<T> clazz) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.statsAggs(metricName, queryBuilder, clazz);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public Stats statsAggs(String metricName, QueryBuilder queryBuilder, Class<T> clazz, String... indexs) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.statsAggs(metricName, queryBuilder, clazz, indexs);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public Map<String, Stats> statsAggs(String metricName, QueryBuilder queryBuilder, Class<T> clazz, String bucketName) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.statsAggs(metricName, queryBuilder, clazz, bucketName);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public Map<String, Stats> statsAggs(String metricName, QueryBuilder queryBuilder, Class<T> clazz, String bucketName, String... indexs) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.statsAggs(metricName, queryBuilder, clazz, bucketName, indexs);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public Aggregations aggs(AggregationBuilder aggregationBuilder, QueryBuilder queryBuilder, Class<T> clazz) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.aggs(aggregationBuilder, queryBuilder, clazz);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public Aggregations aggs(AggregationBuilder aggregationBuilder, QueryBuilder queryBuilder, Class<T> clazz, String... indexs) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.aggs(aggregationBuilder, queryBuilder, clazz, indexs);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public long cardinality(String metricName, QueryBuilder queryBuilder, Class<T> clazz) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.cardinality(metricName, queryBuilder, clazz);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public long cardinality(String metricName, QueryBuilder queryBuilder, Class<T> clazz, String... indexs) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.cardinality(metricName, queryBuilder, clazz, indexs);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public Map percentilesAggs(String metricName, QueryBuilder queryBuilder, Class<T> clazz) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.percentilesAggs(metricName, queryBuilder, clazz);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public Map percentilesAggs(String metricName, QueryBuilder queryBuilder, Class<T> clazz, double[] customSegment, String... indexs) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.percentilesAggs(metricName, queryBuilder, clazz, customSegment, indexs);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public Map percentileRanksAggs(String metricName, QueryBuilder queryBuilder, Class<T> clazz, double[] customSegment) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.percentilesAggs(metricName, queryBuilder, clazz, customSegment);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public Map percentileRanksAggs(String metricName, QueryBuilder queryBuilder, Class<T> clazz, double[] customSegment, String... indexs) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.percentilesAggs(metricName, queryBuilder, clazz, customSegment, indexs);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public Map filterAggs(String metricName, AggsType aggsType, QueryBuilder queryBuilder, Class<T> clazz, FiltersAggregator.KeyedFilter[] filters) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.filterAggs(metricName, aggsType, queryBuilder, clazz, filters);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public Map filterAggs(String metricName, AggsType aggsType, QueryBuilder queryBuilder, Class<T> clazz, FiltersAggregator.KeyedFilter[] filters, String... indexs) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.filterAggs(metricName, aggsType, queryBuilder, clazz, filters, indexs);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public Map histogramAggs(String metricName, AggsType aggsType, QueryBuilder queryBuilder, Class<T> clazz, String bucketName, double interval) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.histogramAggs(metricName, aggsType, queryBuilder, clazz, bucketName, interval);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public Map histogramAggs(String metricName, AggsType aggsType, QueryBuilder queryBuilder, Class<T> clazz, String bucketName, double interval, String... indexs) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.histogramAggs(metricName, aggsType, queryBuilder, clazz, bucketName, interval, indexs);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public Map dateHistogramAggs(String metricName, AggsType aggsType, QueryBuilder queryBuilder, Class<T> clazz, String bucketName, DateHistogramInterval interval) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.dateHistogramAggs(metricName, aggsType, queryBuilder, clazz, bucketName, interval);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }

    @Override
    public Map dateHistogramAggs(String metricName, AggsType aggsType, QueryBuilder queryBuilder, Class<T> clazz, String bucketName, DateHistogramInterval interval, String... indexs) throws EsRuntimeException {
        try {
            return elasticsearchTemplate.dateHistogramAggs(metricName, aggsType, queryBuilder, clazz, bucketName, interval, indexs);
        } catch (Exception e) {
            throw new EsRuntimeException(e);
        }
    }
}
