package me.ddggdd135.hideplayername.handler;

import me.ddggdd135.hideplayername.utils.NameUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Collection;

public class ServerEventHandler {
    @SubscribeEvent
    public void onServerChat(ClientChatReceivedEvent event) {
        IChatComponent originalMessage = event.message;

        event.message = NameUtils.hideAll(originalMessage);
    }

    @SubscribeEvent
    public void onPlayerName(PlayerEvent.NameFormat event) {
        event.displayname = NameUtils.hide(event.username);
    }

    @SubscribeEvent
    public void onTAB(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            return;
        }

        Minecraft minecraft = Minecraft.getMinecraft();
        EntityPlayerSP player = minecraft.thePlayer;
        if (player == null) return;
        NetHandlerPlayClient sendQueue = player.sendQueue;
        if (sendQueue == null) return;
        Collection<NetworkPlayerInfo> playerInfos = sendQueue.getPlayerInfoMap();
        if (playerInfos == null) return;

        for (NetworkPlayerInfo playerInfo : playerInfos) {
            String originalName = playerInfo.getGameProfile().getName();
            playerInfo.setDisplayName(new ChatComponentText(NameUtils.hide(originalName)));
        }
    }

    @SubscribeEvent
    public void onRenderLivingEvent(RenderLivingEvent.Pre<EntityLivingBase> e) {
        EntityLivingBase entity = e.entity;
        if (entity.hasCustomName()) {
            entity.setCustomNameTag(NameUtils.hideAll(entity.getCustomNameTag()));
        }
    }
}
