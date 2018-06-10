package Encryption;

import java.io.BufferedReader;  
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;  
import java.io.FileWriter;  
import java.io.IOException;  
import java.security.InvalidKeyException;  
import java.security.KeyFactory;  
import java.security.KeyPair;  
import java.security.KeyPairGenerator;  
import java.security.NoSuchAlgorithmException;  
import java.security.SecureRandom;  
  
import java.security.interfaces.RSAPrivateKey;  
import java.security.interfaces.RSAPublicKey;  
import java.security.spec.InvalidKeySpecException;  
import java.security.spec.PKCS8EncodedKeySpec;  
import java.security.spec.X509EncodedKeySpec;  
  
import javax.crypto.BadPaddingException;  
import javax.crypto.Cipher;  
import javax.crypto.IllegalBlockSizeException;  
import javax.crypto.NoSuchPaddingException;  
  

public class RSAcrypt {  
  
   public int returnActualLength(byte[] data) { 
	      int i = 0; 
	      for (; i < data.length; i++) { 
	    	  if (data[i] == '\0') 
	              break; 
	        } 
	        return i; 
	    }

    /** 
     * ���������Կ�� 
     */  
    public static void genKeyPair(String filePath, String account) {  
        // KeyPairGenerator���������ɹ�Կ��˽Կ�ԣ�����RSA�㷨���ɶ���  
        KeyPairGenerator keyPairGen = null;  
        try {  
            keyPairGen = KeyPairGenerator.getInstance("RSA");  
        } catch (NoSuchAlgorithmException e) {  
             
            e.printStackTrace();  
        }  
        // ��ʼ����Կ������������Կ��СΪ96-1024λ  
        keyPairGen.initialize(1024,new SecureRandom());  
        // ����һ����Կ�ԣ�������keyPair��  
        KeyPair keyPair = keyPairGen.generateKeyPair();  
        // �õ�˽Կ  
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();  
        // �õ���Կ  
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  
        try {  
            // �õ���Կ�ַ���  
            String publicKeyString = Base64.encode(publicKey.getEncoded());  
            // �õ�˽Կ�ַ���  
            String privateKeyString = Base64.encode(privateKey.getEncoded());  
            // ����Կ��д�뵽�ļ�  
            
            File WritePath = new File(filePath + "\\");
            File WritePath_account = new File(filePath + "\\" + account);
		    File WriteName_Psk = new File(filePath + "\\" + account + "\\" + "privateKey.keystore");
		    File WriteName_Pbk = new File(filePath + "\\" + account + "\\" + "publicKey.keystore");		    
			if(!WritePath.exists())
			{
				WritePath.mkdirs();
				WritePath_account.mkdirs();
		    	WriteName_Pbk.createNewFile();
		    	WriteName_Psk.createNewFile();    
			}
			else if(!WritePath_account.exists())
			{
				WritePath_account.mkdirs();
		    	WriteName_Pbk.createNewFile();
		    	WriteName_Psk.createNewFile();  
			}
			
            FileWriter pubfw = new FileWriter(filePath + "\\"+  account + "/publicKey.keystore");  
            FileWriter prifw = new FileWriter(filePath + "\\"+  account + "/privateKey.keystore");  
            BufferedWriter pubbw = new BufferedWriter(pubfw);  
            BufferedWriter pribw = new BufferedWriter(prifw);  
            pubbw.write(publicKeyString);  
            pribw.write(privateKeyString);  
            pubbw.flush();  
            pubbw.close();  
            pubfw.close();  
            pribw.flush();  
            pribw.close();  
            prifw.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    /** 
     * ���ļ����������м��ع�Կ 
     *  
     * @param in 
     *            ��Կ������ 
     * @throws Exception 
     *             ���ع�Կʱ�������쳣 
     */  
    public static String loadPublicKeyByFile(String path) throws Exception {  
        try {  
            BufferedReader br = new BufferedReader(new FileReader(path  
                    + "/publicKey.keystore"));  
            String readLine = null;  
            StringBuilder sb = new StringBuilder();  
            while ((readLine = br.readLine()) != null) {  
                sb.append(readLine);  
            }  
            br.close();  
            return sb.toString();  
        } catch (IOException e) {  
            throw new Exception("��Կ��������ȡ����");  
        } catch (NullPointerException e) {  
            throw new Exception("��Կ������Ϊ��");  
        }  
    }  
  
    /** 
     * ���ַ����м��ع�Կ 
     *  
     * @param publicKeyStr 
     *            ��Կ�����ַ��� 
     * @throws Exception 
     *             ���ع�Կʱ�������쳣 
     */  
    public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr)  
            throws Exception {  
        try {  
            byte[] buffer = Base64.decode(publicKeyStr);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);  
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("�޴��㷨");  
        } catch (InvalidKeySpecException e) {  
            throw new Exception("��Կ�Ƿ�");  
        } catch (NullPointerException e) {  
            throw new Exception("��Կ����Ϊ��");  
        }  
    }  
    
