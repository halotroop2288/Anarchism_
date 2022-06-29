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


import kz.chesschicken.archaismapi.api.mod.ModInstance;
import kz.chesschicken.archaismapi.utils.InvokeHelper;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.JarFile;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class PassiveModInst {

    public void prepareFolderMods(@NotNull Path folder) {
        try(Stream<Path> pathStream = Files.walk(folder)) {
            MethodHandle __addURL;
            URLClassLoader __mainJar = (URLClassLoader) PassiveModInst.class.getClassLoader();

            try {
                __addURL = InvokeHelper.IMPL_LOOKUP_INSTANCE.findVirtual(URLClassLoader.class, "addURL", MethodType.methodType(void.class, URL.class));
            } catch (NoSuchMethodException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            pathStream.filter(path -> path.toFile().getName().endsWith(".jar")).forEach(path -> {
                try {
                    ZipFile zipFile = new JarFile(path.toFile());
                    ZipEntry mod_descFile = zipFile.getEntry("description.json");

                    if(mod_descFile == null) {
                        ArchaismAPI.LOGGER.severe("The file " + path.toFile().getName() + " doesn't include description file! Aborting its initialization!");
                        return;
                    }

                    try {
                        __addURL.invoke(__mainJar, path.toUri().toURL());
                    } catch (Throwable e) {
                        //Impossible...
                        throw new RuntimeException(e);
                    }
                    ArchaismAPI.getInstance().__addMod(new ModInstance(zipFile.getInputStream(mod_descFile)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }catch (IOException e) {
            ArchaismAPI.LOGGER.severe("Can't walk inside folder (" + folder.toAbsolutePath() + ") !");
        }
    }

    public void loadMods() {
        MethodHandle d;
        ModInstance b;

        for(String a : ArchaismAPI.getInstance().getModList()) {
            b = ArchaismAPI.getInstance().getByID(a);
            if(b.cancelInit())
                continue;
            for(Class<?> c : b.getClasses()) {
                try {
                    d = InvokeHelper.IMPL_LOOKUP_INSTANCE.findConstructor(c, MethodType.methodType(void.class));
                    ArchaismAPI.getInstance().__registerEventBus(d.invoke());
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
