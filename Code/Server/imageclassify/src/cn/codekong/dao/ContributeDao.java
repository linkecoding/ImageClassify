package cn.codekong.dao;

import java.util.List;

import cn.codekong.bean.Contribute_Img;
import cn.codekong.bean.label.ContributeImgListOfAll;

public interface ContributeDao {
	public boolean saveContribute(Contribute_Img cImg);
	public List<Contribute_Img> getContributeBySelf(int user_id,int start,int page_num);
	public List<ContributeImgListOfAll> getContributeOfAllByUser(int start,int page_num);
	public Contribute_Img getContributeImgById(int id);
	public int getAmountContributeOfAllByUser();
	public boolean updateZipUploadByUser(int id,int status);
}

