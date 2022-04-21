package CopyGarbageCollector;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
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
        List<Point> pointers = inputHandler.getPointers();
        HashMap<Integer, HeapObject> heap = inputHandler.getHeap();
        List<HeapObject> newHeap = new ArrayList<>();

        if (roots.isEmpty()) {
            printHeap(newHeap);
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
        printHeap(newHeap);
    }

    private static void printHeap(List<HeapObject> heap) throws IOException {
        File markAndCompactFile = new File("CopyGC.csv");
        if (markAndCompactFile.createNewFile()) {
            System.out.println("File created: " + markAndCompactFile.getName());
        } else {
            System.out.println("File already exists.");
        }

        FileWriter myWriter = new FileWriter("CopyGC.csv");
        for (HeapObject object : heap) {

            myWriter.write(object.getId() + "," + object.getStartingAddress() + "," + object.getEndingAddress() + "\n");
        }
        myWriter.close();
    }
}
