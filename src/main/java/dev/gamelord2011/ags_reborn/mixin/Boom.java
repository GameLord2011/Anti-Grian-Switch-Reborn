package dev.gamelord2011.ags_reborn.mixin;

import dev.gamelord2011.ags_reborn.AntiGrianSwitchReborn;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("null")
@Mixin(GameRenderer.class)
public class Boom {
	@Inject(
    method = "render",
    at = @At("HEAD")
)
private void inijectCrash(
        RenderTickCounter tickCounter,
        boolean renderWorld,
        CallbackInfo ci
	) {
		if(AntiGrianSwitchReborn.enableFallingEntityBug) {
			Object renderer = null;
			renderer.toString();
		}
	}
}
