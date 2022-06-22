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
package kz.chesschicken.archaismapi.api;

import kz.chesschicken.archaismapi.api.event.EventInit;
import kz.chesschicken.archaismapi.api.event.EventPostInit;
import kz.chesschicken.archaismapi.mod.ModInstance;
import net.mine_diver.unsafeevents.Event;
import net.mine_diver.unsafeevents.EventBus;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class ArchaismAPI {
    public final EventBus EVENT_BUS = new EventBus();
    private final Map<String, ModInstance> modList = new HashMap<>();

    public ModInstance getByID(String s) {
        return modList.get(s);
    }

    public Set<String> getModList() {
        return modList.keySet();
    }

    public void __registerEventBus(Object entry) {
        if (entry.getClass() == Class.class)
            ArchaismAPI.getInstance().EVENT_BUS.register((Class<?>) entry);
        else if (entry instanceof Consumer)
            //noinspection unchecked
            ArchaismAPI.getInstance().EVENT_BUS.register((Consumer<? extends Event>) entry);
        else if (entry.getClass() == Method.class)
            ArchaismAPI.getInstance().EVENT_BUS.register((Method) entry);
        else
            ArchaismAPI.getInstance().EVENT_BUS.register(entry);
    }

    public void __addMod(ModInstance a) {
        modList.put(a.getID(), a);
    }

    public void __loadPostInit(Environment a) {
        EVENT_BUS.post(new EventPostInit(a));
    }

    public void __loadInit(Environment a) {
        EVENT_BUS.post(new EventInit(a));
    }

    private static ArchaismAPI instance;
    public static final Logger LOGGER = Logger.getLogger("ArchaismAPI");
    public static ArchaismAPI getInstance() {
        if(instance == null)
            instance = new ArchaismAPI();
        return instance;
    }
}
