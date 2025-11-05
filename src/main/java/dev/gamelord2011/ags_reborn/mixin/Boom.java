package dev.gamelord2011.ags_reborn.mixin;

import dev.gamelord2011.ags_reborn.AntiGrianSwitchReborn;

import net.minecraft.client.render.entity.FallingBlockEntityRenderer;
import net.minecraft.client.render.entity.state.FallingBlockEntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("null")
@Mixin(FallingBlockEntityRenderer.class)
public class Boom {
	@Inject(
    method = "render(Lnet/minecraft/client/render/entity/state/FallingBlockEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V",
    at = @At("HEAD")
)
private void injectCrash(
    FallingBlockEntityRenderState fallingBlockEntityRenderState,
    MatrixStack matrixStack,
    OrderedRenderCommandQueue orderedRenderCommandQueue,
    CameraRenderState cameraRenderState,
    CallbackInfo ci
	) {
		if(AntiGrianSwitchReborn.enableFallingEntityBug) {
			Object renderer = null;
			renderer.toString();
		}
	}
}
