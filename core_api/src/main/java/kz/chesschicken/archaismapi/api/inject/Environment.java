/**
 * Archaism Underscore - a small API for ML/Fabric flexible integration.
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
package kz.chesschicken.archaismapi.api.inject;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public enum Environment {
    CLIENT_LIKE,
    SERVER_LIKE;

    private final Map<MappingData, MappingData> mappingData = new HashMap<>();

    public void initMapData(MappingData deob, MappingData obf) {
        mappingData.putIfAbsent(deob, obf);
    }

    public <T> @Nullable Class<T> getClass(@NotNull String data) {
        MappingData u = mappingData.get(new MappingData(MappingData.DataType.CLASS, data));
        if(u == null)
            return null;
        return u.__generateClass();
    }
}
