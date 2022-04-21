import Utilities.HeapObject;
import Utilities.InputHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CopyGC {
    public static void main(String[] args) throws IOException {
        int nextHeapAddress = 0;
        InputHandler inputHandler = new InputHandler(args);
        inputHandler.parse();
        List<Integer> roots = inputHandler.getRoot();
        HashMap<Integer, HeapObject> heap = inputHandler.getHeap();
        List<HeapObject> newHeap = new ArrayList<>();

        if (roots.isEmpty()) {
            inputHandler.printListHeap(newHeap);
            return;
        }

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
                if (childObject.isMark()) continue;
                nextHeapAddress = childObject.updateAddress(nextHeapAddress);
                newHeap.add(childObject);
                childObject.setMark(true);
            }
        }
        inputHandler.printListHeap(newHeap);
    }
}
