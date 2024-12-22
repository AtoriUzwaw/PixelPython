package com.atri.util;

/**
 * TranslationUtil 类用于根据角色和速度枚举进行角色名称的翻译。
 * 该工具类提供了两种翻译方式，一种为原始角色名称翻译，另一种为自定义翻译。
 */
public class TranslationUtil {

    // 私有化构造函数，防止实例化
    private TranslationUtil() {
    }

    /**
     * 根据传入的角色和速度枚举以及翻译方式进行角色名称的翻译。
     *
     * @param roleAndSpeed 角色和速度枚举，用于获取角色信息。
     * @param original 是否返回原始名称，若为 true 返回英文名称，false 返回自定义翻译名称。
     * @return 翻译后的角色名称。
     */
    public static String translation(RoleAndSpeed roleAndSpeed, boolean original) {
        String role = "未知qaq"; // 默认返回值为“未知qaq”

        // 如果 original 为 false，返回自定义翻译名称
        if (!original) {
            switch (roleAndSpeed.getRole()) {
                case "slug" -> role = "鼻涕虫";   // 鼻涕虫角色翻译
                case "worm" -> role = "蠕虫";    // 蠕虫角色翻译
                case "python" -> role = "python怎么你了QAQ"; // Python 角色翻译
            }
            return role;
        }

        // 如果 original 为 true，返回原始英文名称或自定义翻译
        switch (roleAndSpeed.getRole()) {
            case "slug" -> role = "鼻涕虫";   // 鼻涕虫角色翻译
            case "worm" -> role = "蠕虫";    // 蠕虫角色翻译
            case "python" -> role = "python"; // Python 角色翻译
        }
        return role;
    }

    /**
     * 根据传入的角色和速度枚举进行角色名称的翻译。
     * 默认返回自定义翻译名称（不考虑 original 参数）。
     *
     * @param roleAndSpeed 角色和速度枚举，用于获取角色信息。
     * @return 翻译后的角色名称。
     */
    public static String translation(RoleAndSpeed roleAndSpeed) {
        String role = "未知qaq"; // 默认返回值为“未知qaq”

        switch (roleAndSpeed.getRole()) {
            case "slug" -> role = "鼻涕虫";   // 鼻涕虫角色翻译
            case "worm" -> role = "蠕虫";    // 蠕虫角色翻译
            case "python" -> role = "python怎么你了QAQ"; // Python 角色翻译
        }
        return role;
    }
}

