package eta.runtime.thunk;

import eta.runtime.stg.StgContext;
import eta.runtime.stg.StgClosure;
import static eta.runtime.concurrent.Concurrent.SPIN_COUNT;

public class StgWhiteHole extends StgClosure {
    /* TODO: Extend from something else instead? */
    public static final StgWhiteHole closure = new StgWhiteHole();

    @Override
    public void enter(StgContext context) {
        StgThunk node = (StgThunk) context.R(1);
        int i = 0;
        /* TODO: Verify the condition */
        while (node.indirectee == closure) {
            i = i + 1;
            if (i == SPIN_COUNT) {
                i = 0;
                Thread.yield();
            }
        }
        node.enter(context);
    }
}
