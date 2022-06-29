package kz.chesschicken.archaismapi.api.event;

import kz.chesschicken.archaismapi.api.inject.Environment;
import net.mine_diver.unsafeevents.Event;
import org.jetbrains.annotations.NotNull;

public class EventGeneralInit {
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
