package com.dk.core.common.utils;



/**
 * ticket工具类
 */
public class TicketUtils
{
	public static String createTicket(String id){
		return id.replace("-", "");
	}
	
	public static String createToken(){
		StringBuffer b = new StringBuffer();
		StringBuffer numBuff = new StringBuffer("1,2,3,4,5,6,7,8,9,0");
		String[] numArr = numBuff.toString().split(",");
		java.util.Random r;
		int k ;
		for(int i=0;i<6;i++){			 
			r = new java.util.Random();			 
			k = r.nextInt();			 
			b.append(String.valueOf(numArr[Math.abs(k % 9)]));		
		}
		StringBuffer dbuf = new StringBuffer("a,b,c,d,e,f,g,h,i,g,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z");
		dbuf.append(",A,B,C,D,E,F,G,H,I,G,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z");
		String[] dArr = dbuf.toString().split(",");
		for(int i=0;i<2;i++){			 
			r = new java.util.Random();			 
			k = r.nextInt();			 
			b.append(String.valueOf(dArr[Math.abs(k % 51)]));			
		}
		return b.toString();
	}
}
