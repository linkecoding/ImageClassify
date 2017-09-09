package cn.codekong.dao;

import java.util.List;

import cn.codekong.bean.Zip;

public interface ZipDao {

	boolean saveZip(Zip zip);
	List<Zip> showZip(int start,int num);
	long showZip();
	//List<Zip> showZip();
	List<Zip> showZipAndUnClassifyFolder();
	Zip findZipById(int zip_id);
	boolean updateZip(Zip zip);
}
