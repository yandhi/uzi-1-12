package me.kix.uzi.api.ui.toolkit.theme.renderer;

import me.kix.uzi.api.ui.toolkit.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * The abstracted instance of {@link ComponentRenderer}.
 *
 * @author Kix
 * @since 6/27/2019
 */
public abstract class AbstractComponentRenderer<C extends Component> implements ComponentRenderer<C> {

    /**
     * The class of the component determined in the generic.
     *
     * <p>
     * We have to do some extremely robust programming in order to gain this.
     * But, for ease of use and leisure purposes, it's worth it.
     * </p>
     */
    private Class<C> component;

    @SuppressWarnings("unchecked")
    public AbstractComponentRenderer() {
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            for (Type type : ((ParameterizedType) genericSuperclass).getActualTypeArguments()) {
                if (type instanceof Class && Component.class.isAssignableFrom((Class<?>) type)) {
                    this.component = (Class<C>) type;
                }
            }
        }
    }

    @Override
    public boolean canRender(Component component) {
        return component.getClass() == component.getClass();
    }

    @Override
    public Class<C> getComponent() {
        return component;
    }
}
