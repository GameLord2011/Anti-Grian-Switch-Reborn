package dev.gamelord2011.ags_reborn;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AntiGrianSwitchReborn implements ClientModInitializer {
	public static final String MOD_ID = "anti-grian-switch-reborn";
    public static boolean enableFallingEntityBug = false;
    private static KeyBinding TGG;

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // Fabric API client entrypoint (if needed, move this to a ClientModInitializer class)
    public void onInitializeClient() {
        TGG = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "toggle.grian.keybind", // The translation key of the keybinding's name
            InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
            GLFW.GLFW_KEY_GRAVE_ACCENT, // The keycode of the key
            "what.do.you.do.with.this" // The translation key of the keybinding's category.
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (TGG.wasPressed()) {
                enableFallingEntityBug = !enableFallingEntityBug;
                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(
                    net.minecraft.text.Text.literal(
                        "Anti Grian Switch: " + (AntiGrianSwitchReborn.enableFallingEntityBug ? "ON" : "OFF")
                    )
                );
            }
        });
    }
}
