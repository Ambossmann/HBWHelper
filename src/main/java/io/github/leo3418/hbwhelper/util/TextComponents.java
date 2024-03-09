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

package io.github.leo3418.hbwhelper.util;

import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.ChatFormatting;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.AbstractMap.SimpleImmutableEntry;

import java.util.AbstractMap.SimpleImmutableEntry;

/**
 * Provides methods that operate on {@linkplain ITextComponent text component
 * objects}.
 *
 * @author Leo
 */
public class TextComponents {
    /**
     * An immutable map that associates {@linkplain Color#getValue()} color
     * integer codes} with format control strings with the section sign
     * ({@code §})
     */
    private static final Map<Integer, String> COLOR_INT_TO_CTRL_STR_MAP =
            Collections.unmodifiableMap(Arrays.stream(ChatFormatting.values())
                    .filter(ChatFormatting::isColor)
                    .map(textFormatting -> new SimpleImmutableEntry<>(
                            textFormatting.getColor(),
                            textFormatting.toString()
                    ))
                    .collect(Collectors.toMap(
                            Map.Entry::getKey, Map.Entry::getValue
                    ))
            );

    /**
     * Prevents instantiation of this class.
     */
    private TextComponents() {
    }

    /**
     * Returns the formatted text converted from an {@link ITextComponent}
     * object. The formatted text will end with a reset formatting code
     * ({@code §r}).
     *
     * @param textComponent the text component
     * @return the formatted text generated from the text component
     * @throws NullPointerException if {@code textComponent == null}
     * @apiNote This method is provided mainly to deal with the removal of
     *         the {@code getFormattedText()} method in {@code ITextComponent}
     *         in Minecraft 1.16.
     */
    public static String toFormattedText(Component textComponent) {
        Objects.requireNonNull(textComponent, "textComponent");
        StringBuilder resultBuilder = new StringBuilder();
        String text = textComponent.getContents();
        if (!text.isEmpty()) {
            resultBuilder.append(formattingCodeOf(textComponent.getStyle()))
                    .append(text)
                    .append("\u00A7r");
        }
        textComponent.getSiblings().forEach(component ->
                resultBuilder.append(toFormattedText(component)));
        return resultBuilder.toString();
    }

    /**
     * Returns the formatting code that can generate the specified
     * {@linkplain Style style}.
     *
     * @param style the style whose corresponding formatting code is queried
     * @return the formatting code for the specified style
     * @throws NullPointerException if {@code style == null}
     */
    private static String formattingCodeOf(Style style) {
        Objects.requireNonNull(style, "style");
        StringBuilder formattingCodeBuilder = new StringBuilder();
        TextColor color = style.getColor();
        if (color != null) {
            int colorCode = color.getValue();
            formattingCodeBuilder.append(
                    COLOR_INT_TO_CTRL_STR_MAP.getOrDefault(colorCode, ""));
        }
        if (style.isObfuscated()) {
            formattingCodeBuilder.append(
                    ChatFormatting.OBFUSCATED.toString());
        }
        if (style.isBold()) {
            formattingCodeBuilder.append(
                    ChatFormatting.BOLD.toString());
        }
        if (style.isStrikethrough()) {
            formattingCodeBuilder.append(
                    ChatFormatting.STRIKETHROUGH.toString());
        }
        if (style.isUnderlined()) {
            formattingCodeBuilder.append(
                    ChatFormatting.UNDERLINE.toString());
        }
        if (style.isItalic()) {
            formattingCodeBuilder.append(
                    ChatFormatting.ITALIC.toString());
        }
        return formattingCodeBuilder.toString();
    }
}
