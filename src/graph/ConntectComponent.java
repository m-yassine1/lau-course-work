package graph;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class ConntectComponent {
    static int max = Integer.MIN_VALUE;
    static int c = 0;
    public static void check(ArrayList<Integer> G[] , int visited[] , int s , int count)
    {
        Queue<Integer> q = new LinkedList<>();
        q.offer(s);
        visited[s] = count;
        while(!q.isEmpty())
        {
            int temp = q.poll();
            for(int i = 0 ; i<G[temp].size() ; i++)
            {
                if(visited[G[temp].get(i)] == 0)
                {
                    int x = G[temp].get(i);
                    q.offer(x);
                    visited[G[temp].get(i)] = count;
                }
            }
        }
        for(int i = 0 ; i<visited.length ; i++)
        {
            if(visited[i] == 0)
            {
                count = count + 1;
                check(G , visited , i , count);
            }
        }
        for(int i = 0 ; i<visited.length ; i++)
        {
            if(visited[i]>max)
                max = visited[i];
        }
        if(c == 0)
        {
            while(max>=0)
            {
                for(int i = 0 ; i<visited.length ; i++)
                {
                    if(visited[i] == max)
                        System.out.print(i+" ");
                }
                if(max != 0)
                    System.out.println(":are in the same graph.");
                max--;
            }
            c++;
        }
    }

    public static void main(String args[]) throws Exception
    {
        Scanner scan = new Scanner(new File("g.txt"));
        int v = scan.nextInt();
        int e = scan.nextInt();
        ArrayList<Integer> G[] = new ArrayList[v];
        for(int i = 0 ; i<G.length ; i++)
            G[i] = new ArrayList<>();

        while(scan.hasNext())
        {
            int v1 = scan.nextInt();
            int v2 = scan.nextInt();

            G[v1].add(v2);
            G[v2].add(v1);
        }

        int visited[] = new int[v];
        int count = 1;
        check(G , visited , 0 , count);
    }
}
