package com.atri.util;

public class TranslationUtil {
    private TranslationUtil() {
    }

    public static String translation(RoleAndSpeed roleAndSpeed, boolean original) {
        String role = "未知qaq";
        if (!original) {
            switch (roleAndSpeed.getRole()) {
                case "slug" -> role = "鼻涕虫";
                case "worm" -> role = "蠕虫";
                case "python" -> role = "python怎么你了QAQ";
            }
            return role;
        }
        switch (roleAndSpeed.getRole()) {
            case "slug" -> role = "鼻涕虫";
            case "worm" -> role = "蠕虫";
            case "python" -> role = "python";
        }
        return role;
    }

    public static String translation(RoleAndSpeed roleAndSpeed) {
        String role = "未知qaq";
        switch (roleAndSpeed.getRole()) {
            case "slug" -> role = "鼻涕虫";
            case "worm" -> role = "蠕虫";
            case "python" -> role = "python怎么你了QAQ";
        }
        return role;
    }
}
