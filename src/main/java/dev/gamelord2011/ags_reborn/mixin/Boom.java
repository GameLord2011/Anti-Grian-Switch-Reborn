package dev.gamelord2011.ags_reborn.mixin;

import dev.gamelord2011.ags_reborn.AntiGrianSwitchReborn;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.FallingBlockRenderer;
import net.minecraft.client.renderer.entity.state.FallingBlockRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("null")
@Mixin(FallingBlockRenderer.class)
/**
 * Mixin to inject crash when AntiGrianSwitchReborn.enableFallingEntityBug === true && when a fallingBlockEntity is being randered.
 * @see AntiGrianSwitchReborn
 * @since 1.0.0
 * @author GameLord2011
 * @throws NullPointerException on purpose, this is not a bug.
 */
public class Boom {
	@Inject(
        method = "submit",
        at = @At("HEAD")
    )
    private void injectCrash(
        FallingBlockRenderState fallingBlockRenderState,
        PoseStack poseStack,
        SubmitNodeCollector submitNodeCollector,
        CameraRenderState cameraRenderState,
        CallbackInfo ci
	) {
		if(AntiGrianSwitchReborn.enableFallingEntityBug) {
			Object renderer = null;
			renderer.toString();
		}
	}
}
