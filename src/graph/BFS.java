package graph;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class BFS {
    public static void main(String args[]) throws Exception
    {
        Scanner scan = new Scanner(new File("g.txt"));
        int v = scan.nextInt();
        int e = scan.nextInt();
        ArrayList<Integer> a[] = new ArrayList[v];
        for(int i = 0 ; i<a.length ; i++)
            a[i] = new ArrayList<>();
        while(scan.hasNext())
        {
            int v1 = scan.nextInt();
            int v2 = scan.nextInt();

            a[v1].add(v2);
            a[v2].add(v1);
        }
        for(int i = 0 ; i<a.length ; i++)
        {
            for(int j = 0 ; j<a[i].size() ; j++)
            {
                System.out.print(a[i].get(j)+ " ");
            }
            System.out.println();
        }
        System.out.println("\n");
        int visited[] = new int[v];
        bfs(a , 0 , visited);
    }
    public static void bfs(ArrayList<Integer> G[] , int s , int visited[])
    {
        Queue<Integer> q = new LinkedList<>();
        q.offer(s);
        visited[s] = 1;
        while(!q.isEmpty())
        {
            int temp = q.poll();
            System.out.println(temp);
            for(int i = 0 ; i<G[temp].size() ; i++)
            {
                if(visited[G[temp].get(i)] == 0)
                {
                    int x = G[temp].get(i);
                    q.offer(x);
                    visited[G[temp].get(i)] = 1;
                }
            }
        }
        for(int i = 0 ; i<visited.length ; i++)
            if(visited[i] == 0)
                bfs(G , i , visited);
    }
}
