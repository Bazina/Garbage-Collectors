import Utilities.HeapObject;
import Utilities.Mark;

import java.io.IOException;

public class MarkAndCompact extends Mark {
    public static void compact(int x) {
        int cost = heap.get(x).getEndingAddress() - heap.get(x).getStartingAddress();
        heap.get(x).setStartingAddress(inputHandler.getStarting());
        inputHandler.setStarting(inputHandler.getStarting() + cost);
        heap.get(x).setEndingAddress(inputHandler.getStarting());
    }

    public static void markAndCompact() throws IOException {
        for (Integer item : heap.keySet())
            mark.put(item, false);

        for (HeapObject heapObject : usedObject)
            mark.put(heapObject.getId(), true);

        for (Integer item : heap.keySet())
            if (mark.get(item))
                compact(item);

        for (Integer item : mark.keySet())
            if (!mark.get(item))
                heap.remove(item);

        inputHandler.printMapHeap(heap);
    }

    public static void main(String[] args) throws IOException {
        init(args);
        markAndCompact();
    }
}
