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
package kz.chesschicken.archaismapi.utils;

import sun.misc.Unsafe;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

public class InvokeHelper {

    public static Unsafe UNSAFE_INSTANCE;
    public static MethodHandles.Lookup IMPL_LOOKUP_INSTANCE;

    static {
        //Getting Unsafe field.
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            UNSAFE_INSTANCE = (Unsafe) f.get(null);

            Field a = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
            IMPL_LOOKUP_INSTANCE = (MethodHandles.Lookup) UNSAFE_INSTANCE.getObject(UNSAFE_INSTANCE.staticFieldBase(a), UNSAFE_INSTANCE.staticFieldOffset(a));
        } catch (NoSuchFieldException | IllegalAccessException exp) {
            exp.printStackTrace();
        }
    }
}
