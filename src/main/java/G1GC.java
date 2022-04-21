import Utilities.HeapObject;
import Utilities.InputHandler;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class G1GC {

    private final int blockSize;
    private final List<Integer> roots;
    private final List<Point> pointers;
    private final List<region> regions;
    private final HashMap<Integer, Point> regionsObjects;
    private final HashMap<Integer, Boolean> mark;
    private HashMap<Integer, HeapObject> heap;

    public static void main(String[] args) throws IOException {
        InputHandler inputHandler = new InputHandler(Arrays.copyOfRange(args, 1, args.length));
        new G1GC(Integer.parseInt(args[0]), inputHandler);
    }

    public G1GC(int size, InputHandler inputHandler) throws IOException {
        inputHandler.parse();

        //assuming heap starts from 0
        this.blockSize = size / 16;

        this.regions = new ArrayList<>();
        this.mark = new HashMap<>();
        this.regionsObjects = new HashMap<>();

        this.roots = inputHandler.getRoot();
        this.pointers = inputHandler.getPointers();
        this.heap = inputHandler.getHeap();

        startClean(inputHandler);
    }

    private void startClean(InputHandler inputHandler) throws IOException {
        collectData();
        divideHeap();
        markPhase();
        sweepPhase();
        finalPhase();
        inputHandler.printMapHeap(heap);
    }

    private void collectData() {
        for (Map.Entry<Integer, HeapObject> item : heap.entrySet())
            mark.put(item.getKey(), false);
    }

    private void divideHeap() {
        for (int i = 0; i < 16; i++)
            regions.add(new region(blockSize));

        for (Map.Entry<Integer, HeapObject> item : heap.entrySet()) {

            int startMemory = item.getValue().getStartingAddress();
            int endMemory = item.getValue().getEndingAddress();
            int objSize = (item.getValue().getEndingAddress() - item.getValue().getStartingAddress());
            int startRegionNum = startMemory / blockSize;
            int endRegionNum = endMemory / blockSize;

            if (startRegionNum == endRegionNum) {
                region desiredRegion = regions.get(startRegionNum);
                desiredRegion.addObject(item.getKey(), objSize);

                regionsObjects.put(item.getKey(), new Point(startRegionNum, -1));
            } else {
                region endRegion;
                region startRegion = regions.get(startRegionNum);

                startRegion.addObject(item.getKey(), endRegionNum * blockSize - startMemory);
                if (endMemory - endRegionNum * blockSize != 0 && endRegionNum != regions.size()) {
                    endRegion = regions.get(endRegionNum);
                    endRegion.addObject(item.getKey(), endMemory - endRegionNum * blockSize);
                } else endRegionNum = -1;

                regionsObjects.put(item.getKey(), new Point(startRegionNum, endRegionNum));
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
                DFS(pointer.y);
            }
        }
    }

    private void sweepPhase() {
        for (Map.Entry<Integer, Boolean> item : mark.entrySet()) {
            if (!item.getValue()) {
                heap.remove(item.getKey());
                Point associateRegions = regionsObjects.get(item.getKey());

                regions.get(associateRegions.x).removeObject(item.getKey());
                if (associateRegions.y != -1) regions.get(associateRegions.y).removeObject(item.getKey());
            }
        }
    }

    private void finalPhase() {
        HashMap<Integer, HeapObject> newHeap = new HashMap<>();
        List<Integer> freeRegions = new ArrayList<>();

        for (int i = 0; i < regions.size(); i++) {
            if (regions.get(i).empty) freeRegions.add(i);
        }

        for (Map.Entry<Integer, HeapObject> item : heap.entrySet()) {
            int objSize = item.getValue().getEndingAddress() - item.getValue().getStartingAddress();
            for (var i : freeRegions) {
                var currentRegion = regions.get(i);
                if (objSize <= currentRegion.free) {
                    int newStart = i * blockSize + (blockSize - currentRegion.free);
                    currentRegion.addObject(item.getKey(), objSize);

                    newHeap.put(item.getKey(), new HeapObject(item.getKey(), newStart, newStart + objSize));

                    Point associateRegions = regionsObjects.get(item.getKey());
                    regions.get(associateRegions.x).removeObject(item.getKey());
                    if (associateRegions.y != -1)
                        regions.get(associateRegions.y).removeObject(item.getKey());

                    break;
                }
            }
        }

        this.heap = newHeap;
    }
}

class region {
    int free, total;
    boolean empty;
    List<Point2D.Double> reservedObjects;

    public region(int size) {
        this.free = this.total = size;
        this.empty = true;
        this.reservedObjects = new ArrayList<>();
    }

    public void addObject(int obj, int size) {
        reservedObjects.add(new Point2D.Double(obj, size));
        this.free -= size;
        this.empty = false;
    }

    public void removeObject(int obj) {
        double size = 0;
        for (int i = 0; i < reservedObjects.size(); i++) {
            if (reservedObjects.get(i).x == obj) {
                size = reservedObjects.get(i).y;
                reservedObjects.remove(i);
                break;
            }
        }
        this.free += size;
        if (this.free == this.total) this.empty = true;
    }
}
