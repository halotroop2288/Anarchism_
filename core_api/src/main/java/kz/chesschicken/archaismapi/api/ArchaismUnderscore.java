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
package kz.chesschicken.archaismapi.api;

import it.unimi.dsi.fastutil.objects.AbstractObject2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import kz.chesschicken.archaismapi.api.mod.ModInstance;
import net.mine_diver.unsafeevents.Event;
import net.mine_diver.unsafeevents.EventBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixins;

import java.lang.reflect.Method;
import java.util.function.Consumer;

public class ArchaismUnderscore {
    public final EventBus EVENT_BUS = new EventBus();
    private final AbstractObject2ObjectMap<String, ModInstance> modList = new Object2ObjectOpenHashMap<>();

    public @NotNull ModInstance getByID(@NotNull String s) {
        return modList.get(s);
    }

    public @NotNull ObjectSet<String> getModList() {
        return modList.keySet();
    }

    public void registerEventBus(@NotNull Object entry) {
        if (entry.getClass() == Class.class)
            ArchaismUnderscore.getInstance().EVENT_BUS.register((Class<?>) entry);
        else if (entry instanceof Consumer)
            //noinspection unchecked
            ArchaismUnderscore.getInstance().EVENT_BUS.register((Consumer<? extends Event>) entry);
        else if (entry.getClass() == Method.class)
            ArchaismUnderscore.getInstance().EVENT_BUS.register((Method) entry);
        else
            ArchaismUnderscore.getInstance().EVENT_BUS.register(entry);
    }

    public void loadMixins() {
        ModInstance modInstance;
        for(String a : getModList()) {
            modInstance = getByID(a);
            if(modInstance.getMixinBootstraps().length < 1)
                continue;
            for(String s : modInstance.getMixinBootstraps())
                Mixins.addConfiguration(s);
        }
    }

    public void registerMod(@NotNull ModInstance a) {
        modList.put(a.getID(), a);
    }

    private static ArchaismUnderscore instance;
    public static final Logger LOGGER = LogManager.getLogger("ArchaismAPI");
    public static @NotNull ArchaismUnderscore getInstance() {
        if(instance == null)
            instance = new ArchaismUnderscore();
        return instance;
    }
}
