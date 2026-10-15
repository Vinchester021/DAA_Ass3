package com.example.daa;

import com.example.daa.algorithms.DSU;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class DSUTest {

    @Test
    void randomUnionsMatchReference() {

        int n = 200;

        DSU dsu = new DSU(n);

        int[] reference = new int[n];

        for (int i = 0; i < n; i++) {
            reference[i] = i;
        }

        int referenceComponents = n;

        Random rnd = new Random(42);

        for (int operation = 0; operation < 1000; operation++) {

            int a = rnd.nextInt(n);
            int b = rnd.nextInt(n);

            boolean expectedUnion =
                    reference[a] != reference[b];

            boolean actualUnion =
                    dsu.union(a, b);

            assertEquals(expectedUnion, actualUnion);

            if (expectedUnion) {

                int oldGroup = reference[b];
                int newGroup = reference[a];

                for (int i = 0; i < n; i++) {
                    if (reference[i] == oldGroup) {
                        reference[i] = newGroup;
                    }
                }

                referenceComponents--;
            }

            assertEquals(
                    referenceComponents,
                    dsu.components()
            );
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                boolean expectedConnected =
                        reference[i] == reference[j];

                boolean actualConnected =
                        dsu.find(i) == dsu.find(j);

                assertEquals(
                        expectedConnected,
                        actualConnected
                );
            }
        }
    }
}