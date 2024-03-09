/*
 * Copyright (C) 2018-2021 Leo3418 <https://github.com/Leo3418>
 *
 * This file is part of Hypixel Bed Wars Helper (HBW Helper).
 *
 * HBW Helper is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License (GPL) as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * HBW Helper is distributed in the hope that it will be useful,
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
 * <https://github.com/Leo3418/HBWHelper>.
 */

package io.github.leo3418.hbwhelper.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.leo3418.hbwhelper.ConfigManager;
import io.github.leo3418.hbwhelper.HbwHelper;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * The GUI of this mod's configuration screen.
 *
 * @author Leo
 */
public final class ConfigScreen extends Screen {
    /**
     * URL to the help page with more information about this mod's settings
     */
    private static final String MORE_INFO_URL =
            "https://github.com/Leo3418/HBWHelper/wiki/Mod-Configuration#options";

    /**
     * Distance between this GUI's title and the top of the screen
     */
    private static final int TITLE_HEIGHT = 8;

    /**
     * Distance between the options list's top and the top of the screen
     */
    private static final int OPTIONS_LIST_TOP_HEIGHT = 24;

    /**
     * Distance between the options list's bottom and the bottom of the screen
     */
    private static final int OPTIONS_LIST_BOTTOM_OFFSET = 32;

    /**
     * Distance between the top of each button below the options list and the
     * bottom of the screen
     */
    private static final int BOTTOM_BUTTON_HEIGHT_OFFSET = 26;

    /**
     * Height of each item in the options list
     */
    private static final int OPTIONS_LIST_ITEM_HEIGHT = 25;

    /**
     * Width of each button below the options list
     */
    private static final int BOTTOM_BUTTON_WIDTH = 150;

    /**
     * The {@link ConfigManager} instance
     */
    private static final ConfigManager CMI = ConfigManager.getInstance();

    /**
     * The parent screen of this screen
     */
    private final Screen parentScreen;

    /**
     * The object for registering options on this screen and controlling how
     * they are presented
     */
    private OptionsList optionsRowList;

    /**
     * Constructs a new {@link ConfigScreen} instance.
     *
     * @param parentScreen the screen that will become this screen's parent
     */
    public ConfigScreen(Screen parentScreen) {
        super(Component.translatable("hbwhelper.configGui.title",
                HbwHelper.NAME));
        this.parentScreen = parentScreen;
    }

    /**
     * Initializes this GUI with options list and buttons.
     */
    @Override
    protected void init() {
//        this.optionsRowList = new OptionsList(
//                Objects.requireNonNull(this.minecraft), this.width, this.height,
//                OPTIONS_LIST_TOP_HEIGHT,
//                this.height - OPTIONS_LIST_BOTTOM_OFFSET,
//                OPTIONS_LIST_ITEM_HEIGHT);
//        this.optionsRowList.addBig(new BooleanOption(
//                "hbwhelper.configGui.showGenerationTimes.title",
//                unused -> CMI.showGenerationTimes(),
//                (unused, newValue) -> CMI.changeShowGenerationTimes(newValue)
//        ));
//        this.optionsRowList.addBig(new BooleanOption(
//                "hbwhelper.configGui.showTeamUpgrades.title",
//                unused -> CMI.showTeamUpgrades(),
//                (unused, newValue) -> CMI.changeShowTeamUpgrades(newValue)
//        ));
//        this.optionsRowList.addBig(new BooleanOption(
//                "hbwhelper.configGui.showArmorInfo.title",
//                unused -> CMI.showArmorInfo(),
//                (unused, newValue) -> CMI.changeShowArmorInfo(newValue)
//        ));
//        this.optionsRowList.addBig(new BooleanOption(
//                "hbwhelper.configGui.showEffectsInfo.title",
//                unused -> CMI.showEffectsInfo(),
//                (unused, newValue) -> CMI.changeShowEffectsInfo(newValue)
//        ));
//        this.optionsRowList.addBig(new BooleanOption(
//                "hbwhelper.configGui.alwaysShowEffects.title",
//                unused -> CMI.alwaysShowEffects(),
//                (unused, newValue) -> CMI.changeAlwaysShowEffects(newValue)
//        ));
//        this.optionsRowList.addBig(new ProgressOption(
//                "hbwhelper.configGui.hudX.title",
//                0.0, this.width, 1.0F,
//                unused -> (double) CMI.hudX(),
//                (unused, newValue) -> CMI.changeHudX(newValue.intValue()),
//                (gs, option) -> new TextComponent(I18n.get(
//                        "hbwhelper.configGui.hudX.title"
//                ) + ": " + (int) option.get(gs))));
//        this.optionsRowList.addBig(new ProgressOption(
//                "hbwhelper.configGui.hudY.title",
//                0.0, this.height, 1.0F,
//                unused -> (double) CMI.hudY(),
//                (unused, newValue) -> CMI.changeHudY(newValue.intValue()),
//                (gs, option) -> new TextComponent(I18n.get(
//                        "hbwhelper.configGui.hudY.title"
//                ) + ": " + (int) option.get(gs))));
//        this.optionsRowList.addBig(new CycleOption(
//                "hbwhelper.configGui.currentDreamMode.title",
//                (unused, newValue) ->
//                        CMI.changeCurrentDreamMode(DreamMode.values()[
//                                (CMI.currentDreamMode().ordinal() + newValue)
//                                        % DreamMode.values().length]),
//                (unused, option) -> new TextComponent(I18n.get(
//                        "hbwhelper.configGui.currentDreamMode.title"
//                ) + ": " + I18n.get(
//                        CMI.currentDreamMode().getTranslateKey()
//                ))
//        ));
//        this.children.add(this.optionsRowList);
//
//        this.addButton(new Button(
//                (this.width - BUTTONS_INTERVAL) / 2 - BOTTOM_BUTTON_WIDTH,
//                this.height - BOTTOM_BUTTON_HEIGHT_OFFSET,
//                BOTTOM_BUTTON_WIDTH, BUTTON_HEIGHT,
//                new TranslatableComponent("hbwhelper.configGui.moreInfo"),
//                button -> Util.getPlatform().openUri(MORE_INFO_URL))
//        );
//        this.addButton(new Button(
//                (this.width + BUTTONS_INTERVAL) / 2,
//                this.height - BOTTOM_BUTTON_HEIGHT_OFFSET,
//                BOTTOM_BUTTON_WIDTH, BUTTON_HEIGHT,
//                new TranslatableComponent("gui.done"),
//                button -> this.onClose())
//        );
    }

    /**
     * Draws this GUI on the screen.
     *
     * @param matrixStack the matrix stack
     * @param mouseX horizontal location of the mouse
     * @param mouseY vertical location of the mouse
     * @param partialTicks number of partial ticks
     */
    @Override
    public void render(@Nonnull PoseStack matrixStack,
                       int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        this.optionsRowList.render(matrixStack, mouseX, mouseY, partialTicks);
        drawCenteredString(matrixStack, this.font, this.title.getString(),
                this.width / 2, TITLE_HEIGHT, 0xFFFFFF);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    /**
     * Closes this screen.
     */
    @Override
    public void onClose() {
        CMI.save();
        Objects.requireNonNull(this.minecraft).setScreen(parentScreen);
    }
}
