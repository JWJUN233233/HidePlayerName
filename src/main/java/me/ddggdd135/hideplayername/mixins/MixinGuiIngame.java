package me.ddggdd135.hideplayername.mixins;

import me.ddggdd135.hideplayername.utils.NameUtils;
import net.minecraft.client.gui.GuiIngame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngame.class)
public abstract class MixinGuiIngame {

    @Inject(method = "displayTitle", at = @At("HEAD"))
    private void displayTitle(String p_175178_1_, String p_175178_2_, int p_175178_3_, int p_175178_4_, int p_175178_5_, CallbackInfo callbackInfo){
        p_175178_1_ = NameUtils.hideAll(p_175178_1_ != null ? p_175178_1_ : "");
        p_175178_2_ = NameUtils.hideAll(p_175178_2_ != null ? p_175178_2_ : "");
    }

    @ModifyArg(method = "renderScoreboard", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;III)I", ordinal = 0), index = 0)
    private String modifyRenderScoreboardArgumentText(String text) {
        return NameUtils.hideAll(text);
    }

    @ModifyArg(method = "renderBossHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I", ordinal = 0), index = 0)
    private String modifyRenderBossHealthArgumentText(String text) {
        return NameUtils.hideAll(text);
    }
}
