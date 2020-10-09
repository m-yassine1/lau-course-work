package graph;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Connected {
    public static void main(String args[]) throws Exception
    {
        Scanner scan = new Scanner(new File("g.txt"));
        int v = scan.nextInt();
        int e = scan.nextInt();
        ArrayList<Integer> G[] = new ArrayList[v];
        for(int i = 0 ;i < G.length ;i++)
            G[i] = new ArrayList<Integer>();

        while(scan.hasNext())
        {
            int v1 = scan.nextInt();
            int v2 = scan.nextInt();

            G[v1].add(v2);
            G[v2].add(v1);
        }
        if(isConnected(G,0))
            System.out.println("Is Connected");
        else
            System.out.println("Is not Connected");
    }

    public static boolean isConnected(ArrayList<Integer> G[],int s)
    {
        int[] visited = new int[G.length];
        Queue<Integer> q = new LinkedList<>();
        q.offer(s);
        visited[s] = 1;

        while(!q.isEmpty())
        {
            int node = q.poll();
            for(int i = 0 ;i < G[node].size() ;i++)
            {
                if(visited[G[node].get(i)] == 0)
                {
                    q.offer(G[node].get(i));
                    visited[G[node].get(i)] = 1;
                }
            }
        }

        for(int i = 0; i < visited.length; i++)
        {
            if(visited[i] == 0)
                return false;
        }

        return true;
    }
}
