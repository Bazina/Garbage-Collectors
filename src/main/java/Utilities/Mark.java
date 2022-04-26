package Utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Mark {
    protected static final List<HeapObject> usedObject = new ArrayList<>();
    protected static HashMap<Integer, HeapObject> heap;
    protected static final HashMap<Integer, Boolean> mark = new HashMap<>();
    protected static InputHandler inputHandler;

    public static void init(String[] args) throws IOException {
        inputHandler = new InputHandler(args);
        inputHandler.parse();
        List<Integer> roots = inputHandler.getRoot();

        HashMap<Integer, HeapObject> heapMap = inputHandler.getHeap();
        heap = SortingHashmap.sortByValue(heapMap);

        for (Integer root : roots) {
            HeapObject rootObject = heap.get(root);

            if (rootObject.isMark())
                continue;

            usedObject.add(rootObject);

            rootObject.setMark(true);
        }

        for (int i = 0; i < usedObject.size(); i++) {
            for (Integer pointer : usedObject.get(i).getChildren()) {
                HeapObject childObject = heap.get(pointer);

                if (childObject.isMark())
                    continue;

                usedObject.add(childObject);

                childObject.setMark(true);
            }
        }
    }
}
