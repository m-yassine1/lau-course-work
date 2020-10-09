package dynamicProgramming;

public class LongestCommonSubsequence {
    public static int Longest(String str1 , String str2)
    {
        if(str1.length() == 0 || str2.length() == 0)
            return 0;
        if(str1.charAt(0) == str2.charAt(0))
            return 1+Longest(str1.substring(1) , str2.substring(1));
        return Math.max(Longest(str1 , str2.substring(1)) , Longest(str1.substring(1) , str2));
    }

    public static void main(String args[])
    {
        String str1 = "abdefc";
        String str2 = "acefg";
        System.out.println(Longest(str1 , str2));
    }
}
