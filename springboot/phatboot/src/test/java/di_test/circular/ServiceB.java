package di_test.circular;

import tech.pp.core.annotation.PComponent;
import tech.pp.core.annotation.PInject;

@PComponent
public class ServiceB {
    @PInject
    private ServiceA serviceA;

    public ServiceA getServiceA() {
        return serviceA;
    }
}
