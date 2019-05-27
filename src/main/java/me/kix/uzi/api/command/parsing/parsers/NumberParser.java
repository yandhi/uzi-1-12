package me.kix.uzi.api.command.parsing.parsers;

import me.kix.uzi.api.command.parsing.Parser;
import org.apache.commons.lang3.math.NumberUtils;

public class NumberParser extends Parser {

    @Override
    public Object parse(String input) {
        final String number = input.substring(0, input.contains(" ") ? input.indexOf(32) : input.length());
        if (NumberUtils.isNumber(number)) {
            setIndex(number.length());
            return NumberUtils.createNumber(number);
        }
        return null;
    }

    @Override
    public boolean canHandleType(Class type) {
        return Double.class.isAssignableFrom(type) || Double.TYPE.isAssignableFrom(type) || (Long.class.isAssignableFrom(type) || Long.TYPE.isAssignableFrom(type)) || (Float.class.isAssignableFrom(type) || Float.TYPE.isAssignableFrom(type)) || (Integer.class.isAssignableFrom(type) || Integer.TYPE.isAssignableFrom(type));
    }
}
