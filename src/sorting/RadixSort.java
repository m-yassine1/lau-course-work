package sorting;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class RadixSort {
    public static int getMax(int[] array)
    {
        String length = "";
        int max = array[0];
        for(int i = 1; i < array.length; i++)
        {
            if(array[i] > max)
                max = array[i];
        }
        length+=max;
        return length.length();
    }

    public static void printBucket(ArrayList<Integer>[] bucket)
    {
        for(int i = 0; i < bucket.length; i++)
        {
            System.out.print(i+": ");
            for(int j = 0; j < bucket[i].size(); j++)
            {
                System.out.print(bucket[i].get(j)+" ");
            }
            System.out.println();
        }
    }

    public static void print(int[] array)
    {
        for(int i = 0; i < array.length; i++)
            System.out.print(array[i]);
        System.out.println();
    }

    @SuppressWarnings("unchecked")
    public static void radixSort(int array[],int max)
    {
        ArrayList<Integer>[] bucket = new ArrayList[10];
        int remain10 = 10;
        int digit10 = 1;
        int digit = 0;
        /*Initializing the bucket*/
        for(int i = 0; i < bucket.length; i++)
        {
            bucket[i] = new ArrayList<Integer>();
        }

        for(int digits = 0; digits < max; digits++)
        {
            /*Placing the elements into the bucket based on the digit*/
            for(int i = 0; i < array.length; i++)
            {
                digit = array[i] % remain10;
                digit = digit / digit10;
                bucket[digit].add(array[i]);
            }

            /*Putting the elements back from 0 -> 9 in the array*/
            int index = 0;
            for(int i = 0; i < bucket.length; i++)
            {
                for(int j = 0; j < bucket[i].size(); j++)
                {
                    array[index++] = bucket[i].get(j);
                }
            }

            /*Clearing bucket*/
            for(int i = 0; i < bucket.length; i++)
            {
                bucket[i].clear();
            }

            remain10 *= 10;
            digit10 *= 10;
        }
    }

    public static void main(String args[]) throws Exception
    {
        int length = 0;
        Scanner scan = new Scanner(new File("sort.in"));
        int cases = scan.nextInt();
        for(int Case = 0; Case < cases; Case++)
        {
            int arraylength = scan.nextInt();
            int[] array = new int[arraylength];
            for(int i = 0; i < arraylength; i++)
            {
                array[i] = scan.nextInt();
            }
            length = getMax(array);
            radixSort(array,length);
        }
    }
}
