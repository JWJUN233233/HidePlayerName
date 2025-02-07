package me.ddggdd135.hideplayername;

import me.ddggdd135.hideplayername.handler.ScoreboardManager;
import me.ddggdd135.hideplayername.handler.ServerEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = HidePlayerName.MODID, version = HidePlayerName.VERSION)
public class HidePlayerName
{
    public static final String MODID = "HidePlayerName";
    public static final String VERSION = "1.0";
    public static ServerEventHandler serverEventHandler;
    public static ScoreboardManager scoreboardManager;
    public HidePlayerName() {

    }

    @EventHandler
    public void init(FMLInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(serverEventHandler = new ServerEventHandler());
        MinecraftForge.EVENT_BUS.register(scoreboardManager = new ScoreboardManager());
    }
}
