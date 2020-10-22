package com.byz.elasticsearch.service;

import com.byz.elasticsearch.BaseTest;
import com.byz.elasticsearch.model.po.Main2PO;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.zxp.esclientrhl.repository.*;
import org.zxp.esclientrhl.util.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author baiyizhong
 * @Description:
 * @date 2020-10-22 14:07
 */
public class JicEsServiceTest extends BaseTest {

    @Autowired
    private JicEsService<Main2PO, String> esService;

    @Test
    public void curd() {
        Main2PO main = new Main2PO();
        main.setId("main3");
        main.setAppliCode("123456");
        main.setAppliName("spring");
        main.setSumPremium(new Double(100));
        //新增
        boolean save = esService.save(main);
        Assert.assertTrue(save);

        //查询
        Main2PO mainById = esService.getById(main.getId(), Main2PO.class);
        Assert.assertNotNull(mainById);

        //更新
        main.setAppliCode("111111");
        esService.update(main);
        mainById = esService.getById(main.getId(), Main2PO.class);
        Assert.assertEquals(main.getAppliCode(), mainById.getAppliCode());

        //删除
        boolean delete = esService.deleteById(main.getId(), Main2PO.class);
        Assert.assertTrue(delete);

        //查询
        mainById = esService.getById(main.getId(), Main2PO.class);
        Assert.assertNull(mainById);

    }


    @Test
    public void saveList() {
        List<Main2PO> list = getMain2POS();
        //批量新增
        BulkResponse save = esService.save(list);
        Assert.assertTrue(!save.hasFailures());

        //删除
        delete(list);

        //查询
        String[] ids = new String[list.size()];
        int i = 0;
        for (Main2PO po : list) {
            ids[i++] = po.getId();
        }
        List<Main2PO> main2POS = esService.mgetById(ids, Main2PO.class);
        Assert.assertTrue(main2POS == null || main2POS.size() == 0);
    }


    @Test
    public void update() {
        Main2PO main1 = new Main2PO();
        main1.setId("main1");
        main1.setInsuredCode("123");
        //新增
        boolean save = esService.save(main1);
        Assert.assertTrue(save);

        //更新
        main1.setInsuredCode("222");
        esService.update(main1);

        //查询
        Main2PO byId = esService.getById(main1.getId(), Main2PO.class);
        Assert.assertEquals(byId.getInsuredCode(), main1.getInsuredCode());

        //删除
        boolean delete = esService.delete(main1);
        Assert.assertTrue(delete);
    }


    @Test
    public void testCoverUpdate() {
        //TODO update save coverUpdate之间的区别
        Main2PO main1 = new Main2PO();
        main1.setId("main1");
        main1.setInsuredCode("123");
        //新增
        esService.save(main1);
        //更新
        esService.updateCover(main1);
        //删除
        esService.delete(main1);
    }


    @Test
    public void exists() {
        Main2PO main1 = new Main2PO();
        main1.setId("main1");
        main1.setInsuredCode("123");
        //删除
        esService.delete(main1);

        //判断是否存在
        boolean exists = esService.exists("main1", Main2PO.class);
        Assert.assertFalse(exists);

        //添加
        esService.save(main1);
        exists = esService.exists("main1", Main2PO.class);
        Assert.assertTrue(exists);
    }


