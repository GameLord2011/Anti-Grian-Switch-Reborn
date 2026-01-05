package dev.gamelord2011.ags_reborn;

import io.github.gamelord2011.gmlrdlib.GmlrdLang;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.Style;

import java.util.Map;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants.Type;

/**
 * The main class for the mod.
 * @since 1.0.0
 */
public class AntiGrianSwitchReborn implements ClientModInitializer {
    private static final Map<String, String[][]> LANGUAGE_MAP = Map.ofEntries(
        Map.entry("en_us", new String[][]{
            new String[] {
                "Anti Grian Switch",
                "Should Control Be Held (Y/N)",
                "Anti Grian Switch: ON",
                "Anti Grian Switch: OFF"
            },
            new String[] {
                "Anti Grian Switch Reborn",
            }
        }),
        Map.entry("es_es", new String[][]{
            new String[] {
                "Interruptor Anti Grian",
                "¿Se debe mantener pulsado ctrl? (Y/N)",
                "Interruptor Anti Grian: ACTIVADO",
                "Interruptor Anti Grian: DESACTIVADO"
            },
            new String[] {
                "Interruptor Anti Grian Renacido",
            }
        }),
        Map.entry("pl_pl", new String[][]{ // Credit: @A-Potion and @Magicninja7.
            new String[] {
                "Tryb Anti Grian",
                "Czy należy trzymać ctrl? (Y/N)",
                "Tryb Anti Grian: WŁĄCZONY",
                "Tryb Anti Grian: WYŁĄCZONY"
            },
            new String[] {
                "Nowy Tryb Anti Grain",
            }
        }),
        Map.entry("en_pt", new String[][]{
            new String[] {
                "Anti-Grian Lever",
                "Be ye holdin’ Control? (Y/N)", // Ambigous unicode character alert! (U+2019)
                "Anti-Grian Lever: Aye!",
                "Anti-Grian Lever: Nay!"
            },
            new String[] {
                "Anti-Grian Switch Hoist'd Again",
            }
        }),
        Map.entry("lol_us", new String[][]{
            new String[] {
                "Anti-Grian Swich",
                "Hold CTRL? (Y/N)",
                "Anti-Grian Swich: YUS",
                "Anti-Grian Swich: NU"
            },
            new String[] {
                "Anti-Grian Swich Rebornded",
            }
        }),
        Map.entry("enws", new String[][]{
            new String[] {
                "Anti-Grian Lever",
                "Shall Control Be Held? (Yea/Nay)",
                "Anti-Grian Lever: Engaged",
                "Anti-Grian Lever: Disengaged"
            },
            new String[] {
                "Anti-Grian Lever Reawaken’d", // Ambigous unicode character alert! (U+2019)
            }
            
        }),
        Map.entry("enp", new String[][]{
            new String[] {
                "Anti-Grian Toggle",
                "Should Grasp-Key Be Held? (Y/N)",
                "Anti-Grian Toggle: ON",
                "Anti-Grian Toggle: OFF"
            },
            new String[] {
                "Anti-Grian Toggle Born Anew",
            }
        }),
        Map.entry("en_ud", new String[][]{
            new String[] {
                "ɥɔʇᴉʍS uɐᴉɹ⅁ ᴉʇu∀", // I can't even begin to count the number of ambigious unicode characters in this.
                "(N/⅄) plǝɥ ǝq loɹʇuoƆ plnoɥS",
                "NO :ɥɔʇᴉʍS uɐᴉɹ⅁ ᴉʇu∀",
                "ℲℲO :ɥɔʇᴉʍS uɐᴉɹ⅁ ᴉʇu∀"
            },
            new String[] {
                "uɹoqǝɹ ɥɔʇᴉʍS uɐᴉɹ⅁ ᴉʇu∀",
            }
        }),
        Map.entry("tlh_aa", new String[][]{
            new String[] {
                "grian wIvHa\' SeHlaw",
                "SeHlaw yI\'uch\'a\'? (Y/N)",
                "grian wIvHa\': Qap",
                "grian wIvHa\': Qapbe\'"
            },
            new String[] {
                "grian wIvHa\' chu\' Ha\'DIbaH",
            }
            
        }),
        Map.entry("qya_aa", new String[][]{
            new String[] {
                "Anti-Grian Lelya",
                "Nai mapa i cotumo? (Y/N)",
                "Anti-Grian Lelya: Calima",
                "Anti-Grian Lelya: Lómëa"
            },
            new String[] {
                "Anti-Grian Lelya Ata",
            }
        })
    );

    /**
     * The mod ID.
     */
	public static final String MOD_ID = "ags_reborn";
    /**
     * The varible that controls whether the falling entity bug is enabled.
     */
    public static boolean enableFallingEntityBug = false;
    /**
     * The keybinding for toggling the bug.
     * @since 2.0.0
     */
    private static KeyMapping AGS;
    /**
     * Some hacky configuration.
     * @since 2.0.0
     */
    private static KeyMapping CCB;

    /**
     * The category for the keybinds.
     * @since 2.0.0
     */
    public static KeyMapping.Category AGSR_CONFIG;

    /**
     * Toggles the bug and notifies the player.
     * @since 1.0.0
     * @throws NullPointerExecption but does so inderectly. <strong>This is intentional.</strong>
     */
    private void toggleBug() {
        enableFallingEntityBug = !enableFallingEntityBug;
        Minecraft.getInstance().gui.getChat().addMessage(
            Component.translatable(enableFallingEntityBug ? GmlrdLang.getRuntimeKeyFromMap(2) : GmlrdLang.getRuntimeKeyFromMap(3)) // The odds that anyone'll see this are slim, but still possible.
                .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x9c2c2d))) // The color that Grian uses in his skin's shirt.
        );
    }

    public void onInitializeClient() {
        GmlrdLang.addToLanguageSet(LANGUAGE_MAP, MOD_ID);
        AGSR_CONFIG = KeyMapping.Category.register(GmlrdLang.getIdentifier(0));
        AGS = KeyBindingHelper.registerKeyBinding(new KeyMapping(
            GmlrdLang.getRuntimeKeyFromMap(0),
            GLFW.GLFW_KEY_G,
            AGSR_CONFIG
        ));
        CCB = KeyBindingHelper.registerKeyBinding(new KeyMapping(
            GmlrdLang.getRuntimeKeyFromMap(1),
            GLFW.GLFW_KEY_Y,
            AGSR_CONFIG
        ));

        // key handling, more e-🐟-ent than you'd think (roughly +3 µs / client tick on my pc)
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (AGS == null || CCB == null) return;
            int boundKey = KeyBindingHelper.getBoundKeyOf(CCB).getValue(); // The value of the "Should Control Be Held" keybind.
            boolean agsPressed = AGS.consumeClick();
            long windowHandle = client.getWindow().handle();
            boolean ctrlPressed = 
                    GLFW.glfwGetKey(windowHandle, GLFW.GLFW_KEY_LEFT_CONTROL) == GLFW.GLFW_PRESS
                    || GLFW.glfwGetKey(windowHandle, GLFW.GLFW_KEY_RIGHT_CONTROL) == GLFW.GLFW_PRESS;

            if (boundKey == GLFW.GLFW_KEY_Y) { // Smol check, I think it's less than 1 µs, but I do need to check that.
                if (agsPressed && ctrlPressed) {
                    toggleBug();
                }
            } else {
                if (boundKey != GLFW.GLFW_KEY_N) {
                    CCB.setKey(Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_Y));
                }
                if (agsPressed) {
                    toggleBug();
                }
            }
        });
    }
}
