package org.treef;

import org.treef.model.ziplist.ZipList;
import org.treef.model.ziplist.ZipListLazy;
import org.treef.model.ziptree.NodeLinkTree;
import org.treef.model.ziptree.ZipTreeLazy;
import org.treef.utils.Mut;
import org.treef.utils.adt.Maybe;
import org.treef.utils.adt.T;

import java.util.LinkedList;
import java.util.function.Function;

public class Main {
    private static boolean isPrime(int n) {
        if (n <= 1) return false;

        for (int i = 2; i < n; i++)
            if (n % i == 0) return false;

        return true;
    }
    private static int nextPrime(int n) {
        int m = n + 1;
        while (!isPrime(m)) m++;
        return m;
    }

    public static void main(String[] args) {
        Function<Integer, T<Integer, ZipList<Integer>>> mult = x -> {
            if (x == 1) {
                Function<Integer, Maybe<T<Integer, Integer>>> primes = n -> {
                    int m = nextPrime(n);
                    return new Maybe.Just<>(new T.MkT<>(n, m));
                };
                return new T.MkT<>(x, new ZipListLazy<>(2, primes));
            } else {
                Function<Integer, Maybe<T<Integer, Integer>>> nextMult = m -> {
                    int xMult = x * m;
                    return new Maybe.Just<>(new T.MkT<>(xMult, m+1));
                };
                return new T.MkT<>(x, new ZipListLazy<>(2, nextMult));
            }
        };
        ZipTreeLazy<Integer, Integer> nat = new ZipTreeLazy<>(new ZipListLazy<>(), 1, mult);
        nat.down();
        nat.top();
        nat.down();
        nat.next();
        nat.next();
        nat.down();
        nat.down();
        nat.top();
        nat.top();
        nat.next();
        nat.prev();
        nat.prev();
        nat.prev();
        nat.top();
        nat.drawMemo();

        LinkedList<Integer> list = new LinkedList<>();
    }
}