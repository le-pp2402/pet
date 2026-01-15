package di_test.circular;

import tech.pp.core.annotation.PComponent;
import tech.pp.core.annotation.PInject;

@PComponent
public class ServiceA {
    @PInject
    private ServiceB serviceB;

    public ServiceB getServiceB() {
        return serviceB;
    }
}
