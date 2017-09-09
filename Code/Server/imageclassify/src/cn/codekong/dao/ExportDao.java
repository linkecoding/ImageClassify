package cn.codekong.dao;

import java.util.List;

import cn.codekong.bean.ExportRecord;
import cn.codekong.bean.ExportTag;
import cn.codekong.bean.label.ExportHistoryLabel; 

public interface ExportDao {

	int saveExportRecord(ExportRecord exportTag);
	boolean save(ExportTag exportTag);
	int updateExport(int img_id);
	List<ExportHistoryLabel> gExportHistoryLabels(int start,int page_num);
	long exportHistoryLabelsNum();

}
