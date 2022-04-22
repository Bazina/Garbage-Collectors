
import java.io.*;
import java.util.*;
public class MarkAndSweep {
    public static List<Integer> root = new ArrayList<>();
    public static List<Integer> usedObject = new ArrayList<>();
    public static List<Integer> pointer1 = new ArrayList<>();
    public static List<Integer> pointer2 = new ArrayList<>();
    public static HashMap<Integer, List<Integer>> heapMap = new HashMap<>();
    public static Map<Integer, List<Integer>> heap;
    public static SortingHashmap sort = new SortingHashmap();
    public static HashMap<Integer, Boolean> Mark = new HashMap<>();
    public static int starting;
    public static void markAndSweep(String arg) throws IOException {
        for (Integer item : heap.keySet()) {
            Mark.put(item, false);
        }
        for (Integer i : usedObject) {
            Mark.put(i, true);
        }
        for (Integer item : Mark.keySet()) {
            if (!Mark.get(item)) {
                heap.remove(item);
            }
        }
        try {
            File markAndSweepFile = new File(arg);
            if (markAndSweepFile.createNewFile()) {
                System.out.println("File created: " + markAndSweepFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        FileWriter myWriter = new FileWriter(arg);
        for (Integer item : heap.keySet()) {

            myWriter.write(item + "," + heap.get(item).get(0) + "," + heap.get(item).get(1) + "\n");
        }
        myWriter.close();
/////////////////////////////////////////////////////////////
    }
    public static void main(String[] args) throws IOException {
        starting = -1;
        ////////ROOT///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        File myObj = new File(args[1]);
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            root.add(Integer.parseInt(data));
        }
        /////////Heap//////////////////////////////////////////
        File myObj2 = new File(args[0]);
        Scanner myReader2 = new Scanner(myObj2);
        while (myReader2.hasNextLine()) {
            String data = myReader2.nextLine();
            String[] res = data.split(",", 0);
            starting = starting == -1 ? Integer.parseInt(res[1]) : starting;
            heapMap.put(Integer.parseInt(res[0]), new ArrayList<>());
            for (int i = 1; i < res.length; i++) {
                heapMap.get(Integer.parseInt(res[0])).add(Integer.parseInt(res[i]));
            }
        }
        heap = sort.sortByValue(heapMap);
        /////////pointers//////////////////////////////////////////
        File myObj3 = new File(args[2]);
        Scanner myReader3 = new Scanner(myObj3);
        while (myReader3.hasNextLine()) {
            String data = myReader3.nextLine();
            String[] res = data.split(",", 0);
            pointer1.add(Integer.parseInt(res[0]));
            pointer2.add(Integer.parseInt(res[1]));
        }
        for(int i=0;i<root.size();i++) {
            if(!usedObject.contains(root.get(i)))
            {
                usedObject.add(root.get(i));
            }
            for (int j = 0; j < pointer1.size(); j++) {
                if (usedObject.contains(pointer1.get(j)) && !usedObject.contains(pointer2.get(j))) {
                    usedObject.add(pointer2.get(j));
                }
            }
        }
        System.out.println("pointer1");
        for (Integer element : pointer1) {
            System.out.println(element);
        }
        System.out.println("root");
        for (Integer element : root) {
            System.out.println(element);
        }
        System.out.println("Used object");
        for (Integer element : usedObject) {
            System.out.println(element);
        }
        markAndSweep(args[3]);
        System.out.println("heap");
        for (Integer item : heap.keySet()) {
            int key = item;
            System.out.println("\nobject_identifier " + key);
            System.out.println("Range_heap " + heap.get(key));
        }
    }
}
