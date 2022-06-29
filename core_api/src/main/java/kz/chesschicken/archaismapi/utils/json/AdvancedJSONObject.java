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
package kz.chesschicken.archaismapi.utils.json;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Locale;
import java.util.Map;

/**
 * Boilerplate code...
 */
public class AdvancedJSONObject extends JSONObject {

    public boolean entriesExist(@NotNull String @NotNull ... entries) {
        for(String a : entries)
            if(!has(a))
                return false;
        return true;
    }

    @SuppressWarnings("unchecked")
    public <T> @Nullable T getOrDefault(@NotNull String s, @Nullable T def) {
        if(has(s))
            return (T) get(s);
        return def;
    }

    public AdvancedJSONObject() {
        super();
    }

    public AdvancedJSONObject(JSONObject jo, String... names) {
        super(jo, names);
    }

    public AdvancedJSONObject(JSONTokener x) throws JSONException {
        super(x);
    }

    public AdvancedJSONObject(Map<?, ?> m) {
        super(m);
    }

    public AdvancedJSONObject(Object bean) {
        super(bean);
    }

    public AdvancedJSONObject(Object object, String... names) {
        super(object, names);
    }

    public AdvancedJSONObject(String source) throws JSONException {
        super(source);
    }

    public AdvancedJSONObject(String baseName, Locale locale) throws JSONException {
        super(baseName, locale);
    }

    protected AdvancedJSONObject(int initialCapacity) {
        super(initialCapacity);
    }
}
