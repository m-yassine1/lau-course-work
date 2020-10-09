package graph;

import java.io.File;
import java.util.Scanner;

public class MinimumSpanningTree {
    public static void main(String args[]) throws Exception
    {
        Scanner scan = new Scanner(new File("a.txt"));
        int v = scan.nextInt();
        int e = scan.nextInt();
        int a[][] = new int[v][v];
        while(scan.hasNext())
        {
            int v1 = scan.nextInt();
            int v2 = scan.nextInt();
            int weight = scan.nextInt();

            a[v1][v2] = weight;
            a[v2][v1] = weight;
        }

        int visited[] = new int[v];

        int adj[] = new int[v];
        for(int i = 0 ; i<adj.length ; i++)
            adj[i] = Integer.MAX_VALUE;

        int track[] = new int[v];
        int min = Integer.MAX_VALUE;

        int i = 0;
        visited[0] = 1;
        int count = 0;
        int sum = 0;

        while(count<v-1)
        {
            for(int j = 0 ; j<a[i].length ; j++)
            {
                if(a[i][j]<adj[j] && a[i][j] != 0 && visited[j] == 0)
                {
                    adj[j] = a[i][j];
                    track[j] = i;
                }
            }

            for(int k = 0 ; k<adj.length ; k++)
                if(adj[k]<min && visited[k] == 0)
                    min = adj[k];

            for(int z = 0 ; z<adj.length ; z++)
            {
                if(min == adj[z] && visited[z] ==0)
                {
                    System.out.println(track[z]+" to "+z+" with weight "+adj[z]);
                    sum += adj[z];
                    i = z;
                }
            }
            visited[i] = 1;
            min = Integer.MAX_VALUE;
            count++;
        }
        System.out.println("The total weight is: "+sum);
    }
}
