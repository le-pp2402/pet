package di_test.circular;

import tech.pp.core.annotation.MyComponent;
import tech.pp.core.annotation.MyInject;

@MyComponent
public class ServiceA {
    @MyInject
    private ServiceB serviceB;

    public ServiceB getServiceB() {
        return serviceB;
    }
}
