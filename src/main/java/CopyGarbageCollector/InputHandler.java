package CopyGarbageCollector;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

public class InputHandler {
    private final String[] args;

    private File file;

    private final List<Integer> root = new ArrayList<>();
    private final List<Point> pointers = new ArrayList<>();
    private final HashMap<Integer, HeapObject> heap = new HashMap<>();
    private Scanner reader;


    public InputHandler(String[] args) {
        this.args = args;
    }

    public void parse() throws FileNotFoundException {
        readRoots();
        readHeap();
        readPointers();
    }

    public void readRoots() throws FileNotFoundException {
        file = new File(args[1]);
        reader = new Scanner(InputHandler.this.file);
        while (reader.hasNextLine()) {
            String data = reader.nextLine();
            root.add(Integer.parseInt(data));
        }
    }

    public void readHeap() throws FileNotFoundException {
        file = new File(args[0]);
        reader = new Scanner(file);
        while (reader.hasNextLine()) {
            String data = reader.nextLine();
            String[] res = data.split(",", 0);
            heap.put(Integer.parseInt(res[0]),
                    new HeapObject(Integer.parseInt(res[0]), Integer.parseInt(res[1]), Integer.parseInt(res[2])));
        }
    }

    public void readPointers() throws FileNotFoundException {
        file = new File(args[2]);
        reader = new Scanner(file);
        while (reader.hasNextLine()) {
            String data = reader.nextLine();
            String[] res = data.split(",", 0);
            pointers.add(new Point(Integer.parseInt(res[1]), Integer.parseInt(res[0])));
            heap.get(Integer.parseInt(res[0])).addChild(Integer.parseInt(res[1]));
        }
    }

    public List<Integer> getRoot() {
        return root;
    }

    public List<Point> getPointers() {
        return pointers;
    }

    public HashMap<Integer, HeapObject> getHeap() {
        return heap;
    }
}
