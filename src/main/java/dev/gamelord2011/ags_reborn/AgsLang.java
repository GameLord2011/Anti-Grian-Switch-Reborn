package dev.gamelord2011.ags_reborn;

import java.nio.charset.StandardCharsets;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.UUID;

import net.minecraft.client.MinecraftClient;

public class AgsLang {
    private static String getRandomKey() {
        try {
                String uuids = 
                    UUID.randomUUID().toString() + UUID.randomUUID().toString();
                MessageDigest digest = MessageDigest.getInstance("SHA3-512");
                StringBuilder hexString = new StringBuilder();

                byte[] hash = digest.digest(uuids.getBytes(StandardCharsets.UTF_8));

                for (byte b : hash) {
                    String hex = Integer.toHexString(0xff & b);
                    if (hex.length() == 1) hexString.append('0');
                    hexString.append(hex);
                }

                return 
                    UUID.randomUUID().toString() + "." +
                    UUID.randomUUID().toString() + "." +
                    hexString.toString();
            } catch (NoSuchAlgorithmException e) {
                System.out.println("[ERROR]: No such hash algorithim.");
                return 
                    UUID.randomUUID().toString() + "." +
                    UUID.randomUUID().toString() + "." +
                    UUID.randomUUID().toString();
            }
    }

    /*
        The sets are in the order of:
        keybind configuration screen,
        keybind for the swich,
        keybind for wether or not control should be held,
        turning the anti grian switch on,
        turning the anti grian switch off.
    */

    Set<String> EnglishStrings = Set.of(
        "Anti Grian Switch Reborn",
        "Anti Grian Switch",
        "Should Control Be Held (Y/N)",
        "Anti Grian Switch: ON",
        "Anti Grian Switch: OFF"
    );

    Set<String> SpanishStrings = Set.of(
        "Interruptor Anti Grian Renacido",
        "Interruptor Anti Grian",
        "¿Se debe mantener pulsado ctrl? (Y/N)",
        "Interruptor Anti Grian: ACTIVADO",
        "Interruptor Anti Grian: DESACTIVADO"
    );

    // Credit: @A-Potion and @Magicninja7
    Set<String> PolishStrings = Set.of(
        "Nowy Tryb Anti Grain",
        "Tryb Anti Grian",
        "Czy należy trzymać ctrl? (Y/N)",
        "Tryb Anti Grian: WŁĄCZONY",
        "Tryb Anti Grian: WYŁĄCZONY"
    );

    private static Map<String, String> Lang = null;
    private static List<String> orderedKeys = null;

    public StringBuilder keyConfigScreen = new StringBuilder("Anti Grian Switch Reborn");
    
    private void initializeIfNeeded() {
        if (Lang == null) {
            Lang = new LinkedHashMap<>();
            orderedKeys = new ArrayList<>();
        }
    }

    public String keySwitch() {
        return orderedKeys.size() > 1 ? orderedKeys.get(1) : "default.switch";
    }

    public String keyControlToggle() {
        return orderedKeys.size() > 2 ? orderedKeys.get(2) : "default.control.toggle";
    }

    public String keySwitchOn() {
        return orderedKeys.size() > 3 ? orderedKeys.get(3) : "default.switch.on";
    }

    public String keySwitchOff() {
        return orderedKeys.size() > 4 ? orderedKeys.get(4) : "default.switch.off";
    }

    private String getCurrentLangCode() {
        MinecraftClient client = MinecraftClient.getInstance();
        return client.getLanguageManager().getLanguage();
    }

    public Map<String, String> constructLanguageSet() {
        if (Lang != null) {
            return Lang;
        }

        initializeIfNeeded();
        String langCode = getCurrentLangCode();
        Set<String> selectedSet;

        if (langCode.equals("en_us")) {
            selectedSet = EnglishStrings;
        } else if (langCode.equals("es_es")) {
            selectedSet = SpanishStrings;
        } else if (langCode.equals("pl_pl")) {
            selectedSet = PolishStrings;
        } else {
            selectedSet = EnglishStrings;
        }

        for (String value : selectedSet) {
            String key = getRandomKey();
            Lang.put(key, value);
            orderedKeys.add(key);
        }

        return Lang;
    }
}
