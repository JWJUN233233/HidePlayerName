package me.ddggdd135.hideplayername.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NameUtils {
    public static Set<String> playerNames = new HashSet<>();
    @Nonnull
    public static String hide(@Nonnull String playerName) {
        try {
            return "旅行者#" + String.valueOf(Math.abs(playerName.hashCode() + 0xabcdef) * 13 + 0xabcdef).substring(1, 5);
        } catch (Exception e) {
            return "旅行者#????";
        }
    }

    @Nonnull
    public static String hideAll(@Nonnull String msg) {
        String result = msg;
        for (String name : getAllPlayers()) {
            result = result.replace(name, hide(name));
        }

        return result;
    }

    public static IChatComponent hideAll(@Nonnull IChatComponent component) {
        ChatComponentText result = new ChatComponentText("");
        if (component instanceof ChatComponentText) {
            result = new ChatComponentText(hideAll(component.getUnformattedTextForChat()));
            result.setChatStyle(component.getChatStyle());
        }
        boolean isFirst = true;
        for (IChatComponent chatComponent : component) {
            if (isFirst) {
                isFirst = false;
                continue;
            }
            result.appendSibling(hideAll(chatComponent));
        }

        return result;
    }

    @Nonnull
    public static List<String> getAllPlayers() {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;

        if (player != null) {
            World world = mc.theWorld;
            List<EntityPlayer> players = world.playerEntities;

            for (EntityPlayer p : players) {
                playerNames.add(p.getName());
            }
        }

        playerNames.remove("");

        return new ArrayList<>(playerNames);
    }
}
