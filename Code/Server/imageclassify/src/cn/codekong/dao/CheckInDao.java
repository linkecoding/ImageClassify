package cn.codekong.dao;

import java.util.List;

import cn.codekong.bean.CheckIn;

public interface CheckInDao {
   public boolean saveCheckIn(CheckIn checkIn);
   public List<CheckIn> getCheckIns(int user_id);
}
