package cn.codekong.util;

import java.util.List;

public class ListToString {

	public String getListToString(List<Integer> obj){
		
		StringBuilder stringBuilder = new StringBuilder();
		
		if (obj.size()!=0) {
			stringBuilder.append("(");
			for (int i = 0; i < obj.size()-1; i++) {
				stringBuilder.append(obj.get(i));
				stringBuilder.append(",");
			}
			stringBuilder.append(obj.get(obj.size()-1));
			stringBuilder.append(")");
			return stringBuilder.toString();
		}else {
			return "()";
		}
		
	}
}
