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
package kz.chesschicken.archaismapi.mod;

import kz.chesschicken.archaismapi.api.ArchaismAPI;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Collectors;

public class ModInstance implements Comparable<ModInstance> {
    private final String modid, name, desc, version, icon;
    private final Class<?>[] launchClasses;

    public ModInstance(InputStream o) {
        JSONObject object = new JSONObject(new BufferedReader(new InputStreamReader(o, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n")));
        modid = object.getString("modid");
        name = object.getString("name");
        desc = object.getString("description");
        version = object.getString("version");
        icon = object.has("icon") ? object.getString("icon") : "/pack.png";

        Class<?>[] _temp1;
        try {
            if(object.has("classes")) {
                JSONArray _o_classes = object.getJSONArray("classes");
                _temp1 = new Class[_o_classes.length()];
                for(int i = 0; i < _o_classes.length(); i++)
                    _temp1[i] = Class.forName(_o_classes.getString(i));
            }else _temp1 = new Class[0];
        }catch (ClassNotFoundException e) {
            ArchaismAPI.LOGGER.severe(e.getMessage());
            _temp1 = new Class[0];
        }
        launchClasses = _temp1;
    }

    public Class<?>[] getClasses() {
        return this.launchClasses;
    }

    public String getID() {
        return this.modid;
    }

    public String getVersion() {
        return this.version;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.desc;
    }

    public String getIcon() {
        return this.icon;
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
