package com.byz.elasticsearch.service;

import com.byz.elasticsearch.EsRuntimeException;
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
import org.zxp.esclientrhl.enums.AggsType;
import org.zxp.esclientrhl.enums.SqlFormat;
import org.zxp.esclientrhl.repository.*;
import org.zxp.esclientrhl.repository.response.ScrollResponse;

import java.util.List;
import java.util.Map;

/**
 * @author baiyizhong
 * @Description:
 * @date 2020-10-22 13:30
 */
public interface JicEsService<T, M> {
    /**
     * 通过Low Level REST Client 查询
     * https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-low-usage-requests.html
     *
     * @param request 原生查询对象
     * @return
     * @throws EsRuntimeException
     */
    Response request(Request request) throws EsRuntimeException;

    /**
     * 新增索引
     *
     * @param t 索引pojo
     */
    boolean save(T t) throws EsRuntimeException;

    /**
     * 新增索引（路由方式）
     *
     * @param t       索引pojo
     * @param routing 路由信息（默认路由为索引数据_id）
     * @return
     * @throws EsRuntimeException
     */
    boolean save(T t, String routing) throws EsRuntimeException;

    /**
     * 新增索引集合
     *
     * @param list 索引pojo集合
     */
    BulkResponse save(List<T> list) throws EsRuntimeException;

    /**
     * 新增索引集合（分批方式，提升性能，防止es服务内存溢出，每批默认5000条数据）
     *
     * @param list 索引pojo集合
     */
    BulkResponse[] saveBatch(List<T> list) throws EsRuntimeException;

    /**
     * 更新索引集合
     *
     * @param list 索引pojo集合
     * @return
     * @throws EsRuntimeException
     */
    BulkResponse bulkUpdate(List<T> list) throws EsRuntimeException;

    /**
     * 更新索引集合（分批方式，提升性能，防止es服务内存溢出，每批默认5000条数据）
     *
     * @param list 索引pojo集合
     * @return
     * @throws EsRuntimeException
     */
    BulkResponse[] bulkUpdateBatch(List<T> list) throws EsRuntimeException;


    /**
     * 按照有值字段更新索引
     *
     * @param t 索引pojo
     */
    boolean update(T t) throws EsRuntimeException;

    /**
     * 根据queryBuilder所查结果，按照有值字段更新索引
     *
     * @param queryBuilder 查询条件（官方）
     * @param t            索引pojo
     * @param clazz        索引pojo类类型
     * @param limitcount   更新字段不能超出limitcount
     * @param asyn         true异步处理  否则同步处理
     * @return
     * @throws EsRuntimeException
     */
    BulkResponse batchUpdate(QueryBuilder queryBuilder, T t, Class clazz, int limitcount, boolean asyn) throws EsRuntimeException;

    /**
     * 覆盖更新索引
     *
     * @param t 索引pojo
     */
    boolean updateCover(T t) throws EsRuntimeException;

    /**
     * 删除索引
     *
     * @param t 索引pojo
     */
    boolean delete(T t) throws EsRuntimeException;


    /**
     * 删除索引（路由方式）
     *
     * @param t       索引pojo
     * @param routing 路由信息（默认路由为索引数据_id）
     * @return
     * @throws EsRuntimeException
     */
    boolean delete(T t, String routing) throws EsRuntimeException;


    /**
     * 根据条件删除索引
     * https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high-document-delete-by-query.html#java-rest-high-document-delete-by-query-response
     *
     * @param queryBuilder 查询条件（官方）
     * @param clazz        索引pojo类类型
     * @return
     * @throws EsRuntimeException
     */
    BulkByScrollResponse deleteByCondition(QueryBuilder queryBuilder, Class<T> clazz) throws EsRuntimeException;

    /**
     * 删除索引
     *
     * @param id    索引主键
     * @param clazz 索引pojo类类型
     * @return
     * @throws EsRuntimeException
     */
    boolean deleteById(M id, Class<T> clazz) throws EsRuntimeException;


