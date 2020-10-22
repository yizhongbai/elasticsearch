package com.byz.elasticsearch.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zxp.esclientrhl.annotation.ESMapping;
import org.zxp.esclientrhl.annotation.ESMetaData;
import org.zxp.esclientrhl.enums.DataType;

/**
 * @author baiyizhong
 * @Description:
 * @date 2020-10-22 14:11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ESMetaData(indexName = "tmp_index", indexType = "tmp", number_of_shards = 8, number_of_replicas = 1)
public class TmpPO {
    private String no;

    @ESMapping(datatype = DataType.text_type, keyword = true)
    private String name;

    @ESMapping(datatype = DataType.double_type)
    private Double price;

    @ESMapping(datatype = DataType.double_type)
    private Double count;

    @ESMapping(datatype = DataType.double_type)
    private double tmpPrice;

}
