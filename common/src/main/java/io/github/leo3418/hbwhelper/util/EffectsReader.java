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
package io.github.leo3418.hbwhelper.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;

import java.util.Collection;
import java.util.Objects;

/**
 * Provides methods for getting the player's status effects.
 * <p>
 * Like some other classes under this package, this class is designed <b>to be
 * used only when the client is in a Minecraft world</b>. Calling some methods
 * when the client is not in a Minecraft world (e.g. in the main menu) might
 * produce {@link NullPointerException}.
 *
 * @author Leo
 */
public class EffectsReader {
    /**
     * Number of game ticks per second in Minecraft
     */
    private static final int TICKS_PER_SECOND = 20;

    /**
     * Prevents instantiation of this class.
     */
    private EffectsReader() {
    }

    /**
     * Returns a {@link Collection} of all potion effects on the player.
     *
     * @return a {@code Collection} of all potion effects on the player
     */
    public static Collection<MobEffectInstance> getEffects() {
        return Objects.requireNonNull(Minecraft.getInstance().player)
                .getActiveEffects();
    }

    /**
     * Returns the displayed amplifier of a potion effect.
     * <p>
     * The amplifier returned is equivalent to the Roman numeral displayed in
     * game (e.g. 1 represents I, 2 represents II, 3 represents III).
     *
     * @param potionEffect the potion effect whose amplifier is queried
     * @return the displayed amplifier of a potion effect
     */
    public static int getDisplayedAmplifier(MobEffectInstance potionEffect) {
        // Internal amplifier is 0 for level I, 1 for level II, etc.
        int internalAmplifier = potionEffect.getAmplifier();
        return internalAmplifier + 1;
    }

    /**
     * Returns duration of a potion effect as how they are shown in the client.
     *
     * @param potionEffect the potion effect whose duration is queried
     * @return duration of the potion effect as how they are shown in the client
     */
    public static String getDisplayedDuration(MobEffectInstance potionEffect) {
        return MobEffectUtil.formatDuration(potionEffect, 1.0F, Minecraft.getInstance().level.tickRateManager().tickrate()).getString();
    }

    /**
     * Returns duration of a potion effect in seconds, or {@code -1} if it has
     * maximum duration.
     *
     * @param potionEffect the potion effect whose duration is queried
     * @return duration of the potion effect in seconds, or {@code -1} if it has
     *         maximum duration
     */
    public static int getDuration(MobEffectInstance potionEffect) {
        if (potionEffect.isInfiniteDuration()) {
            return -1;
        }
        return potionEffect.getDuration() / TICKS_PER_SECOND;
    }

    /**
     * Returns the icon of a potion effect.
     *
     * @param potionEffect the potion effect whose icon is queried
     * @return the icon of the potion effect
     */
    public static TextureAtlasSprite getIcon(MobEffectInstance potionEffect) {
        return Minecraft.getInstance().getMobEffectTextures()
                .get(potionEffect.getEffect());
    }
}
