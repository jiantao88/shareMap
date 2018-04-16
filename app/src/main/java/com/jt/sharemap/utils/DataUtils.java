package com.jt.sharemap.utils;

import android.graphics.Bitmap;

import com.orhanobut.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 * 数据处理的工具类
 * <p>
 * <p>主要实现对数据进行加密、解密、排序等
 * <p>
 * <p>Created by WECH:Cino on 2015/5/4.
 */
public class DataUtils {

    /**
     * 类标记
     */
    private static final String TAG = "DataUtil";
    /**
     * 所有大写拉丁字母字符
     */
    public static final String UPPER_LETTER_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * 所有小写拉丁字母字符
     */
    public static final String LOWER_LETTER_CHAR = "abcdefghijklmnopqrstuvwxyz";
    /**
     * 所有数字字符
     */
    public static final String ARAB_NUMBER_CHAR = "0123456789";
    /**
     * 所有大写中文数字字符
     */
    public static final String UPPER_CHINA_NUMBER_CHAR = "零壹贰叁肆伍陆柒捌玖拾佰仟萬億";
    /**
     * 所有小写中文数字字符
     */
    public static final String LOWER_CHINA_NUMBER_CHAR = "零一二三四五六七八九十百千万亿";

    private DataUtils() {
    }

    /**
     * 随机生成唯一UUID字符串
     *
     * @return 返回随机产生的唯一字符
     */
    public static String getOnlyStr() {
        Logger.v(TAG, "getOnlyStr");
        return UUID.randomUUID().toString();
    }

