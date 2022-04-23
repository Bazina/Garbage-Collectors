package Utilities;

import java.util.*;

public class SortingHashmap {
    public static HashMap<Integer, HeapObject> sortByValue(HashMap<Integer, HeapObject> hm) {
        List<Map.Entry<Integer, HeapObject>> list = new LinkedList<>(hm.entrySet());
        list.sort(Comparator.comparing(o -> (o.getValue().getStartingAddress())));
        HashMap<Integer, HeapObject> temp = new LinkedHashMap<>();
        for (Map.Entry<Integer, HeapObject> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