    /**
     * 原生查询
     *
     * @param searchRequest 原生查询请求对象
     * @return
     * @throws EsRuntimeException
     */
    SearchResponse search(SearchRequest searchRequest) throws EsRuntimeException;

    /**
     * 非分页查询
     *
     * @param queryBuilder 查询条件
     * @param clazz        索引pojo类类型
     * @return
     * @throws EsRuntimeException
     */
    List<T> search(QueryBuilder queryBuilder, Class<T> clazz) throws EsRuntimeException;

    /**
     * 非分页查询(跨索引)
     *
     * @param queryBuilder 查询条件
     * @param clazz        索引pojo类类型
     * @param indexs       索引名称
     * @return
     * @throws EsRuntimeException
     */
    List<T> search(QueryBuilder queryBuilder, Class<T> clazz, String... indexs) throws EsRuntimeException;

    /**
     * 非分页查询，指定最大返回条数
     *
     * @param queryBuilder 查询条件
     * @param limitSize    最大返回条数
     * @param clazz        索引pojo类类型
     * @return
     * @throws EsRuntimeException
     */
    List<T> searchMore(QueryBuilder queryBuilder, int limitSize, Class<T> clazz) throws EsRuntimeException;

    /**
     * 非分页查询(跨索引)，指定最大返回条数
     *
     * @param queryBuilder 查询条件
     * @param limitSize    最大返回条数
     * @param clazz        索引pojo类类型
     * @param indexs       索引名称
     * @return
     * @throws EsRuntimeException
     */
    List<T> searchMore(QueryBuilder queryBuilder, int limitSize, Class<T> clazz, String... indexs) throws EsRuntimeException;


    /**
     * 通过uri querystring进行查询
     *
     * @param uri   uri查询的查询条件
     * @param clazz 索引pojo类类型
     * @return
     * @throws EsRuntimeException
     */
    List<T> searchUri(String uri, Class<T> clazz) throws EsRuntimeException;


    /**
     * 通过sql进行查询
     *
     * @param sql       sql脚本（支持mysql语法）
     * @param sqlFormat sql请求返回类型
     * @return
     * @throws EsRuntimeException
     */
    String queryBySQL(String sql, SqlFormat sqlFormat) throws EsRuntimeException;

    /**
     * 查询数量
     *
     * @param queryBuilder 查询条件
     * @param clazz        索引pojo类类型
     * @return
     * @throws EsRuntimeException
     */
    long count(QueryBuilder queryBuilder, Class<T> clazz) throws EsRuntimeException;


    /**
     * 查询数量(跨索引)
     *
     * @param queryBuilder 查询条件
     * @param clazz        索引pojo类类型
     * @param indexs       索引名称
     * @return
     * @throws EsRuntimeException
     */
    long count(QueryBuilder queryBuilder, Class<T> clazz, String... indexs) throws EsRuntimeException;

    /**
     * 支持分页、高亮、排序的查询
     *
     * @param queryBuilder      查询条件
     * @param pageSortHighLight 分页+排序+高亮对象封装
     * @param clazz             索引pojo类类型
     * @return
     * @throws EsRuntimeException
     */
    PageList<T> search(QueryBuilder queryBuilder, PageSortHighLight pageSortHighLight, Class<T> clazz) throws EsRuntimeException;


    /**
     * 支持分页、高亮、排序的查询（跨索引）
     *
     * @param queryBuilder      查询条件
     * @param pageSortHighLight 分页+排序+高亮对象封装
     * @param clazz             索引pojo类类型
     * @param indexs            索引名称
     * @return
     * @throws EsRuntimeException
     */
    PageList<T> search(QueryBuilder queryBuilder, PageSortHighLight pageSortHighLight, Class<T> clazz, String... indexs) throws EsRuntimeException;


    /**
     * 支持分页、高亮、排序、指定返回字段、路由的查询
     *
     * @param queryBuilder 查询条件
     * @param attach       查询增强对象（可支持分页、高亮、排序、指定返回字段、路由、searchAfter信息的定制）
     * @param clazz        索引pojo类类型
     * @return
     * @throws EsRuntimeException
     */
    PageList<T> search(QueryBuilder queryBuilder, Attach attach, Class<T> clazz) throws EsRuntimeException;