    /**
     * 随机生成字符串
     *
     * @param length 指定生产字符串长度
     * @param chars  生成的字符串中的每个字符都是随机从字符粗chars取出字符
     * @return 返回随机生成的字符 (不能保证不重复)
     */
    public static String generateStr(int length, String chars) {
        Logger.v(TAG, "generateStr");
        String result = null;
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        int size = chars.length();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(size)));
        }
        result = sb.toString();
        return result;
    }

    /**
     * 十六进制数值字符串转byte类数据
     *
     * @param strhex 数据源
     * @return 返回转换后的byte数据
     */
    public static byte[] hex2byte(String strhex) {
        Logger.v(TAG, "hex2byte");
        if (strhex == null) {
            return null;
        }
        int l = strhex.length();
        if (l % 2 == 1) {
            return null;
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++) {
            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
        }
        return b;
    }

    /**
     * byte数据转十六进制数值字符串
     *
     * @param b byte数据源
     * @return 返回转换字符串数据
     */
    public static String byte2hex(byte[] b) {
        Logger.v(TAG, "byte2hex");
        StringBuffer hs = new StringBuffer(b.length);
        String stmp = "";
        int len = b.length;
        for (int n = 0; n < len; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            if (stmp.length() == 1) hs = hs.append("0").append(stmp);
            else {
                hs = hs.append(stmp);
            }
        }
        return String.valueOf(hs);
    }

    /**
     * MD5加密(一般认为无法解密，不过现在已经被证明可以解密)
     * <p>
     * <p>信息-摘要
     *
     * @param data 数据源
     * @return 返回加密的字符串数据
     */
    public static String encryptData2MD5(String data) {
        Logger.v(TAG, "encryptionData2MD5");
        String result = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(data.getBytes("UTF-8"));
            result = byte2hex(md5.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * SHA加密 (不可以解密)
     *
     * @param data 数据源
     * @return 返回加密的字符串数据
     */
    public static String encyptData2SHA(String data) {
        Logger.v(TAG, "encyptData2SHA");
        String result = null;
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA");
            sha.update(data.getBytes("UTF-8"));
            result = byte2hex(sha.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 简单可逆加密解密
     *
     * @param data 数据源
     * @return 解密或加密后的数据
     */
    public static String encryptDataReversible(String data) {
        Logger.v(TAG, "encryptionDataReversible");
        char[] chars = data.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (chars[i] ^ '1');
        }
        return new String(chars);
    }

    /**
     * 获取用于加密的公钥和私钥的钥匙包
     *
     * @param type 加密方式
     * @param size 生成公钥和私钥的字符大小
     * @return 换回生成的钥匙包
     */
    public static KeyPair getKeyCase(String type, int size) {
        Logger.v(TAG, "getKeyCase");
        KeyPair keyPair = null;
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(type);
            keyPairGen.initialize(size);
            keyPair = keyPairGen.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.v(TAG, e.getMessage());
        }
        return keyPair;
    }

    /**
     * 获取消息验证码
     *
     * @param data 数据源
     * @param key  获取数据源钥匙
     * @return 返回加密的消息验证码
     */
    public static String getEncryptHMACMD5(String data, String key) {
        Logger.v(TAG, "getEncryptHMACMD5");
        String result = null;
        try {
            byte[] keyByte = key.getBytes("UTF-8");
            SecretKeySpec sks = new SecretKeySpec(keyByte, "HMACMD5");
            Mac mac = Mac.getInstance("HMACMD5");
            mac.init(sks);
            mac.update(data.getBytes());
            byte[] certifyCode = mac.doFinal();
            if (certifyCode != null) {
                result = byte2hex(certifyCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static final String MAC_NAME = "HmacSHA1";
    private static final String ENCODING = "UTF-8";

  /*
  * 展示了一个生成指定算法密钥的过程 初始化HMAC密钥
  * @return
  * @throws Exception
  *
  public static String initMacKey() throws Exception {
  //得到一个 指定算法密钥的密钥生成器
  KeyGenerator KeyGenerator keyGenerator =KeyGenerator.getInstance(MAC_NAME);
  //生成一个密钥
  SecretKey secretKey =keyGenerator.generateKey();
  return null;
  }
  */

    /**
     * 使用 HMAC-SHA1 签名方法对对encryptText进行签名
     *
     * @param encryptText 被签名的字符串
     * @param encryptKey  密钥
     * @return
     * @throws Exception
     */
    public static String hmacSha1Encrypt(String encryptText, String encryptKey) throws Exception {
        Logger.v(TAG, "HmacSHA1Encrypt");
        String result = null;

        byte[] data = encryptKey.getBytes(ENCODING);
        //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
        //生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance(MAC_NAME);
        //用给定密钥初始化 Mac 对象
        mac.init(secretKey);

        byte[] text = encryptText.getBytes(ENCODING);
        //完成 Mac 操作
        byte[] certifyCode = mac.doFinal();
        if (certifyCode != null) {
            result = byte2hex(certifyCode);
        }

        return result;
    }

    /**
     * 加密
     *
     * @param src  数据源
     * @param key  密钥
     * @param type 加密类型 RAS DSA
     * @return 返回加密后的数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] src, byte[] key, String type) {
        Logger.v(TAG, "encrypt");
        byte[] reuslt = null;
        try {
            SecretKeySpec sKeySpec = new SecretKeySpec(key, type);
            Cipher cipher = Cipher.getInstance(type);
            cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
            reuslt = cipher.doFinal(src);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e(TAG, e.getMessage());
        }
        return reuslt;
    }

    /**
     * 解密
     *
     * @param src  数据源
     * @param key  密匙
     * @param type 加密类型 RSA DSA
     * @return 返回解密后的原始数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] src, byte[] key, String type) {
        Logger.v(TAG, "decrypt");
        byte[] reuslt = null;
        try {
            SecretKeySpec sKeySpec = new SecretKeySpec(key, type);
            Cipher cipher = Cipher.getInstance(type);
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec);
            reuslt = cipher.doFinal(src);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e(TAG, e.getMessage());
        }
        return reuslt;
    }

    /**
     * 使用公钥加密
     *
     * @param publicKey 加密公钥
     * @param srcBytes  数据源
     * @param type      加密类型主要有 RSA
     * @return 返回加密后的数据
     */
    public static byte[] encrypt(PublicKey publicKey, byte[] srcBytes, String type) {
        Logger.v(TAG, "encrypt");
        byte[] result = null;
        if (publicKey != null) {
            try {
                Cipher cipher = Cipher.getInstance(type);
                cipher.init(Cipher.ENCRYPT_MODE, publicKey);
                result = cipher.doFinal(srcBytes);
            } catch (Exception e) {
                e.printStackTrace();
                Logger.e(TAG, e.getMessage());
            }
        }
        return result;
    }

    /**
     * 使用私钥加密
     *
     * @param privateKey 加密私钥
     * @param srcBytes   数据源
     * @param type       加密类型 RSA DSA
     * @return 返回加密后的数据
     */
    public static byte[] encrypt(PrivateKey privateKey, byte[] srcBytes, String type) {
        Logger.v(TAG, "encrypt");
        byte[] result = null;
        if (privateKey != null) {
            try {
                Cipher cipher = Cipher.getInstance(type);
                cipher.init(Cipher.ENCRYPT_MODE, privateKey);
                result = cipher.doFinal(srcBytes);
            } catch (Exception e) {
                e.printStackTrace();
                Logger.e(TAG, e.getMessage());
            }
        }
        return result;
    }

    /**
     * 使用私钥解密
     *
     * @param privateKey 解密私钥
     * @param serBytes   数据源
     * @param type       解密类型 RSA
     * @return 解密后的数据
     */
    public static byte[] decrypt(PrivateKey privateKey, byte[] serBytes, String type) {
        Logger.v(TAG, "decryot");
        byte[] result = null;
        if (privateKey != null) {
            try {
                Cipher cipher = Cipher.getInstance(type);
                cipher.init(Cipher.DECRYPT_MODE, privateKey);
                result = cipher.doFinal(serBytes);
            } catch (Exception e) {
                e.printStackTrace();
                Logger.e(TAG, e.getMessage());
            }
        }
        return result;
    }

    /**
     * 使用公钥解密
     *
     * @param publicKey 解密私钥
     * @param serBytes  数据源
     * @param type      解密类型 RSA DSA
     * @return 解密后的数据
     */
    public static byte[] decrypt(PublicKey publicKey, byte[] serBytes, String type) {
        Logger.v(TAG, "decryot");
        byte[] result = null;
        if (publicKey != null) {
            try {
                Cipher cipher = Cipher.getInstance(type);
                cipher.init(Cipher.DECRYPT_MODE, publicKey);
                result = cipher.doFinal(serBytes);
            } catch (Exception e) {
                e.printStackTrace();
                Logger.e(TAG, e.getMessage());
            }
        }
        return result;
    }

    /**
     * AES加密
     *
     * @param data 数据源
     * @param key  加密钥匙
     * @return 加密后的数据
     */
    public static String encryptAESStr(String data, String key) {
        Logger.v(TAG, "encryptAESStr");
        String result = null;
        if (key != null && key.length() == 16) {
            try {
                byte[] resultbytes = encrypt(data.getBytes(), key.getBytes("UTF-8"), "AES");
                if (resultbytes != null) {
                    result = byte2hex(resultbytes);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Logger.e(TAG, e.getMessage());
            }
        }
        return result;
    }

    /**
     * AES 解密
     *
     * @param data 数据源
     * @param key  解密钥匙
     * @return 解密后的数据
     */
    public static String decryptAESStr(String data, String key) {
        Logger.v(TAG, "decryptAESStr");
        String result = null;
        if (key != null && key.length() == 16) {
            try {
                byte[] databytes = hex2byte(data);
                byte[] resultbytes = decrypt(databytes, key.getBytes("UTF-8"), "AES");
                if (resultbytes != null) {
                    result = new String(resultbytes);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Logger.e(TAG, e.getMessage());
            }
        }
        return result;
    }

    /**
     * 3DES加密
     *
     * @param data 数据源
     * @param key  加密钥匙
     * @return 返回加密后的数据
     */
    public static String decrypt3DESStr(String data, String key) {
        Logger.v(TAG, "decrypt3DESStr");
        String result = null;
        if (key != null && key.length() == 16) {
            try {
                byte[] databytes = hex2byte(data);
                byte[] resultbytes = decrypt(databytes, key.getBytes("UTF-8"), "DESede");
                if (resultbytes != null) {
                    result = new String(resultbytes);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Logger.e(TAG, e.getMessage());
            }
        }
        return result;
    }

    /**
     * 3DES解密
     *
     * @param data 数据源
     * @param key  解密钥匙
     * @return 返回解密后的原始数据
     */
    public static String encrypt3DESStr(String data, String key) {
        Logger.v(TAG, "encrypt3DESStr");
        String result = null;
        if (key != null && key.length() == 16) {
            try {
                byte[] resultbytes = encrypt(data.getBytes(), key.getBytes("UTF-8"), "DESede");
                if (resultbytes != null) {
                    result = byte2hex(resultbytes);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Logger.e(TAG, e.getMessage());
            }
        }
        return result;
    }

    /**
     * 3DES解密
     *
     * @param data 数据源
     * @param key  解密钥匙
     * @return 返回解密后的原始数据
     */
    public static String decryptDESStr(String data, String key) {
        Logger.v(TAG, "decryptDESStr");
        String result = null;
        if (key != null && key.length() == 8) {
            try {
                byte[] databytes = hex2byte(data);
                byte[] resultbytes = decrypt(databytes, key.getBytes("UTF-8"), "DES");
                if (resultbytes != null) {
                    result = new String(resultbytes);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Logger.e(TAG, e.getMessage());
            }
        }
        return result;
    }

    /**
     * DES加密
     *
     * @param data 数据源
     * @param key  加密钥匙
     * @return 返回加密后的数据
     */
    public static String encryptDESStr(String data, String key) {
        Logger.v(TAG, "encryptDESStr");
        String result = null;
        if (key != null && key.length() == 8) {
            try {
                byte[] resultbytes = encrypt(data.getBytes(), key.getBytes("UTF-8"), "DES");
                if (resultbytes != null) {
                    result = byte2hex(resultbytes);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Logger.e(TAG, e.getMessage());
            }
        }
        return result;
    }

    /**
     * RSA私密加密
     *
     * @param data       数据源
     * @param privateKey 加密钥匙
     * @return 返回加密的数据
     */
    public static String encryptPrivateKeyStr(String data, PrivateKey privateKey, String type) {
        Logger.v(TAG, "encryptPrivateKeyStr");
        String result = null;
        try {
            byte[] resultbytes = encrypt(privateKey, data.getBytes(), type);
            if (resultbytes != null) {
                result = byte2hex(resultbytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e(TAG, e.getMessage());
        }
        return result;
    }

    /**
     * RSA公钥加密
     *
     * @param data      数据源
     * @param publicKey 加密钥匙
     * @return 返回加密后的数据
     */
    public static String encryptPublicKeyStr(String data, PublicKey publicKey, String type) {
        Logger.v(TAG, "encryptPublicKeyStr");
        String result = null;
        try {
            byte[] resultbytes = encrypt(publicKey, data.getBytes(), type);
            if (resultbytes != null) {
                result = byte2hex(resultbytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e(TAG, e.getMessage());
        }
        return result;
    }

    /**
     * RSA私密解密
     *
     * @param data       数据源
     * @param privateKey 解密钥匙
     * @return 返回解密后的数据
     */
    public static String decryptPrivateKeyStr(String data, PrivateKey privateKey, String type) {
        Logger.v(TAG, "decryptPrivateKeyStr");
        String result = null;
        try {
            byte[] databytes = hex2byte(data);
            byte[] resultbytes = decrypt(privateKey, databytes, type);
            if (resultbytes != null) {
                result = new String(resultbytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e(TAG, e.getMessage());
        }
        return result;
    }

    /**
     * RSA公钥解密
     *
     * @param data      数据源
     * @param publicKey 解密钥匙
     * @return 返回解密后的数据
     */
    public static String decryptPublicKeyStr(String data, PublicKey publicKey, String type) {
        Logger.v(TAG, "decryptPublicKeyStr");
        String result = null;
        try {
            byte[] databytes = hex2byte(data);
            byte[] resultbytes = decrypt(publicKey, databytes, type);
            if (resultbytes != null) {
                result = new String(resultbytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e(TAG, e.getMessage());
        }
        return result;
    }

    /**
     * DSA私钥数据签名
     *
     * @param data       数据源
     * @param privateKey 签名私钥
     * @return 返回已经签名了的数据
     */
    public static String signetPrivateKeyDSAStr(String data, PrivateKey privateKey) {
        String result = null;
        try {
            java.security.Signature signet = java.security.Signature.getInstance("DSA");
            signet.initSign(privateKey);
            signet.update(data.getBytes());
            byte[] resultbytes = signet.sign();
            if (resultbytes != null) {
                result = byte2hex(resultbytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * DSA公钥验证
     *
     * @param data      原始数据源
     * @param publicKey 验证签名的公钥
     * @param signed    签名的数据源
     * @return 返回验证是否通过
     */
    public static boolean verificationPublicKeyDSA(String data, PublicKey publicKey, String signed) {
        boolean result = false;
        try {
            java.security.Signature signetcheck = java.security.Signature.getInstance("DSA");
            signetcheck.initVerify(publicKey);
            signetcheck.update(data.getBytes());
            if (signetcheck.verify(hex2byte(signed))) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * BitMap 转数据流
     *
     * @param bm
     * @return
     */
    public static byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * Utf8编码
     *
     * @param strSrc
     * @return
     */
    public static String utf8Encode(String strSrc) {
        String str = "";
        try {
            str = URLEncoder.encode(strSrc, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * utf8解码
     *
     * @param strSrc
     * @return
     */
    public static String utf8Decode(String strSrc) {
        String str = "";

        try {
            str = URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return str;
    }


    /**
     * 字符串截取
     *
     * @param str
     * @param splitsign
     * @return
     */
    public static String[] split(String str, String splitsign) {
        int index;
        if (str == null || splitsign == null) return null;
        ArrayList al = new ArrayList();
        while ((index = str.indexOf(splitsign)) != -1) {
            al.add(str.substring(0, index));
            str = str.substring(index + splitsign.length());
        }
        al.add(str);
        return (String[]) al.toArray(new String[0]);
    }

    /**
     * 判断是不是合法手机 handset 手机号码
     */
    public static boolean isHandset(String handset) {
        try {
            if (!handset.substring(0, 1).equals("1")) {
                return false;
            }
            if (handset == null || handset.length() != 11) {
                return false;
            }
            String check = "^[0123456789]+$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(handset);
            boolean isMatched = matcher.matches();
            if (isMatched) {
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            return false;
        }
    }

    /**
     * 判断是不是数字
     *
     * @param num
     * @return
     */
    public static boolean isNumber(String num) {
        try {
            if (num == null || num.length() <= 0) {
                return false;
            }

            Pattern p = Pattern.compile("^[-+]?\\d+(\\.\\d+)?$");
            Matcher matcher = p.matcher(num);
            boolean isMatched = matcher.matches();
            if (isMatched) {
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            return false;
        }
    }

    /**
     * 判断是不是字母
     *
     * @param letter
     * @return
     */
    public static boolean isLetter(String letter) {
        Pattern p = Pattern.compile("[a-zA-Z]*");
        Matcher m = p.matcher(letter);
        if (m.matches()) {
            return true;
        }

        return false;
    }

    /**
     * 判断是不是中文
     *
     * @param chinese
     * @return
     */
    public static boolean isChinese(String chinese) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]*");
        Matcher m = p.matcher(chinese);
        if (m.matches()) {
            return true;
        }

        return false;
    }

    public static boolean isTelNumber(String tel) {
        Pattern p = Pattern.compile("^[1][3578]\\d{9}$*");
        Matcher m = p.matcher(tel);
        if (m.matches()) {
            return true;
        }

        return false;
    }

    /**
     * 将分为单位的转换为元 （除100）
     * 必须保留2为有效小数
     *
     * @param amount
     * @return
     * @throws Exception
     */
    public static String uniConversion(double amount) {
        BigDecimal bd = new BigDecimal(amount / 100).setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd.toString();
    }

    /**
     * 将分为单位的转换为元 （除100）
     * 有则保留，没有则不保留
     *
     * @param amount
     * @return
     */
    public static String uniBitsConversion(double amount) {
        int scale = 0;
        if (amount % 100 != 0) {
            scale = 2;
        }
        BigDecimal bd = new BigDecimal(amount / 100).setScale(scale, BigDecimal.ROUND_HALF_UP);
        return bd.toString();
    }

    public Map<String, String> getParameters(String url) {
        Map<String, String> params = new HashMap<>();
        if (url == null || "".equals(url.trim())) {
            return params;
        }
        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String[] split = url.split("[?]");
        if (split.length == 2 && !"".equals(split[1].trim())) {
            String[] parameters = split[1].split("&");
            if (parameters != null && parameters.length != 0) {
                for (int i = 0; i < parameters.length; i++) {
                    if (parameters[i] != null
                            && parameters[i].trim().contains("=")) {
                        String[] split2 = parameters[i].split("=");
                        //split2可能为1，可能为2
                        if (split2.length == 1) {
                            //有这个参数但是是空的
                            params.put(split2[0], "");
                        } else if (split2.length == 2) {
                            if (!"".equals(split2[0].trim())) {
                                params.put(split2[0], split2[1]);
                            }
                        }
                    }
                }
            }
        }
        return params;
    }

    /*
    * 毫秒转化
    */
    public static String formatTime(long ms) {

        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;

        String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟
        String strSecond = second < 10 ? "0" + second : "" + second;//秒

        return strMinute + " ： " + strSecond;
    }

    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014年06月14日16:09"）
     *
     * @param time
     * @return
     */
    public static String timet(long time) {
        SimpleDateFormat sdr = new SimpleDateFormat("MM月dd日  HH:mm");
        String times = sdr.format(new Date(time));
        return times;

    }

    public static String OrderStatus(int orderStatus) {
        switch (orderStatus) {
            case 10:
                return "待支付";
            case 11:
                return "待支付订金";
            case 12:
                return "待支付尾款";
            case 20:
                return "待排产";
            case 21:
                return "生产中";
            case 22:
                return "待出库";
            case 30:
                return "待收货";
            case 40:
                return "已完成";
            case 41:
                return "已关闭";
            case 50:
                return "退款中";
            case 51:
                return "待退款";
            case 52:
                return "已退款";
            case 60:
                return "待激活";
            case 61:
                return "已激活";
            default:
        }
        return "";
    }
}
