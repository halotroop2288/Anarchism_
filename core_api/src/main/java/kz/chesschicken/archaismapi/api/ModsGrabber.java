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
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.JarFile;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ModsGrabber {


    public static void prepareFolderMods(@NotNull File folder, LaunchClassLoader loader) {
        if(!folder.isDirectory()) {
            ArchaismUnderscore.LOGGER.error("The \"mods\" folder is not indeed a folder. Aborting.");
            return;
        }

        try(Stream<Path> stream = Files.walk(folder.toPath())) {
            stream.filter(s -> s.toFile().getName().endsWith(".jar")).forEach(a -> {
                try {
                    ZipFile zipFile = new JarFile(a.toFile());
                    ZipEntry desc = zipFile.getEntry("description.json");
                    if(desc == null) {
                        ArchaismUnderscore.LOGGER.error("The file " + a.toFile().getName() + " doesn't include description file! Aborting its initialization!");
                        return;
                    }
                    loader.addURL(a.toFile().toURI().toURL());
                    ArchaismUnderscore.getInstance().registerMod(new ModInstance(zipFile.getInputStream(desc)));
                } catch (IOException e) {
                    ArchaismUnderscore.LOGGER.error("Failed to parse a mod!", e);
                }
            });
        }catch (IOException e) {
            ArchaismUnderscore.LOGGER.error("Failed to parse mods folder!", e);
        }
    }


}
