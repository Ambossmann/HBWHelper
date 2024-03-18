/*
 * Copyright (C) 2024 Ambossmann <https://github.com/Ambossmann>
 * Copyright (C) 2018-2021 Leo3418 <https://github.com/Leo3418>
 *
 * This file is part of Hypixel Bed Wars Helper - Sleepover Edition (HBW Helper SE).
 *
 * HBW Helper SE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License (GPL) as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * HBW Helper SE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * Under section 7 of GPL version 3, you are granted additional
 * permissions described in the HBW Helper MC Exception.
 *
 * You should have received a copy of the GNU GPL and a copy of the
 * HBW Helper MC Exception along with this program's source code; see
 * the files LICENSE.txt and LICENSE-MCE.txt respectively.  If not, see
 * <http://www.gnu.org/licenses/> and
 * <https://github.com/Anvil-Mods/HBWHelper>.
 */
package io.github.leo3418.hbwhelper;

import dev.architectury.event.CompoundEventResult;
import dev.architectury.event.events.client.*;
import dev.architectury.hooks.client.screen.ScreenAccess;
import io.github.leo3418.hbwhelper.event.GameEvent;
import io.github.leo3418.hbwhelper.game.GameManager;
import io.github.leo3418.hbwhelper.game.GameType;
import io.github.leo3418.hbwhelper.game.GameTypeDetector;
import io.github.leo3418.hbwhelper.gui.HudGui;
import io.github.leo3418.hbwhelper.util.GameDetector;
import io.github.leo3418.hbwhelper.util.HypixelDetector;
import io.github.leo3418.hbwhelper.util.InProgressGameDetector;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

/**
 * Event manager of this mod, which responds to events fired on Minecraft Forge's event bus and this
 * mod's {@link EventManager#EVENT_BUS proprietary event bus}, and also holds that proprietary event
 * bus.
 *
 * <p>This class is the place where most core behaviors of this mod is defined. It decides what
 * actions to take upon each kind of event, whereas other classes define the actions' details and
 * complete the actions.
 *
 * <p>This is a Singleton class. Only one instance of this class may be created per runtime.
 *
 * @author Leo
 */
public class EventManager {

    /**
     * {@link Component} object storing prompt being shown when client rejoins a Bed Wars game it was
     * in before after Minecraft restarts
     */
    private static final Component CLIENT_RESTART_PROMPT =
            Component.translatable("hbwhelper.messages.clientRestart", HbwHelper.NAME);

    /**
     * {@link Component} object storing prompt being shown when client rejoins a Bed Wars game it was
     * in before, but Minecraft has not been restarted
     */
    private static final Component CLIENT_REJOIN_PROMPT =
            Component.translatable("hbwhelper.messages.clientRejoin", HbwHelper.NAME);

    /** The only instance of this class */
    private static final EventManager INSTANCE = new EventManager();

    /** The {@link HypixelDetector} instance */
    private final HypixelDetector hypixelDetector;

    /** The {@link GameDetector} instance */
    private final GameDetector gameDetector;

    /** The {@link InProgressGameDetector} instance */
    private final InProgressGameDetector ipGameDetector;

    /** The {@link GameTypeDetector} instance */
    private final GameTypeDetector gameTypeDetector;

    /** The {@link HudGui} instance */
    private final HudGui hudGui;

    /**
     * Whether the current {@link GameManager} instance returned by {@link GameManager#getInstance()}
     * should be cleared when client switches to the next Bed Wars game
     *
     * <p>If this boolean's value is set to {@code true}, it should be changed to {@code false} when
     * one of the following conditions is satisfied:
     *
     * <ul>
     *   <li>Client leaves the current Bed Wars game and joins the next game
     *   <li>Client was being transferred to another in-progress Bed Wars game, but the teleport is
     *       cancelled for whatever reason
     * </ul>
     */
    private boolean shouldClearGMInstance;

