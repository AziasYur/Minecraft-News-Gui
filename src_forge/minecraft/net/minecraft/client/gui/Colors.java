package net.minecraft.client.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public enum Colors {
    BLACK('0', 0),
    DARK_BLUE('1', 1),
    DARK_GREEN('2', 2),
    DARK_AQUA('3', 3),
    DARK_RED('4', 4),
    DARK_PURPLE('5', 5),
    GOLD('6', 6),
    GRAY('7', 7),
    DARK_GRAY('8', 8),
    BLUE('9', 9),
    GREEN('a', 10),
    AQUA('b', 11),
    RED('c', 12),
    LIGHT_PURPLE('d', 13),
    YELLOW('e', 14),
    WHITE('f', 15),
    MAGIC('k', 16, true),
    BOLD('l', 17, true),
    STRIKETHROUGH('m', 18, true),
    UNDERLINE('n', 19, true),
    ITALIC('o', 20, true),
    RESET('r', 21);
    public static final char COLOR_CHAR = '\u00a7';
    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + String.valueOf('\u00a7') + "[0-9A-FK-OR]");
    private final int intCode;
    private final char code;
    private final boolean isFormat;
    private final String toString;
    private static final Map BY_ID = new HashMap();
    private static final Map BY_CHAR = new HashMap();

    private Colors(char var3, int var4) {
        this(var3, var4, false);
    }

    private Colors(char var3, int var4, boolean var5) {
        this.code = var3;
        this.intCode = var4;
        this.isFormat = var5;
        this.toString = new String(new char[] {'\u00a7', var3});
    }

    public char getChar() {
        return this.code;
    }

    public String toString() {
        return this.toString;
    }

    public boolean isFormat() {
        return this.isFormat;
    }

    public boolean isColor() {
        return !this.isFormat && this != RESET;
    }

    public static Colors getByChar(char var0) {
        return (Colors)BY_CHAR.get(Character.valueOf(var0));
    }

    public static Colors getByChar(String var0) {
        return (Colors)BY_CHAR.get(Character.valueOf(var0.charAt(0)));
    }

    public static String stripColor(String var0) {
        return var0 == null ? null : STRIP_COLOR_PATTERN.matcher(var0).replaceAll("");
    }

    public static String translateAlternateColorCodes(char var0, String var1) {
        char[] var2 = var1.toCharArray();        
        for (int var3 = 0; var3 < var2.length - 1; ++var3) {
            if (var2[var3] == var0 && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(var2[var3 + 1]) > -1) {
                var2[var3] = 167;
                var2[var3 + 1] = Character.toLowerCase(var2[var3 + 1]);
            }
        }
        return new String(var2);
    }

    public static String getLastColors(String var0) {
        String var1 = "";
        int var2 = var0.length();

        for (int var3 = var2 - 1; var3 > -1; --var3) {
            char var4 = var0.charAt(var3);
            
            if (var4 == 167 && var3 < var2 - 1) {
                char var5 = var0.charAt(var3 + 1);
                Colors var6 = getByChar(var5);

                if (var6 != null) {
                    var1 = var6.toString() + var1;

                    if (var6.isColor() || var6.equals(RESET)) {
                        break;
                    }
                }
            }
        }
        return var1;
    }
    
    static {
        Colors[] var0 = values();
        int var1 = var0.length;

        for (int var2 = 0; var2 < var1; ++var2) {
            Colors var3 = var0[var2];
            BY_ID.put(Integer.valueOf(var3.intCode), var3);
            BY_CHAR.put(Character.valueOf(var3.code), var3);
        }
    }
}