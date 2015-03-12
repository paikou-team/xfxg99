package com.xfxg99.sale.dao;

import java.util.List;
import java.util.Map;

import com.xfxg99.core.MyBatisRepository;
import com.xfxg99.sale.model.SaleBill;
@MyBatisRepository
public interface SaleBillMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SaleBill record);

    int insertSelective(SaleBill record);

    SaleBill selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SaleBill record);

    int updateByPrimaryKey(SaleBill record);
    
    int countByMap(Map<String,Object> map);
    
    List<SaleBill> loadListWithPage(Map<String,Object> map);
}