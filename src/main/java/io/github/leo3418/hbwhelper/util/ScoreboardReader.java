/*
 * HBW Helper: Hypixel Bed Wars Helper Minecraft Forge Mod
 * Copyright (C) 2018 Leo3418
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.leo3418.hbwhelper.util;

import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

/**
 * Provides methods for reading the scoreboard and getting information from it.
 * <p>
 * Like some other classes under this package, this class is designed <b>to be
 * used only when the client is in a Minecraft world</b>. Calling some methods
 * when the client is not in a Minecraft world (e.g. in the main menu) might
 * produce {@link NullPointerException}.
 *
 * @author Leo
 */
public class ScoreboardReader {
    /**
     * Pattern of regular expression of formatting codes in Minecraft
     * <p>
     * I have to use the Unicode encoding for the section sign ({@code §})
     * because Minecraft is so mean that it does not want the player to use
     * this sign in game to produce formatted text, even if the player is
     * merely using it in a mod's code.
     *
     * @see <a href="https://minecraft.gamepedia.com/Formatting_codes"
     *         target="_top">Formatting codes in Minecraft</a>
     * @see <a href="https://en.wikipedia.org/wiki/Section_sign">
     *         The section sign's information on Wikipedia</a>
     */
    private static final Pattern FORMATTING_PATTERN =
            Pattern.compile("\u00A7[0-9a-fk-or]");

    /**
     * Prevents instantiation of this class.
     */
    private ScoreboardReader() {
    }

    /**
     * Returns whether any line on the scoreboard contains a piece of text.
     * <p>
     * The formatting codes of all lines on the scoreboard will be removed for
     * the sake of this method.
     *
     * @param text the text to be matched
     * @return whether any line on the scoreboard contains a piece of text.
     */
    public static boolean contains(String text) {
        Collection<String> lines = getLines();
        for (String line : lines) {
            if (line.contains(text)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the size of the scoreboard.
     *
     * @return the size of the scoreboard
     */
    private static int getSize() {
        return Minecraft.getMinecraft().world.getScoreboard().getScores()
                .size();
    }

    /**
     * Returns a {@link Collection} of all lines on the scoreboard without
     * formatting codes.
     *
     * @return a {@code Collection} of all lines on the scoreboard without
     *         formatting codes
     * @see #FORMATTING_PATTERN Pattern of regular expression of formatting
     *         codes in Minecraft
     */
    private static Collection<String> getLines() {
        Scoreboard scoreboard = Minecraft.getMinecraft().world.getScoreboard();
        Collection<Score> scores = scoreboard.getScores();
        Collection<String> lines = new ArrayList<>(getSize());
        for (Score score : scores) {
            ScorePlayerTeam line = scoreboard
                    .getPlayersTeam(score.getPlayerName());
            String lineText = FORMATTING_PATTERN
                    .matcher(ScorePlayerTeam.formatPlayerName(line,
                            score.getPlayerName()))
                    .replaceAll("");
            lines.add(lineText);
        }
        return lines;
    }
}