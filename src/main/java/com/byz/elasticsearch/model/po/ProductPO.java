package com.byz.elasticsearch.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zxp.esclientrhl.annotation.ESID;
import org.zxp.esclientrhl.annotation.ESMapping;
import org.zxp.esclientrhl.annotation.ESMetaData;
import org.zxp.esclientrhl.enums.DataType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author baiyizhong
 * @Description:
 * @date 2020-10-21 14:50
 */
@ESMetaData(indexName = "product_center", indexType = "product", number_of_shards = 8, number_of_replicas = 1)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPO {

    @ESID
    private String no;

    @ESMapping(datatype = DataType.text_type, keyword = true)
    private String name;

    @ESMapping(datatype = DataType.double_type)
    private BigDecimal price;

    @ESMapping(datatype = DataType.date_type)
    private Date createDate;

}
