package com.byz.elasticsearch.service;

import com.byz.elasticsearch.BaseTest;
import com.byz.elasticsearch.model.po.Main2PO;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.filter.FiltersAggregator;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.metrics.Stats;
import org.elasticsearch.search.aggregations.metrics.Sum;
import org.elasticsearch.search.aggregations.metrics.SumAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.ValueCount;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.zxp.esclientrhl.enums.AggsType;

import java.util.Map;

/**
 * @author baiyizhong
 * @Description:
 * @date 2020-10-22 15:28
 */
public class EsAggsTest extends BaseTest {

    @Autowired
    JicEsService<Main2PO, String> esService;

    @Test
    public void ori() {
        //https://www.elastic.co/guide/en/elasticsearch/client/java-api/6.6/_metrics_aggregations.html
        SumAggregationBuilder aggregation = AggregationBuilders.sum("agg").field("sumAmount");
        Aggregations aggregations = esService.aggs(aggregation, null, Main2PO.class);
        Sum agg = aggregations.get("agg");
        double value = agg.getValue();
        System.out.println(value);
    }


    @Test
    public void aggs() {
        double sum = esService.aggs("sumPremium", AggsType.sum, null, Main2PO.class);
        double count = esService.aggs("sumPremium", AggsType.count, null, Main2PO.class);
        double avg = esService.aggs("sumPremium", AggsType.avg, null, Main2PO.class);
        double min = esService.aggs("sumPremium", AggsType.min, null, Main2PO.class);
        double max = esService.aggs("sumPremium", AggsType.max, null, Main2PO.class);


        System.out.println("sum====" + sum);
        System.out.println("count====" + count);
        System.out.println("avg====" + avg);
        System.out.println("min====" + min);
        System.out.println("max====" + max);
    }


    @Test
    public void aggs2() {
        Map map = esService.aggs("sumPremium", AggsType.sum, null, Main2PO.class, "appliName");
        map.forEach((k, v) -> System.out.println(k + "     " + v));
    }

    @Test
    public void aggsStats() {
        Stats stats = esService.statsAggs("sumPremium", null, Main2PO.class);
        System.out.println("max:" + stats.getMax());
        System.out.println("min:" + stats.getMin());
        System.out.println("sum:" + stats.getSum());
        System.out.println("count:" + stats.getCount());
        System.out.println("avg:" + stats.getAvg());

    }


    @Test
    public void cardinality() {
        long value = esService.cardinality("id", null, Main2PO.class);
        System.out.println(value);
    }


    @Test
    public void percentiles() {
        Map map = esService.percentilesAggs("sumPremium", null, Main2PO.class);
        map.forEach((k, v) ->
                {
                    System.out.println(k + "     " + v);
                }
        );
        double[] dbs = {10.0, 20.0, 30.0, 50.0, 60.0, 90.0, 99.0};
        Map map2 = esService.percentilesAggs("sumPremium", null, Main2PO.class, dbs);
    }

    @Test
    public void percentilesRank() {
        double[] dbs = {1, 4, 5, 9};
        Map map = esService.percentileRanksAggs("sumPremium", null, Main2PO.class, dbs);
        map.forEach((k, v) ->
                {
                    System.out.println(k + "     " + v);
                }
        );
    }

    @Test
    public void filterAggs() {
        FiltersAggregator.KeyedFilter[] filters = {new FiltersAggregator.KeyedFilter("0101", QueryBuilders.matchPhraseQuery("riskCode", "0101")),
                new FiltersAggregator.KeyedFilter("0103", QueryBuilders.matchQuery("riskCode", "0103"))};
        Map map = esService.filterAggs("sumPremium", AggsType.sum, null, Main2PO.class, filters);
        map.forEach((k, v) ->
                System.out.println(k + "    " + v)
        );
    }

    @Test
    public void histogramAggs() {
        Map map = esService.histogramAggs("id", AggsType.count, null, Main2PO.class, "sumPremium", 3);
        map.forEach((k, v) ->
                System.out.println(k + "    " + v)
        );
    }


    @Test
    public void dateHistogramAggs() {
        Map map = esService.dateHistogramAggs("sumPremium", AggsType.sum, null, Main2PO.class, "createDate", DateHistogramInterval.hours(2));
        map.forEach((k, v) ->
                System.out.println(k + "    " + v)
        );
    }

    @Test
    public void rangeAggs() {
        AggregationBuilder aggregation =
                AggregationBuilders.range("range").field("sumPremium").addUnboundedTo(1).addRange(1, 4).addRange(4, 100).addUnboundedFrom(100);
        aggregation.subAggregation(AggregationBuilders.count("agg").field("id.keyword"));
        Aggregations aggregations = esService.aggs(aggregation, null, Main2PO.class);
        Range range = aggregations.get("range");
        for (Range.Bucket entry : range.getBuckets()) {
            ValueCount count = entry.getAggregations().get("agg");
            long value = count.getValue();
            System.out.println(entry.getKey() + "    " + value);
        }
    }
}
