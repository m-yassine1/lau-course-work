package sorting;

public class QuickSort {
    public static void main(String args[])
    {
        int array[] = {12,9,4,99,120,1,3,10,13};

        System.out.println("Values Before the sort");
        for(int i = 0; i < array.length; i++)
            System.out.print( array[i]+"  ");
        System.out.println();
        quickSort(array,0,array.length-1);
        System.out.print("Values after the sort:\n");
        for(int i = 0; i <array.length; i++)
            System.out.print(array[i]+"  ");
        System.out.println();

    }

    public static void quickSort(int array[],int first, int last)
    {
        int low = first;
        int high = last;
        if (low >= last)
        {
            return;
        }

        int mid = array[(low + high) / 2];//value of the pivot
        while (low < high)
        {
            while (low<high && array[low] < mid)
            {
                low++;
            }
            while (low<high && array[high] > mid)
            {
                high--;
            }
            if (low < high)
            {
                int T = array[low];
                array[low] = array[high];
                array[high] = T;
            }
        }

        if (high < low)
        {
            int T = high;
            high = low;
            low = T;
        }

        quickSort(array, first, low);
        quickSort(array, (low+1) , last);
    }
}
