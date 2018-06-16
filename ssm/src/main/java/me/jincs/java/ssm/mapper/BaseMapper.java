package me.jincs.java.ssm.mapper;

public  interface BaseMapper<D> {
    int deleteByPrimaryKey(String key);

    int insert(D record);

    int insertSelective(D record);

    D selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(D record);

    int updateByPrimaryKey(D record);
}
