package me.ddggdd135.hideplayername.handler;

import me.ddggdd135.hideplayername.utils.NameUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ScoreboardManager {
    private String title;
    private List<String> content;
    public ScoreboardManager() {
        this.title = "";
        this.content = new ArrayList<>();
    }


    public void updateScoreboardContent() {
        if (Minecraft.getMinecraft() == null || Minecraft.getMinecraft().theWorld == null) {
            return;
        }
        Scoreboard scoreboard = Minecraft.getMinecraft().theWorld.getScoreboard();
        ScoreObjective sidebarObjective = scoreboard.getObjectiveInDisplaySlot(1);
        if (sidebarObjective == null) {
            return;
        }

        this.title = sidebarObjective.getDisplayName();

        List<String> scoreboardLines = new ArrayList<>();
        Collection<Score> scores = scoreboard.getSortedScores(sidebarObjective);
        List<Score> filteredScores = scores.stream()
                .filter(p_apply_1_ -> p_apply_1_.getPlayerName() != null && !p_apply_1_.getPlayerName().startsWith("#")).collect(Collectors.toCollection(CopyOnWriteArrayList::new));
        if (filteredScores.isEmpty()) {
            return;
        } else {
            scores = filteredScores;
        }
        Collections.reverse(filteredScores);
        for (Score line : scores) {
            ScorePlayerTeam team = scoreboard.getPlayersTeam(line.getPlayerName());
            String scoreboardLine = ScorePlayerTeam.formatPlayerName(team, line.getPlayerName()).trim();
            scoreboardLines.add(NameUtils.hideAll(scoreboardLine));
        }



        this.content = scoreboardLines;


    }

    public void clear() {
        this.title = "";
        this.content = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public List<String> getContents() {
        return content;
    }

    public String getContent(int row) {
        if (row > this.getSize() || row < 1) {
            return "";
        }
        return content.get(row - 1);
    }

    public int getSize() {
        return content.size();
    }

    private int tick;
    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event){
        Minecraft mc = Minecraft.getMinecraft();
        if (mc == null || mc.theWorld == null || mc.isSingleplayer()) {
            return;
        }

        if(event.phase != TickEvent.Phase.START) {
            return;
        }

        EntityPlayerSP p = mc.thePlayer;

        if(p == null) {
            return;
        }

        tick ++;

        if (tick != 0 && tick % 5 == 0) {
            this.updateScoreboardContent();
            tick = 0;
        }
    }
}

