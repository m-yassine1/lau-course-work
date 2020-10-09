package dynamicProgramming;

public class KnapSack {
    public static void main(String args[])
    {
        int capacity = 5;
        int weight[] = {3 , 2 , 1};
        int value[] = {5 , 3 , 4};

        int a[][] = new int[value.length+1][capacity+1];
        int keep[][] = new int[value.length+1][capacity+1];

        for(int i = 1; i < a.length; i++)
        {
            for(int j = 1 ; j<a[i].length ; j++)
            {
                if(weight[i-1]>j)
                    a[i][j] = a[i-1][j];
                else
                {
                    a[i][j] = Math.max(a[i-1][j] , (value[i-1]+a[i-1][j-weight[i-1]]));
                    if(a[i][j] == (value[i-1]+a[i-1][j-weight[i-1]]))
                        keep[i][j] = 1;
                }
            }
        }
        for(int i = 0 ; i<a.length ; i++)
        {
            for(int j = 0 ; j<a[i].length ; j++)
                System.out.print(a[i][j]+" ");
            System.out.println();
        }
        System.out.println("\n");
        for(int i = 0 ; i<keep.length ; i++)
        {
            for(int j = 0 ; j<keep[i].length ; j++)
                System.out.print(keep[i][j]+" ");
            System.out.println();
        }

        System.out.println("\n");
        int r = keep.length-1;
        int c = capacity;
        while(r>0)
        {
            if(keep[r][c] == 1)
            {
                System.out.println("Item "+r+" is taken.");
                c = c-weight[r-1];
                r = r-1;
            }
            else
            {
                System.out.println("Item "+r+" is not taken.");
                r = r-1;
            }
        }
    }
}
