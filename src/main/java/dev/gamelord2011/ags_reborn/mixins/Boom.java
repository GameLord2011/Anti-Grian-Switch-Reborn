package dev.gamelord2011.ags_reborn.mixins;

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

import java.lang.NullPointerException;

@Mixin(FallingBlockRenderer.class)
/**
 * Mixin to inject crash when AntiGrianSwitchReborn.enableFallingEntityBug == true && when a fallingBlockEntity is being rendered.
 * @see AntiGrianSwitchReborn
 * @since 1.0.0
 * @author GameLord2011
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
		if (AntiGrianSwitchReborn.enableFallingEntityBug) {
			NullPointerException npe = new NullPointerException(
                "Cannot invoke \"net.fabricmc.fabric.api.renderer.v1.Renderer.meshBuilder()\" " +
                "because the return value of \"net.fabricmc.fabric.api.renderer.v1.RendererAccess.getRenderer()\" is null"
            );

            npe.setStackTrace(
                new StackTraceElement[] { // I'm gonna have fun refactoring this for 26.1, see ya obfuscation.
                    new StackTraceElement("me.pepperbell.continuity.impl.client.ProcessingContextImpl", "<init>", "ProcessingContextImpl.java", 19),
                    new StackTraceElement("me.pepperbell.continuity.client.model.CTMBakedModel$CTMQuadTransform", "<init>", "CTMBakedModel.java", 70),
                    new StackTraceElement("java.base/java.lang.ThreadLocal$SuppliedThreadLocal", "initialValue", "ThreadLocal.java", 309),
                    new StackTraceElement("java.base/java.lang.ThreadLocal", "setInitialValue", "ThreadLocal.java", 195),
                    new StackTraceElement("java.base/java.lang.ThreadLocal", "get", "ThreadLocal.java", 172),
                    new StackTraceElement("me.pepperbell.continuity.client.model.ModelObjectsContainer", "get", "ModelObjectsContainer.java", 18),
                    new StackTraceElement("me.pepperbell.continuity.impl.client.ContinuityFeatureStatesImpl", "get", "ContinuityFeatureStatesImpl.java", 16),
                    new StackTraceElement("me.pepperbell.continuity.api.client.ContinuityFeatureStates", "get", "ContinuityFeatureStates.java", 10),
                    new StackTraceElement("net.minecraft.client.renderer.entity.FallingBlockRenderer", "handler$zjb000$beforeRenderModel", "FallingBlockRenderer.java", 518),
                    new StackTraceElement("net.minecraft.client.renderer.entity.FallingBlockRenderer", "submit", "FallingBlockRenderer.java", 45),
                    new StackTraceElement("net.minecraft.client.renderer.entity.FallingBlockRenderer", "submit", "FallingBlockRenderer.java", 17),
                    new StackTraceElement("net.minecraft.client.renderer.entity.EntityRenderDispatcher", "render", "EntityRenderDispatcher.java", 141),
                    new StackTraceElement("net.minecraft.client.renderer.LevelRenderer", "renderEntity", "LevelRenderer.java", 1567),
                    new StackTraceElement("net.minecraft.client.renderer.LevelRenderer", "renderLevel", "LevelRenderer.java", 1315),
                    new StackTraceElement("net.minecraft.client.renderer.GameRenderer", "renderLevel", "GameRenderer.java", 1039),
                    new StackTraceElement("net.minecraft.client.renderer.GameRenderer", "render", "GameRenderer.java", 816),
                    new StackTraceElement("net.minecraft.client.Minecraft", "runTick", "Minecraft.java", 1143),
                    new StackTraceElement("net.minecraft.client.Minecraft", "run", "Minecraft.java", 734),
                    new StackTraceElement("net.minecraft.client.main.Main", "main", "Main.java", 237),
                    new StackTraceElement("net.fabricmc.loader.impl.game.minecraft.MinecraftGameProvider", "launch", "MinecraftGameProvider.java", 460),
                    new StackTraceElement("net.fabricmc.loader.impl.launch.knot.Knot", "launch", "Knot.java", 74),
                    new StackTraceElement("net.fabricmc.loader.impl.launch.knot.KnotClient", "main", "KnotClient.java", 23)
                }
            );

            throw npe;
		}
	}
}
