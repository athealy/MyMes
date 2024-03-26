package com.jacksoft.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 3dec加密类
 * @author 许华兴
 * @since 2018/10/24
 */
public class Desde {

	private static final Log log = LogFactory.getLog(Desde.class);
	private static final String Algorithm 		= 	"DESede";
	//private static final String IV				=	"1qaz2wsx";
	private static final String KEYSTR			=	"jackey";
	private	static final String ECB				=	"DESede/ECB/NoPadding";
	//private	static final String ECB			=	"DESede/ECB/PKCS5Padding";
	private static final String CBC				=	"DESede/CBC/NoPadding";
	private static final String ENCRYPT_MODE	=	"1";
	private static SecretKey deskey;
			
	/**
	 * ECB加解密
	 * @param data
	 * @return
	 */
	private static byte[] ecbCrypt(byte[] data,String mode){
		try {
			Cipher cipher	=	Cipher.getInstance(ECB);
			if(null==deskey) deskey	= initKey();	
			if(ENCRYPT_MODE.equals(mode)){
				cipher.init(Cipher.ENCRYPT_MODE, deskey);
			}
			else{
				cipher.init(Cipher.DECRYPT_MODE, deskey);
			}
			return cipher.doFinal(data);
		} catch (Exception e) {
			log.error("加密操作异常 ：",e);
			return null;
		}
	}
	
	/**
	 * CBC加解密
	 * @param data
	 * @return
	 */
	private static byte[] cbcCrypt(byte[] data, String mode){
		try {
			Cipher cipher	=	Cipher.getInstance(CBC);
			//byte[] ivBytes = IV.getBytes();
			//byte[] ivBytes = UUID.randomUUID().toString().substring(0,8).getBytes();
			IvParameterSpec ips = new IvParameterSpec("1qaz2wsx".getBytes());
			if(null==deskey) deskey	= initKey();
			if(ENCRYPT_MODE.equals(mode)){
				cipher.init(Cipher.ENCRYPT_MODE, deskey,ips);
			}
			else{
				cipher.init(Cipher.DECRYPT_MODE, deskey,ips);
			}
			return cipher.doFinal(data);
		} catch (Exception e) {
			log.error("加密操作异常 ：",e);
			return null;
		}
	}
		
	/**
	 * ECB加密  16进制密文输出
	 * @param srcData
	 */
	public static String enECB(String srcData){
		String result	=	 null;
		try {
			result	=	byteArray2HexStr(ecbCrypt(padding(srcData.getBytes()), "1"));
		} catch (NullPointerException e) {
			log.error("加密算法出现空指针异常",e);
		}
		return result;
	}
	
	/**
	 * ECB解密   字符串明文输出
	 * @param cryptData
	 */
	public static String deECB(String cryptData){
		try {
			//return new String(dec3Decrypt(hex2Byte(cbcd2string(hex2Byte(cryptData)))));
			return new String(reductive(ecbCrypt(hex2Byte(cryptData),"2")));
		} catch (Exception e) {
			log.error("",e);
			return null;
		}
	}
	
	/**
	 * CBC加密  16进制密文输出
	 * @param srcData
	 * @return
	 */
	public static String enCBC(String srcData){
		String result	=	null;
		try {
			result	=	byteArray2HexStr(cbcCrypt(padding(srcData.getBytes()), "1"));
		} catch (NullPointerException e) {
			log.error("加密算法出现空指针异常",e);
		}
		return result;
	}
	
	/**
	 * CBC解密  字符串明文输出
	 * @param cryptData
	 * @return
	 */
	public static String deCBC(String cryptData){
		try {
			return new String(reductive(cbcCrypt(hex2Byte(cryptData),"2")));
		} catch (Exception e) {
			log.error("",e);
			return null;
		}
	}

	/**
	 * 字符串BCD压缩
	 * @return
	 */
	/*private byte[] str2cbcd(String s) {
		int len = (s.length() / 2);   
	    byte[] result = new byte[len];   
	    char[] achar = s.toCharArray();   
	    for (int i = 0; i < len; i++) {   
	     int pos = i * 2;   
	     result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));   
	    }   
	    return result; 
	} */
	
