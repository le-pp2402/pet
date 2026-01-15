package di_test.chain;

import tech.pp.core.annotation.PComponent;
import tech.pp.core.annotation.PInject;

@PComponent
public class ChainA {
    @PInject
    private ChainB chainB;

    public ChainB getChainB() {
        return chainB;
    }
}
