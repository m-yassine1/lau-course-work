package graph;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class DFS {
    public static void dfs(ArrayList<Integer> G[] , int a , int visited[])
    {
        Stack<Integer> s = new Stack<Integer>();
        s.push(a);
        visited[a] = 1;
        while(!s.isEmpty())
        {
            int temp = s.pop();
            System.out.println(temp);
            for(int i = 0 ; i<G[temp].size() ; i++)
            {
                if(visited[G[temp].get(i)] == 0)
                {
                    int x = G[temp].get(i);
                    s.push(x);
                    visited[G[temp].get(i)] = 1;
                }
            }
        }
    }

    public static void main(String args[]) throws Exception
    {
        Scanner scan = new Scanner(new File("g.txt"));

        int v = scan.nextInt();
        int e = scan.nextInt();

        ArrayList<Integer> G[] = new ArrayList[v];

        for(int i = 0 ; i<G.length ; i++)
            G[i] = new ArrayList<Integer>();

        while(scan.hasNext())
        {
            int v1 = scan.nextInt();
            int v2 = scan.nextInt();

            G[v1].add(v2);
            G[v2].add(v1);
        }

        for(int i = 0 ; i<G.length ; i++)
        {
            for(int j = 0 ; j<G[i].size() ; j++)
            {
                System.out.print(G[i].get(j) + " ");
            }
            System.out.println();
        }

        int visited[] = new int[v];
        System.out.println("\n");
        dfs(G , 0 , visited);
    }
}
