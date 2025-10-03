package dev.gamelord2011.ags_reborn;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
//import net.minecraft.client.util.InputUtil;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AntiGrianSwitchReborn implements ClientModInitializer {
	public static final String MOD_ID = "anti-grian-switch-reborn";
    public static boolean enableFallingEntityBug = false;
    private static KeyBinding TGG;
    private static KeyBinding CCB;

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // Fabric API client entrypoint (if needed, move this to a ClientModInitializer class)
    public void onInitializeClient() {
        TGG = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "Anti Grian Switch", // The translation key of the keybinding's name
            GLFW.GLFW_KEY_G, // The keycode of the key
            KeyBinding.Category.GAMEPLAY
        ));

        CCB = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "Should Control Be Held (AGS Reborn)(y/n)", // The translation key of the keybinding's name
            GLFW.GLFW_KEY_Y,
            KeyBinding.Category.GAMEPLAY
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(KeyBindingHelper.getBoundKeyOf(CCB).getCode() == GLFW.GLFW_KEY_N) {
                while (TGG.wasPressed() && ((GLFW.glfwGetKey(client.getWindow().getHandle(), GLFW.GLFW_KEY_LEFT_CONTROL) == GLFW.GLFW_PRESS) || (GLFW.glfwGetKey(client.getWindow().getHandle(), GLFW.GLFW_KEY_RIGHT_CONTROL) == GLFW.GLFW_PRESS))) {
                    enableFallingEntityBug = !enableFallingEntityBug;
                    MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(
                        net.minecraft.text.Text.literal(
                            "Anti Grian Switch: " + (AntiGrianSwitchReborn.enableFallingEntityBug ? "ON" : "OFF")
                        )
                    );
                }
            } else {
                if(KeyBindingHelper.getBoundKeyOf(CCB).getCode() != GLFW.GLFW_KEY_Y) {
                    CCB.setBoundKey(InputUtil.Type.KEYSYM.createFromCode(GLFW.GLFW_KEY_Y));
                }
                while (TGG.wasPressed()) {
                    enableFallingEntityBug = !enableFallingEntityBug;
                    MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(
                        net.minecraft.text.Text.literal(
                            "Anti Grian Switch: " + (AntiGrianSwitchReborn.enableFallingEntityBug ? "ON" : "OFF")
                        )
                    );
                }
            }
        });
    }
}
