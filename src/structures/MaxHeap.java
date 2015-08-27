package structures;

public class MaxHeap{
    private int currentSize;      // Number of elements in heap
    private int[] array; // The heap array
    
    public MaxHeap(int capacity){
        currentSize = 0;
   array = new int[ capacity + 1];
            
        buildHeap();
    }

    public void insert( int x ) throws Exception{
        if( isFull( ) )
            throw new Exception( );

        // Percolate up
        int hole = ++currentSize;
        
        for( hole = currentSize; hole > 1 && x> array[ hole / 2 ]; hole=hole/2 )
          {  array[ hole ] = array[ hole / 2 ];
            }        
        array[ hole ] = x;
    }

    public int findMax( ) throws Exception{
        if( isEmpty( ) )
            throw new Exception();
        
        return array[ 1 ];
    }

    public int deleteMax( ) throws Exception{
        if( isEmpty( ) )
            throw new Exception();

        int maxItem = findMax();
        array[ 1 ] = array[ currentSize-- ];
        percolateDown( 1 );
        return maxItem;
    }

    private void buildHeap( ){
        for( int i = currentSize / 2; i > 0; i-- )
            percolateDown( i );
    }

    public boolean isEmpty( ){
        return currentSize == 0;
    }

    public boolean isFull( ){
        return currentSize == array.length - 1;
    }

    public void makeEmpty( ){
        currentSize = 0;
    }

    private void percolateDown( int hole ){
        int child;
        int tmp = array[ hole ];

        for( ; hole * 2 <= currentSize; hole = child ){
            child = hole * 2;
                if(child != currentSize && array[ child + 1 ] > array[ child ])
                    child++;
                if( array[ child ]>tmp)
                    array[ hole ] = array[ child ];
                else
                    break;
        }
        
      //  array[ hole ] = tmp;
     }
     public void print(){
         
         for(int i=1;i<currentSize+1;i++)
         {
             System.out.print(array[i]+" ");
            }
         System.out.println();
        }
 ///////////////////heap sort/////////////////////////////////
  static   void   HeapSort(int[]   a){  
  for(int   i=a.length/2-1;   i>=0;   i--){  
        adjustHeap(a,   i,   a.length-1);  
     }  
  for(int   i=a.length-1,temp;   i>=1;   i--){  
            temp   =   a[0];  
             a[0]   =   a[i];  
         a[i]   =   temp;  
           adjustHeap(a,   0,   i-1);  
  }  
  }  
  static   void   adjustHeap(int[]   a,   int   fromIndex,   int   toIndex){  
  int   key   =   a[fromIndex],temp;  
  for(int   i=2*fromIndex;   i<=toIndex;   i*=2){  
  if(i<toIndex   &&   a[i]<a[i+1])i++;  
  if(key   >=   a[i])break;  
  temp   =   a[fromIndex];  
  a[fromIndex]   =   a[i];  
  a[i]   =   temp;  
  fromIndex   =   i;  
  }  
  a[fromIndex]   =   key;  
  }   
  ///////////////////////heapsort//////////////////////////////////////////
        public static void main(String[] argv)throws Exception{
          int[] a={7, 12, 1, 3, 22, 5, 11};   
            MaxHeap heap=new MaxHeap(a.length);
            for(int i=0;i<a.length;i++){
          //  System.out.println(a[i]);
              heap.insert(a[i]);
            }
           System.out.println("compare");
           heap.print();
          int b=heap.deleteMax( );
          System.out.println(b);
           heap.print();
          int c=heap.deleteMax( );
            System.out.println(c);
             
            
          heap.print();
            
            
            
        }
}