import java.util.Arrays;



class Item{
    int vertex;
    int weight;
    Item(int v,int p){
        this.vertex=v;
        this.weight =p;
    }
    int get_value(){
        return vertex;
    }
    int get_prio(){
        return weight;
    }
    void set_Both(int val,int prio){
        this.weight =prio;
        this.vertex=val;
    }
}

 class Priority_Queue {
    private Edge[] heap1;
    private Item[] heap;
    private int heapSize;
    private int capacity;

        Priority_Queue(int capacity, String item){
            this.capacity = capacity + 1;
            heapSize = 0;
                if (item.equals("Item")) {
                    this.heap = new Item[this.capacity];
                } else if (item.equals("Edge")) {
                    this.heap1 = new Edge[this.capacity];
                }
        }


    // A class to represent a subset for union-find

        void push(Edge e){
            if (heapSize < heap1.length) {
            int i,j;
            i=++heapSize;
            j=i/2;
            while(i>1&&( heap1[j].weight>e.weight)){
                heap1[i]=heap1[j];
                i=j;
                j=i/2;
            }
            heap1[i]=e;
        }
            else{
                this.heap1= Arrays.copyOf(heap1,2*heap1.length);
                push(e);
            }
        }
        Edge popMaxEdge(){
            Edge h=heap1[1];
            heap1[1] = heap1[heapSize--];
            heap1[heapSize+1]=h;
            minheapify(1,false);
            return h;
        }

        void swap( int idx1,int idx2,boolean t){
            if(t) {
                int temp = heap[idx1].get_value();
                int temp2 = heap[idx1].get_prio();
                heap[idx1].set_Both(heap[idx2].get_value(), heap[idx2].get_prio());
                heap[idx2].set_Both(temp, temp2);
            }
            else{
                int temp = heap1[idx1].src;
                int temp1 = heap1[idx1].dest;
                int temp2 = heap1[idx1].weight;
                heap1[idx1].setEdges(heap1[idx2].src,heap1[idx2].dest,heap1[idx2].weight);
                heap1[idx2].setEdges(temp,temp1,temp2);
            }
        }

        void insert_value (   int val,int prio){
            Item item= new Item(val,prio);
            int i,j;
            i=++heapSize;
            j=i/2;
            while(i>1&&(heap[j].get_prio()>item.get_prio())){
                heap[i]=heap[j];
                i=j;
                j=i/2;
            }
            heap[i]=item;
            }

        public  int maxShow(){
            if(isEmpty())
            {
                System.out.println();
                return -1;
            }
            return heap[1].get_value();
        }
        int popMaxItem(){
            if(heapSize == 0) {
                System.out.println();
                return 0;
            }
            Item h=heap[1];
            heap[1] = heap[heapSize--];
            heap[heapSize+1]=h;
            minheapify(1,true);
            return h.vertex;
        }



     public void changePriority(int x, int p) {
         if (!isEmpty()) {
             int k;
             for (int i = 1; i < heapSize; i++)
                 if (heap[i].get_value() == x && heap[i].get_prio() > p) {
                     heap[i].weight=p;
                     k = i;
                     while (k != 0 && heap[k/2].get_prio() > heap[k].get_prio()) {
                         swap(k/2, k,true);
                         k = k/2;
                     }

                 }
         }
     }
        void minheapify( int i,boolean Item){
            int left = i * 2 ;
            int right = i * 2 + 1;

            int smallest;
            if(left <= heapSize && (Item ? heap[left].get_prio() < heap[i].get_prio() : heap1[left].weight < heap1[i].weight))
                smallest = left;
            else
                smallest = i;
            if(right <= heapSize && (Item ? heap[right].get_prio() < heap[smallest].get_prio() : heap1[right].weight < heap1[smallest].weight) )
                smallest = right;
            if(smallest != i )
            {
                swap (i, smallest,Item);
                minheapify (smallest,Item);
            }
        }
        void build_minheap(boolean item){
            for(int i = heapSize/2 ; i >= 1 ; i-- )
            {
                minheapify (i,item) ;
            }

        }
        boolean isEmpty(){
            return heapSize==0;
        }
        void printQue(){
            int h=heapSize;
            for (int i=1;i<=h;i++) {
                System.out.println("(" + heap[1].get_value() + " , " + heap[1].get_prio() + ")");
                popMaxItem();
            }
            heapSize=h;
            build_minheap(true);
        }
        void printQue1(){
            for (int i=1;i<=heapSize;i++) {
                System.out.println("(" + heap[i].get_value() + " , " + heap[i].get_prio() + ")");
            }
        }
    }

