package di_test.basic;

import tech.pp.core.annotation.MyComponent;

@MyComponent
public class SimpleRepository {
    private String data = "test data";

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
