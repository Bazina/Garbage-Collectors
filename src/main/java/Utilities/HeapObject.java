package Utilities;

import java.util.ArrayList;
import java.util.List;

public class HeapObject {
    private final int id;
    private int startingAddress;
    private int endingAddress;

    private boolean mark;
    private final List<Integer> children = new ArrayList<>();

    public HeapObject(int id, int startingAddress, int endingAddress) {
        this.id = id;
        this.startingAddress = startingAddress;
        this.endingAddress = endingAddress;
    }

    public int getId() {
        return id;
    }

    public int getStartingAddress() {
        return startingAddress;
    }

    public int getEndingAddress() {
        return endingAddress;
    }

    public boolean isMark() {
        return mark;
    }

    public List<Integer> getChildren() {
        return children;
    }

    public int updateAddress(int newStartingAddress) {
        int delta = newStartingAddress - startingAddress;
        startingAddress = newStartingAddress;
        endingAddress += delta;
        return endingAddress;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }

    public void addChild(int childId) {
        children.add(childId);
    }
}
