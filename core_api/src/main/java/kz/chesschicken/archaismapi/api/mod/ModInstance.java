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
package kz.chesschicken.archaismapi.api.mod;

import kz.chesschicken.archaismapi.api.ArchaismAPI;
import kz.chesschicken.archaismapi.utils.json.AdvancedJSONObject;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Collectors;

public class ModInstance implements Comparable<ModInstance> {
    private final String modid, name, desc, version, icon;
    private final Class<?>[] launchClasses;
    private final String[] mixinFiles;

    public ModInstance(@NotNull InputStream o) {
        AdvancedJSONObject object = new AdvancedJSONObject(new BufferedReader(new InputStreamReader(o, StandardCharsets.UTF_8)).lines().collect(Collectors.joining()));
        modid = object.getString("modid");
        name = object.getString("name");
        desc = object.getOrDefault("description", "DEFAULT_DESCRIPTION");
        version = object.getString("version");
        icon = object.getOrDefault("icon","/pack.png");

        if(object.has("classes")) {
            JSONArray _o_classes = object.getJSONArray("classes");
            launchClasses = new Class[_o_classes.length()];
            for(int i = 0; i < _o_classes.length(); i++)
                launchClasses[i] = getChecked(_o_classes.getString(i));
        } else launchClasses = new Class[0];

        if(object.has("mixins")) {
            JSONArray _o_mixins = object.getJSONArray("mixins");
            mixinFiles = new String[_o_mixins.length()];
            for(int i = 0; i < _o_mixins.length(); i++)
                mixinFiles[i] = _o_mixins.getString(i);
        }else mixinFiles = new String[0];
    }

    @ApiStatus.Internal
    @Nullable Class<?> getChecked(@NotNull String s) {
        try {
            return Class.forName(s);
        }catch (ClassNotFoundException e) {
            ArchaismAPI.LOGGER.severe(e.getMessage());
            return null;
        }
    }

    public @Nullable Class<?> @NotNull [] getClasses() {
        return this.launchClasses;
    }

    public boolean cancelInit() {
        return this.launchClasses == null;
    }

    public @NotNull String getID() {
        return this.modid;
    }

    public @NotNull String getVersion() {
        return this.version;
    }

    public @NotNull String getName() {
        return this.name;
    }

    public @NotNull String getDescription() {
        return this.desc;
    }

    public @NotNull String getIcon() {
        return this.icon;
    }

    public @NotNull String[] getMixinBootstraps() {
        return this.mixinFiles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || this.getClass() != o.getClass())
            return false;
        return Objects.equals(modid, ((ModInstance) o).modid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(modid, name, version);
    }

    @Override
    public int compareTo(ModInstance o) {
        return this.modid.compareTo(o.modid);
    }
}
