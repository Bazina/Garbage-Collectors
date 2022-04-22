import java.util.*;

public class SortingHashmap {
    public static HashMap<Integer, List<Integer>> sortByValue(HashMap<Integer, List<Integer>> hm) {
        List<Map.Entry<Integer, List<Integer>>> list =
                new LinkedList<>(hm.entrySet());
        list.sort(Comparator.comparing(o -> (o.getValue().get(0))));
        HashMap<Integer, List<Integer>> temp = new LinkedHashMap<>();
        for (Map.Entry<Integer, List<Integer>> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
