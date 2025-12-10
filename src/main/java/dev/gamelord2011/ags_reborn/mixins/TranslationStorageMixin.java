package dev.gamelord2011.ags_reborn.mixins;

import dev.gamelord2011.ags_reborn.AgsLang;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.ClientLanguage;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mixin(ClientLanguage.class)
/**
 * Mixin to modify the translation storage varible.
 * @since 4.0.0
 */
public class TranslationStorageMixin {
    private static final Logger LOGGER = LoggerFactory.getLogger("ags_reborn.TranslationStorageMixin");

    @ModifyVariable(
        method = "loadFrom",
        at = @At("STORE"),
        ordinal = 0
    )
    private static Map<String, String> injectDynamicTranslations(Map<String, String> map) {
        String langCode;
        try {
            langCode = Minecraft.getInstance().getLanguageManager().getSelected();
            map.putAll(AgsLang.constructLanguageSet(langCode));
        } catch (Throwable t) {
            LOGGER.error("Someone's code broke my langcode, here's the error: {}", t);
        }
        return map;
    }
}
