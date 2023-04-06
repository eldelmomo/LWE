package utils;
import java.lang.instrument.Instrumentation;

public class ObjectSize {
    private static volatile Instrumentation globalInstrumentation;

    public static void premain(final String agentArgs, final Instrumentation inst) {
        globalInstrumentation = inst;
    }

    public static long getObjectSize(final Object[] o) {
        if (globalInstrumentation == null) {
            throw new IllegalStateException("Agent not initialized.");
        }
        long r = 0;
        for (int i = 0; i < o.length; i++) {
            r += globalInstrumentation.getObjectSize(o[i]);
        }
        return r;
    }
    
}