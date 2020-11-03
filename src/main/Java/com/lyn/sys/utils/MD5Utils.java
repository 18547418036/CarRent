package com.lyn.sys.utils;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha1Hash;

/** @date 2020/8/22
 * @description 散列
 * 		该类当前的行为是为数据库中的密码进行md5加密，由于当前项目还未有注册功能，
 * 		所以使用该类来统一数据库中的密码
 * @param
 * @return
 */
public class MD5Utils {

	/**
	 * 对密码加密 md5
	 * @param source  要加密的明文
	 * @param salt  盐
	 * @param hashIterations  散列次数
	 * @return
	 */
	public static String md5(String source, Object salt, Integer hashIterations) {
		return new Md5Hash(source, salt, hashIterations).toString();
	}
	
	
	/**
	 * 对密码加密sha1
	 * @param source  要加密的明文
	 * @param salt  盐
	 * @param hashIterations  散列次数
	 * @return
	 */
	public static String sha1(String source, Object salt, Integer hashIterations) {
		return new Sha1Hash(source, salt, hashIterations).toString();
	}

	public static void main(String[] args) {
		// 明文
		String source="123456";

		/*Md5Hash hash1=new Md5Hash(source);
		System.out.println("使用MD5加密后的结果:"+hash1.toString());*/

		/*Md5Hash hash2=new Md5Hash(source, "武汉");
		System.out.println("使用MD5加密并加盐后的结果:"+hash2.toString());*/

		Md5Hash hash3=new Md5Hash(source, "武汉", 2);
		System.out.println("使用MD5加密加盐并散列两次后的结果:"+hash3.toString());
	}
	
}
