package cn.codekong.bean;

import java.util.Date;

public class ExportRecord {
	private int export_id;
	private Date export_time;
	
	public int getExport_id() {
		return export_id;
	}
	public void setExport_id(int export_id) {
		this.export_id = export_id;
	}
	public Date getExport_time() {
		return export_time;
	}
	public void setExport_time(Date export_time) {
		this.export_time = export_time;
	}
	
}
