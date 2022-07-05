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
package com.halotroop.anarchismapi.api.mod;

import com.halotroop.anarchismapi.api.AnarchismUnderscore;
import com.halotroop.anarchismapi.utils.InvokeHelper;
import com.halotroop.anarchismapi.utils.json.AdvancedJSONObject;
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

@SuppressWarnings("unused")
public class ModInstance implements Comparable<ModInstance> {
    protected final String modId, name, desc, version, icon;
    protected final Class<?>[] launchClasses;
    protected final String[] mixinFiles;

    public ModInstance(@NotNull InputStream o) {
        AdvancedJSONObject object = new AdvancedJSONObject(new BufferedReader(new InputStreamReader(o, StandardCharsets.UTF_8)).lines().collect(Collectors.joining()));

        if(!object.has("mod_id")) {
            AnarchismUnderscore.LOGGER.error("A modification doesn't include \"mod_id\" parameter in its' description file!");
            throw new RuntimeException("A modification doesn't include \"mod_id\" parameter in its' description file!");
        }

        modId = object.getString("mod_id");
        name = object.getOrDefault("name", "default_name");
        desc = object.getOrDefault("description", "default_description");
        version = object.getOrDefault("version", "default_version");
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
            return InvokeHelper.getHomeClassLoader().loadClass(s);
        }catch (ClassNotFoundException e) {
            AnarchismUnderscore.LOGGER.error(e.getMessage());
            return null;
        }
    }

    public @Nullable Class<?> @NotNull [] getClasses() {
        return this.launchClasses;
    }

    public @NotNull String getID() {
        return this.modId;
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
        return Objects.equals(modId, ((ModInstance) o).modId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(modId, name, version);
    }

    @Override
    public int compareTo(ModInstance o) {
        return this.modId.compareTo(o.modId);
    }
}
