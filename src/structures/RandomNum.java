package structures;

 

import java.util.Random;

public class RandomNum {
    int[] A;
   
    //develop a array random number of double type
    void setRandom( ){
        int j = 0;
        double k;
        A = new int[300000];
        Random rand = new Random();
       
        for(int i = 1; i <= 300000; i++){
            if(i == 300000) System.out.println(i);
            do{
                k = rand.nextDouble();
                j = (int)((k * 1000000000) % 300000);
            }
            while((j == 0) || (j == 299999) || ((A[j] != 0) && (A[j - 1] != 0) && (A[j + 1] != 0)));
                if(A[j] == 0) A[j] = i;
                else if(A[j - 1] == 0) A[j - 1] = i;
                else if(A[j + 1] == 0) A[j + 1] = i;
           
        }
    }
}



/*
 * @(#)Main.java
 *
 * Title: This class is to operate the RBtree.
 * Description: This file is the assignment of Algorithms.
 * @copyright (c) OpenSource
 * @author Bobby Yang
 * @tester Bobby Yang
 * @version 1.00
 * @time 2005.11.02
 */


