package cn.codekong.bean.label;

import java.util.Date;

public class ExportHistoryLabel {

	private int export_id;
	private Date export_time;
	private int amount;
	
	public ExportHistoryLabel(){}
	public ExportHistoryLabel(int export_id,Date export_time,int amount){
		this.export_id = export_id;
		this.export_time = export_time;
		this.amount = amount;
	} 
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
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	} 
}
