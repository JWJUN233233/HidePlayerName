package me.ddggdd135.hideplayername.mixins;

import me.ddggdd135.hideplayername.utils.NameUtils;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Render.class)
public abstract class MixinRender<T extends Entity> {

    @Inject(method = "renderLivingLabel", at = @At("HEAD"))
    private void renderLivingLabel(T p_renderLivingLabel_1_, String p_renderLivingLabel_2_, double p_renderLivingLabel_3_, double p_renderLivingLabel_5_, double p_renderLivingLabel_7_, int p_renderLivingLabel_9_, CallbackInfo ci){
        p_renderLivingLabel_2_ = NameUtils.hideAll(p_renderLivingLabel_2_);
    }
}
