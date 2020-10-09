package sorting;

public class CountingSort {
    public static void main(String args[])
    {
        int a[] = {5 , 9 , 10 , 9 , 6 , 24 , 1 , 21};
        int max = a[0];
        for(int i = 1 ; i<a.length ; i++)
        {
            if(max<a[i])
                max = a[i];
        }
        int temp[] = new int[max+1];
        for(int z = 0 ; z<a.length ; z++)
        {
            temp[a[z]]++;
        }
        for(int i = 0 ; i<temp.length ; i++)
        {
            for(int j = 0 ; j<temp[i] ; j++)
            {
                System.out.println(i);
            }
        }
    }
}
