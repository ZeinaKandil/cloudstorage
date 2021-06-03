package com.udacity.jwdnd.course1.cloudstorage.model;

import java.util.*;

public class test {
    static int fib(int n)
    {
        if (n <= 1)
            return n;
        return fib(n-1) + fib(n-2);
    }

    public static void main (String args[])
    {
        int n = 9;
        List<Integer> l = new ArrayList<>();
        System.out.println(fib(n));
    }
    public static int numDuplicates(List<String> name, List<Integer> price, List<Integer> weight) {
        // Write your code here
        int count = 0;
        int n = name.size();
        String [] s = new String [n];
        for (int i = 0; i < n; i++) {
            s[i] = name.get(i)+price.get(i)+" "+ weight.get(i);
        }
        HashSet<String> hs = new HashSet<>();
        for (int i = 0; i < n; i++) {
            if(!hs.contains(s[i]))
                hs.add(s[i]);
            else
                count++;
        }
        return count;
    }

    static int n1=0,n2=1,n3=0;
    static void printFibonacci(int count){
        if(count>0){
            n3 = n1 + n2;
            n1 = n2;
            n2 = n3;
            System.out.print(" "+n3);
            printFibonacci(count-1);
        }
    }


}
