package structures;

public class KMP {
    public static int[] next(char[] s)
    {
        int length = s.length;
        int next[]=new int[length];
        next[0]=-1;
        for(int i=1;i<length;i++)
        {
            /*???????????????next?????????????????next?????????next????1*/
            int temp = next[i-1];
            while(temp!=-1&&s[i-1]!=s[temp])
            {
                temp = next[temp];
            }
            if(temp==-1) next[i] = 0;
            else next[i] = temp+1;
        }
        return next;
    }

    public static int compare(char[] t, char[] s)
    {
        int tlen = t.length;
        int slen = s.length;
        int [] next = next(s);
        System.out.println("the function is   ");
        for(int i=0;i<next.length;i++){
                 
                      System.out.print(next[i]+" ");
        }
         System.out.println();
        int i=0;
        int j=0;
      while (i<tlen){
          if(t[i]==s[j]){
              if (j==slen-1){
                 return i-j; 
                }
              else 
              {
                 i++;
                 j++;
                }
            }else{
             if(j>0)
                 j=next[j];
                else
                 i++;                
            }
          
          
        }
        return -1;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        char s[] = "aaaaa".toCharArray();
        char t[] = "zhangabliababling".toCharArray();
        int temp = compare(t,s);
        System.out.println("the outprint is");
        System.out.println(temp);
        
    }

}