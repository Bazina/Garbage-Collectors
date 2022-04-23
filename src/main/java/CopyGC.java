import Utilities.HeapObject;
import Utilities.InputHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CopyGC {
    private List<Integer> roots;
    private List<HeapObject> newHeap;
    private HashMap<Integer, HeapObject> heap;

    public static void main(String[] args) throws IOException {
        InputHandler inputHandler = new InputHandler(args);
        inputHandler.parse();
        new CopyGC(inputHandler);
    }

    public CopyGC(InputHandler inputHandler) throws IOException {
        getData(inputHandler);
        startCleaning(inputHandler);
    }

    private void startCleaning(InputHandler inputHandler) throws IOException {
        if (roots.isEmpty()) {
            inputHandler.printListHeap(newHeap);
            return;
        }

        collectGarbage();

        inputHandler.printListHeap(newHeap);
    }

    private void getData(InputHandler inputHandler) {
        roots = inputHandler.getRoot();
        heap = inputHandler.getHeap();
        newHeap = new ArrayList<>();
    }

    private void collectGarbage() {
        int nextHeapAddress = 0;

        for (Integer root : roots) {
            HeapObject rootObject = heap.get(root);

            if (rootObject.isMark())
                continue;

            nextHeapAddress = rootObject.updateAddress(nextHeapAddress);
            newHeap.add(rootObject);

            rootObject.setMark(true);
        }

        for (int i = 0; i < newHeap.size(); i++) {
            for (Integer pointer : newHeap.get(i).getChildren()) {
                HeapObject childObject = heap.get(pointer);

                if (childObject.isMark())
                    continue;

                nextHeapAddress = childObject.updateAddress(nextHeapAddress);
                newHeap.add(childObject);

                childObject.setMark(true);
            }
        }
    }
}
