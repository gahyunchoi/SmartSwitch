package com.example.user.myapplication.utils;
import android.widget.TextView;

/**
 * Created by timyu on 14-12-24.
 */
public class StringUtils
{
    private static final String EMPTY_STRING = "";

    public static boolean isEmpty(String str)
    {
        return str == null || EMPTY_STRING.equals(str.trim().toString());
    }

    public static boolean isNotEmpty(CharSequence str)
    {
        return !isEmpty(str);
    }


    public static boolean isEmpty(CharSequence str)
    {
        return str == null || str.length() == 0;
    }

    /**
     * 判断字符串是否符合数字格式
     *
     * @param str
     * @return
     */
    public static boolean isNum(String str)
    {
        return !StringUtils.isEmpty(str)
                && str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }

    /**
     * 获取去掉前后空格的输入内容
     *
     * @param et
     * @return
     */
    public static String getTrimText(TextView et)
    {
        if (et == null)
            return "";
        else
            return et.getText().toString().trim();
    }

    public static boolean isInt(String val)
    {
        try
        {
            Integer.parseInt(val);
            return true;
        } catch (Exception e)
        {
            return false;
        }
    }

    // Temporary method for getting a first name from a full name.
    public static String getFirstName (String userName){
        if(userName.split("\\w+").length>1) {
            String firstName = userName.substring(0, userName.lastIndexOf(' '));
            return firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
        }
        else
            return userName;
    }

}