    /**
     * 支持分页、高亮、排序、指定返回字段、路由的查询（跨索引）
     *
     * @param queryBuilder 查询条件
     * @param attach       查询增强对象（可支持分页、高亮、排序、指定返回字段、路由、searchAfter信息的定制）
     * @param clazz        索引pojo类类型
     * @param indexs       索引名称
     * @return
     * @throws EsRuntimeException
     */
    PageList<T> search(QueryBuilder queryBuilder, Attach attach, Class<T> clazz, String... indexs) throws EsRuntimeException;

    /**
     * scroll方式查询，创建scroll
     *
     * @param queryBuilder 查询条件
     * @param clazz        索引pojo类类型
     * @param time         scroll窗口时间，单位：小时
     * @param size         scroll查询每次查询条数
     * @return
     * @throws EsRuntimeException
     */
    ScrollResponse<T> createScroll(QueryBuilder queryBuilder, Class<T> clazz, Long time, Integer size) throws EsRuntimeException;

    /**
     * scroll方式查询，创建scroll
     *
     * @param queryBuilder 查询条件
     * @param clazz        索引pojo类类型
     * @param time         scroll窗口时间，单位：小时
     * @param size         scroll查询每次查询条数
     * @param indexs       索引名称
     * @return
     * @throws EsRuntimeException
     */
    ScrollResponse<T> createScroll(QueryBuilder queryBuilder, Class<T> clazz, Long time, Integer size, String... indexs) throws EsRuntimeException;


    /**
     * scroll方式查询
     *
     * @param clazz    索引pojo类类型
     * @param time     scroll窗口时间，单位：小时
     * @param scrollId scroll查询id，from ScrollResponse
     * @return
     * @throws EsRuntimeException
     */
    ScrollResponse<T> queryScroll(Class<T> clazz, Long time, String scrollId) throws EsRuntimeException;

    /**
     * Template方式搜索，Template已经保存在script目录下
     *
     * @param template_params 模版参数
     * @param templateName    模版名称
     * @param clazz           索引pojo类类型
     * @return
     */
    List<T> searchTemplate(Map<String, Object> template_params, String templateName, Class<T> clazz) throws EsRuntimeException;

    /**
     * Template方式搜索，Template内容以参数方式传入
     *
     * @param template_params 模版参数
     * @param templateSource  模版内容
     * @param clazz           索引pojo类类型
     * @return
     */
    List<T> searchTemplateBySource(Map<String, Object> template_params, String templateSource, Class<T> clazz) throws EsRuntimeException;

    /**
     * 保存Template
     *
     * @param templateName   模版名称
     * @param templateSource 模版内容
     * @return
     */
    Response saveTemplate(String templateName, String templateSource) throws EsRuntimeException;

    /**
     * 搜索建议Completion Suggester
     *
     * @param fieldName  搜索建议对应查询字段
     * @param fieldValue 搜索建议查询条件
     * @param clazz      索引pojo类类型
     * @return
     * @throws EsRuntimeException
     */
    List<String> completionSuggest(String fieldName, String fieldValue, Class<T> clazz) throws EsRuntimeException;


    /**
     * 搜索建议Completion Suggester
     *
     * @param fieldName  搜索建议对应查询字段
     * @param fieldValue 搜索建议查询条件
     * @param clazz      索引pojo类类型
     * @param indexs     索引名称
     * @return
     * @throws EsRuntimeException
     */
    List<String> completionSuggest(String fieldName, String fieldValue, Class<T> clazz, String... indexs) throws EsRuntimeException;


    /**
     * 搜索建议phrase Suggester
     * https://www.elastic.co/guide/en/elasticsearch/reference/current/search-suggesters.html
     *
     * @param fieldName  搜索建议对应查询字段
     * @param fieldValue 搜索建议查询条件
     * @param param      定制Phrace Suggester的参数
     * @param clazz      索引pojo类类型
     * @return
     * @throws EsRuntimeException
     */
    List<String> phraseSuggest(String fieldName, String fieldValue, ElasticsearchTemplateImpl.PhraseSuggestParam param, Class<T> clazz) throws EsRuntimeException;


