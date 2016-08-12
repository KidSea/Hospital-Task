package com.example.sql;

import com.example.bdevice.BdeviceModel;
import com.example.host.HostModel;

public class AddSqlData {

	String addres;
	int type;
	
	int hos_count ;
	public int getHos_count() {
		return hos_count;
	}

	public void setHos_count(int hos_count) {
		this.hos_count = hos_count;
	}

	public int getBd_count() {
		return bd_count;
	}

	public void setBd_count(int bd_count) {
		this.bd_count = bd_count;
	}

	int bd_count;
	// 构造函数,用来传数据
	public AddSqlData(int type, String res) {
		// TODO Auto-generated constructor stub
		addres = res;
		this.type = type;

	}

	public void addSqlData() {
		// TODO Auto-generated method stub

		if (type == 1) {
			//序号增加
		   // hos_count++;
		    //希望添加
		     HostModel hostModel = new HostModel();
		     String str = "Z" +hos_count;
		     String sql="insert into hosadd values(?,?,?,?)";
		     String paras[]={str, addres, "host", "0"};
		     if(hostModel.updHost(sql, paras)){
		    	 
		    	 System.out.println("添加成功");
		    	 return;
		     }
		} else if (type == 2) {
			//序号增加
			   // hos_count++;
			    //希望添加
			     BdeviceModel bdeviceModel = new BdeviceModel();
			     String str = "C" +bd_count;
			     String sql="insert into bdadd values(?,?,?,?)";
			     String paras[]={str, addres, "bd", "0"};
			     if(bdeviceModel.updBdevice(sql, paras)){
			    	 
			    	 System.out.println("添加成功");
			    	 return;
		}

	}
	}
}
