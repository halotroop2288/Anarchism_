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
package kz.chesschicken.archaismapi.utils;

import org.jetbrains.annotations.NotNull;
import sun.misc.Unsafe;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

public class InvokeHelper {

    private static Unsafe UNSAFE_INSTANCE;
    private static MethodHandles.Lookup IMPL_LOOKUP_INSTANCE;
    private static ClassLoader homeClassLoader;
    static boolean ehCL;

    public static void initClassLoader(@NotNull ClassLoader loader) {
        homeClassLoader = loader;
        ehCL = true;
    }

    public static @NotNull ClassLoader getHomeClassLoader() {
        if(ehCL)
            return homeClassLoader;
        System.out.println("Something is wrong... entirely wrong...");
        return ClassLoader.getSystemClassLoader();
    }

    public static @NotNull Unsafe getUnsafeInstance() {
        if(UNSAFE_INSTANCE == null) {
            try {
                Field f = Unsafe.class.getDeclaredField("theUnsafe");
                f.setAccessible(true);
                UNSAFE_INSTANCE = (Unsafe) f.get(null);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return UNSAFE_INSTANCE;
    }

    public static @NotNull MethodHandles.Lookup getImplLookupInstance() {
        if(IMPL_LOOKUP_INSTANCE == null) {
            try {
                Field a = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
                IMPL_LOOKUP_INSTANCE = (MethodHandles.Lookup) getUnsafeInstance().getObject(getUnsafeInstance().staticFieldBase(a), getUnsafeInstance().staticFieldOffset(a));
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }
        return IMPL_LOOKUP_INSTANCE;
    }
}
