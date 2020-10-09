package dynamicProgramming;

public class CoinExchange {
    public static void main(String args[])
    {
        int c[] ={1 , 3 , 5 , 7};
        int max = 11;

        int a[] = new int[max+1];

        for(int i = 0 ; i<c.length ; i++)
        {
            for(int j = c[i] ; j<a.length ; j++)
            {
                if(a[j-c[i]]+1 > a[j]-c[i])
                    a[j] = a[j-c[i]]+1;
            }
        }
        for(int i = 0 ; i<a.length ; i++)
            System.out.print(a[i]+" ");
        System.out.println();
        System.out.println("The number of coins needed is: " + a[a.length -1]);
    }
}
