package com.xfxg99.sale.dao;

import com.xfxg99.core.MyBatisRepository;
import com.xfxg99.sale.model.Inventory;

@MyBatisRepository
public interface InventoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Inventory record);

    int insertSelective(Inventory record);

    Inventory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Inventory record);

    int updateByPrimaryKey(Inventory record);
}