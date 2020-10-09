package sorting;

public class BubbleSort {
    public static void bubbleSort(int a[] , int n)
    {
        int swap;
        for(int j = n-1 ; j>0 ; j--)
        {
            swap = 0;
            for(int i = 0 ; i<j ; i++)
            {
                if(a[i]>a[i+1])
                {
                    int temp = a[i];
                    a[i] = a[i+1];
                    a[i+1] = temp;
                    swap = 1;
                }
            }
            if(swap == 0)
                break;
        }
    }

    public static void main(String args[])
    {
        int a[] = {9 , 1, 2 , 8 , 3 , 7 , 4 , 6 , 5 , 0};
        int n = a.length;
        bubbleSort(a , n);
        for(int i = 0 ; i<a.length ; i++)
        {
            System.out.println(a[i]);
        }
    }
}
