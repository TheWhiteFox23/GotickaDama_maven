package cz.whiterabbit.elements.movegenerator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MostCaptureEnemiesFilterTest {

    MostCaptureEnemiesFilter mostCaptureEnemiesFilter;


    @BeforeEach
    void beforeEach(){
        mostCaptureEnemiesFilter = new MostCaptureEnemiesFilter();
    }

    @Test
    @DisplayName("Most captured enemies filter")
    void filter() {
        List<byte[]> before = new ArrayList<>();
        before.add(new byte[]{48,-1,0,40,0,-1});
        before.add(new byte[]{48,-1,0,41,0,-1});
        before.add(new byte[]{49,-1,0,41,0,-1});
        before.add(new byte[]{49,-1,0,40,0,-1});
        before.add(new byte[]{49,-1,0,42,0,-1});
        before.add(new byte[]{50,-1,0,41,0,-1});
        before.add(new byte[]{50,-1,0,42,0,-1});
        before.add(new byte[]{51,-1,0,43,1,0,35,0,-1,35,-1,0,36,1,0,37,0,-1});
        before.add(new byte[]{53,-1,0,44,0,-1});
        before.add(new byte[]{53,-1,0,45,0,-1});
        before.add(new byte[]{53,-1,0,46,0,-1});
        before.add(new byte[]{53,-1,0,52,0,-1});
        before.add(new byte[]{54,-1,0,45,0,-1});
        before.add(new byte[]{54,-1,0,46,0,-1});
        before.add(new byte[]{54,-1,0,47,0,-1});
        before.add(new byte[]{55,-1,0,46,0,-1});
        before.add(new byte[]{55,-1,0,47,0,-1});
        before.add(new byte[]{56,-1,0,57,0,-1});
        before.add(new byte[]{58,-1,0,57,0,-1});
        before.add(new byte[]{60,-2,0,36,1, 0, 28,0,-2,28,-2,0,20,2,0,12,0,-2});
        before.add(new byte[]{34,-1,0,33,0,-1});
        before.add(new byte[]{34,-1,0,35,0,-1});
        before.add(new byte[]{34,-1,0,25,0,-1});
        before.add(new byte[]{34,-1,0,26,0,-1});
        before.add(new byte[]{34,-1,0,27,0,-1});
        before.add(new byte[]{59,-1,0,52,0,-1});
        before.add(new byte[]{61,-1,0,52,0,-1});
        before.add(new byte[]{19,-1,0,20,2,0,21,0,-1});
        List<byte[]> after = new ArrayList<>();
        after.add(new byte[]{51,-1,0,43,1,0,35,0,-1,35,-1,0,36,1,0,37,0,-1});
        after.add(new byte[]{60,-2,0,36,1,0,28,0,-2,28,-2,0,20,2,0,12,0,-2});

        mostCaptureEnemiesFilter.filter(before);

        compareLists(before, after);
    }

    class ArrayCountComparator implements Comparator<byte[]> {
        @Override
        public int compare(byte[] o1, byte[] o2) {
            int byteCount1 = 0;
            int byteCount2 = 0;
            for (byte b : o1) {
                byteCount1 += b;
            }
            for (byte b : o2) {
                byteCount2 += b;
            }
            return byteCount1 - byteCount2;
        }
    }

    private void compareLists(List<byte[]> l1, List<byte[]> l2) {
        l1.sort(new ArrayCountComparator());
        l2.sort(new ArrayCountComparator());

        for (int i = 0; i < l1.size(); i++) {
            assertArrayEquals(l1.get(i), l2.get(i));
        }
    }
}