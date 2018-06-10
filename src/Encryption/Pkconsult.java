package Encryption;

import java.math.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.Random;

public class Pkconsult {

	public static final BigInteger P = new BigInteger("13157081893224844969");
	public static final BigInteger g = new BigInteger("2");
	
	public static int random_consult(){
		int ran = (int)(Math.random() * 9000) + 1000;
		return ran;
	}
	
	public static BigInteger BigSushuGen(){
	    Random rnd = new Random(new Date().getTime());
		return BigInteger.probablePrime(64, rnd);
	}
	
	public static BigInteger InttoBigInteger(int a){
		
		String s =  a + "";
		BigInteger A = new BigInteger(s);
		return A;
	}	
	
	public static String qume(int a){
        BigInteger GA = g.pow(a);
        BigInteger PA = GA.remainder(P);
        String String_PA = PA.toString();
        return String_PA;
	}
	
	public static String qume2(int a,String String_GB){
		 BigInteger GB = new BigInteger(String_GB);
		 BigInteger GA_B = GB.pow(a);
		 BigInteger PA_B = GA_B.remainder(P);
		 String String_PA_B = PA_B.toString();
		 return String_PA_B;
	}
	
	public static String consult_encrypt(String String_GA, int b, String filepath) throws Exception{
		
		Pkconsult pkconsult = new Pkconsult();
		BigInteger GA = new BigInteger(String_GA);
		String String_GB = pkconsult.qume(b);
		BigInteger GAB = (GA.pow(b));
	    BigInteger PA = GAB.remainder(P);
	    String String_GAB = PA.toString();
	        
		AEScrypt aescrypt = new AEScrypt();
		String String_GA_B = String_GA + "," + String_GB;
		
		byte[] cipherData = RSAcrypt.encrypt(RSAcrypt.loadPrivateKeyByStr(RSAcrypt.loadPrivateKeyByFile(filepath)),String_GA_B.getBytes());  
	    String cipher = aescrypt.parseByte2HexStr(cipherData); 
	    System.out.println(cipher);
        byte[] encryptResult = aescrypt.encrypt(cipher, String_GAB);
        String encryptResultStr = aescrypt.parseByte2HexStr(encryptResult);
		return encryptResultStr;
	}
	
	public static String consult_decrypt(String C_encrypt, String String_GB, int a, String filepath) throws Exception
	{
		RSAcrypt rsacrypt = new RSAcrypt();
		AEScrypt aescrypt1 = new AEScrypt();
		BigInteger GB = new BigInteger(String_GB);
		BigInteger GAB = (GB.pow(a));
	    BigInteger PA = GAB.remainder(P);
	    String String_GAB = PA.toString();
		byte[] decryptFrom = aescrypt1.parseHexStr2Byte(C_encrypt); 
		System.out.println(C_encrypt);
		byte[] aesdecryptResult = aescrypt1.decrypt(decryptFrom,String_GAB); 
		
		String St_aesdecryptResult = new String(aesdecryptResult, "utf-8");
		System.out.println(St_aesdecryptResult);
		byte[] aesdecryptForm = aescrypt1.parseHexStr2Byte(St_aesdecryptResult);
		
		byte[] rsadecryptResult = rsacrypt.decrypt(RSAcrypt.loadPublicKeyByStr(RSAcrypt.loadPublicKeyByFile(filepath)), aesdecryptForm);
	    String S = new String(rsadecryptResult, "utf-8");
		return S;
	}
	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
 /*       String filepath = "E:/tmp/";  
        int a = 1000;
		Pkconsult pkconsult = new Pkconsult(); 
	    String String_GA = pkconsult.qume(a);
	    String String_GB = pkconsult.qume(400);
	    System.out.println(String_GA);
	    System.out.println(String_GB);
	    RSAPublicKey Pbk = RSAcrypt.loadPublicKeyByStr(RSAcrypt.loadPublicKeyByFile(filepath));
		String S = pkconsult.consult_encrypt(String_GA, 400, filepath);
		System.out.println(S);
	    String Ss = pkconsult.consult_decrypt(S, String_GB, a, Pbk);
	    System.out.println(Ss);*/
		
//		BigInteger SS = BigSushuGen();
	//	System.out.println(SS);
		Pkconsult pkconsult = new Pkconsult(); 
		AEScrypt aescrypt = new AEScrypt();
		String S = "诉讼监督手段vu哦背后的覅哦我黑i发货是倒数第哦还是短发速度和防守对方送i号都发生的积分速度个粉丝啊否狗都不不死u的风格元素过得不好速度v啊岁的范围更富威认为日胡文海覅u我还i哦互动覅uhowe u哦和服务额哦回复我的愤怒我";
		System.out.println("原文：" + S);
		String hash = Hash.hex_sha1(S);
		String Pk = pkconsult.qume(1000*400); 
		byte[] encryptResult = aescrypt.encrypt(S, Pk);  
		String encryptResultStr = aescrypt.parseByte2HexStr(encryptResult);  
		System.out.println("加密后：" + encryptResultStr); 
		System.out.println("加密前hash值：" + hash);
		
		byte[] decryptFrom = aescrypt.parseHexStr2Byte(encryptResultStr);  
		byte[] decryptResult = aescrypt.decrypt(decryptFrom, Pk); 
		String d_S = new String(decryptResult, "utf-8");
		String d_hash = Hash.hex_sha1(d_S);
		System.out.println("解密后：" + d_S); 
		System.out.println("解密后hash值：" + d_hash);
		
	}
	

}
