package me.jincs.java.ssm.service;

import me.jincs.java.ssm.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseService<T extends BaseMapper<D>,D>{
    @Autowired
    protected BaseMapper<D> baseMapper;
    public int deleteByPrimaryKey(String key){
        return baseMapper.deleteByPrimaryKey(key);
    }

    public int insert(D record){
        return baseMapper.insert(record);
    }

    public int insertSelective(D record){
        return baseMapper.insertSelective(record);
    }

    public D selectByPrimaryKey(String key){
        return baseMapper.selectByPrimaryKey(key);
    }

    public int updateByPrimaryKeySelective(D record){
        return baseMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(D record){
        return baseMapper.updateByPrimaryKey(record);
    }
}
