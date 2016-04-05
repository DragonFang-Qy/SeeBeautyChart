package com.fangzitcl.libs.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName: UtilEncryption
 * @PackageName: com.fangzitcl.libs.util
 * @Acthor: Fang_QingYou
 * @Time: 2016.01.06 14:55
 */
public class UtilEncryption {

    // 全局数组
    private final static String[] strDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    private static String MD5Type16 = "MD5Type16";
    private static String MD5Type32 = "MD5Type32";

    private UtilEncryption() {
    }

    /**
     * 得到MD5（16位长度）
     *
     * @param string
     * @return
     */
    public static String getMD5Code(String string, String type) {
        String resultString = null;

        MessageDigest md = null;
        try {
            resultString = new String(string);
            md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            if (MD5Type16.equals(type)) {
                resultString = byteToString(md.digest(resultString.getBytes())).substring(8, 24);

            } else if (MD5Type32.equals(type)) {
                resultString = byteToString(md.digest(resultString.getBytes()));

            } else {

            }
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }

    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();

        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();


    }

    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }

        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];


    }

}
