package me.kix.uzi.api.event;

import java.lang.invoke.*;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The manager for all events in the client.
 *
 * @author Kix
 * @since April 2018 (Revised June 2019).
 */
public class EventManager {

    /**
     * The current collection of events in mapped form based on the listener and event class.
     */
    private final ConcurrentHashMap<Class<? extends Event>, List<MethodData>> collection = new ConcurrentHashMap<>();

    /**
     * Registers an object as a listener.
     *
     * @param object The object to be registered as a listener.
     */
    public void register(Object object) {
        Arrays.stream(object.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(Register.class))
                .forEach(m -> {
                    final Class<? extends Event> event = (Class<? extends Event>) m.getParameterTypes()[0];
                    if (!collection.containsKey(event)) {
                        collection.put(event, new CopyOnWriteArrayList<>());
                    }
                    collection.get(event).add(new MethodData(m, object));
                });
    }

    /**
     * Removes the object from being a listener.
     *
     * @param object The object to be removed.
     */
    public void unregister(Object object) {
        collection.values().forEach(list -> list.stream().filter(ed -> ed.parent == object).forEach(list::remove));
    }

    /**
     * Fires an event off into the event system.
     *
     * <p>
     * This basically pipelines the functions from the listeners into the given event method.
     * </p>
     *
     * @param event The event to be dispatched from.
     */
    public void dispatch(Event event) {
        if (collection.get(event.getClass()) != null) {
            if (!collection.get(event.getClass()).isEmpty()) {
                collection.get(event.getClass()).forEach(ed -> {
                    try {
                        ed.handler.invokeExact(ed.parent, event);
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
            }
        }
    }

    /**
     * A container class for method data.
     *
     * @author Alerithe
     * @since December 2017.
     */
    private class MethodData {

        /**
         * A handler for a reflections method.
         */
        private MethodHandle handler;

        /**
         * The listener for the method.
         */
        private Object parent;

        MethodData(Method method, Object parent) {
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            MethodHandle m = null;
            try {
                m = MethodHandles.lookup().unreflect(method);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            this.handler = m.asType(m.type().changeParameterType(0, Object.class).changeParameterType(1, Event.class));
            this.parent = parent;
        }
    }
}
