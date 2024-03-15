/*
 * Copyright (C) 2024 Ambossmann <https://github.com/Ambossmann>
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
 * <https://github.com/Ambossmann/HBWHelper>.
 */
package io.github.leo3418.hbwhelper.neoforge;

import io.github.leo3418.hbwhelper.ConfigManager;
import io.github.leo3418.hbwhelper.HbwHelper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
@Mod(HbwHelper.MOD_ID)
public final class HbwHelperNeoForge {

    public HbwHelperNeoForge(IEventBus modEventBus) {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigManager.getSpec());
        modEventBus.addListener(this::clientSetup);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        HbwHelper.init();
    }
}