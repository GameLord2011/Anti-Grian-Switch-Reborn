package dev.gamelord2011.ags_reborn.mixins;

import dev.gamelord2011.ags_reborn.AntiGrianSwitchReborn;

import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.RenderBuffers;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import java.lang.NullPointerException;

@Mixin(GameRenderer.class)
/**
 * Mixin to inject crash when AntiGrianSwitchReborn.enableFallingEntityBug == true && when a fallingBlockEntity is being rendered.
 * @see AntiGrianSwitchReborn
 * @since 1.0.0
 * @author GameLord2011
 */
public class Boom {
	@Inject(
        method = "GameRenderer",
        at = @At("HEAD")
    )
    private void injectCrash(
        Minecraft minecraft,
        ItemInHandRenderer itemInHandRenderer,
        RenderBuffers renderBuffers,
        BlockRenderDispatcher blockRenderDispatcher
	) {
		if (AntiGrianSwitchReborn.enableFallingEntityBug) {
			throw new NullPointerException();
		}
	}
}