package dev.gamelord2011.ags_reborn;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Handles language key generation.
 * @since 4.0.0
 */
public class AgsLang {

    /**
     * Generates a SHA3-512 hash of the input string.
     * @since 4.1.0
     * @param input The input string to hash.
     * @return The hashed string.
     */
    private static String hashString(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA3-512");
            byte[] hashBytes = digest.digest(input.getBytes());

            // Convert the byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return input;
        }
    }

    // Runtime-generated keys
    private static final String KEY_CATEGORY_RUNTIME;
    private static final String KEY_SWITCH_RUNTIME;
    private static final String KEY_CONTROL_TOGGLE_RUNTIME;
    private static final String KEY_SWITCH_ON_RUNTIME;
    private static final String KEY_SWITCH_OFF_RUNTIME;

    static {
        KEY_CATEGORY_RUNTIME = hashString(UUID.randomUUID().toString());
        KEY_SWITCH_RUNTIME = hashString(UUID.randomUUID().toString());
        KEY_CONTROL_TOGGLE_RUNTIME = hashString(UUID.randomUUID().toString());
        KEY_SWITCH_ON_RUNTIME = hashString(UUID.randomUUID().toString());
        KEY_SWITCH_OFF_RUNTIME = hashString(UUID.randomUUID().toString());
    }

    // Index constants
    private static final int AGS_CATEGORY = 0;
    private static final int AGS_SWITCH = 1;
    private static final int AGS_CONTROL = 2;
    private static final int AGS_ON = 3;
    private static final int AGS_OFF = 4;

    // Language map
    private static final Map<String, String[]> LANGUAGE_MAP = Map.ofEntries(
        Map.entry("en_us", new String[]{
            "Anti Grian Switch Reborn",
            "Anti Grian Switch",
            "Should Control Be Held (Y/N)",
            "Anti Grian Switch: ON",
            "Anti Grian Switch: OFF"
        }),
        Map.entry("es_es", new String[]{
            "Interruptor Anti Grian Renacido",
            "Interruptor Anti Grian",
            "¿Se debe mantener pulsado ctrl? (Y/N)",
            "Interruptor Anti Grian: ACTIVADO",
            "Interruptor Anti Grian: DESACTIVADO"
        }),
        Map.entry("pl_pl", new String[]{ // Credit: @A-Potion and @Magicninja7.
            "Nowy Tryb Anti Grain",
            "Tryb Anti Grian",
            "Czy należy trzymać ctrl? (Y/N)",
            "Tryb Anti Grian: WŁĄCZONY",
            "Tryb Anti Grian: WYŁĄCZONY"
        }),
        Map.entry("en_pt", new String[]{
            "Anti-Grian Switch Hoist'd Again",
            "Anti-Grian Lever",
            "Be ye holdin’ Control? (Y/N)", // Ambigous unicode character alert! (U+2019)
            "Anti-Grian Lever: Aye!",
            "Anti-Grian Lever: Nay!"
        }),
        Map.entry("lol_us", new String[]{
            "Anti-Grian Swich Rebornded",
            "Anti-Grian Swich",
            "Hold CTRL? (Y/N)",
            "Anti-Grian Swich: YUS",
            "Anti-Grian Swich: NU"
        }),
        Map.entry("enws", new String[]{
            "Anti-Grian Lever Reawaken’d", // Ambigous unicode character alert! (U+2019)
            "Anti-Grian Lever",
            "Shall Control Be Held? (Yea/Nay)",
            "Anti-Grian Lever: Engaged",
            "Anti-Grian Lever: Disengaged"
        }),
        Map.entry("enp", new String[]{
            "Anti-Grian Toggle Born Anew",
            "Anti-Grian Toggle",
            "Should Grasp-Key Be Held? (Y/N)",
            "Anti-Grian Toggle: ON",
            "Anti-Grian Toggle: OFF"
        }),
        Map.entry("en_ud", new String[]{
            "uɹoqǝɹ ɥɔʇᴉʍS uɐᴉɹ⅁ ᴉʇu∀",
            "ɥɔʇᴉʍS uɐᴉɹ⅁ ᴉʇu∀",
            "(N/⅄) plǝɥ ǝq loɹʇuoƆ plnoɥS",
            "NO :ɥɔʇᴉʍS uɐᴉɹ⅁ ᴉʇu∀",
            "ℲℲO :ɥɔʇᴉʍS uɐᴉɹ⅁ ᴉʇu∀"
        }),
        Map.entry("tlh_aa", new String[]{
            "grian wIvHa\' chu\' Ha\'DIbaH",
            "grian wIvHa\' SeHlaw",
            "SeHlaw yI\'uch\'a\'? (Y/N)",
            "grian wIvHa\': Qap",
            "grian wIvHa\': Qapbe\'"
        }),
        Map.entry("qya_aa", new String[]{
            "Anti-Grian Lelya Ata",
            "Anti-Grian Lelya",
            "Nai mapa i cotumo? (Y/N)",
            "Anti-Grian Lelya: Calima",
            "Anti-Grian Lelya: Lómëa"
        })
    );

    /**
     * Constricts a map for the language keys.
     * @since 4.0.0
     * @return A map with the translation keys.
     * @param langCode The ISO 639-1 language code to generate for.
     */
    public static Map<String, String> constructLanguageSet(String langCode) {

        final String[] values = LANGUAGE_MAP.getOrDefault(langCode, LANGUAGE_MAP.get("en_us"));
        Map<String, String> translationsMap = new LinkedHashMap<>();

        translationsMap.put(KEY_CATEGORY_RUNTIME, values[AGS_CATEGORY]);

        String categoryKey = "key.category.ags_reborn." + KEY_CATEGORY_RUNTIME;

        translationsMap.put(categoryKey, values[AGS_CATEGORY]); // Handles the category key, as Minecraft handles these weirdly.

        translationsMap.put(KEY_SWITCH_RUNTIME, values[AGS_SWITCH]);
        translationsMap.put(KEY_CONTROL_TOGGLE_RUNTIME, values[AGS_CONTROL]);
        translationsMap.put(KEY_SWITCH_ON_RUNTIME, values[AGS_ON]);
        translationsMap.put(KEY_SWITCH_OFF_RUNTIME, values[AGS_OFF]);

        return translationsMap;
    }

    /**
     * Returns the runtime-generated translation key for the keybind category.
     * 
     * @return The translation key for the keybind category.
     * @since 4.0.0
     */
    static String getRuntimeCategoryKey() {
        return KEY_CATEGORY_RUNTIME;
    }

    /**
     * Returns the runtime-generated translation key for the toggle keybind.
     * 
     * @return The translation key for the toggle keybind.
     * @since 4.0.0
     */
    static String getRuntimeKeySwitch() {
        return KEY_SWITCH_RUNTIME;
    }

    /**
     * Returns the runtime-generated translation key for the "does control need to be held" keybind.
     * 
     * @return The translation key for the "does control need to be held" keybind.
     * @since 4.0.0
     */
    static String getRuntimeKeyControlToggle() {
        return KEY_CONTROL_TOGGLE_RUNTIME;
    }

    /**
     * Returns the runtime-generated translation key for the switch on chat message.
     * 
     * @return The translation key for the switch on chat message.
     * @since 4.0.0
     */
    static String getRuntimeKeySwitchOn() {
        return KEY_SWITCH_ON_RUNTIME;
    }

    /**
     * Returns the runtime-generated translation key for the switch off chat message.
     * 
     * @return The translation key for the switch off chat message.
     * @since 4.0.0
     */
    static String getRuntimeKeySwitchOff() {
        return KEY_SWITCH_OFF_RUNTIME;
    }
}