package Inquirer.Util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class UtilHash {

    public static String getHash(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {


        // Солим. Считаем хэш.
        password += "+SALT";
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.reset();
        byte[] hashb = digest.digest(password.getBytes("UTF-8"));


        // Преобразуем byte[] в String
        String ret = "";
        int i = 0;
        while (i < hashb.length) {
            char hex[] = {'0', '1', '2', '3', '4', '5',
                    '6', '7', '8', '9', 'A', 'B',
                    'C', 'D', 'E', 'F'};
            ret = ret + String.valueOf(hex[(hashb[i] & 0xF0) >> 4]);
            ret = ret + String.valueOf(hex[hashb[i] & 0x0F]);
            i++;
        }
        return ret;
    }
}