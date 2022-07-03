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


import kz.chesschicken.archaismapi.api.mod.ModInstance;
import kz.chesschicken.archaismapi.utils.InvokeHelper;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ModsGrabber {


    public static void prepareFolderMods(@NotNull File folder, LaunchClassLoader loader) {
//        MethodHandle __addURL;
//        URLClassLoader __mainJar = (URLClassLoader) ClassLoader.getSystemClassLoader();
//
//        try {
//            __addURL = InvokeHelper.getImplLookupInstance().findVirtual(URLClassLoader.class, "addURL", MethodType.methodType(void.class, URL.class));
//        } catch (NoSuchMethodException | IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
        if(!folder.isDirectory())
            throw new RuntimeException("What the fock?");

        for (File fileEntry : folder.listFiles()) {
            if (!fileEntry.isDirectory()) {
                if(fileEntry.getName().endsWith(".jar")) {
                    try {
                        ZipFile zipFile = new JarFile(fileEntry);
                        ZipEntry mod_descFile = zipFile.getEntry("description.json");

                        if(mod_descFile == null) {
                            ArchaismUnderscore.LOGGER.error("The file " + fileEntry.getName() + " doesn't include description file! Aborting its initialization!");
                            return;
                        }
                        loader.addURL(fileEntry.toURI().toURL());
                        ArchaismUnderscore.getInstance().registerMod(new ModInstance(zipFile.getInputStream(mod_descFile)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}
