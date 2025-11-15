package dev.gamelord2011.ags_reborn;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AgsLang {
    private static final Logger LOGGER = LoggerFactory.getLogger("ags_reborn.AgsLang");

    // Stable translation keys
    private static final String KEY_CATEGORY = "key.categories.ags_reborn";
    private static final String KEY_SWITCH = "key.ags_reborn.switch";
    private static final String KEY_CONTROL_TOGGLE = "key.ags_reborn.control_toggle";
    private static final String KEY_SWITCH_ON = "message.ags_reborn.switch_on";
    private static final String KEY_SWITCH_OFF = "message.ags_reborn.switch_off";

    // Runtime-generated keys
    private static final String KEY_CATEGORY_RUNTIME;
    private static final String KEY_SWITCH_RUNTIME;
    private static final String KEY_CONTROL_TOGGLE_RUNTIME;
    private static final String KEY_SWITCH_ON_RUNTIME;
    private static final String KEY_SWITCH_OFF_RUNTIME;

    static {
        final String prefix = "ags_reborn.dynamic.";
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
    private static final int IDX_CATEGORY = 0;
    private static final int IDX_SWITCH = 1;
    private static final int IDX_CONTROL = 2;
    private static final int IDX_ON = 3;
    private static final int IDX_OFF = 4;

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
        Map.entry("pl_pl", new String[]{ // Credit: @A-Potion and @Magicninja7. Also, SIX-SEVEN!
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
            "(N/⅄) plǝɥ ǝq lɐɹʇuoƆ plnoɥS",
            "NO :ɥɔʇᴉʍS uᴉɐɹ⅁ ᴉʇu∀",
            "ℲℲO :ɥɔʇᴉʍS uᴉɐɹ⅁ ᴉʇu∀"
        }),
        Map.entry("tlh_aa", new String[]{
            "Grian wIvHa' chu' Ha'DIbaH",
            "Grian wIvHa' SeHlaw",
            "SeHlaw yI'uch'a'? (HIja'/ghobe')",
            "Grian wIvHa': Qap",
            "Grian wIvHa': Qapbe'"
        }),
        Map.entry("qya_aa", new String[]{
            "Anti-Grian Lelya Ata",
            "Anti-Grian Lelya",
            "Nai mapa i cotumo? (Y/N)",
            "Anti-Grian Lelya: Calima",
            "Anti-Grian Lelya: Lómëa"
        })
    );

    public static Map<String, String> constructLanguageSet(String langCode) {
        LOGGER.info("constructLanguageSet called for langCode={}", langCode);

        final String[] values = LANGUAGE_MAP.getOrDefault(langCode, LANGUAGE_MAP.get("en_us"));
        Map<String, String> translationsMap = new LinkedHashMap<>();

        translationsMap.put(KEY_CATEGORY_RUNTIME, values[IDX_CATEGORY]);

        String[] fallbackKeys = {
            "key.categories." + KEY_CATEGORY_RUNTIME,
            "key.category." + KEY_CATEGORY_RUNTIME,
            "key.category.minecraft." + KEY_CATEGORY_RUNTIME,
            "key.category.ags_reborn." + KEY_CATEGORY_RUNTIME,
            "key.categories.ags_reborn." + KEY_CATEGORY_RUNTIME
        };

        for (String fallbackKey : fallbackKeys) {
            translationsMap.put(fallbackKey, values[IDX_CATEGORY]);
        }

        translationsMap.put(KEY_SWITCH_RUNTIME, values[IDX_SWITCH]);
        translationsMap.put(KEY_CONTROL_TOGGLE_RUNTIME, values[IDX_CONTROL]);
        translationsMap.put(KEY_SWITCH_ON_RUNTIME, values[IDX_ON]);
        translationsMap.put(KEY_SWITCH_OFF_RUNTIME, values[IDX_OFF]);

        LOGGER.info("constructLanguageSet produced {} entries for lang {}", translationsMap.size(), langCode);
        return translationsMap;
    }

    public static String getCategoryKey() {
        return KEY_CATEGORY;
    }

    public static String getKeySwitch() {
        return KEY_SWITCH;
    }

    public static String getKeyControlToggle() {
        return KEY_CONTROL_TOGGLE;
    }

    public static String getKeySwitchOn() {
        return KEY_SWITCH_ON;
    }

    public static String getKeySwitchOff() {
        return KEY_SWITCH_OFF;
    }

    public static String getRuntimeCategoryKey() {
        return KEY_CATEGORY_RUNTIME;
    }

    public static String getRuntimeKeySwitch() {
        return KEY_SWITCH_RUNTIME;
    }

    public static String getRuntimeKeyControlToggle() {
        return KEY_CONTROL_TOGGLE_RUNTIME;
    }

    public static String getRuntimeKeySwitchOn() {
        return KEY_SWITCH_ON_RUNTIME;
    }

    public static String getRuntimeKeySwitchOff() {
        return KEY_SWITCH_OFF_RUNTIME;
    }

    public static String getStableMessageSwitchOn() {
        return KEY_SWITCH_ON;
    }

    public static String getStableMessageSwitchOff() {
        return KEY_SWITCH_OFF;
    }
}
