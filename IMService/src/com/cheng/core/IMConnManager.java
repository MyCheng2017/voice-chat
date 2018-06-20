package com.cheng.core;

import java.math.BigInteger;
import java.util.HashMap;

import com.cheng.domain.User;

public class IMConnManager {
	public static HashMap<Long, IMConnection> conns = new HashMap<Long, IMConnection>();
	

	public static void put(User u, IMConnection conn) {
		BigInteger id=u.getId();
		if(id==null)return;
		long account=id.longValue();
		System.out.println("====�˺�" + account + "������");
		remove(u);
		conns.put(account, conn);
		
	}

	public static void remove(User u) {
		BigInteger id=u.getId();
		if(id==null)return;
		long account=id.longValue();
		if (conns.containsKey(account)) {
			System.out.println("====�˺�" + account + "������");
			conns.remove(account);
			
		}
	}

	public static IMConnection get(User u) {
		BigInteger id=u.getId();
		if(id==null)return null;
		long account=id.longValue();
		if (conns.containsKey(account)) {
			return conns.get(account);
		}
		return null;
	}
	
}
