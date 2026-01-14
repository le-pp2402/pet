package di_test.basic;

import tech.pp.core.annotation.MyComponent;
import tech.pp.core.annotation.MyInject;

@MyComponent
public class SimpleService {
    @MyInject
    private SimpleRepository repository;

    public String fetchData() {
        return repository.getData();
    }

    public SimpleRepository getRepository() {
        return repository;
    }
}
