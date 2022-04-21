import eu.hansolo.tilesfx.tools.Point;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class G1GC {
    private final String heapFile, rootsFile, pointersFile, newHeapFile;

    private final Double heapSize, blockSize;
    private final List<Integer> roots;
    private final List<Point> pointers;
    private final List<region> regions;
    private final HashMap<Integer, Point> regionsObjects;
    private final HashMap<Integer, Boolean> mark;
    private HashMap<Integer, Point> heap;

    public G1GC(Double size, String heap, String roots, String pointers, String newHeap) throws IOException {
        this.heapFile = heap;
        this.rootsFile = roots;
        this.pointersFile = pointers;
        this.newHeapFile = newHeap;
        this.heapSize = size;

        //assuming heap starts from 0
        this.blockSize = this.heapSize / 16;

        this.roots = new ArrayList<>();
        this.pointers = new ArrayList<>();
        this.regions = new ArrayList<>();
        this.heap = new HashMap<>();
        this.mark = new HashMap<>();
        this.regionsObjects = new HashMap<>();

        startClean();
    }

    private void startClean() throws IOException {
        collectData();
        divideHeap();
        markPhase();
        sweepPhase();
        finalPhase();
        writeData();
    }

    private void collectData() throws FileNotFoundException {
        //reading roots
        File myObj = new File(rootsFile);
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            roots.add(Integer.parseInt(data));
        }

        //reading heap
        myObj = new File(heapFile);
        myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            String[] res = data.split(",", 0);
            heap.put(Integer.parseInt(res[0]), new Point(Double.parseDouble(res[1]), Double.parseDouble(res[2])));
            mark.put(Integer.parseInt(res[0]), false);
        }

        //reading pointers
        myObj = new File(pointersFile);
        myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            String[] res = data.split(",", 0);
            pointers.add(new Point(Double.parseDouble(res[0]), Double.parseDouble(res[1])));
        }
    }

    private void divideHeap() {
        for (int i = 0; i < 16; i++) {
            regions.add(new region(blockSize));
        }

        for (Map.Entry<Integer, Point> item : heap.entrySet()) {

            Double startMemory = item.getValue().x;
            Double endMemory = item.getValue().y;
            Double objSize = (item.getValue().y - item.getValue().x);
            int startRegionNum = (int) (startMemory / blockSize);
            int endRegionNum = (int) (endMemory / blockSize);

            if (startRegionNum == endRegionNum) {
                region desiredRegion = regions.get(startRegionNum - 1);
                desiredRegion.addObject(item.getKey(), objSize);

                regionsObjects.put(item.getKey(), new Point(startRegionNum - 1, -1));
            } else {
                region startRegion = regions.get(startRegionNum - 1);
                region endRegion = regions.get(endRegionNum - 1);

                startRegion.addObject(item.getKey(), endRegionNum * blockSize - startMemory);
                if (endMemory - endRegionNum * blockSize != 0)
                    endRegion.addObject(item.getKey(), endMemory - endRegionNum * blockSize);
                else endRegionNum = 0;

                regionsObjects.put(item.getKey(), new Point(startRegionNum - 1, endRegionNum - 1));
            }
        }
    }

    private void markPhase() {
        for (var root : roots) {
            DFS(root);
        }
    }

    private void DFS(int root) {
        if (mark.get(root)) return;
        mark.put(root, true);
        for (Point pointer : pointers) {
            if (pointer.x == root) {
                DFS((int) pointer.y);
            }
        }
    }

    private void sweepPhase() {
        for (Map.Entry<Integer, Boolean> item : mark.entrySet()) {
            if (!item.getValue()) {
                heap.remove(item.getKey());
                Point associateRegions = regionsObjects.get(item.getKey());

                regions.get((int) associateRegions.x).removeObject(item.getKey());
                if ((int) associateRegions.y != -1) regions.get((int) associateRegions.y).removeObject(item.getKey());
            }
        }
    }

    private void finalPhase() {
        HashMap<Integer, Point> newHeap = new HashMap<>();
        List<Integer> freeRegions = new ArrayList<>();

        for (int i = 0; i < regions.size(); i++) {
            if (regions.get(i).empty) freeRegions.add(i);
        }

        for (Map.Entry<Integer, Point> item : heap.entrySet()) {
            double objSize = item.getValue().y - item.getValue().x;
            for (var i : freeRegions) {
                var currentRegion = regions.get(i);
                if (objSize > currentRegion.free) continue;
                else {
                    double newStart = i * blockSize + (blockSize - currentRegion.free);
                    currentRegion.addObject(item.getKey(), objSize);

                    newHeap.put(item.getKey(), new Point(newStart, newStart + objSize));

                    Point associateRegions = regionsObjects.get(item.getKey());
                    regions.get((int) associateRegions.x).removeObject(item.getKey());
                    if ((int) associateRegions.y != -1)
                        regions.get((int) associateRegions.y).removeObject(item.getKey());

                    break;
                }
            }
        }

        this.heap = newHeap;
    }

    private void writeData() throws IOException {
        FileWriter fileWriter = new FileWriter(newHeapFile);
        for (Map.Entry<Integer, Point> item : heap.entrySet()) {
            fileWriter.write(item.getKey() + "," + item.getValue().x + "," + item.getValue().y + "\n");
        }
        fileWriter.close();
    }

}

class region {
    Double free, total;
    boolean empty;
    List<Point> reservedObjects;

    public region(Double size) {
        this.free = this.total = size;
        this.empty = true;
        this.reservedObjects = new ArrayList<>();
    }

    public void addObject(Integer obj, Double size) {
        reservedObjects.add(new Point(obj, size));
        this.free -= size;
        this.empty = false;
    }

    public void removeObject(Integer obj) {
        double size = 0;
        for (int i = 0; i < reservedObjects.size(); i++) {
            if (reservedObjects.get(i).x == obj) {
                size = reservedObjects.get(i).y;
                reservedObjects.remove(i);
                break;
            }
        }
        this.free += size;
        if (this.free.equals(this.total)) this.empty = true;
    }
}
