package dev.gamelord2011.ags_reborn;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

@Mod(modid = AntiGrianSwitchReborn.MOD_ID, clientSideOnly = true)
public class AntiGrianSwitchReborn {
    public static final String MOD_ID = "anti-grian-switch-reborn";

    public static boolean enableFallingEntityBug = false;

    private static KeyBinding TGG;
    private static KeyBinding CCB;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        // Register keybindings
        TGG = new KeyBinding("key.agsreborn.tgg", Keyboard.KEY_G, "key.categories.gameplay");
        CCB = new KeyBinding("key.agsreborn.ccb", Keyboard.KEY_Y, "key.categories.gameplay");

        ClientRegistry.registerKeyBinding(TGG);
        ClientRegistry.registerKeyBinding(CCB);

        // Register event handlers
        MinecraftForge.EVENT_BUS.register(this);

        System.out.println(MOD_ID + " initialized");
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getMinecraft().world == null) return; // Avoid NPE in menus

        boolean controlHeld = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);

        if (CCB.isKeyDown()) {
            if (TGG.isPressed() && controlHeld) {
                toggleFeature();
            }
        } else {
            // If CCB not Y, set it to N
            if (CCB.getKeyCode() != Keyboard.KEY_N) {
                CCB.setKeyCode(Keyboard.KEY_N);
            }
            if (TGG.isPressed()) {
                toggleFeature();
            }
        }
    }

    private void toggleFeature() {
        enableFallingEntityBug = !enableFallingEntityBug;
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(
                new TextComponentString(
                        "Anti Grian Switch: " + (enableFallingEntityBug ? "ON" : "OFF")
                )
        );
    }

    // Deliberately crash rendering of falling blocks
    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        if (!enableFallingEntityBug) return;

        for (Object obj : Minecraft.getMinecraft().world.loadedEntityList) {
            if (obj instanceof EntityFallingBlock) {
                Object renderer = null;
                renderer.toString(); // deliberate crash
            }
        }
    }
}
