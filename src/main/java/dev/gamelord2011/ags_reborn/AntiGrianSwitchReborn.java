package dev.gamelord2011.ags_reborn;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants.Type;

/**
 * The main class for the mod.
 * @since 1.0.0
 */
public class AntiGrianSwitchReborn implements ClientModInitializer {
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
            Component.translatable(enableFallingEntityBug ? AgsLang.getRuntimeKeySwitchOn() : AgsLang.getRuntimeKeySwitchOff()) // The odds that anyone'll see this are slim, but still possible.
                .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x9c2c2d))) // The color that Grian uses in his skin's shirt.
        );
    }

    public void onInitializeClient() {
        // register keybindings immediately using the runtime-random keys (generated at class load)
        AGSR_CONFIG = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(MOD_ID, AgsLang.getRuntimeCategoryKey()));
        AGS = KeyBindingHelper.registerKeyBinding(new KeyMapping(
            AgsLang.getRuntimeKeySwitch(),
            GLFW.GLFW_KEY_G,
            AGSR_CONFIG
        ));
        CCB = KeyBindingHelper.registerKeyBinding(new KeyMapping(
            AgsLang.getRuntimeKeyControlToggle(),
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
