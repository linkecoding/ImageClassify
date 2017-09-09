package cn.codekong.dao;

import java.util.List;

import cn.codekong.bean.Constant;

public interface ConstantDao {
	boolean updateValueOfKey(Constant constant);
	Constant getConstantByKey(String key);
	List<Constant> getConstantByKey();
	List<Constant> getConstantConfig();
}
