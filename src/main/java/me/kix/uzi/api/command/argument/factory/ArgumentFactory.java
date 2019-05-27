package me.kix.uzi.api.command.argument.factory;

import me.kix.uzi.api.command.argument.Argument;
import me.kix.uzi.api.command.argument.factory.registration.RegisterArgument;
import me.kix.uzi.api.command.commands.ArgumentativeCommand;
import me.kix.uzi.api.command.parsing.Parser;
import me.kix.uzi.api.command.parsing.parsers.BooleanParser;
import me.kix.uzi.api.command.parsing.parsers.NumberParser;
import me.kix.uzi.api.command.parsing.parsers.PluginParser;
import me.kix.uzi.api.command.parsing.parsers.StringParser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ArgumentFactory {

    private final List<Parser> parsers = new CopyOnWriteArrayList<>();

    public ArgumentFactory() {
        parsers.add(new NumberParser());
        parsers.add(new PluginParser());
        parsers.add(new BooleanParser());
        parsers.add(new StringParser());
    }

    public void setupArgs(ArgumentativeCommand command) {
        Arrays.stream(command.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(RegisterArgument.class))
                .forEach(m -> {
                    final RegisterArgument regAnnotation = m.getDeclaredAnnotation(RegisterArgument.class);
                    command.getArguments().add(new Argument(regAnnotation.value()) {
                        @Override
                        public void execute(String args) {
                            try {
                                final String argument = args.split(" ")[1].replace(".", "");
                                m.invoke(command, getParametersFromMethod(m, args.substring(args.indexOf(argument) + argument.length())));
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                });
    }

    private Object[] getParametersFromMethod(Method method, String input) {
        final List<Object> objects = new ArrayList<>();
        String message = input.trim();
        int index = 0;
        while (index < method.getParameterCount()) {
            for (Parser parser : parsers) {
                if (parser.canHandleType(method.getParameters()[index].getType())) {
                    final Object result = parser.parse(message);
                    if (result == null) {
                        continue;
                    }
                    objects.add(result);
                    message = message.substring(parser.getIndex()).trim();
                    ++index;
                }
            }
        }
        return objects.toArray(new Object[objects.size()]);
    }


}