    /**
     * 搜索建议phrase Suggester
     * https://www.elastic.co/guide/en/elasticsearch/reference/current/search-suggesters.html
     *
     * @param fieldName  搜索建议对应查询字段
     * @param fieldValue 搜索建议查询条件
     * @param param      定制Phrace Suggester的参数
     * @param clazz      索引pojo类类型
     * @param indexs     索引名称
     * @return
     * @throws EsRuntimeException
     */
    List<String> phraseSuggest(String fieldName, String fieldValue, ElasticsearchTemplateImpl.PhraseSuggestParam param, Class<T> clazz, String... indexs) throws EsRuntimeException;


    /**
     * 根据ID查询
     *
     * @param id    索引数据id值
     * @param clazz 索引pojo类类型
     * @return
     * @throws EsRuntimeException
     */
    T getById(M id, Class<T> clazz) throws EsRuntimeException;

    /**
     * 根据ID列表批量查询
     *
     * @param ids   索引数据id值数组
     * @param clazz 索引pojo类类型
     * @return
     * @throws EsRuntimeException
     */
    List<T> mgetById(M[] ids, Class<T> clazz) throws EsRuntimeException;

    /**
     * id数据是否存在
     *
     * @param id    索引数据id值
     * @param clazz 索引pojo类类型
     * @return
     */
    boolean exists(M id, Class<T> clazz) throws EsRuntimeException;

    /**
     * 普通聚合查询，请结合 https://gitee.com/zxporz/ESClientRHL/wikis/Elasticsearch-ESClientRHL 使用
     * 以bucket分组以aggstypes的方式metric度量
     *
     * @param metricName   度量字段名称
     * @param aggsType     度量类型
     * @param queryBuilder 查询条件
     * @param clazz        索引pojo类类型
     * @param bucketName   分桶字段名称
     * @return
     */
    Map aggs(String metricName, AggsType aggsType, QueryBuilder queryBuilder, Class<T> clazz, String bucketName) throws EsRuntimeException;


    /**
     * 普通聚合查询，请结合 https://gitee.com/zxporz/ESClientRHL/wikis/Elasticsearch-ESClientRHL 使用
     *
     * @param metricName   度量字段名称
     * @param aggsType     度量类型
     * @param queryBuilder 查询条件
     * @param clazz        索引pojo类类型
     * @param bucketName   分桶字段名称
     * @param indexs       索引名称
     * @return
     * @throws EsRuntimeException
     */
    Map aggs(String metricName, AggsType aggsType, QueryBuilder queryBuilder, Class<T> clazz, String bucketName, String... indexs) throws EsRuntimeException;

    /**
     * 以aggstypes的方式metric度量，请结合 https://gitee.com/zxporz/ESClientRHL/wikis/Elasticsearch-ESClientRHL 使用
     *
     * @param metricName   度量字段名称
     * @param aggsType     度量类型
     * @param queryBuilder 查询条件
     * @param clazz        索引pojo类类型
     * @return
     * @throws EsRuntimeException
     */
    double aggs(String metricName, AggsType aggsType, QueryBuilder queryBuilder, Class<T> clazz) throws EsRuntimeException;

    /**
     * 以aggstypes的方式metric度量，请结合 https://gitee.com/zxporz/ESClientRHL/wikis/Elasticsearch-ESClientRHL 使用
     *
     * @param metricName   度量字段名称
     * @param aggsType     度量类型
     * @param queryBuilder 查询条件
     * @param clazz        索引pojo类类型
     * @param indexs       索引名称
     * @return
     * @throws EsRuntimeException
     */
    double aggs(String metricName, AggsType aggsType, QueryBuilder queryBuilder, Class<T> clazz, String... indexs) throws EsRuntimeException;


