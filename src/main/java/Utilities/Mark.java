package Utilities;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Mark {
    protected static final List<Integer> usedObject = new ArrayList<>();
    protected static HashMap<Integer, HeapObject> heap;
    protected static final HashMap<Integer, Boolean> mark = new HashMap<>();
    protected static InputHandler inputHandler;

    public static void init(String[] args) throws IOException {
        inputHandler = new InputHandler(args);
        inputHandler.parse();
        List<Integer> roots = inputHandler.getRoot();

        HashMap<Integer, HeapObject> heapMap = inputHandler.getHeap();
        heap = SortingHashmap.sortByValue(heapMap);

        List<Point> pointers = inputHandler.getPointers();
        for (Integer root : roots) {
            if (!usedObject.contains(root))
                usedObject.add(root);
            for (Point pointer : pointers)
                if (usedObject.contains(pointer.x) && !usedObject.contains(pointer.y))
                    usedObject.add(pointer.y);
        }
    }
}
