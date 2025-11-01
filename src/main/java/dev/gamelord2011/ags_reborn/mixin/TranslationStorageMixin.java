package dev.gamelord2011.ags_reborn.mixin;

import net.minecraft.client.resource.language.TranslationStorage;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

/*
    Explination of code (not a javadoc):
    It goes through each key whilst they're mapping, and if it's one of the keys in
    TARGET_KEYS, it then replaces it with a pseudo-random key. Here is a graphic
    (looks best in monospaced font):

    + <-- translation key load (either on init, or resource pack swap).
    |
    +---+ <-- if it's a key in TARGET_KEYS, swap it out with a key generated like this:
    |   |     "agsr.random." + sha3-512_hash(RANDOM_UUID + RANDOM_UUID) (psuedocode
    |   |     ftw)
    |   |
    |   |
    +---+
    |
    + <-- end of load

    It obfuscates it while still making it readable to the game.

*/

@Mixin(TranslationStorage.class)
public class TranslationStorageMixin {

    private static final Set<String> TARGET_KEYS = Set.of(
        "key.category.minecraft.agsr.config.keybinds",
        "agsr.config.keybind",
        "agsr.config.ctrlheld",
        "agsr.activate.message.on",
        "agsr.activate.message.off"
    );

    private final Map<String, String> randomizedTranslations = new HashMap<>();
    private boolean randomized = false;

    @Inject(method = "get", at = @At("HEAD"), cancellable = true)
    private void interceptGet(
            String key,
            String fallback,
            CallbackInfoReturnable<String> cir
        ) {
        TranslationStorageAccessor accessor = (TranslationStorageAccessor) (Object) this;
        Map<String, String> original = accessor.getTranslations();

        if (!randomized) {
            for (String targetKey : TARGET_KEYS) {
                if (original.containsKey(targetKey)) {
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

                        String randomKey = "agsr.random." + hexString.toString();
                        randomizedTranslations.put(targetKey, randomKey);
                        randomizedTranslations.put(randomKey, original.get(targetKey));
                        System.out.println(
                            "[AGSR]: Randomization triggered, " + targetKey + " is now " + randomKey
                        );
                    } catch (NoSuchAlgorithmException e) {
                        System.out.println("[ERROR]: No such hash algorithim.");
                    }
                }
            }
            randomized = true;
        }

        if (TARGET_KEYS.contains(key)) {
            String randomKey = randomizedTranslations.get(key);
            if (randomKey != null && randomizedTranslations.containsKey(randomKey)) {
                cir.setReturnValue(randomizedTranslations.get(randomKey));
            }
        }
    }
}
