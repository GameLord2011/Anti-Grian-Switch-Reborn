package dev.gamelord2011.ags_reborn.mixin;

import net.minecraft.client.resource.language.TranslationStorage;

import dev.gamelord2011.ags_reborn.AgsLang;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(TranslationStorage.class)
public class TranslationStorageMixin {

    @Shadow
    private Map<String, String> translations;

    private static final AgsLang agsLang = new AgsLang();

    @Inject(method="load", at= @At("TAIL"), cancellable = true)
    private void interceptLoad(String key, String fallback, CallbackInfoReturnable<String> cir) {
        Map<String, String> dynamicTranslations = agsLang.constructLanguageSet();
        translations.putAll(dynamicTranslations);
    }

}