    /** 
     * ���ļ��м���˽Կ 
     *  
     * @param keyFileName 
     *            ˽Կ�ļ��� 
     * @return �Ƿ�ɹ� 
     * @throws Exception 
     */  
    public static String loadPrivateKeyByFile(String path) throws Exception {  
        try {  
            BufferedReader br = new BufferedReader(new FileReader(path  
                    + "/privateKey.keystore"));  
            String readLine = null;  
            StringBuilder sb = new StringBuilder();  
            while ((readLine = br.readLine()) != null) {  
                sb.append(readLine);  
            }  
            br.close();  
            return sb.toString();  
        } catch (IOException e) {  
            throw new Exception("˽Կ���ݶ�ȡ����");  
        } catch (NullPointerException e) {  
            throw new Exception("˽Կ������Ϊ��");  
        }  
       
    }  
  
    public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr)  
            throws Exception {  
        try {  
            byte[] buffer = Base64.decode(privateKeyStr);  
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("�޴��㷨");  
        } catch (InvalidKeySpecException e) {  
            throw new Exception("˽Կ�Ƿ�");  
        } catch (NullPointerException e) {  
            throw new Exception("˽Կ����Ϊ��");  
        }  
    }  
    
    /** 
     * ��Կ���ܹ��� 
     *  
     * @param publicKey 
     *            ��Կ 
     * @param plainTextData 
     *            �������� 
     * @return 
     * @throws Exception 
     *             ���ܹ����е��쳣��Ϣ 
     */  
    public static byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData)  
            throws Exception {  
        if (publicKey == null) {  
            throw new Exception("���ܹ�ԿΪ��, ������");  
        }  
        Cipher cipher = null;  
        try {  
            // ʹ��Ĭ��RSA  
            cipher = Cipher.getInstance("RSA");  
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());  
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
            
            byte[] output = cipher.doFinal(plainTextData);  
            return output;  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("�޴˼����㷨");  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
            return null;  
        } catch (InvalidKeyException e) {  
            throw new Exception("���ܹ�Կ�Ƿ�,����");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("���ĳ��ȷǷ�");  
        } catch (BadPaddingException e) {  
            throw new Exception("������������");  
        }  
    }  
    /** 
     * ˽Կ���ܹ��� 
     *  
     * @param privateKey 
     *            ˽Կ 
     * @param plainTextData 
     *            �������� 
     * @return 
     * @throws Exception 
     *             ���ܹ����е��쳣��Ϣ 
     */  
    public static byte[] encrypt(RSAPrivateKey privateKey, byte[] plainTextData)  
            throws Exception {  
        if (privateKey == null) {  
            throw new Exception("����˽ԿΪ��, ������");  
        }  
        Cipher cipher = null;  
        try {  
            // ʹ��Ĭ��RSA  
            cipher = Cipher.getInstance("RSA");  
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);  
            byte[] output = cipher.doFinal(plainTextData);  
            return output;  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("�޴˼����㷨");  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
            return null;  
        } catch (InvalidKeyException e) {  
            throw new Exception("����˽Կ�Ƿ�,����");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("���ĳ��ȷǷ�");  
        } catch (BadPaddingException e) {  
            throw new Exception("������������");  
        }  
    }  
    /** 
     * ˽Կ���ܹ��� 
     *  
     * @param privateKey 
     *            ˽Կ 
     * @param cipherData 
     *            �������� 
     * @return ���� 
     * @throws Exception 
     *             ���ܹ����е��쳣��Ϣ 
     */  
    public static byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData)  
            throws Exception {  
        if (privateKey == null) {  
            throw new Exception("����˽ԿΪ��, ������");  
        }  
        Cipher cipher = null;  
        try {  
            // ʹ��Ĭ��RSA  
            cipher = Cipher.getInstance("RSA");  
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());  
            cipher.init(Cipher.DECRYPT_MODE, privateKey);  
            byte[] output = cipher.doFinal(cipherData);  
            return output;  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("�޴˽����㷨");  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
            return null;  
        } catch (InvalidKeyException e) {  
            throw new Exception("����˽Կ�Ƿ�,����");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("���ĳ��ȷǷ�");  
        } catch (BadPaddingException e) {  
            throw new Exception("������������");  
        }  
    }  
    /** 
     * ��Կ���ܹ��� 
     *  
     * @param publicKey 
     *            ��Կ 
     * @param cipherData 
     *            �������� 
     * @return ���� 
     * @throws Exception 
     *             ���ܹ����е��쳣��Ϣ 
     */  
    public byte[] decrypt(RSAPublicKey publicKey, byte[] cipherData)  
            throws Exception {  
        if (publicKey == null) {  
            throw new Exception("���ܹ�ԿΪ��, ������");  
        }  
        Cipher cipher = null;  
        try {  
            // ʹ��Ĭ��RSA  
            cipher = Cipher.getInstance("RSA");  
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());  
            cipher.init(Cipher.DECRYPT_MODE, publicKey);  
            byte[] output = cipher.doFinal(cipherData);  
            return output;  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("�޴˽����㷨");  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
            return null;  
        } catch (InvalidKeyException e) {  
            throw new Exception("���ܹ�Կ�Ƿ�,����");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("���ĳ��ȷǷ�");  
        } catch (BadPaddingException e) {  
            throw new Exception("������������");  
        }  
    }  

    public static void main(String[] args) throws Exception {  
        String filepath = "E:/tmp/";  
        String account = "yyb";
        AEScrypt aescrypt = new AEScrypt();
        RSAcrypt rsacrypt = new RSAcrypt();
        RSAcrypt.genKeyPair(filepath, account);  
        
 /*         
        System.out.println("--------------��Կ����˽Կ���ܹ���-------------------");  
        String plainText = "1213";  
        //��Կ���ܹ���  
        byte[] cipherData = RSAcrypt.encrypt(RSAcrypt.loadPublicKeyByStr(RSAcrypt.loadPublicKeyByFile(filepath)),plainText.getBytes());  
        String cipher = aescrypt.parseByte2HexStr(cipherData);  
        //˽Կ���ܹ���  
        byte[] res = RSAcrypt.decrypt(RSAcrypt.loadPrivateKeyByStr(RSAcrypt.loadPrivateKeyByFile(filepath)), aescrypt.parseHexStr2Byte(cipher));  
        String restr = new String(res);  
        System.out.println("ԭ�ģ�"+plainText);  
        System.out.println("���ܣ�"+cipher);  
        System.out.println("���ܣ�"+restr);  
        System.out.println();  
          
        System.out.println("--------------˽Կ���ܹ�Կ���ܹ���-------------------");     
        plainText = "5193854050481778699056278127671370294715826645471973602301919805202452165651";  
        //˽Կ���ܹ���  
        cipherData = RSAcrypt.encrypt(RSAcrypt.loadPrivateKeyByStr(RSAcrypt.loadPrivateKeyByFile(filepath)),plainText.getBytes());  
        cipher = Base64.encode(cipherData);  
        //��Կ���ܹ���  
        res = rsacrypt.decrypt(RSAcrypt.loadPublicKeyByStr(RSAcrypt.loadPublicKeyByFile(filepath)), Base64.decode(cipher));  
        restr = new String(res);  
        System.out.println("ԭ�ģ�"+plainText);  
        System.out.println("���ܣ�"+cipher);  
        System.out.println("���ܣ�"+restr);  
        System.out.println();*/
        }

}
