package hp.dit.hpsssb.aadhaar.com.hpsssb;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by kuush on 5/23/2016.
 */
public class EncryptData {
    private static MessageDigest digester;
    static {
        try {
            digester = MessageDigest.getInstance(EConstants.EnycType);
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    public static String Encrypt_String(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException(EConstants.EnycError);
        }
        digester.update(str.getBytes());
        byte[] hash = digester.digest();
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            if ((0xff & hash[i]) < 0x10) {
                // hexString.append(EConstants.IMEINumber + Integer.toHexString((0xFF & hash[i])));
            } else {
                hexString.append(Integer.toHexString(0xFF & hash[i]));
            }
        }
        return hexString.toString();
    }

    /**
     * Method To Encryption methode for eParivar and Aadhaar
     */
    public String Encryption(String values)
    {
        String encryptedString=null;
        byte[] plainText = null;
        byte[] base64_Previous = null;
        try{
             plainText = values.getBytes("UTF-16LE");  //UTF-16LE
            String base64 = Base64.encodeToString(plainText, Base64.DEFAULT);
            base64_Previous = base64.getBytes("UTF-16LE");
            encryptedString = Base64.encodeToString(base64_Previous, Base64.DEFAULT);
          //  encryptedString = Base64.encodeToString(Base64.encode(plainText,Base64.DEFAULT));
          //  BASE64Encoder base64encoder = new BASE64Encoder();
            //encryptedString = base64encoder.encode(base64encoder.encode(plainText).getBytes("UTF-16LE"));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedString;

    }

    /**
     * Method To Decryption methode for eParivar and Aadhaar
     */
   /* public String Decryption(String Encryptedvalue)
    {
        String decryptedText=null;
        try {
            BASE64Decoder base64decoder = new BASE64Decoder();
            byte[] encryptedText = base64decoder.decodeBuffer(bytes2String(base64decoder.decodeBuffer(Encryptedvalue)));
            decryptedText= bytes2String(encryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedText;
    }*/
}
