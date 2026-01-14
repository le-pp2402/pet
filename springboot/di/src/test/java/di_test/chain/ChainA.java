package di_test.chain;

import tech.pp.core.annotation.MyComponent;
import tech.pp.core.annotation.MyInject;

@MyComponent
public class ChainA {
    @MyInject
    private ChainB chainB;

    public ChainB getChainB() {
        return chainB;
    }
}
