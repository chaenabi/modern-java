package com.company;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Combinations<T> implements Iterable<ArrayList<T>> {

    private final int totalCombinations;
    private final int n;
    private final int r;
    private int factorial(int n) {
        int p = 1;
        for (int i = n; i > 1; i--) {
            p *= i;
        }
        return p;
    }
    private final ArrayList<T> seq;

    public Combinations(ArrayList<T> sequence, int r) {
        this.r = r;
        seq = sequence;
        n = sequence.size();
        int num = 1;
        for (int i = n, j = n - r + 1; i >= j; i--) {
            num *= i;
        }
        totalCombinations = num / factorial(r);
    }

    @Override
    public Iterator<ArrayList<T>> iterator() {
        return new CombinationIterator();
    }

    private class CombinationIterator implements Iterator<ArrayList<T>> {
        private int count = 0;
        private final int[] pos;

        private CombinationIterator() {
            pos = new int [r];
            for (int i = 0; i < r; i++) {
                pos[i] = i;
            }
        }
        @Override
        public boolean hasNext() {

            return count < totalCombinations;
        }

        @Override
        public ArrayList<T> next() {
            int l, m;
            if(!hasNext())
                throw new NoSuchElementException();
            ArrayList<T> combination = makeList();
            //computing for next iteration
            int k = r - 1;
            if (pos[k] + 1 < n) {
                pos[k]++;
            }
            else {
                l = k - 1;
                while (l >= 0) {
                    if (pos[l] + 1 < n - r + (l + 1)) {
                        pos[l]++;
                        m = l + 1;
                        while (m <= k) {
                            pos[m] = pos[m-1] + 1;
                            m++;
                        }
                        break;
                    }
                    l--;
                }
            }
            count++;
            return combination;
        }

        private ArrayList<T> makeList(){
            ArrayList<T> list = new ArrayList<T>();
            for(int i = 0; i < r; i++) {
                list.add(seq.get(pos[i]));
            }
            return list;
        }
    }

    public static void main(String[] args){
        String test1 = "abcde";
        ArrayList<Character> converted = new ArrayList<>();
        for (int i = 0; i < test1.length(); i++) {
            converted.add(test1.charAt(i));
        }

        Combinations<Character> combCharacter
                = new Combinations<Character>(converted, 3);

        for (ArrayList<Character> arrayList : combCharacter) {
            for (Character character : arrayList) {
                System.out.print(character);
            }
            System.out.println();
        }

    }
}