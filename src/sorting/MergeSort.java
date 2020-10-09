package sorting;

import java.util.Scanner;

public class MergeSort {
    /*Using subarrays*/
    public static void Split(int a[])
    {
        if(a.length<=1)
            return;
        int mid = a.length / 2;
        int first[] = new int[mid];
        int second[] = new int[a.length - mid];
        for (int i = 0 ; i<mid ; i++)
        {
            first[i] = a[i];
        }
        int temp = 0;
        for(int i = mid ; i<a.length ; i++)
        {
            second[temp++] = a[i];
        }
        Split(first);///////////////////////the requires T(n/2)
        Split(second);///////////////////////the requires T(n/2)
        Merge(first , second , a);///////////////////////the requires n where n is the number of elemets
    }

    public static void Merge(int first[] , int second[] , int num[])
    {
        int x = 0;//index for the left Array
        int y = 0;//index for the right Array
        int z = 0;//index for the num array

        while(x < first.length && y < second.length)
        {
            if(first[x] < second[y])
            {
                num[z++] = first[x++];
            }
            else
            {
                num[z++] = second[y++];
            }

            for(int i = x; i < first.length; i++)
            {
                num[z++] = first[i];
            }

            for(int i = y; i < second.length; i++)
            {
                num[z++] = second[i];
            }
        }
    }

    public static void main(String arsg[])
    {
        Scanner scan = new Scanner(System.in);
        int need[] = new int[10];
        for (int i = 0 ; i<need.length ; i++)
        {
            need[i] = scan.nextInt();
        }
        Split(need);
        for (int i = 0 ; i<need.length ; i++)
        {
            System.out.print(need[i]+" ");
        }
    }
    /*Pointer style*/
    public static void mergeSort(int A[], int start, int end) {
        if (start < end) {
            int mid = (start + end)/2;
            mergeSort(A, start, mid);
            mergeSort(A, mid+1, end);
            merge(A, start, mid, mid+1, end);
        }
    }

    public static void merge(int A[], int s1, int e1, int s2, int e2) {
        int B[] = new int[e2 - s1 + 1];

        int i = s1, j = s2, k = 0;

        while (i <= e1 && j <= e2) {
            if(A[i] < A[j])
                B[k++] = A[i++];
            else
                B[k++] = A[j++];
        }

        while (i <= e1)
            B[k++] = A[i++];

        while (j <= e2)
            B[k++] = A[j++];

        k = 0;

        for (i = s1; i <= e2; i++)
            A[i] = B[k++];
    }
}