	private static byte toByte(char c) {   
	    byte b = (byte) "0123456789ABCDEF".indexOf(c);   
	    return b;   
	}  
	
	/**
	 * BCD还原字符串
	 * @param b
	 * @return
	 */
	private static String cbcd2string(byte[] b) {
		StringBuffer sb = new StringBuffer(b.length);   
	    String sTemp;   
	    for (int i = 0; i < b.length; i++) {   
	     sTemp = Integer.toHexString(0xFF & b[i]);   
	     if (sTemp.length() < 2)   
	      sb.append(0);   
	     sb.append(sTemp);   
	    }   
	    return sb.toString();   
	}
	
	/**
	 * 16进制字符串转字节数组
	 * @param strIn
	 * @return
	 */
	private static byte[] hex2Byte(String strIn) { 
		byte[] arrOut	=	null;
		int l	=	strIn.length()/2;
		if(l*2<strIn.length()) l++;
		String[] temp	=	new String[l];
		int j	=	0;
		for(int k=0;k<strIn.length();k++){
			if(k%2==0){//每隔两个
				temp[j]=""+strIn.charAt(k);
			}else{
				temp[j]=temp[j]+strIn.charAt(k);
				j++;
			}  
		}
		if(temp.length>0){
			arrOut		=	new byte[temp.length];
			for(int i=0;i<temp.length;i++){
				arrOut[i]=(byte)Integer.parseInt(temp[i], 16);
			}
		}
		return arrOut;
	}
	
	/**
	 * 字节数组转16进制字符串
	 * @param arrB
	 * @return
	 */
	private static String byteArray2HexStr(byte[] arrB) {
        int iLen = arrB.length;  
        StringBuffer sb = new StringBuffer(iLen * 2); 
        for (int i = 0; i < iLen; i++) {  
            int intTmp = arrB[i];  
            while (intTmp < 0) {
                intTmp = intTmp + 256;  
            }  
            if (intTmp < 16) {
                sb.append("0");  
            }  
            sb.append(Integer.toString(intTmp, 16));  
        }  
        return sb.toString();  
    }
	
	/**
	 * 补全数组
	 * @param source
	 * @return
	 */
	private static byte[] padding(byte[] source){
		if( source.length%8 != 0 ){
			int rem = 8-(source.length%8);
			byte[] result	=	new byte[source.length+rem];
			for(int i=0;i<result.length;i++){
				if(i>(source.length-1)){
					result[i]=(byte)0;
				}
				else{
					result[i]=source[i];
				}
			}
			return result;
		}
		else{
			return source;
		}
	}
	
	/**
	 * 还原数组
	 * @param source
	 * @return
	 */
	private static byte[] reductive(byte[] source){
		int rem = 0;
		for(int i=source.length-1; i>=0; i--){
			if(source[i]!=(byte)0){
				rem = i;
				break;
			}
		}
		if(rem == 0 || rem == source.length-1){
			return source;
		}
		else{
			byte[] result	=	new byte[rem+1];
			for(int i=0;i<rem+1;i++){
				result[i] = source[i];
			}
			return result;
		}
	}
	
	/**
	 * 初始化key
	 * @return
	 */
	private static SecretKey initKey(){
		SecretKey deskey;
		byte[] keyBytes = KEYSTR.getBytes();			
		byte[] tmpKey = new byte[24];
		if (tmpKey.length > keyBytes.length) {
			int b 	= tmpKey.length/keyBytes.length;
			int rem = tmpKey.length%keyBytes.length;
			if(b>0){
				for(int i=0;i<b;i++){
					System.arraycopy(keyBytes, 0, tmpKey, i*keyBytes.length, keyBytes.length);
				}
				if(rem>0){
					System.arraycopy(keyBytes,0,tmpKey,b*keyBytes.length,rem);
	            }
			}
        }
		else{
			System.arraycopy(keyBytes, 0, tmpKey, 0, tmpKey.length);
		}			
		deskey = new SecretKeySpec(tmpKey, Algorithm);
		return deskey;
	}
	
}
