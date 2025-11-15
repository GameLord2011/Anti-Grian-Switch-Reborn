package dev.gamelord2011.ags_reborn.mixin;

import dev.gamelord2011.ags_reborn.AgsLang;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.ClientLanguage;
import net.minecraft.server.packs.resources.ResourceManager;
import java.util.LinkedHashMap;
import java.util.List;
import java.lang.reflect.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mixin(ClientLanguage.class)
public class TranslationStorageMixin {
    private static final Logger LOGGER = LoggerFactory.getLogger("ags_reborn.TranslationStorageMixin");

    @Inject(method = "loadFrom", at = @At("TAIL"))
    private static void interceptLoad(ResourceManager resourceManager, List<?> list, boolean reload, CallbackInfoReturnable<ClientLanguage> cir) {
        ClientLanguage storage = cir.getReturnValue();
        if (storage == null) return;

        String langCode = "en_us"; // fallback

        try {
            langCode = Minecraft.getInstance().getLanguageManager().getSelected();
            LOGGER.info("Hey, the current language is {}", langCode);
        } catch (Throwable t) {
            LOGGER.error("Failed to get the language, sry, here's why, {}", t);
        }

        Map<String, String> dynamicTranslations = AgsLang.constructLanguageSet(langCode);
        LOGGER.info("Built {} dynamic translations for {}", dynamicTranslations.size(), langCode);

        // Inject dynamic translations into TranslationStorage
        try {
            Class<?> cls = storage.getClass();
            while (cls != null) {
                for (Field field : cls.getDeclaredFields()) {
                    if (Map.class.isAssignableFrom(field.getType())) {
                        field.setAccessible(true);
                        Object val = field.get(storage);
                        if (val instanceof Map) {
                            @SuppressWarnings("unchecked")
                            Map<String, String> translations = (Map<String, String>) val;
                            try {
                                translations.putAll(dynamicTranslations);
                                LOGGER.info("Merged dynamic translations into TranslationStorage (field {}).", field.getName());
                                return;
                            } catch (UnsupportedOperationException uoe) {
                                try {
                                    Map<String, String> newMap = new LinkedHashMap<>(translations);
                                    newMap.putAll(dynamicTranslations);
                                    field.set(storage, newMap);
                                    LOGGER.info("Replaced TranslationStorage.{} with mutable map and merged dynamic translations.", field.getName());
                                    return;
                                } catch (Throwable setException) {
                                    LOGGER.error("Failed to replace translations field: {}", setException.toString());
                                    return;
                                }
                            }
                        }
                    }
                }
                cls = cls.getSuperclass();
            }
            LOGGER.warn("No translations map field found on TranslationStorage instance to merge into.");
        } catch (Throwable exception) {
            LOGGER.error("Failed merging dynamic translations: {}", exception.toString());
        }
    }
}
