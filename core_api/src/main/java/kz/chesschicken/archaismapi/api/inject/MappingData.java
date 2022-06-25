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

import java.util.Objects;

class MappingData {
    public static enum DataType {
        FIELD,
        METHOD,
        CLASS
    }

    public final String data;
    public final DataType type;

    public MappingData(@NotNull DataType type, @NotNull String data) {
        this.type = type;
        this.data = data;
    }

    @SuppressWarnings("unchecked")
    public <T> @Nullable Class<T> __generateClass() {
        try {
            return (Class<T>) Class.forName(data);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MappingData that = (MappingData) o;
        return Objects.equals(data, that.data) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, type);
    }
}
