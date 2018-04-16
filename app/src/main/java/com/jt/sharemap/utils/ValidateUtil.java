package com.jt.sharemap.utils;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 提供基础的验证逻辑 */
public class ValidateUtil {
  public static final String TAG = "ValidateUtil";
  public static final String EMPTY = "";
  public static final String REG_EMAIl_STRICT =
      "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
  public static final String REG_EMAIL =
      "^([a-z0-9A-Z]+([_|\\.\\-]*))+([a-z0-9A-Z_-])*@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)*\\.)+[a-zA-Z]{2,}$";
  public static final String REG_NUMBER = "[0-9]*";
  /** 银行卡替换成“*”号 */
  public static final String REG_BANK_REPLACEALL = "(?<=\\d{3})\\d(?=\\d{3})";

  public static final String REG_NUMBER_WITH_STARS_CENTER = "^[0-9]*[\\*]*[0-9]*$";
  public static final String REG_PHONE = "^1[3|4|5|7|8]\\d{9}";
  public static final String REG_PHONE_WITH_STARS_CENTER = "^1[3|4|5|7|8][\\*]*[0-9]*$";

  /**
   * 判断是否value不为空（null或空字符串）
   *
   * @param value
   * @return
   */
  public static boolean isNotEmpty(String value) {
    if (!TextUtils.isEmpty(value) && !TextUtils.isEmpty(value.trim())) {
      return true;
    }
    return false;
  }

  /**
   * 判断字符串长度是否符合要求，最短minLength位
   *
   * @param value
   * @param minLength
   * @return
   */
  public static boolean isValidMinLength(String value, int minLength) {
    if (isNotEmpty(value)) {
      if (value.length() >= minLength) {
        return true;
      }
    }
    return false;
  }

  /**
   * 判断字符串长度是否符合要求，最长maxLength位
   *
   * @param value
   * @param maxLength
   * @return
   */
  public static boolean isValidMaxLength(String value, int maxLength) {
    if (isNotEmpty(value)) {
      if (value.length() <= maxLength) {
        return true;
      }
    }
    return false;
  }

  /**
   * 判断字符串长度是否正好为length
   *
   * @param value
   * @param length
   * @return
   */
  public static boolean isRightLength(String value, int length) {
    if (isNotEmpty(value)) {
      if (value.length() == length) {
        return true;
      }
    }
    return false;
  }

