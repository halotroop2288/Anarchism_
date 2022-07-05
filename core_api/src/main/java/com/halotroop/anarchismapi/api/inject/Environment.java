/**
 * Archaism Underscore - a small API for flexible integration.
 * Copyright (C) 2022 ChessChicken-KZ
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301
 * USA
 */
package com.halotroop.anarchismapi.api.inject;

import it.unimi.dsi.fastutil.objects.AbstractObject2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.jetbrains.annotations.NotNull;

public enum Environment {
    CLIENT,
    SERVER; //<- is this even going to be implemented?

    private final AbstractObject2ObjectMap<String, String> mappings = new Object2ObjectOpenHashMap<>();

    public void addMapping(@NotNull String s, @NotNull String s1) {
        mappings.putIfAbsent(s, s1);
    }

    public @NotNull String getMapping(@NotNull String s) {
        return mappings.getOrDefault(s, s);
    }

    public static void addMapping(@NotNull Environment environment, @NotNull String deobf, @NotNull String obf) {
        environment.addMapping(deobf, obf);
    }

    public static @NotNull String getMapping(@NotNull Environment environment, @NotNull String deobf) {
        return environment.getMapping(deobf);
    }
}
