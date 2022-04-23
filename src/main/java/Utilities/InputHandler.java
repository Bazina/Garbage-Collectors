package Utilities;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class InputHandler {
    private final String[] args;

    private File file;

    private final List<Integer> root = new ArrayList<>();
    private final List<Point> pointers = new ArrayList<>();
    private final HashMap<Integer, HeapObject> heap = new HashMap<>();
    private Scanner reader;
    private int starting = -1;


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
            starting = starting == -1 ? Integer.parseInt(res[1]) : starting;
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
            pointers.add(new Point(Integer.parseInt(res[0]), Integer.parseInt(res[1])));
            heap.get(Integer.parseInt(res[0])).addChild(Integer.parseInt(res[1]));
        }
    }

    public void printListHeap(List<HeapObject> heap) throws IOException {
        FileWriter writer = createOutputFile();

        for (HeapObject object : heap)
            writer.write(object.getId() + "," +
                         object.getStartingAddress() + "," + object.getEndingAddress() + "\n");
        writer.close();
    }

    public void printMapHeap(HashMap<Integer, HeapObject> heap) throws IOException {
        FileWriter writer = createOutputFile();
        for (Map.Entry<Integer, HeapObject> item : heap.entrySet()) {
            writer.write(item.getKey() + "," +
                         item.getValue().getStartingAddress() + "," + item.getValue().getEndingAddress() + "\n");
        }
        writer.close();
    }

    private FileWriter createOutputFile() throws IOException {
        File markAndCompactFile = new File(args[3]);
        if (markAndCompactFile.createNewFile()) {
            System.out.println("File created: " + markAndCompactFile.getName());
        } else {
            System.out.println("File already exists.");
        }

        return new FileWriter(args[3]);
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

    public int getStarting() {
        return starting;
    }

    public void setStarting(int starting) {
        this.starting = starting;
    }
}
