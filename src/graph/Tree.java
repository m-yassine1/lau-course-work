package graph;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Tree {
    public static void main(String args[]) throws Exception
    {
        Scanner scan = new Scanner(new File("g.txt"));
        int v = scan.nextInt();
        int e = scan.nextInt();
        ArrayList<Integer> G[] = new ArrayList[v];
        for(int i = 0 ;i < G.length ;i++) {
            G[i] = new ArrayList<>();
        }

        while(scan.hasNext())
        {
            int v1 = scan.nextInt();
            int v2 = scan.nextInt();

            G[v1].add(v2);
            G[v2].add(v1);
        }
        if(isTree(G,0))
            System.out.println("Is Tree");
        else
            System.out.println("Is not a Tree");
    }

    public static boolean isTree(ArrayList<Integer> G[],int s)
    {
        if(!isCycle(G, 0) && isConnected(G, s))
            return true;
        return false;
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

    public static boolean isCycle(ArrayList<Integer> G[] , int s)
    {
        Queue<Integer> q = new LinkedList<>();
        int inQue[] = new int[G.length];
        int[] visited = new int[G.length];
        q.offer(s);
        visited[s] = 1;
        inQue[s] = 1;
        while(!q.isEmpty())
        {
            int temp = q.poll();
            for(int i = 0 ; i<G[temp].size() ; i++)
            {
                if(visited[G[temp].get(i)] == 0)
                {
                    int x = G[temp].get(i);
                    q.offer(x);
                    visited[G[temp].get(i)] = 1;
                    inQue[x] = 1;
                }
                else if (visited[G[temp].get(i)] == 1)
                    if(inQue[G[temp].get(i)] == 1)
                        return true;
            }
            inQue[temp] = 0;
        }
        return false;
    }
}
