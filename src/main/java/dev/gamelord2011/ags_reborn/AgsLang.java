package dev.gamelord2011.ags_reborn;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles language key generation.
 * @since 4.0.0
 */
public class AgsLang {
    private static final Logger LOGGER = LoggerFactory.getLogger("ags_reborn.AgsLang");

    // Runtime-generated keys
    private static final String KEY_CATEGORY_RUNTIME;
    private static final String KEY_SWITCH_RUNTIME;
    private static final String KEY_CONTROL_TOGGLE_RUNTIME;
    private static final String KEY_SWITCH_ON_RUNTIME;
    private static final String KEY_SWITCH_OFF_RUNTIME;

    static {
        final String prefix = UUID.randomUUID().toString() + ".";
        KEY_CATEGORY_RUNTIME = prefix + UUID.randomUUID();
        KEY_SWITCH_RUNTIME = prefix + UUID.randomUUID();
        KEY_CONTROL_TOGGLE_RUNTIME = prefix + UUID.randomUUID();
        KEY_SWITCH_ON_RUNTIME = prefix + UUID.randomUUID();
        KEY_SWITCH_OFF_RUNTIME = prefix + UUID.randomUUID();

        LOGGER.info(
            "Generated runtime keys - category={}, switch={}, control={}, on={}, off={}",
            KEY_CATEGORY_RUNTIME,
            KEY_SWITCH_RUNTIME,
            KEY_CONTROL_TOGGLE_RUNTIME,
            KEY_SWITCH_ON_RUNTIME,
            KEY_SWITCH_OFF_RUNTIME
        );
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
            "Be ye holdin’ Control? (Y/N)",
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
            "Anti-Grian Lever Reawaken’d",
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
            "uɹoqǝɹ ɥɔʇᴉʍS uᴉɐɹ⅁ ᴉʇu∀",
            "ɥɔʇᴉʍS uᴉɐɹ⅁ ᴉʇu∀",
            "(N/⅄) plǝɥ ǝq loɹʇuoƆ plnoɥS",
            "NO :ɥɔʇᴉʍS uᴉɐɹ⅁ ᴉʇu∀",
            "ℲℲO :ɥɔʇᴉʍS uᴉɐɹ⅁ ᴉʇu∀"
        }),
        Map.entry("tlh_aa", new String[]{
            "grian wIvHa' chu' Ha'DIbaH",
            "grian wIvHa' SeHlaw",
            "SeHlaw yI'uch'a'? (Y/N)",
            "grian wIvHa': Qap",
            "grian wIvHa': Qapbe'"
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
        LOGGER.info("constructLanguageSet called for langCode={}", langCode);

        final String[] values = LANGUAGE_MAP.getOrDefault(langCode, LANGUAGE_MAP.get("en_us"));
        Map<String, String> translationsMap = new LinkedHashMap<>();

        translationsMap.put(KEY_CATEGORY_RUNTIME, values[AGS_CATEGORY]);

        String categoryKey = "key.category.ags_reborn." + KEY_CATEGORY_RUNTIME;

        translationsMap.put(categoryKey, values[AGS_CATEGORY]);

        translationsMap.put(KEY_SWITCH_RUNTIME, values[AGS_SWITCH]);
        translationsMap.put(KEY_CONTROL_TOGGLE_RUNTIME, values[AGS_CONTROL]);
        translationsMap.put(KEY_SWITCH_ON_RUNTIME, values[AGS_ON]);
        translationsMap.put(KEY_SWITCH_OFF_RUNTIME, values[AGS_OFF]);

        LOGGER.info("constructLanguageSet produced {} entries for lang {}", translationsMap.size(), langCode);
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