  /**
   * 判断给定的字符串是否符合，regex定义的规则
   *
   * @param value
   * @param regex
   * @return
   */
  public static boolean isPattern(String value, String regex) {
    if (isNotEmpty(value)) {
      if (isNotEmpty(regex)) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        boolean result = matcher.matches();
        return result;
      } else {
        Logger.d(TAG, "pattern regex is null");
      }
    }
    return false;
  }

  /**
   * 判断给定的字符串是否符合邮箱格式
   *
   * @param email
   * @return
   */
  public static boolean isEmail(String email) {
    return isPattern(email, REG_EMAIl_STRICT);
  }

  /**
   * 电话号码中间可能出现*号
   *
   * @param phoneNumber
   * @return
   */
  public static boolean isPhoneNumberMayWithStarCenter(String phoneNumber) {
    return isPattern(phoneNumber, REG_PHONE_WITH_STARS_CENTER);
  }

  public static boolean isPhoneNumber(String phoneNumber) {
    return isPattern(phoneNumber, REG_PHONE);
  }

  /**
   * 只校验手机号第一位1
   *
   * @param phoneNumber
   * @return
   */
  public static boolean isPhoneNumber11(String phoneNumber) {
    return isPattern(phoneNumber, REG_NUMBER)
            && (phoneNumber.startsWith("1") || phoneNumber.startsWith("18"))
            && phoneNumber.length() == 11;
  }

  public static boolean isIdCard15(String idCard15) {
    return true;
  }

  public static boolean isIdCard18(String idCard18) {
    return true;
  }

  public static boolean isIdCard(String idCard) {
    return IDCardUtil.IDCardValidate(idCard);
  }

  //简单规则的身份证验证
  public static boolean isIDCard(String idCard) {
    return IDCardUtil.SampleIDCardValidate(idCard);
  }

  public static boolean isAge18(String idCard) {
    return IDCardUtil.getAgeByIdCard(idCard) > 18;
  }

  public static boolean isChineseName(String name) {
    if (name.length() < 2 || name.length() > 20) {
      return false;
    } else {
      Boolean isChineseName = true;
      //            String a="[\u4e00-\u9fa5]{1,2}(·){0,1}[\u4e00-\u9fa5]{1,3}";
      String chinese = "[\u4e00-\u9fa5]{2,20}";
      if (isNotEmpty(name) && isPattern(name, chinese)) {
        isChineseName = true;
      } else {
        isChineseName = false;
      }
      return isChineseName;
    }
  }

  private static boolean isSupportNumbersOfBankCard(String bankNo) {
    return bankNo.length() >= 16 && bankNo.length() <= 19;
  }

  /**
   * 判断银行卡是否只有数字 和 * 两种字符，并且满足银行卡位数
   *
   * @param bankNo
   * @return
   */
  public static boolean isBankCardMayWithStarsCenter(String bankNo) {
    if ((isPattern(bankNo, REG_NUMBER_WITH_STARS_CENTER) || isPattern(bankNo, REG_NUMBER))
        && isSupportNumbersOfBankCard(bankNo)) {
      return true;
    } else {
      return false;
    }
  }

  public static boolean isBankCard(String bankNo) {
    if (isPattern(bankNo, REG_NUMBER) && isSupportNumbersOfBankCard(bankNo)) {
      return true;
    } else {
      return false;
    }
  }

  public static boolean minAddressChineseLength(String name) {

    int i = 0;
    //            String a="[\u4e00-\u9fa5]{1,2}(·){0,1}[\u4e00-\u9fa5]{1,3}";
    String chinese = "[\u4e00-\u9fa5]";
    Pattern p = Pattern.compile(chinese);
    Matcher m;
    for (int count = 0; count < name.length(); count++) {
      char temp = name.charAt(count);
      m = p.matcher(String.valueOf(temp));
      if (m.find()) {
        i++;
      }
    }
    if (i < 4) {
      return false;
    } else {
      return true;
    }
  }

  /** 交易密码需为不相同且不连续的数字 */
  public static boolean isLegalPwd(String pwd) {

    boolean isLegal = false;
    if (equalStr(pwd)) {
      isLegal = false;
    } else if (isOrderNumeric(pwd)) {
      isLegal = false;
    } else if (isOrderNumeric_(pwd)) {
      isLegal = false;
    } else {
      isLegal = true;
    }
    return isLegal;
  }

  /**
   * 不能全是相同的数字或者字母（如：000000、111111、aaaaaa） 全部相同返回true
   *
   * @param numOrStr
   * @return
   */
  public static boolean equalStr(String numOrStr) {
    boolean flag = true;
    if (!TextUtils.isEmpty(numOrStr)) {
      char str = numOrStr.charAt(0);
      for (int i = 0; i < numOrStr.length(); i++) {
        if (str != numOrStr.charAt(i)) {
          flag = false;
          break;
        }
      }
    } else {
      flag = true;
    }

    return flag;
  }

  /**
   * 不能是连续的数字--递增（如：123456、12345678）连续数字返回true
   *
   * @param numOrStr
   * @return
   */
  public static boolean isOrderNumeric(String numOrStr) {
    boolean flag = true; //如果全是连续数字返回true
    boolean isNumeric = true; //如果全是数字返回true
    if (!TextUtils.isEmpty(numOrStr)) {
      for (int i = 0; i < numOrStr.length(); i++) {
        if (!Character.isDigit(numOrStr.charAt(i))) {
          isNumeric = false;
          break;
        }
      }
      if (isNumeric) { //如果全是数字则执行是否连续数字判断
        for (int i = 0; i < numOrStr.length(); i++) {
          if (i > 0) { //判断如123456
            int num = Integer.parseInt(numOrStr.charAt(i) + "");
            int num_ = Integer.parseInt(numOrStr.charAt(i - 1) + "") + 1;
            if (num != num_) {
              flag = false;
              break;
            }
          }
        }
      } else {
        flag = false;
      }
    } else {
      flag = true;
    }

    return flag;
  }

  /**
   * 不能是连续的数字--递减（如：987654、876543）连续数字返回true
   *
   * @param numOrStr
   * @return
   */
  public static boolean isOrderNumeric_(String numOrStr) {
    boolean flag = true; //如果全是连续数字返回true
    boolean isNumeric = true; //如果全是数字返回true
    if (!TextUtils.isEmpty(numOrStr)) {
      for (int i = 0; i < numOrStr.length(); i++) {
        if (!Character.isDigit(numOrStr.charAt(i))) {
          isNumeric = false;
          break;
        }
      }
      if (isNumeric) { //如果全是数字则执行是否连续数字判断
        for (int i = 0; i < numOrStr.length(); i++) {
          if (i > 0) { //判断如654321
            int num = Integer.parseInt(numOrStr.charAt(i) + "");
            int num_ = Integer.parseInt(numOrStr.charAt(i - 1) + "") - 1;
            if (num != num_) {
              flag = false;
              break;
            }
          }
        }
      } else {
        flag = false;
      }
    } else {
      flag = true;
    }

    return flag;
  }

  public static boolean isContainsChinese(String str) {
    String regEx = "[\u4e00-\u9fa5]";
    Pattern pat = Pattern.compile(regEx);
    Matcher matcher = pat.matcher(str);
    boolean flg = false;
    if (matcher.find()) {
      flg = true;
    }
    return flg;
  }
}
