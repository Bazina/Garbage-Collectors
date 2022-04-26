
import Utilities.Mark;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MarkAndSweep extends Mark {
    public static List<Integer> root = new ArrayList<>();
    public static List<Integer> pointer1 = new ArrayList<>();
    public static List<Integer> pointer2 = new ArrayList<>();
    public static void markAndSweep() throws IOException {
        for (Integer item : heap.keySet())
            mark.put(item, false);

        for (Integer i : usedObject)
            mark.put(i, true);

        for (Integer item : mark.keySet())
            if (!mark.get(item))
                heap.remove(item);

        inputHandler.printMapHeap(heap);
    }

    public static void main(String[] args) throws IOException {
        init(args);
        File myObj = new File(args[1]);
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            root.add(Integer.parseInt(data));
        }
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
        for(int i=0;i<usedObject.size();i++)
        {
            for (int j =0 ;j<usedObject.size();j++)
            {
                if(usedObject.get(i)==pointer1.get(j))
                {
                    if(!usedObject.contains(pointer2.get(j)))
                    {
                        usedObject.add(pointer2.get(j));
                    }
                }
            }

        }
        for (int i=0;i<usedObject.size();i++)
        {
            System.out.println(usedObject.get(i));
        }
        markAndSweep();
    }
}
