package di_test.basic;

import tech.pp.core.annotation.PComponent;
import tech.pp.core.annotation.PInject;

@PComponent
public class SimpleService {
    @PInject
    private SimpleRepository repository;

    public String fetchData() {
        return repository.getData();
    }

    public SimpleRepository getRepository() {
        return repository;
    }
}
