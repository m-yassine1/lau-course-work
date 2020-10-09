package sorting;

public class SelectionSort {
    public static void main(String args[])
    {
        int array[] = {12,9,4,99,120,1,3,10,13};

        System.out.println("Values Before the sort");
        for(int i = 0; i < array.length; i++)
            System.out.print( array[i]+"  ");
        System.out.println();
        selectionSort(array);
        System.out.print("Values after the sort:\n");
        for(int i = 0; i <array.length; i++)
            System.out.print(array[i]+"  ");
        System.out.println();
    }

    public static void selectionSort(int[] a)
    {
        int i, j, minIndex, tmp;
        int n = a.length;
        for (i = 0; i < n - 1; i++)
        {
            minIndex = i;
            for (j = i + 1; j < n; j++)
                if (a[j] < a[minIndex])
                    minIndex = j;
            if (minIndex != i)
            {
                tmp = a[i];
                a[i] = a[minIndex];
                a[minIndex] = tmp;
            }
        }
    }
}
