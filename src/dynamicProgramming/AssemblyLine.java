package dynamicProgramming;

public class AssemblyLine {
    public static void main(String args[])
    {
        int b1[] = {2 , 3 , 2 , 1};
        int b2[] = {1 , 4 , 2 , 5};
        int d1[] = {1 , 0 , 2};
        int d2[] = {0 , 1 , 2};

        int temp1[] = new int[b1.length];
        int temp2[] = new int[b2.length];

        temp1[0] = b1[0];
        temp2[0] = b2[0];

        for(int i = 1 ; i<b1.length ; i++)
        {
            temp1[i] = b1[i] + Math.min(temp1[i-1] , temp2[i-1]+d2[i-1]);
            temp2[i] = b2[i] + Math.min(temp2[i-1] , temp1[i-1]+d1[i-1]);
        }
        int min = Math.min(temp1[temp1.length-1] , temp2[temp2.length-1]);
        System.out.println("The minimum time needed is: "+min);
    }
}
