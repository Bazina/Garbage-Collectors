import java.util.*;
public class sortingHashmap {
    public static HashMap<Integer, List<Integer>> sortByValue(HashMap<Integer,List<Integer>> hm)
    {
        List<Map.Entry<Integer, List<Integer>> > list =
                new LinkedList<Map.Entry<Integer,List<Integer>> >(hm.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, List<Integer>> >() {
            public int compare(Map.Entry<Integer, List<Integer>> o1,
                               Map.Entry<Integer,List<Integer>> o2)
            {
                return (o1.getValue().get(0)).compareTo(o2.getValue().get(0));
            }
        });
        HashMap<Integer, List<Integer>> temp = new LinkedHashMap<Integer,List<Integer>>();
        for (Map.Entry<Integer, List<Integer>> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
