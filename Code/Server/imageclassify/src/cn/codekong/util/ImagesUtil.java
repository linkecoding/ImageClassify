package cn.codekong.util;

import java.util.ArrayList;
import java.util.List;

import cn.codekong.bean.label.ImageTask;
import cn.codekong.bean.label.Images;
import cn.codekong.bean.label.Label;  

public class ImagesUtil {
	/**
	 * 封装ImageTask对象
	 * @param task_id
	 * @param images
	 * @return
	 */
	public ImageTask getImagesTaskJson(int task_id,List<Images> images){
		 
		 ImageTask imageTask = new ImageTask();
		 imageTask.setTask_id(task_id+"");
		 imageTask.setImages(images); 
		 return imageTask;
	}
  
	/**
	 * 封装Images对象
	 * @param img_id
	 * @param img_path
	 * @param labels
	 * @return
	 */
	public Images getImages(String img_id,String img_path,List<Label> machine_labels,List<Label> manual_labels,List<Label> option_labels){
		 Images images = new Images();
		 images.setImg_id(img_id);
		 images.setImg_path(img_path); 
			 images.setMachine_labels(machine_labels);  
			 images.setManual_labels(manual_labels);  
			 images.setOption_labels(option_labels); 
		 return images;
	}
	
	public List<Label> getLabels(String img_machine_tag_label){
		
		List<Label> labels = new ArrayList<Label>(); 
		String []arr = img_machine_tag_label.split("\\;"); 
		for (int i = 0; i < arr.length; i++) {
				 Label label = new Label();
				 String []array =  arr[i].split("\\|");
				 label.setLabel_name(array[1]);
				 labels.add(label);
			}
		return labels;
	}
	
	/**
	 * 将labelName转化为Label类型的列表
	 * @param allLabels
	 * @return
	 */
	public List<Label> getUserLabels(String labelName){
		
		List<Label> labels = new ArrayList<Label>(); 
		String []arr = labelName.split("\\;"); 
		for (int i = 0; i < arr.length; i++) {
				 Label label = new Label(); 
				 if (arr[i]!=null && !arr[i].equals(" ")&& !arr[i].equals("null")) {
					 label.setLabel_name(arr[i]);
					 labels.add(label);
				} 
			}
		return labels;
	}
	
	/**
	 * 
	 * @param labelName
	 * @return
	 */
		public List<String> getExportLabels(String labelName){
		
		List<String> labels = new ArrayList<String>(); 
		if (labelName!=null && !labelName.equals("")) {
			String [] arr = labelName.split(";");
			for (int i = 0; i < arr.length; i++) { 
				 if (arr[i]!=null && !arr[i].equals(" ")&& !arr[i].equals("null")) { 
					 labels.add(arr[i]);
				} 
			}
		} 
		return labels;
	}
	/**
	 * 将list类型的转化为String字符串，且以“;”隔开
	 * @param list
	 * @return
	 */
	public String getString(List<String> list){
		
		String label_name = "";
		for (int i = 0; i < list.size(); i++) {
			label_name +=list.get(i)+";";
		}
		return label_name;
	} 
	//23|门;17|店铺;15|男孩;13|街道;
public List<String> getLabelName(String img_machine_tag_label){
	List<String> labels = new ArrayList<String>(); 
		if (!img_machine_tag_label.isEmpty()) {
			
			String []arr = img_machine_tag_label.split("\\;"); 
			for (int i = 0; i < arr.length; i++) {
					 String []array =  arr[i].split("\\|"); 
					 labels.add(array[1]);
				}
			return labels;
		}else{
		return labels;
		}
	}
}
