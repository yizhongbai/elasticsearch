package com.byz.elasticsearch.service;

import com.byz.elasticsearch.BaseTest;
import com.byz.elasticsearch.model.po.ProductPO;
import com.byz.elasticsearch.model.po.TmpPO;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author baiyizhong
 * @Description:
 * @date 2020-10-21 14:47
 */
public class JicEsIndexServiceTest extends BaseTest {

    @Resource
    JicEsIndexService<TmpPO> tmpDOJicEsIndexService;

    @Resource
    JicEsIndexService<ProductPO> productDOJicEsIndexService;

    @Test
    public void index() {
        //删除索引
        //应用在启动时会扫描并创建索引
        tmpDOJicEsIndexService.dropIndex(TmpPO.class);
        boolean exists = tmpDOJicEsIndexService.exists(TmpPO.class);
        Assert.assertTrue(!exists);

        //创建索引
        tmpDOJicEsIndexService.createIndex(TmpPO.class);
        boolean exists3 = tmpDOJicEsIndexService.exists(TmpPO.class);
        Assert.assertTrue(exists3);
    }


    @Test
    public void index2() {
        //删除索引
        //应用在启动时会扫描并创建索引
        tmpDOJicEsIndexService.dropIndex(TmpPO.class);
        boolean exists = tmpDOJicEsIndexService.exists(TmpPO.class);
        Assert.assertTrue(!exists);

        productDOJicEsIndexService.dropIndex(ProductPO.class);
        boolean exists2 = productDOJicEsIndexService.exists(ProductPO.class);
        Assert.assertTrue(!exists2);


        //创建索引
        tmpDOJicEsIndexService.createIndex(TmpPO.class);
        boolean exists3 = tmpDOJicEsIndexService.exists(TmpPO.class);
        Assert.assertTrue(exists3);

        productDOJicEsIndexService.createIndex(ProductPO.class);
        boolean exists4 = productDOJicEsIndexService.exists(ProductPO.class);
        Assert.assertTrue(exists4);

    }


}


