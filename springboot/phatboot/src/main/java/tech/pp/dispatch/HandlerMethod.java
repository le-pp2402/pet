package tech.pp.dispatch;

import java.lang.reflect.Method;

public class HandlerMethod {
    Object bean;
    Method method;

    public HandlerMethod(Object bean, Method method) {
        this.bean = bean;
        this.method = method;
    }

    public Object getBean() {
        return bean;
    }

    public Method getMethod() {
        return method;
    }
}
