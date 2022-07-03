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
package kz.chesschicken.archaismapi.loader;

import kz.chesschicken.archaismapi.api.ArchaismUnderscore;
import kz.chesschicken.archaismapi.api.ModsGrabber;
import kz.chesschicken.archaismapi.api.event.EventGeneralInit;
import kz.chesschicken.archaismapi.api.inject.Environment;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class ArchaismTweaker implements ITweaker {
    private List<String> args;

    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
        this.args = args;
    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader) {
        //TODO: Debug code, remove later.
        ArchaismUnderscore.LOGGER.fine("Arguments: " + args.toString());

        //TODO: Add here a check for need.
        classLoader.registerTransformer("net.minecraft.launchwrapper.injector.VanillaTweakInjector");

        /* Init Mixins */
        MixinBootstrap.init();
        MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);

        /* Parse Mods */
        Path path = new File("mods").toPath();
        ArchaismUnderscore.LOGGER.fine("Mods folder: " + path.toAbsolutePath());
        ModsGrabber.prepareFolderMods(path);
        ModsGrabber.loadMods();

        /* Init mods mixins. */
        ArchaismUnderscore.getInstance().loadMixins();

        /* Init mods pre-init state. */
        ArchaismUnderscore.getInstance().EVENT_BUS.post(new EventGeneralInit.EventPreInit(Environment.CLIENT));
    }

    @Override
    public String getLaunchTarget() {
        return "net.minecraft.client.Minecraft";
    }

    @Override
    public String[] getLaunchArguments() {
        return args.toArray(new String[0]);
    }
}
