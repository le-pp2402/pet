package di_test.chain;

import tech.pp.core.annotation.MyComponent;
import tech.pp.core.annotation.MyInject;

@MyComponent
public class ChainB {
    @MyInject
    private ChainC chainC;

    public ChainC getChainC() {
        return chainC;
    }
}
