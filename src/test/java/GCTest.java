import org.junit.jupiter.api.Test;

import java.io.IOException;

class GCTest {

    @Test
    void test1() throws IOException {
        CopyGC.main(new String[]{"src/main/resources/1/heap.csv", "src/main/resources/1/root.txt",
                "src/main/resources/1/pointers.csv", "src/main/resources/1/OutC.csv"});
        MarkAndSweep.main(new String[]{"src/main/resources/1/heap.csv", "src/main/resources/1/root.txt",
                "src/main/resources/1/pointers.csv", "src/main/resources/1/OutMS.csv"});
        MarkAndCompact.main(new String[]{"src/main/resources/1/heap.csv", "src/main/resources/1/root.txt",
                "src/main/resources/1/pointers.csv", "src/main/resources/1/OutMC.csv"});
        G1GC.main(new String[]{"1808", "src/main/resources/1/heap.csv", "src/main/resources/1/root.txt",
                "src/main/resources/1/pointers.csv", "src/main/resources/1/OutG1.csv"});
    }

    @Test
    void test2() throws IOException {
        CopyGC.main(new String[]{"src/main/resources/2/heap.csv", "src/main/resources/2/root.txt",
                "src/main/resources/2/pointers.csv", "src/main/resources/2/OutC.csv"});
        MarkAndSweep.main(new String[]{"src/main/resources/2/heap.csv", "src/main/resources/2/root.txt",
                "src/main/resources/2/pointers.csv", "src/main/resources/2/OutMS.csv"});
        MarkAndCompact.main(new String[]{"src/main/resources/2/heap.csv", "src/main/resources/2/root.txt",
                "src/main/resources/2/pointers.csv", "src/main/resources/2/OutMC.csv"});
        G1GC.main(new String[]{"1808", "src/main/resources/2/heap.csv", "src/main/resources/2/root.txt",
                "src/main/resources/2/pointers.csv", "src/main/resources/2/OutG1.csv"});
    }

    @Test
    void test3() throws IOException {
        CopyGC.main(new String[]{"src/main/resources/3/heap.csv", "src/main/resources/3/root.txt",
                "src/main/resources/3/pointers.csv", "src/main/resources/3/OutC.csv"});
        MarkAndSweep.main(new String[]{"src/main/resources/3/heap.csv", "src/main/resources/3/root.txt",
                "src/main/resources/3/pointers.csv", "src/main/resources/3/OutMS.csv"});
        MarkAndCompact.main(new String[]{"src/main/resources/3/heap.csv", "src/main/resources/3/root.txt",
                "src/main/resources/3/pointers.csv", "src/main/resources/3/OutMC.csv"});
        G1GC.main(new String[]{"1344", "src/main/resources/3/heap.csv", "src/main/resources/3/root.txt",
                "src/main/resources/3/pointers.csv", "src/main/resources/3/OutG1.csv"});
    }

    @Test
    void test4() throws IOException {
        CopyGC.main(new String[]{"src/main/resources/4/heap.csv", "src/main/resources/4/root.txt",
                "src/main/resources/4/pointers.csv", "src/main/resources/4/OutC.csv"});
        MarkAndSweep.main(new String[]{"src/main/resources/4/heap.csv", "src/main/resources/4/root.txt",
                "src/main/resources/4/pointers.csv", "src/main/resources/4/OutMS.csv"});
        MarkAndCompact.main(new String[]{"src/main/resources/4/heap.csv", "src/main/resources/4/root.txt",
                "src/main/resources/4/pointers.csv", "src/main/resources/4/OutMC.csv"});
        G1GC.main(new String[]{"1968", "src/main/resources/4/heap.csv", "src/main/resources/4/root.txt",
                "src/main/resources/4/pointers.csv", "src/main/resources/4/OutG1.csv"});
    }

    @Test
    void test5() throws IOException {
        CopyGC.main(new String[]{"src/main/resources/5/heap.csv", "src/main/resources/5/root.txt",
                "src/main/resources/5/pointers.csv", "src/main/resources/5/OutC.csv"});
        MarkAndSweep.main(new String[]{"src/main/resources/5/heap.csv", "src/main/resources/5/root.txt",
                "src/main/resources/5/pointers.csv", "src/main/resources/5/OutMS.csv"});
        MarkAndCompact.main(new String[]{"src/main/resources/5/heap.csv", "src/main/resources/5/root.txt",
                "src/main/resources/5/pointers.csv", "src/main/resources/5/OutMC.csv"});
        G1GC.main(new String[]{"1968", "src/main/resources/5/heap.csv", "src/main/resources/5/root.txt",
                "src/main/resources/5/pointers.csv", "src/main/resources/5/OutG1.csv"});
    }

    @Test
    void test6() throws IOException {
        CopyGC.main(new String[]{"src/main/resources/6/heap.csv", "src/main/resources/6/root.txt",
                "src/main/resources/6/pointers.csv", "src/main/resources/6/OutC.csv"});
        MarkAndSweep.main(new String[]{"src/main/resources/6/heap.csv", "src/main/resources/6/root.txt",
                "src/main/resources/6/pointers.csv", "src/main/resources/6/OutMS.csv"});
        MarkAndCompact.main(new String[]{"src/main/resources/6/heap.csv", "src/main/resources/6/root.txt",
                "src/main/resources/6/pointers.csv", "src/main/resources/6/OutMC.csv"});
        G1GC.main(new String[]{"1344", "src/main/resources/6/heap.csv", "src/main/resources/6/root.txt",
                "src/main/resources/6/pointers.csv", "src/main/resources/6/OutG1.csv"});
    }

    @Test
    void test7() throws IOException {
        CopyGC.main(new String[]{"src/main/resources/test/heap.csv", "src/main/resources/test/root.txt",
                "src/main/resources/test/pointers.csv", "src/main/resources/test/OutC.csv"});
        MarkAndSweep.main(new String[]{"src/main/resources/test/heap.csv", "src/main/resources/test/root.txt",
                "src/main/resources/test/pointers.csv", "src/main/resources/test/OutMS.csv"});
        MarkAndCompact.main(new String[]{"src/main/resources/test/heap.csv", "src/main/resources/test/root.txt",
                "src/main/resources/test/pointers.csv", "src/main/resources/test/OutMC.csv"});
        G1GC.main(new String[]{"256", "src/main/resources/test/heap.csv", "src/main/resources/test/root.txt",
                "src/main/resources/test/pointers.csv", "src/main/resources/test/OutG1.csv"});
    }
}