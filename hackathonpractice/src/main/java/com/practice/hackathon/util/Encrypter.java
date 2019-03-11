package com.practice.hackathon.util;


import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.util.Base64;


public class Encrypter {
	
	private String transformation = "AES/CBC/PKCS5Padding";
	private String secretKeyAlgrthm = "AES";
	private byte [] keyVal;
	
	public Encrypter(String key){
		this.keyVal = key.getBytes();
	}
	
	Cipher initCipher(int mode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException{
		Cipher c = Cipher.getInstance(transformation);
		Key cipherKey = new SecretKeySpec(keyVal,secretKeyAlgrthm);
		AlgorithmParameterSpec algorithmParameterSpec = getAlgorithmParameterSpec(c);
		c.init(mode, cipherKey, algorithmParameterSpec);
		return c;
	}
	
	String doEncrypt(Cipher cipher,String data) throws IllegalBlockSizeException, BadPaddingException{
		byte[] encVal = cipher.doFinal(data.getBytes());
		return new String(Base64.getEncoder().encode(encVal));
	}
	
	String doDecrypt(Cipher cipher,String data) throws IllegalBlockSizeException, BadPaddingException{
		byte[] decVal = cipher.doFinal(Base64.getDecoder().decode(data));
		return new String(decVal);
	}
	
	
	 int getCipherBlockSize(Cipher cipher) {
	        return cipher.getBlockSize();
	    }

	    private AlgorithmParameterSpec getAlgorithmParameterSpec(Cipher cipher) {
	        byte[] iv = new byte[getCipherBlockSize(cipher)];
	        return new IvParameterSpec(iv);
	    }
	
	

}
