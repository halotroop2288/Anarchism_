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
package kz.chesschicken.archaismapi.api.event;

import kz.chesschicken.archaismapi.api.inject.Environment;
import net.mine_diver.unsafeevents.Event;
import org.jetbrains.annotations.NotNull;

public class EventGeneralInit {
    public static class EventPreInit extends Event {

        public final Environment TYPE;

        public EventPreInit(@NotNull Environment a) {
            this.TYPE = a;
        }

        @Override
        protected int getEventID() {
            return ID;
        }

        public static final int ID = NEXT_ID.incrementAndGet();
    }

    public static class EventInit extends Event {

        public final Environment TYPE;

        public EventInit(@NotNull Environment a) {
            this.TYPE = a;
        }

        @Override
        protected int getEventID() {
            return ID;
        }

        public static final int ID = NEXT_ID.incrementAndGet();
    }

    public static class EventPostInit extends Event {

        public final Environment TYPE;

        public EventPostInit(@NotNull Environment a) {
            this.TYPE = a;
        }

        @Override
        protected int getEventID() {
            return ID;
        }

        public static final int ID = NEXT_ID.incrementAndGet();
    }
}
