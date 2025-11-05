package dev.gamelord2011.ags_reborn;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import net.minecraft.text.Text;

import org.lwjgl.glfw.GLFW;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AntiGrianSwitchReborn implements ClientModInitializer {
	public static final String MOD_ID = "ags_reborn";
    public static boolean enableFallingEntityBug = false;
    private static KeyBinding TGG;
    private static KeyBinding CCB;
    private static final AgsLang agsLang = new AgsLang();

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final KeyBinding.Category AGSR_CONFIG = KeyBinding.Category.create(
        Identifier.of(MOD_ID, agsLang.keyConfigScreen.toString())
    );

    private void toggleBug() {
        enableFallingEntityBug = !enableFallingEntityBug;
        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(
            Text.of(enableFallingEntityBug ? agsLang.keySwitchOn() : agsLang.keySwitchOff())
        );
    }

    public void onInitializeClient() {
        // Initialize translations once at startup
        agsLang.constructLanguageSet();

        TGG = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            agsLang.keySwitch(),
            GLFW.GLFW_KEY_G,
            AGSR_CONFIG
        ));

        CCB = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            agsLang.keyControlToggle(),
            GLFW.GLFW_KEY_Y,
            AGSR_CONFIG
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            int boundKey = KeyBindingHelper.getBoundKeyOf(CCB).getCode();
            boolean tggPressed = TGG.wasPressed();
        
            if (boundKey == GLFW.GLFW_KEY_Y) {
                boolean ctrlPressed = 
                GLFW.glfwGetKey(client.getWindow().getHandle(), GLFW.GLFW_KEY_LEFT_CONTROL) == GLFW.GLFW_PRESS
                || GLFW.glfwGetKey(client.getWindow().getHandle(), GLFW.GLFW_KEY_RIGHT_CONTROL) == GLFW.GLFW_PRESS;
        
                if (tggPressed && ctrlPressed) {
                    toggleBug();
                }
            } else {
                if (boundKey != GLFW.GLFW_KEY_N) {
                    CCB.setBoundKey(InputUtil.Type.KEYSYM.createFromCode(GLFW.GLFW_KEY_Y));
                }
                if (tggPressed) {
                    toggleBug();
                }
            }
        });
    }
}
