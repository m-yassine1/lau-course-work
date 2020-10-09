package dynamicProgramming;

import java.util.Scanner;

public class LongestIncreasingSequence {
    public static void main(String args[])
    {
        Scanner scan = new Scanner(System.in);
        //int testcases = scan.nextInt();
        for(int cases = 0; cases < 1; cases++)
        {
            int length = scan.nextInt();
            int num[] = new int[length];
            int a[] = new int[num.length];

            for(int i = 0 ; i<a.length ; i++)
                a[i] = 1;

            for(int i = 1 ; i<num.length ; i++)
            {
                for(int j = i-1 ; j>= 0 ; j--)
                    if(num[j]<num[i])
                        if(a[j]+1>a[i])
                            a[i] = a[j]+1;
            }
            int max = Integer.MIN_VALUE;
            for(int i = 0 ; i<a.length ; i++)
            {
                if(max<a[i])
                    max = a[i];
            }
            System.out.println(max);
			/*System.out.print("Thier values are: ");
			for(int i = a.length-1 ; i>=0 ; i--)
			{
				if(a[i] == max)
				{
					System.out.print(num[i]+" ");
					max--;
				}

			}*/
        }

    }
}
