package di_test.circular;

import tech.pp.core.annotation.MyComponent;
import tech.pp.core.annotation.MyInject;

@MyComponent
public class ServiceB {
    @MyInject
    private ServiceA serviceA;

    public ServiceA getServiceA() {
        return serviceA;
    }
}
