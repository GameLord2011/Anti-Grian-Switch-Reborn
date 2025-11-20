package dev.gamelord2011.ags_reborn;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

/**
 * The main class for the mod.
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
    private static KeyMapping TGG;
    private static KeyMapping CCB;

    /**
     * The category for the keybinds.
     */
    public static KeyMapping.Category AGSR_CONFIG;

    private void toggleBug() {
        enableFallingEntityBug = !enableFallingEntityBug;
        Minecraft.getInstance().gui.getChat().addMessage(
            // display translatable using whatever key was injected for the runtime message key
            Component.translatable(enableFallingEntityBug ? AgsLang.getRuntimeKeySwitchOn() : AgsLang.getRuntimeKeySwitchOff())
        );
    }

    public void onInitializeClient() {
        // register keybindings immediately using the runtime-random keys (generated at class load)
        AGSR_CONFIG = KeyMapping.Category.register(ResourceLocation.fromNamespaceAndPath(MOD_ID, AgsLang.getRuntimeCategoryKey()));
        TGG = KeyBindingHelper.registerKeyBinding(new KeyMapping(
            AgsLang.getRuntimeKeySwitch(),
            GLFW.GLFW_KEY_G,
            AGSR_CONFIG
        ));
        CCB = KeyBindingHelper.registerKeyBinding(new KeyMapping(
            AgsLang.getRuntimeKeyControlToggle(),
            GLFW.GLFW_KEY_Y,
            AGSR_CONFIG
        ));

        // key handling
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (TGG == null || CCB == null) return;
            int boundKey = KeyBindingHelper.getBoundKeyOf(CCB).getValue();
            boolean tggPressed = TGG.consumeClick();

            if (boundKey == GLFW.GLFW_KEY_Y) {
                boolean ctrlPressed = 
                    GLFW.glfwGetKey(client.getWindow().handle(), GLFW.GLFW_KEY_LEFT_CONTROL) == GLFW.GLFW_PRESS
                    || GLFW.glfwGetKey(client.getWindow().handle(), GLFW.GLFW_KEY_RIGHT_CONTROL) == GLFW.GLFW_PRESS;

                if (tggPressed && ctrlPressed) {
                    toggleBug();
                }
            } else {
                if (boundKey != GLFW.GLFW_KEY_N) {
                    CCB.setKey(InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_Y));
                }
                if (tggPressed) {
                    toggleBug();
                }
            }
        });
    }
}