    @Test
    public void testOri() {
        this.batchSave();
        SearchRequest searchRequest = new SearchRequest(new String[]{"index"});
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(new MatchAllQueryBuilder());
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(10);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = esService.search(searchRequest);

        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            Main2PO t = JsonUtils.string2Obj(hit.getSourceAsString(), Main2PO.class);
            Assert.assertNotNull(t);
        }
        this.batchDelete();
    }


    @Test
    public void search() {
        List<Main2PO> main2POList = esService.search(new MatchAllQueryBuilder(), Main2PO.class);
        main2POList.forEach(main2PO -> System.out.println(main2PO));
    }


    @Test
    public void source() {
        SearchRequest searchRequest = new SearchRequest("index");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(new MatchAllQueryBuilder());
        searchRequest.source(searchSourceBuilder);
    }

    @Test
    public void saveHighlight() {
        Main2PO main1 = new Main2PO();
        main1.setId("main1123123123");
        main1.setAppliCode("123");
        main1.setAppliName("一二三四五唉收到弄得你阿斯达岁的阿斯蒂芬斯蒂芬我单位代缴我佛非我方是的佛挡杀佛第三方东方闪电凡事都红is都if觉得搜房水电费啥都if结算单佛第四届发送到");
        main1.setRiskCode("0501");
        main1.setSumPremium(new Double(100));
        esService.save(main1);
    }

    @Test
    public void search2() {
        int currentPage = 1;
        int pageSize = 10;
        //分页
        PageSortHighLight psh = new PageSortHighLight(currentPage, pageSize);
        //排序
        String sorter = "id.keyword";
        Sort.Order order = new Sort.Order(SortOrder.ASC, sorter);
        psh.setSort(new Sort(order));
        //定制高亮，如果定制了高亮，返回结果会自动替换字段值为高亮内容
        psh.setHighLight(new HighLight().field("appli_name"));
        //可以单独定义高亮的格式
        //new HighLight().setPreTag("<em>");
        //new HighLight().setPostTag("</em>");
        PageList<Main2PO> pageList = new PageList<>();
        pageList = esService.search(QueryBuilders.matchQuery("appli_name", "我"), psh, Main2PO.class);
        pageList.getList().forEach(main2PO -> System.out.println(main2PO));
    }


    @Test
    public void count() {
        long count = esService.count(new MatchAllQueryBuilder(), Main2PO.class);
        System.out.println(count);
    }


    @Test
    public void completionSuggest() {
        List<String> list = esService.completionSuggest("appli_name", "1", Main2PO.class);
        list.forEach(main2 -> System.out.println(main2));
    }


    @Test
    public void queryBuilder() {
        QueryBuilder queryBuilder = QueryBuilders.boostingQuery(QueryBuilders.matchQuery("title", "bryant fox"),
                QueryBuilders.matchQuery("flag", "123")).negativeBoost(0.2f);
        QueryBuilder queryBuilder2 = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("appli_name.keyword", "456"))
                .filter(QueryBuilders.matchPhraseQuery("risk_code", "0101"));
        List<Main2PO> list = esService.search(queryBuilder, Main2PO.class);
        list.forEach(main2PO -> System.out.println(main2PO));
    }


    @Test
    public void saveTemplate() {
        String templatesource = "{\n" +
                "  \"script\": {\n" +
                "    \"lang\": \"mustache\",\n" +
                "    \"source\": {\n" +
                "      \"_source\": [\n" +
                "        \"id\",\"appli_name\"\n" +
                "      ],\n" +
                "      \"size\": 20,\n" +
                "      \"query\": {\n" +
                "        \"term\": {\n" +
                "          \"appli_name\": \"{{name}}\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";
        esService.saveTemplate("tempdemo1", templatesource);
    }

    @Test
    public void searchTemplate() {
        Map param = new HashMap();
        param.put("name", "123");
        esService.searchTemplate(param, "tempdemo1", Main2PO.class).forEach(s -> System.out.println(s));
    }

    @Test
    public void searchTemplate2() {
        Map param = new HashMap();
        param.put("name", "123");
        String templatesource = "{\n" +
                "      \"query\": {\n" +
                "        \"term\": {\n" +
                "          \"appli_name\": \"{{name}}\"\n" +
                "        }\n" +
                "      }\n" +
                "}";
        esService.searchTemplateBySource(param, templatesource, Main2PO.class).forEach(s -> System.out.println(s));
    }

    @Test
    public void attachQuery() {
        Main2PO main2PO = new Main2PO();
        main2PO.setId("qq360");
        main2PO.setAppliName("zzxxpp");
        esService.save(main2PO, "R01");

        Attach attach = new Attach();
        attach.setRouting("R01");
        esService.search(QueryBuilders.termQuery("id", "qq360"), attach, Main2PO.class)
                .getList().forEach(s -> System.out.println(s));

    }


    @Test
    public void attachQuery2() {
        Attach attach = new Attach();
        esService.search(QueryBuilders.termQuery("id", "qq360"), attach, Main2PO.class)
                .getList().forEach(s -> System.out.println(s));
    }

    @Test
    public void attachQuery3() {
        Main2PO main2PO = new Main2PO();
        main2PO.setId("qq360");
        main2PO.setAppliName("zzxxpp");
        esService.save(main2PO, "R01");
        esService.delete(main2PO, "R01");
    }

    @Test
    public void attachQuery4() {
        Attach attach = new Attach();
        PageSortHighLight pageSortHighLight = new PageSortHighLight(1, 5);
        attach.setPageSortHighLight(pageSortHighLight);
        String[] ins = {"id"};
        attach.setIncludes(ins);
        esService.search(new MatchAllQueryBuilder(), attach, Main2PO.class)
                .getList().forEach(s -> System.out.println(s));
    }

    @Test
    public void attachQuery5() {
        PageSortHighLight pageSortHighLight = new PageSortHighLight(1, 5);
        esService.search(new MatchAllQueryBuilder(), pageSortHighLight, Main2PO.class)
                .getList().forEach(s -> System.out.println(s));
    }

    @Test
    public void attachQuery6() {
        Attach attach = new Attach();
        attach.setSearchAfter(true);
        PageSortHighLight pageSortHighLight = new PageSortHighLight(1, 10);
        String sorter = "sum_amount";
        Sort.Order order = new Sort.Order(SortOrder.ASC, sorter);
        pageSortHighLight.setSort(new Sort(order));
        attach.setPageSortHighLight(pageSortHighLight);
        PageList page = esService.search(new MatchAllQueryBuilder(), attach, Main2PO.class);
        page.getList().forEach(s -> System.out.println(s));
        Object[] sortValues = page.getSortValues();
        while (true) {
            attach.setSortValues(sortValues);
            page = esService.search(new MatchAllQueryBuilder(), attach, Main2PO.class);
            if (page.getList() != null && page.getList().size() != 0) {
                page.getList().forEach(s -> System.out.println(s));
                sortValues = page.getSortValues();
            } else {
                break;
            }
        }

    }


    @Test
    public void attachQuery7() {
        Attach attach = new Attach();
        PageSortHighLight pageSortHighLight = new PageSortHighLight(2, 12);
        String sorter = "id.keyword";
        Sort.Order order = new Sort.Order(SortOrder.ASC, sorter);
        pageSortHighLight.setSort(new Sort(order));
        attach.setPageSortHighLight(pageSortHighLight);
        PageList page = esService.search(new MatchAllQueryBuilder(), attach, Main2PO.class);
        page.getList().forEach(s -> System.out.println(s));
    }

    private void delete(List<Main2PO> list) {
        for (Main2PO po : list) {
            boolean delete = esService.deleteById(po.getId(), Main2PO.class);
            Assert.assertTrue(delete);
        }
    }

    private List<Main2PO> getMain2POS() {
        List<Main2PO> list = new ArrayList<>();
        Main2PO main1 = new Main2PO();
        main1.setId("main1");
        main1.setAppliName("456");
        main1.setRiskCode("0101");
        main1.setSumPremium(new Double(1));

        Main2PO main2PO = new Main2PO();
        main2PO.setId("main2");
        main2PO.setAppliName("456");
        main2PO.setSumPremium(new Double(2));
        main2PO.setRiskCode("0102");

        Main2PO main3 = new Main2PO();
        main3.setId("main3");
        main3.setRiskCode("0103");
        main3.setSumPremium(new Double(3));
        main3.setAppliName("456");

        Main2PO main4 = new Main2PO();
        main4.setId("33333333");
        main4.setRiskCode("0103");
        main4.setSumPremium(new Double(4));
        main4.setAppliName("123");

        Main2PO main5 = new Main2PO();
        main5.setId("11111111");
        main5.setRiskCode("0103");
        main5.setAppliName("123");
        main5.setSumPremium(new Double(5));

        Main2PO main6 = new Main2PO();
        main6.setId("22222222");
        main6.setRiskCode("0103");
        main6.setAppliName("123");
        main6.setSumPremium(new Double(6));
        list.add(main1);
        list.add(main2PO);
        list.add(main3);
        list.add(main4);
        list.add(main5);
        list.add(main6);
        return list;
    }

    private void batchSave() {
        List<Main2PO> main2POS = this.getMain2POS();
        BulkResponse save = esService.save(main2POS);
        Assert.assertFalse(save.hasFailures());
    }

    private void batchDelete() {
        this.delete(this.getMain2POS());
    }


}
