import java.io.*;
import java.util.*;

public class Main {

    public static List<Integer> root = new ArrayList<>();
    public static HashMap<Integer, List<Integer>> heap = new HashMap<>();
    public static HashMap<Integer, Integer> temp = new HashMap<>();
    public static HashMap<Integer, Integer> usedObject = new HashMap<>();
    public static int starting;
    public static void compact(int x){
        int cost=heap.get(x).get(1)-heap.get(x).get(0);
        heap.get(x).clear();
        heap.get(x).add(starting);
        starting+=cost;
        heap.get(x).add(starting);
    }
    public static void main(String[] args) throws IOException {
        starting=-1;
        ////////ROOT///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        File myObj = new File("root.txt");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            root.add(Integer.parseInt(data));
        }

        /////////Heap//////////////////////////////////////////
        File myObj2 = new File("heap.txt");
        Scanner myReader2 = new Scanner(myObj2);
        while (myReader2.hasNextLine()) {
            String data = myReader2.nextLine();
            String[] res = data.split(",", 0);
            starting=starting==-1? Integer.parseInt(res[1]) :starting;
            heap.put(Integer.parseInt(res[0]), new ArrayList<>());
            for (int i = 1; i < res.length; i++) {
                heap.get(Integer.parseInt(res[0])).add(Integer.parseInt(res[i]));
            }
        }

        /////////pointers//////////////////////////////////////////
        File myObj3 = new File("pointers.txt");
        Scanner myReader3 = new Scanner(myObj3);
        while (myReader3.hasNextLine()) {
            String data = myReader3.nextLine();
            String[] res = data.split(",", 0);
            temp.put(Integer.parseInt(res[1]), Integer.parseInt(res[0]));
        }


        for (Integer integer1 : root) {
            for (Integer integer : temp.keySet()) {
                int key = integer;
                int f;
                if (integer1 == key) {
                    f = temp.get(key);
                    usedObject.put(key, f);
                    for (Integer value : temp.keySet()) {
                        int key2 = value;
                        if (f == key2) {
                            usedObject.put(key2, temp.get(key2));
                        }
                    }

                }
            }

        }
        System.out.println("root");
        for (Integer element : root) {
            System.out.println(element);
        }
        System.out.println("heap");
        for (Integer item : heap.keySet()) {
            int key = item;
            System.out.println("\nobject_identifier " + key);
            System.out.println("Range_heap " + heap.get(key));
        }
        System.out.println("Used object");
        for (Integer value : usedObject.keySet()) {
            int key = value;
            System.out.println("\nchild " + key);
            System.out.println("parent " + usedObject.get(key));
        }

        HashMap<Integer, Boolean> Mark = new HashMap<>();
        for (Integer item : heap.keySet()) {
            Mark.put(item, false);
        }


        /*
        for (Integer i : root) {
            Mark.put(i, true);
            int x = i;
            while (usedObject.containsKey(x)) {
                x = usedObject.get(x);
                System.out.println("\nchild " + x);
                if (Mark.get(x)) break;
                Mark.put(x, true);
            }
        }

        */
        boolean first=false;
        for (Integer i : root) {
            Mark.put(i,true);
            compact(i);
            int x=i;
            while(usedObject.containsKey(x)){
                x=usedObject.get(x);
                System.out.println("\nchild " + x);
                if(Mark.get(x))break;
                Mark.put(x,true);
                compact(x);

            }
        }















        for (Integer item : Mark.keySet()) {
            if (!Mark.get(item)) {
                heap.remove(item);
            }
        }












        try {
            File markAndSweepFile = new File("out1.txt");
            if (markAndSweepFile.createNewFile()) {
                System.out.println("File created: " + markAndSweepFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        sortingHashmap sort = new sortingHashmap();
        Map<Integer,  List<Integer>> hm1 = sort.sortByValue(heap);
        FileWriter myWriter = new FileWriter("out1.txt");
        for (Integer item : hm1.keySet()) {

            myWriter.write(item + "," + hm1.get(item).get(0) + "," + hm1.get(item).get(1) + "\n");
        }
        myWriter.close();


        System.out.println("heap");
        for (Integer item : hm1.keySet()) {
            int key = item;
            System.out.println("\nobject_identifier " + key);
            System.out.println("Range_heap " + hm1.get(key));
        }
    }

}
