package di_test.chain;

import tech.pp.core.annotation.PComponent;
import tech.pp.core.annotation.PInject;

@PComponent
public class ChainB {
    @PInject
    private ChainC chainC;

    public ChainC getChainC() {
        return chainC;
    }
}
