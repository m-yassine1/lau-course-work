package graph;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Bipartite {
    public static String bipartite(int G[][] , int s , int visited[] , int color[])
    {
        Queue<Integer> q = new LinkedList<Integer>();
        q.offer(s);
        visited[s] = 1;
        color[s] = 0;
        while(!q.isEmpty())
        {
            int temp = q.poll();
            for(int i = 0 ; i<G[temp].length ; i++)
            {
                if(visited[i] == 0 && G[temp][i] == 1)
                {
                    if(color[temp] == 0)
                    {
                        q.offer(i);
                        color[i] = 1;
                        visited[i] = 1;
                    }
                    else
                    {
                        color[i] = 0;
                        q.offer(i);
                    }
                }
                else if(visited[i] == 1 && G[temp][i] == 1)
                    if(color[temp] == color[i])
                        return "Not Bipartite";
            }
        }
        return "Bipartite";
    }

    public static void main(String args[]) throws Exception
    {
        Scanner scan = new Scanner(new File("g.txt"));
        int v = scan.nextInt();
        int e = scan.nextInt();
        int G[][] = new int[v][v];

        while(scan.hasNext())
        {
            int v1 = scan.nextInt();
            int v2 = scan.nextInt();

            G[v1][v2] = 1;
            G[v2][v1] = 1;
        }

        int visited[] = new int[v];
        int color[] = new int[v];
        System.out.println(bipartite(G , 0 , visited , color));
    }
}
