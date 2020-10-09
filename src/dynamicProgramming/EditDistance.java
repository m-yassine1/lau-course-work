package dynamicProgramming;

import java.io.File;
import java.util.Scanner;

public class EditDistance {
    public static void main(String args[]) throws Exception
    {
        Scanner scan = new Scanner(new File("LCS.txt"));
        int Cases = scan.nextInt();

        for(int numCases = 0; numCases < Cases; numCases++)
        {
            int insert = scan.nextInt();
            int delete = scan.nextInt();
            int replace = scan.nextInt();
            String s1 = scan.next();
            String s2 = scan.next();
            int min = 0;
            int[][] sol = new int[s1.length() + 1][s2.length() + 1];

            for(int i = 1; i < sol.length; i++)
            {
                for(int j = 1; j < sol[i].length; j++)
                {
                    min = Math.min(sol[i - 1][j] + delete, sol[i][j - 1] + insert);
                    if(s1.charAt(i - 1) == s2.charAt(j - 1))
                    {
                        min = Math.min(sol[i][j], sol[i - 1][ j - 1]);
                    }
                    else
                    {
                        min = Math.min(sol[i][j], sol[i - 1][j - 1] + replace);
                    }

                    sol[i][j] = min;
                }
            }

            System.out.println(sol[sol.length - 1][sol[0].length - 1]);
        }

    }
}
