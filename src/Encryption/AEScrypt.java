package Encryption;
import java.io.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;


public class AEScrypt{

	
	/**将二进制转换成16进制 
	 * @param buf 
	 * @return 
	 */  
	public static String parseByte2HexStr(byte buf[]) {  
	        StringBuffer sb = new StringBuffer();  
	        for (int i = 0; i < buf.length; i++) {  
	                String hex = Integer.toHexString(buf[i] & 0xFF);  
	                if (hex.length() == 1) {  
	                        hex = '0' + hex;  
	                }  
	                sb.append(hex.toUpperCase());  
	        }  
	        return sb.toString();  
	}  
	
	/**将16进制转换为二进制 
	 * @param hexStr 
	 * @return 
	 */  
	public static  byte[] parseHexStr2Byte(String hexStr) {  
	        if (hexStr.length() < 1)  
	                return null;  
	        byte[] result = new byte[hexStr.length()/2];  
	        for (int i = 0;i< hexStr.length()/2; i++) {  
	                int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
	                int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
	                result[i] = (byte) (high * 16 + low);  
	        }  
	        return result;  
	}  
	
	
	public static byte[] encrypt(String content, String AES_KEY) {  
		        try {             
		                KeyGenerator kgen = KeyGenerator.getInstance("AES");  
		                kgen.init(128, new SecureRandom(AES_KEY.getBytes()));  
		                SecretKey secretKey = kgen.generateKey();  
		                byte[] enCodeFormat = secretKey.getEncoded();  
		                SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");  
		                Cipher cipher = Cipher.getInstance("AES");// 创建密码器  
		                byte[] byteContent = content.getBytes("utf-8");  
		                cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化  
		                byte[] result = cipher.doFinal(byteContent);  
		                return result; // 加密  
		        } catch (NoSuchAlgorithmException e) {  
		                e.printStackTrace();  
		        } catch (NoSuchPaddingException e) {  
		                e.printStackTrace();  
		        } catch (InvalidKeyException e) {  
		                e.printStackTrace();  
		        } catch (UnsupportedEncodingException e) {  
		                e.printStackTrace();  
		        } catch (IllegalBlockSizeException e) {  
		                e.printStackTrace();  
		        } catch (BadPaddingException e) {  
		                e.printStackTrace();  
		        }  
		        return null;  
		}  

	
	/**解密 
	 * @param content  待解密内容 
	 * @param password 解密密钥 
	 * @return 
	 */  
	public static byte[] decrypt(byte[] content, String AES_KEY) {  
	        try {  
	                 KeyGenerator kgen = KeyGenerator.getInstance("AES");  
	                 kgen.init(128, new SecureRandom(AES_KEY.getBytes()));  
	                 SecretKey secretKey = kgen.generateKey();  
	                 byte[] enCodeFormat = secretKey.getEncoded();  
	                 SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");              
	                 Cipher cipher = Cipher.getInstance("AES");// 创建密码器  
	                 cipher.init(Cipher.DECRYPT_MODE, key);// 初始化  
	                 byte[] result = cipher.doFinal(content);  
	                 return result; // 加密  
	        } catch (NoSuchAlgorithmException e) {  
	                e.printStackTrace();  
	        } catch (NoSuchPaddingException e) {  
	                e.printStackTrace();  
	        } catch (InvalidKeyException e) {  
	                e.printStackTrace();  
	        } catch (IllegalBlockSizeException e) {  
	                e.printStackTrace();  
	        } catch (BadPaddingException e) {  
	                e.printStackTrace();  
	        }  
	        return null;  
	}  
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String content = "123";  
		Pkconsult p = new Pkconsult();
		AEScrypt aescrypt = new AEScrypt();
		String password = p.qume(1000);
		//加密  
		System.out.println("加密前：" + content);  
		byte[] encryptResult = aescrypt.encrypt(content, password);  
		String encryptResultStr = aescrypt.parseByte2HexStr(encryptResult);  
		System.out.println("加密后：" + encryptResultStr);  
		//解密  
		byte[] decryptFrom = aescrypt.parseHexStr2Byte(encryptResultStr);  
		byte[] decryptResult = aescrypt.decrypt(decryptFrom,password);  
		System.out.println("解密后：" + new String(decryptResult, "utf-8"));  

	}

}

