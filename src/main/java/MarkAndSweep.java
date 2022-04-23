
import Utilities.Mark;

import java.io.*;

public class MarkAndSweep extends Mark {
    public static void markAndSweep() throws IOException {
        for (Integer item : heap.keySet())
            mark.put(item, false);

        for (Integer i : usedObject)
            mark.put(i, true);

        for (Integer item : mark.keySet())
            if (!mark.get(item))
                heap.remove(item);

        inputHandler.printMapHeap(heap);
    }

    public static void main(String[] args) throws IOException {
        init(args);
        markAndSweep();
    }
}