    /**
     * 下钻聚合查询(无排序默认策略)，请结合 https://gitee.com/zxporz/ESClientRHL/wikis/Elasticsearch-ESClientRHL 使用
     * 以bucket分组以aggstypes的方式metric度量
     *
     * @param metricName   度量字段名称
     * @param aggsType     度量类型
     * @param queryBuilder 查询条件
     * @param clazz        索引pojo类类型
     * @param bucketNames  分桶字段名称
     * @return
     * @throws EsRuntimeException
     */
    List<Down> aggswith2level(String metricName, AggsType aggsType, QueryBuilder queryBuilder, Class<T> clazz, String[] bucketNames) throws EsRuntimeException;


    /**
     * 下钻聚合查询(无排序默认策略)，请结合 https://gitee.com/zxporz/ESClientRHL/wikis/Elasticsearch-ESClientRHL 使用
     *
     * @param metricName   度量字段名称
     * @param aggsType     度量类型
     * @param queryBuilder 查询条件
     * @param clazz        索引pojo类类型
     * @param bucketNames  分桶字段名称
     * @param indexs       索引名称
     * @return
     * @throws EsRuntimeException
     */
    List<Down> aggswith2level(String metricName, AggsType aggsType, QueryBuilder queryBuilder, Class<T> clazz, String[] bucketNames, String... indexs) throws EsRuntimeException;


    /**
     * 统计聚合metric度量，请结合 https://gitee.com/zxporz/ESClientRHL/wikis/Elasticsearch-ESClientRHL 使用
     *
     * @param metricName   度量字段名称
     * @param queryBuilder 查询条件
     * @param clazz        索引pojo类类型
     * @return
     * @throws EsRuntimeException
     */
    Stats statsAggs(String metricName, QueryBuilder queryBuilder, Class<T> clazz) throws EsRuntimeException;

    /**
     * 统计聚合metric度量，请结合 https://gitee.com/zxporz/ESClientRHL/wikis/Elasticsearch-ESClientRHL 使用
     *
     * @param metricName   度量字段名称
     * @param queryBuilder 查询条件
     * @param clazz        索引pojo类类型
     * @param indexs       索引名称
     * @return
     * @throws EsRuntimeException
     */
    Stats statsAggs(String metricName, QueryBuilder queryBuilder, Class<T> clazz, String... indexs) throws EsRuntimeException;

    /**
     * 以bucket分组，统计聚合metric度量，请结合 https://gitee.com/zxporz/ESClientRHL/wikis/Elasticsearch-ESClientRHL 使用
     *
     * @param metricName   度量字段名称
     * @param queryBuilder 查询条件
     * @param clazz        索引pojo类类型
     * @param bucketName   分桶字段名称
     * @return
     * @throws EsRuntimeException
     */
    Map<String, Stats> statsAggs(String metricName, QueryBuilder queryBuilder, Class<T> clazz, String bucketName) throws EsRuntimeException;

    /**
     * 以bucket分组，统计聚合metric度量，请结合 https://gitee.com/zxporz/ESClientRHL/wikis/Elasticsearch-ESClientRHL 使用
     *
     * @param metricName   度量字段名称
     * @param queryBuilder 查询条件
     * @param clazz        索引pojo类类型
     * @param bucketName   分桶字段名称
     * @param indexs       索引名称
     * @return
     * @throws EsRuntimeException
     */
    Map<String, Stats> statsAggs(String metricName, QueryBuilder queryBuilder, Class<T> clazz, String bucketName, String... indexs) throws EsRuntimeException;


    /**
     * 通用（定制）聚合基础方法，请结合 https://gitee.com/zxporz/ESClientRHL/wikis/Elasticsearch-ESClientRHL 使用
     *
     * @param aggregationBuilder 原生聚合Builder
     * @param queryBuilder       查询条件
     * @param clazz              索引pojo类类型
     * @return
     * @throws EsRuntimeException
     */
    Aggregations aggs(AggregationBuilder aggregationBuilder, QueryBuilder queryBuilder, Class<T> clazz) throws EsRuntimeException;

