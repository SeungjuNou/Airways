package com.cafe24.khteam1.adminCommon.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cafe24.khteam1.common.dao.AbstractDAO;
 
@Repository
public class AdminDAO extends AbstractDAO{


    @SuppressWarnings("unchecked")
    public Map<String, Object> selectToday(Map<String, Object> map) {
        return (Map<String, Object>) selectOne("adminSales.selectToday", map);
    }
    
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> selectWeek(Map<String, Object> map) {
        return selectList("adminSales.selectWeek", map);
    }
    
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> selectMonth(Map<String, Object> map) {
        return selectList("adminSales.selectMonth", map);
    }
    
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> selectMoney(Map<String, Object> map) {
        return selectList("adminSales.selectMoney", map);
    }
    
    @SuppressWarnings("unchecked")
    public Map<String, Object> selectMen(Map<String, Object> map) {
        return (Map<String, Object>) selectOne("adminSales.selectMen", map);
    }
    
    @SuppressWarnings("unchecked")
    public Map<String, Object> selectWomen(Map<String, Object> map) {
        return (Map<String, Object>) selectOne("adminSales.selectWomen", map);
    }
    
    @SuppressWarnings("unchecked")
    public Map<String, Object> seatCount(Map<String, Object> map) {
        return (Map<String, Object>) selectOne("adminSales.seatCount", map);
    }
    
    @SuppressWarnings("unchecked")
    public Map<String, Object> seatCountCheck(Map<String, Object> map) {
        return (Map<String, Object>) selectOne("adminSales.seatCountCheck", map);
    }
}

 