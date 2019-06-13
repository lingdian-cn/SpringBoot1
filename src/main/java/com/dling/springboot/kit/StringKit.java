package com.dling.springboot.kit;

import java.util.UUID;

/**
 * @description 字符串工具类
 * @author dling
 * @date 2019-06-12 10:57:17
 * @since jdk8
 */
public class StringKit {
    public StringKit() {
    }

    public static String firstCharToLowerCase(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= 'A' && firstChar <= 'Z') {
            char[] arr = str.toCharArray();
            arr[0] = (char)(arr[0] + 32);
            return new String(arr);
        } else {
            return str;
        }
    }

    public static String firstCharToUpperCase(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= 'a' && firstChar <= 'z') {
            char[] arr = str.toCharArray();
            arr[0] = (char)(arr[0] - 32);
            return new String(arr);
        } else {
            return str;
        }
    }

    public static boolean isBlank(String str) {
        if (str == null) {
            return true;
        } else {
            int len = str.length();
            if (len == 0) {
                return true;
            } else {
                int i = 0;

                while(i < len) {
                    switch(str.charAt(i)) {
                        case '\t':
                        case '\n':
                        case '\r':
                        case ' ':
                            ++i;
                            break;
                        default:
                            return false;
                    }
                }

                return true;
            }
        }
    }

    public static boolean notBlank(String str) {
        return !isBlank(str);
    }

    public static boolean notBlank(String... strings) {
        if (strings != null && strings.length != 0) {
            String[] var1 = strings;
            int var2 = strings.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                String str = var1[var3];
                if (isBlank(str)) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断对象是否为null
     * @param paras 对象
     * @return boolean
     */
    public static boolean notNull(Object... paras) {
        if (paras == null) {
            return false;
        } else {
            Object[] var1 = paras;
            int var2 = paras.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Object obj = var1[var3];
                if (obj == null) {
                    return false;
                }
            }

            return true;
        }
    }

    public static String toCamelCase(String stringWithUnderline) {
        if (stringWithUnderline.indexOf(95) == -1) {
            return stringWithUnderline;
        } else {
            stringWithUnderline = stringWithUnderline.toLowerCase();
            char[] fromArray = stringWithUnderline.toCharArray();
            char[] toArray = new char[fromArray.length];
            int j = 0;

            for(int i = 0; i < fromArray.length; ++i) {
                if (fromArray[i] == '_') {
                    ++i;
                    if (i < fromArray.length) {
                        toArray[j++] = Character.toUpperCase(fromArray[i]);
                    }
                } else {
                    toArray[j++] = fromArray[i];
                }
            }

            return new String(toArray, 0, j);
        }
    }

    /**
     * 将字符串数组拼接
     * @param stringArray 字符数组
     * @return String
     */
    public static String join(String[] stringArray) {
        StringBuilder sb = new StringBuilder();
        String[] var2 = stringArray;
        int var3 = stringArray.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String s = var2[var4];
            sb.append(s);
        }

        return sb.toString();
    }

    /**
     * 给字符串数组添加分隔符
     * @param stringArray 字符数组
     * @param separator 分隔符
     * @return String
     */
    public static String join(String[] stringArray, String separator) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < stringArray.length; ++i) {
            if (i > 0) {
                sb.append(separator);
            }

            sb.append(stringArray[i]);
        }

        return sb.toString();
    }

    public static boolean slowEquals(String aStr, String bStr) {
        byte[] a = aStr != null ? aStr.getBytes() : null;
        byte[] b = bStr != null ? bStr.getBytes() : null;

        if (a != null && b != null) {
            int diff = a.length ^ b.length;

            for(int i = 0; i < a.length && i < b.length; ++i) {
                diff |= a[i] ^ b[i];
            }

            return diff == 0;
        } else {
            return false;
        }
        // return HashKit.slowEquals(aBytes, bBytes);
    }



    public static boolean equals(String a, String b) {
        return a == null ? b == null : a.equals(b);
    }

    /**
     * 获取36位随机字符串
     * @return String
     */
    public static String getRandomUUID36() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取32位随机字符串
     * @return String
     */
    public static String getRandomUUID32() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
