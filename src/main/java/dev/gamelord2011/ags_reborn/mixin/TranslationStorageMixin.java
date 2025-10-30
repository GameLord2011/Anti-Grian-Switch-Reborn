package dev.gamelord2011.ags_reborn.mixin;

import net.minecraft.client.resource.language.TranslationStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Mixin(TranslationStorage.class)
public class TranslationStorageMixin {
    @Mutable
    @Shadow
    @Final
    private Map<String, String> translations;

    private static final Set<String> TARGET_KEYS = Set.of(
        "key.category.minecraft.agsr.config.keybinds",
        "agsr.config.keybind",
        "agsr.config.ctrlheld",
        "agsr.activate.message.on",
        "agsr.activate.message.off"
    );

    private final Map<String, String> randomizedKeys = new HashMap<>();
    private boolean randomized = false;

    @Inject(method = "get", at = @At("HEAD"), cancellable = true)
    private void interceptGet(String key, String fallback, CallbackInfoReturnable<String> cir) {
        if (!randomized) {
            randomizeTargetKeys();
            randomized = true;
        }

        if (TARGET_KEYS.contains(key)) {
            String randomKey = randomizedKeys.get(key);
            if (randomKey != null && translations.containsKey(randomKey)) {
                cir.setReturnValue(translations.get(randomKey));
            }
        }
    }

    private void randomizeTargetKeys() {
        Map<String, String> mutable = new HashMap<>(translations);

        for (String key : TARGET_KEYS) {
            if (translations.containsKey(key)) {
                String randomKey = "agsr.random." + UUID.randomUUID() + UUID.randomUUID();
                randomizedKeys.put(key, randomKey);
                mutable.put(randomKey, translations.get(key));
            }
        }

        translations = mutable;
    }
}
