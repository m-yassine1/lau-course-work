package backTracking;

import java.util.ArrayList;
import java.util.Scanner;

public class KColor {
    public static void main(String args[]) throws Exception
    {
        Scanner scan = new Scanner(System.in);
        int N, E, u, v, i, k;

        N = scan.nextInt();
        ArrayList<Integer>[] al = new ArrayList[N];
        int[] color = new int[N];
        for(i = 0; i < al.length; i++)
            al[i] = new ArrayList<>();

        E = scan.nextInt();
        for(i = 0;i < E; i++)
        {
            u = scan.nextInt();
            v = scan.nextInt();
            al[u].add(v);
            al[v].add(u);//undirected graph
        }

        k = scan.nextInt();//number of colors
        if(kColor(al, color, k))
            System.out.println("yes");
        else
            System.out.println("no");
    }

    public static boolean kColor(ArrayList<Integer>[] al, int[] color,int k)
    {
        int vertex = selectVertex(color);//criteria: first uncolored vertex

        if(vertex == -1)//All vertices are colored
            return true;

        for(int i = 1; i <= k; i++)
        {
            if(possibleToColor(al,vertex,color,k))
            {
                color[vertex] = k;
                if(kColor(al,color,k))
                    return true;
                color[vertex] = 0;
            }
        }

        return false;
    }

    private static boolean possibleToColor(ArrayList<Integer>[] al, int vertex, int[] color, int k)
    {
        int size = al[vertex].size();
        int neighbor = 0;

        for(int i = 0; i < size; i++)
        {
            neighbor = al[vertex].get(i);
            if(color[vertex] == color[neighbor])
                return false;
        }

        return true;
    }

    private static int selectVertex(int[] color)
    {
        for(int i = 0; i < color.length; i++)
            if(color[i] == 0)
                return i;

        return -1;
    }
}
