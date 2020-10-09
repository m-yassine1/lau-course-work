package graph;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Cycle {
    public static void main(String args[]) throws Exception
    {
        Scanner scan = new Scanner(new File("g.txt"));
        int v = scan.nextInt();
        int e = scan.nextInt();
        ArrayList a[] = new ArrayList[v];
        for(int i = 0 ; i<a.length ; i++)
            a[i] = new ArrayList<Integer>();
        while(scan.hasNext())
        {
            int v1 = scan.nextInt();
            int v2 = scan.nextInt();

            a[v1].add(v2);
            a[v2].add(v1);
        }

        int visited[] = new int[v];

        if(!check(a , 0 , visited))
            System.out.println("There is no cycles.");
        else
            System.out.println("A cycle exists.");
    }

    public static boolean check(ArrayList<Integer> G[] , int s , int visited[])
    {
        Queue<Integer> q = new LinkedList<>();
        int inQue[] = new int[G.length];
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
