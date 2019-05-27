package me.kix.uzi.api.event;

import java.lang.invoke.*;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventManager {

    private final ConcurrentHashMap<Class<? extends Event>, List<MethodData>> collection = new ConcurrentHashMap<>();

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

    public void unregister(Object object) {
        collection.values().forEach(list -> list.stream().filter(ed -> ed.parent == object).forEach(list::remove));
    }

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

    private class MethodData {
        private MethodHandle handler;
        private Object parent;

        public MethodData(Method method, Object parent) {
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