    /**
     * 通用（定制）聚合基础方法，请结合 https://gitee.com/zxporz/ESClientRHL/wikis/Elasticsearch-ESClientRHL 使用
     *
     * @param aggregationBuilder 原生聚合Builder
     * @param queryBuilder       查询条件
     * @param clazz              索引pojo类类型
     * @param indexs             索引名称
     * @return
     * @throws EsRuntimeException
     */
    Aggregations aggs(AggregationBuilder aggregationBuilder, QueryBuilder queryBuilder, Class<T> clazz, String... indexs) throws EsRuntimeException;


    /**
     * 基数查询，请结合 https://gitee.com/zxporz/ESClientRHL/wikis/Elasticsearch-ESClientRHL 使用
     *
     * @param metricName   度量字段名称
     * @param queryBuilder 查询条件
     * @param clazz        索引pojo类类型
     * @return
     * @throws EsRuntimeException
     */
    long cardinality(String metricName, QueryBuilder queryBuilder, Class<T> clazz) throws EsRuntimeException;

    /**
     * 基数查询，请结合 https://gitee.com/zxporz/ESClientRHL/wikis/Elasticsearch-ESClientRHL 使用
     *
     * @param metricName   度量字段名称
     * @param queryBuilder 查询条件
     * @param clazz        索引pojo类类型
     * @param indexs       索引名称
     * @return
     * @throws EsRuntimeException
     */
    long cardinality(String metricName, QueryBuilder queryBuilder, Class<T> clazz, String... indexs) throws EsRuntimeException;

    /**
     * 百分比聚合 默认聚合见Constant.DEFAULT_PERCSEGMENT，请结合 https://gitee.com/zxporz/ESClientRHL/wikis/Elasticsearch-ESClientRHL 使用
     *
     * @param metricName   度量字段名称
     * @param queryBuilder 查询条件
     * @param clazz        索引pojo类类型
     * @return
     * @throws EsRuntimeException
     */
    Map percentilesAggs(String metricName, QueryBuilder queryBuilder, Class<T> clazz) throws EsRuntimeException;

    /**
     * 以百分比聚合，请结合 https://gitee.com/zxporz/ESClientRHL/wikis/Elasticsearch-ESClientRHL 使用
     *
     * @param metricName    度量字段名称
     * @param queryBuilder  查询条件
     * @param clazz         索引pojo类类型
     * @param customSegment 百分比段位
     * @param indexs        索引名称
     * @return
     * @throws EsRuntimeException
     */
    Map percentilesAggs(String metricName, QueryBuilder queryBuilder, Class<T> clazz, double[] customSegment, String... indexs) throws EsRuntimeException;


    /**
     * 以百分等级聚合 (统计在多少数值之内占比多少)，请结合 https://gitee.com/zxporz/ESClientRHL/wikis/Elasticsearch-ESClientRHL 使用
     *
     * @param metricName    度量字段名称
     * @param queryBuilder  查询条件
     * @param clazz         索引pojo类类型
     * @param customSegment 百分比段位
     * @return
     * @throws EsRuntimeException
     */
    Map percentileRanksAggs(String metricName, QueryBuilder queryBuilder, Class<T> clazz, double[] customSegment) throws EsRuntimeException;

    /**
     * 以百分等级聚合 (统计在多少数值之内占比多少)，请结合 https://gitee.com/zxporz/ESClientRHL/wikis/Elasticsearch-ESClientRHL 使用
     *
     * @param metricName    度量字段名称
     * @param queryBuilder  查询条件
     * @param clazz         索引pojo类类型
     * @param customSegment 百分比段位
     * @param indexs        索引名称
     * @return
     * @throws EsRuntimeException
     */
    Map percentileRanksAggs(String metricName, QueryBuilder queryBuilder, Class<T> clazz, double[] customSegment, String... indexs) throws EsRuntimeException;