    /**
     * Implementation of Singleton design pattern, which allows only one instance of this class to be
     * created.
     */
    private EventManager() {
        hypixelDetector = HypixelDetector.getInstance();
        gameDetector = GameDetector.getInstance();
        ipGameDetector = InProgressGameDetector.getInstance();
        gameTypeDetector = GameTypeDetector.getInstance();
        hudGui = HudGui.getInstance();
    }

    /**
     * Returns the instance of this class.
     *
     * @return the instance of this class
     */
    static EventManager getInstance() {
        return INSTANCE;
    }

    void register() {
        ClientChatEvent.RECEIVED.register(this::onClientChatReceived);
        ClientSystemMessageEvent.RECEIVED.register(m -> this.onClientChatReceived(null, m));
        ClientTickEvent.CLIENT_POST.register(this::onClientTick);
        ClientGuiEvent.RENDER_HUD.register(this::onRenderGameOverlay);
        ClientGuiEvent.INIT_POST.register(this::onGuiOpen);
        ClientPlayerEvent.CLIENT_PLAYER_JOIN.register(
                player -> this.onClientPlayerNetworkEvent(NetworkEventType.LOGGING_IN));
        ClientPlayerEvent.CLIENT_PLAYER_QUIT.register(
                player -> this.onClientPlayerNetworkEvent(NetworkEventType.LOGGING_OUT));
        GameEvent.CLIENT_JOIN_IN_PROGRESS_GAME.register(this::onClientJoinIPGame);
        GameEvent.CLIENT_REJOIN_GAME.register(this::onClientRejoinGame);
        GameEvent.CLIENT_LEAVE_GAME.register(this::onClientLeaveGame);
        GameEvent.GAME_START.register(this::onGameStart);
        GameEvent.GAME_TYPE_DETECTED.register(this::onGameTypeDetected);
        GameEvent.TELEPORT_CANCELLED.register(this::onTeleportCancelled);
    }

    public void onClientPlayerNetworkEvent(NetworkEventType networkEventType) {
        hypixelDetector.update(networkEventType);
        gameDetector.update(networkEventType);
    }

    public void onGuiOpen(Screen screen, ScreenAccess access) {
        gameDetector.update(screen);
    }

    public CompoundEventResult<Component> onClientChatReceived(
            ChatType.Bound type, Component message) {
        gameDetector.update(message);
        ipGameDetector.detect(message);
        if (gameDetector.isIn() && GameManager.getInstance() != null) {
            GameManager.getInstance().update(message);
        }
        return CompoundEventResult.pass();
    }

    public void onRenderGameOverlay(GuiGraphics graphics, float tickDelta) {
        hudGui.render(graphics);
    }

    public void onClientTick(Minecraft mc) {
        gameTypeDetector.detect();
    }

    public void onGameStart() {
        GameManager.clearInstance();
        gameTypeDetector.startDetection();
        HbwHelper.LOGGER.info("Game started");
    }

    public void onClientJoinIPGame() {
        if (gameDetector.isIn()) {
            shouldClearGMInstance = true;
        } else {
            GameManager.clearInstance();
        }
    }

    public void onClientRejoinGame() {
        if (shouldClearGMInstance) {
            GameManager.clearInstance();
            shouldClearGMInstance = false;
        }
        Player player = Objects.requireNonNull(Minecraft.getInstance().player);
        if (GameManager.getInstance() == null) {
            // Client is rejoining a Bed Wars game after restart of Minecraft
            player.sendSystemMessage(CLIENT_RESTART_PROMPT);
            gameTypeDetector.startDetection();
        } else {
            // Client is rejoining a Bed Wars game, but Minecraft is not closed
            player.sendSystemMessage(CLIENT_REJOIN_PROMPT);
        }
    }

    public void onClientLeaveGame() {
        gameTypeDetector.stopDetection();
    }

    public void onGameTypeDetected(GameType gameType) {
        GameManager.createInstance(gameType);
    }

    public void onTeleportCancelled() {
        shouldClearGMInstance = false;
    }

    public enum NetworkEventType {
        LOGGING_IN,
        LOGGING_OUT,
        RESPAWN
    }
}
