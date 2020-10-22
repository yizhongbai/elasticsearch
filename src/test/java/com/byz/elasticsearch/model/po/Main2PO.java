package com.byz.elasticsearch.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zxp.esclientrhl.annotation.ESID;
import org.zxp.esclientrhl.annotation.ESMapping;
import org.zxp.esclientrhl.annotation.ESMetaData;
import org.zxp.esclientrhl.enums.DataType;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: esdemo
 * @description: ${description}
 * @author: X-Pacific zhang
 * @create: 2019-01-30 09:22
 **/
@SuppressWarnings("jol")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ESMetaData(indexName = "main2", indexType = "main2", number_of_shards = 5, number_of_replicas = 0, printLog = false)
public class Main2PO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ESID
    private String id;

    @ESMapping(datatype = DataType.keyword_type)
    private String riskCode;

    @ESMapping(datatype = DataType.text_type)
    private String riskName;

    @ESMapping(keyword = true)
    private String businessNature;

    @ESMapping(datatype = DataType.text_type)
    private String businessNatureName;

    //可以用默认值，这样会有appli_code.keyword可以直接搜
    private String appliCode;

    @ESMapping(suggest = true)
    private String appliName;

    private String insuredCode;

    @ESMapping(keyword = true)
    private String insuredName;

    @ESMapping(datatype = DataType.date_type)
    private Date operateDate;

    @ESMapping(datatype = DataType.text_type)
    private String operateDateFormat;

    @ESMapping(datatype = DataType.date_type)
    private Date startDate;

    @ESMapping(datatype = DataType.date_type)
    private Date endDate;

    @ESMapping(datatype = DataType.double_type)
    private Double sumAmount;

    @ESMapping(datatype = DataType.double_type)
    private Double sumPremium;

    @ESMapping(datatype = DataType.keyword_type)
    private String comCode;

    @ESMapping(datatype = DataType.date_type)
    private Date createDate;

}