    /**
     * 过滤器聚合，请结合 https://gitee.com/zxporz/ESClientRHL/wikis/Elasticsearch-ESClientRHL 使用
     * new FiltersAggregator.KeyedFilter("men", QueryBuilders.termQuery("gender", "male"))
     *
     * @param metricName   度量字段名称
     * @param aggsType     度量类型
     * @param clazz        索引pojo类类型
     * @param queryBuilder 查询条件
     * @param filters      分桶过滤器数组
     * @return
     * @throws EsRuntimeException
     */
    Map filterAggs(String metricName, AggsType aggsType, QueryBuilder queryBuilder, Class<T> clazz, FiltersAggregator.KeyedFilter[] filters) throws EsRuntimeException;

    /**
     * 过滤器聚合，请结合 https://gitee.com/zxporz/ESClientRHL/wikis/Elasticsearch-ESClientRHL 使用
     * new FiltersAggregator.KeyedFilter("men", QueryBuilders.termQuery("gender", "male"))
     *
     * @param metricName   度量字段名称
     * @param aggsType     度量类型
     * @param clazz        索引pojo类类型
     * @param queryBuilder 查询条件
     * @param filters      分桶过滤器数组
     * @param indexs       索引名称
     * @return
     * @throws EsRuntimeException
     */
    Map filterAggs(String metricName, AggsType aggsType, QueryBuilder queryBuilder, Class<T> clazz, FiltersAggregator.KeyedFilter[] filters, String... indexs) throws EsRuntimeException;


    /**
     * 直方图聚合，请结合 https://gitee.com/zxporz/ESClientRHL/wikis/Elasticsearch-ESClientRHL 使用
     *
     * @param metricName   度量字段名称
     * @param aggsType     度量类型
     * @param queryBuilder 查询条件
     * @param clazz        索引pojo类类型
     * @param bucketName   分桶字段名称
     * @param interval     分桶字段值的间隔
     * @return
     * @throws EsRuntimeException
     */
    Map histogramAggs(String metricName, AggsType aggsType, QueryBuilder queryBuilder, Class<T> clazz, String bucketName, double interval) throws EsRuntimeException;


    /**
     * 直方图聚合，请结合 https://gitee.com/zxporz/ESClientRHL/wikis/Elasticsearch-ESClientRHL 使用
     *
     * @param metricName   度量字段名称
     * @param aggsType     度量类型
     * @param queryBuilder 查询条件
     * @param clazz        索引pojo类类型
     * @param bucketName   分桶字段名称
     * @param interval     分桶字段值的间隔
     * @param indexs       索引名称
     * @return
     * @throws EsRuntimeException
     */
    Map histogramAggs(String metricName, AggsType aggsType, QueryBuilder queryBuilder, Class<T> clazz, String bucketName, double interval, String... indexs) throws EsRuntimeException;


    /**
     * 日期直方图聚合，请结合 https://gitee.com/zxporz/ESClientRHL/wikis/Elasticsearch-ESClientRHL 使用
     *
     * @param metricName   度量字段名称
     * @param aggsType     度量类型
     * @param queryBuilder 查询条件
     * @param clazz        索引pojo类类型
     * @param bucketName   分桶字段名称
     * @param interval     分桶日期字段值的间隔
     * @return
     * @throws EsRuntimeException
     */
    Map dateHistogramAggs(String metricName, AggsType aggsType, QueryBuilder queryBuilder, Class<T> clazz, String bucketName, DateHistogramInterval interval) throws EsRuntimeException;

    /**
     * 日期直方图聚合，请结合 https://gitee.com/zxporz/ESClientRHL/wikis/Elasticsearch-ESClientRHL 使用
     *
     * @param metricName   度量字段名称
     * @param aggsType     度量类型
     * @param queryBuilder 查询条件
     * @param clazz        索引pojo类类型
     * @param bucketName   分桶字段名称
     * @param interval     分桶日期字段值的间隔
     * @param indexs       索引名称
     * @return
     * @throws EsRuntimeException
     */
    Map dateHistogramAggs(String metricName, AggsType aggsType, QueryBuilder queryBuilder, Class<T> clazz, String bucketName, DateHistogramInterval interval, String... indexs) throws EsRuntimeException;
}
