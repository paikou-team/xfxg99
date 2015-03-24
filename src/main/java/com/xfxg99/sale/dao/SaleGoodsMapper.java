package com.xfxg99.sale.dao;

import java.util.List;
import java.util.Map;

import com.xfxg99.core.MyBatisRepository;
import com.xfxg99.sale.model.SaleGoods;
import com.xfxg99.sale.viewmodel.SaleGoodsVM;
@MyBatisRepository
public interface SaleGoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SaleGoods record);

    int insertSelective(SaleGoods record);

    SaleGoods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SaleGoods record);

    int updateByPrimaryKey(SaleGoods record);

	int countByBillId(Map<String, Object> map);

	List<SaleGoodsVM> loadProductListByBillId(Map<String, Object> map);
}