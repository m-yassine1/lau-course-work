package sorting;

public class InsertionSort {
    public static void Insertion(int array[] , int n)
    {
        for(int i = 1 ; i<n ; i++)
        {
            int j = i;
            int B = array[i];
            while ((j > 0) && (array[j-1] > B))
            {
                array[j] = array[j-1];
                j--;
            }
            array[j] = B;
        }
    }
    public static void main(String args[])
    {
        int a[] = {1 , 9, 2 , 8 , 3 , 7 , 4 , 6 , 5 , 0};
        int n = a.length;
        Insertion(a , n);
        for(int i = 0 ; i<a.length ; i++)
        {
            System.out.println(a[i]);
        }
    }
}
