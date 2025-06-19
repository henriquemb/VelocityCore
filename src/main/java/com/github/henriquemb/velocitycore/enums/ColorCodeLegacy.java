package com.github.henriquemb.velocitycore.enums;

public enum ColorCodeLegacy {
    BLACK("0", "<black>"),
    DARK_BLUE("1", "<dark_blue>"),
    DARK_GREEN("2", "<dark_green>"),
    DARK_AQUA("3", "<dark_aqua>"),
    DARK_RED("4", "<dark_red>"),
    DARK_PURPLE("5", "<dark_purple>"),
    GOLD("6", "<gold>"),
    GRAY("7", "<gray>"),
    DARK_GRAY("8", "<dark_gray>"),
    BLUE("9", "<blue>"),
    GREEN("a", "<green>"),
    AQUA("b", "<aqua>"),
    RED("c", "<red>"),
    LIGHT_PURPLE("d", "<light_purple>"),
    YELLOW("e", "<yellow>"),
    WHITE("f", "<white>"),
    RESET("r", "<reset>"),
    OBFUSCATED("k", "<obfuscated>"),
    BOLD("l", "<bold>"),
    STRIKETHROUGH("m", "<strikethrough>"),
    UNDERLINE("n", "<underline>"),
    ITALIC("o", "<italic>");

    private static final String USER_CODE_PREFIX = "&";
    private static final String CODE_PREFIX = "§";
    private static final String LEGACY_HEX_PATTERN = "&#([0-9A-Fa-f]{6}|[0-9A-Fa-f]{3})";

    private final String legacyCode;
    private final String newCode;

    ColorCodeLegacy(String legacyCode, String newCode) {
        this.legacyCode = legacyCode;
        this.newCode = newCode;
    }

    public static String getNewCode(String legacyCode) {
        for (ColorCodeLegacy color : ColorCodeLegacy.values()) {
            if (color.legacyCode.equalsIgnoreCase(CODE_PREFIX.concat(legacyCode))) {
                return color.newCode;
            }
        }
        return legacyCode;
    }

    public static String translateLegacyCodes(String message) {
        message = translateUserCodes(message);

        for (ColorCodeLegacy color : ColorCodeLegacy.values()) {
            message = message.replaceAll(CODE_PREFIX.concat(color.legacyCode), color.newCode);
        }

        return message;
    }

    private static String translateUserCodes(String message) {
        for (ColorCodeLegacy color : ColorCodeLegacy.values()) {
            message = message.replaceAll(USER_CODE_PREFIX.concat(color.legacyCode), CODE_PREFIX.concat(color.legacyCode));
        }

        message = message.replaceAll(LEGACY_HEX_PATTERN, "<#$1>");

        return message;
    }
}