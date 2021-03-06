package com.code4people.jsonrpclib.binding.info;

import com.code4people.jsonrpclib.binding.BindingErrorException;
import com.code4people.jsonrpclib.binding.annotations.Optional;
import com.code4people.jsonrpclib.binding.annotations.Param;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NamedParamsInfo {

    private List<Parameter> parameters;

    public static NamedParamsInfo create(java.lang.reflect.Parameter[] parameters, String methodName) throws BindingErrorException {

        List<Parameter> params = new ArrayList<>(parameters.length);
        for (java.lang.reflect.Parameter parameter : parameters) {
            Param annotation = parameter.getAnnotation(Param.class);
            if (annotation == null) {
                String message = String.format(
                        "Missing @Param annotation on method: '%s'. You have to place @Param annotation on method parameter explicitly when method has named params.",
                        methodName);
                throw new BindingErrorException(message);
            }
            params.add(new Parameter(
                    annotation.value(),
                    parameter.isAnnotationPresent(Optional.class),
                    parameter.getParameterizedType()));
        }

        return new NamedParamsInfo(params);
    }

    private NamedParamsInfo(List<Parameter> parameters) throws BindingErrorException {
        this.parameters = parameters;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public static class Parameter {
        public final String name;
        public final boolean optional;
        public final Type type;

        public Parameter(String name, boolean optional, Type type) {
            this.name = name;
            this.optional = optional;
            this.type = type;
        }
    }
}
