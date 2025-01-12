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
package io.github.leo3418.hbwhelper.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import io.github.leo3418.hbwhelper.game.GameType;
public interface GameEvent {

    Event<ClientJoinInProgressGame> CLIENT_JOIN_IN_PROGRESS_GAME = EventFactory.createLoop();

    Event<ClientLeaveGame> CLIENT_LEAVE_GAME = EventFactory.createLoop();

    Event<ClientRejoinGame> CLIENT_REJOIN_GAME = EventFactory.createLoop();

    Event<GameStart> GAME_START = EventFactory.createLoop();

    Event<GameTypeDetected> GAME_TYPE_DETECTED = EventFactory.createLoop();

    Event<TeleportCancelled> TELEPORT_CANCELLED = EventFactory.createLoop();

    interface ClientJoinInProgressGame {
        void join();
    }

    interface ClientLeaveGame {
        void leave();
    }

    interface ClientRejoinGame {
        void rejoin();
    }

    interface GameStart {
        void start();
    }

    interface GameTypeDetected {
        void gameTypeDetected(GameType gameType);
    }

    interface TeleportCancelled {
        void teleportCancelled();
    }
}
