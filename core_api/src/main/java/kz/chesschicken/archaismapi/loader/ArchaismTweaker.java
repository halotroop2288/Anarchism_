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
import kz.chesschicken.archaismapi.utils.InvokeHelper;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;

import java.io.File;
import java.util.List;

public class ArchaismTweaker implements ITweaker {
    private List<String> args;

    boolean parseBoolean(String f) {
        for(String a : args) {
            if(a.startsWith(f)) {
                String[] b = a.split("=");
                return b[1].equalsIgnoreCase("true");
            }
        }
        return false;
    }

    String parseString(String f) {
        for(String a : args) {
            if(a.startsWith(f)) {
                return a.split("=")[1];
            }
        }
        return null;
    }

    String[] provideGameArgs() {
        String[] main = new String[] {"null", "null"};
        for(String a : args) {
            if(a.startsWith("--username"))
                main[0] = a.split("=")[1];
            if(a.startsWith("--session"))
                main[1] = a.split("=")[1];
        }
        return main;
    }

    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
        this.args = args;
    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader) {
        //TODO: Debug code, remove later.
        System.out.println("Arguments: " + args.toString());

        InvokeHelper.initClassLoader(classLoader);

        //TODO: Add here a check for need.
        if(parseBoolean("--au-vti")) {
            System.out.println("Accepted VanillaTweakInjector!");
            classLoader.registerTransformer("net.minecraft.launchwrapper.injector.VanillaTweakInjector");
        }

        /* Parse Mods */
        File modsFolder = new File("mods");
        System.out.println("Mods folder: " + modsFolder.getAbsolutePath());
        ModsGrabber.prepareFolderMods(modsFolder, classLoader);

        /* Init Mixins */
        MixinBootstrap.init();
        MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);
        ArchaismUnderscore.getInstance().loadMixins();
    }

    @Override
    public String getLaunchTarget() {
        return parseString("--au-mc");
    }

    @Override
    public String[] getLaunchArguments() {
        return provideGameArgs();
    }
}
