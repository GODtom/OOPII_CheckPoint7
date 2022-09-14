import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashMap;
public class A1083331_checkpoint7_RouteFinder {
    //Description : The target block.
    private A1083331_checkpoint7_Block target;
    //Description : The hashmap that records the parent block.
    private HashMap<A1083331_checkpoint7_Block, A1083331_checkpoint7_Block> ParentBlock;
    //Description : Record which block has been visited.
    private boolean[][] visited ;
    // Description : The root frame.
    private A1083331_checkpoint7_GameFrame parentFrame;
    //Description : the map with all blocks.
    //You can get the location block you want with typing map[x][y].
    private A1083331_checkpoint7_Block[][] map;
    //Description : record the cost if you go on the block.
    private HashMap<A1083331_checkpoint7_Block, Integer> accumulatedCost;
    // Description : The route searching algorithm.
    private int algorithm;
    private A1083331_checkpoint7_Fringe fringe;
    private static final int DFS = 0;
    private static final int BFS = 1;
    private static final int UCS = 2;
    public A1083331_checkpoint7_RouteFinder(A1083331_checkpoint7_GameFrame parentFrame, A1083331_checkpoint7_Block target, A1083331_checkpoint7_Block root,int algorithm, A1083331_checkpoint7_Block[][] map){
        /**********************************The TODO This Time (A1083331_Checkpoint7)**************************
         * 
         * TODO(1): For the TODO here, you have to implement fringe according "algorithm".
         * 
         * Hint(1): The BFS algorithm needs to use the queue to work, so we make a object named BlockQueue for BFS.
         * Hint(2): The DFS algorithm needs to use the stack to work, so we make a object named BlockStack for DFS.
         * Hint(3): The UCS algorithm needs to use the priority queue  to work, so we make a object named PriorityQueue for UCS.
         * Hint(4): These three objects all implement the fringe, and the detail description can be found 
         *          in the code of Fringe.java, BlockQueue.java, BlockStack.java, BlockPriorityQueue.java.
         * Hint(5): You have to add the root (the player current location) into fringe.
         * Hint(6): To calculate the priority, you have to implement a Comparator<block> object and make 
         *          it as an input in the constructor of BlockPriorityQueue.
         * Hint(7): Before starting the searching, you need to initialize the accumulatedCost and set the root with
         *          its cost.
         **********************************The End of the TODO**************************************/
        this.target = target;
        this.ParentBlock = new HashMap<A1083331_checkpoint7_Block, A1083331_checkpoint7_Block>();
        this.parentFrame = parentFrame;
        this.visited = new boolean[4096 / 256][4096 / 256];
        this.accumulatedCost = new HashMap<A1083331_checkpoint7_Block, Integer>();
        this.algorithm = algorithm;
        this.map = map;
        for(int x = 0 ; x < 4096 / 256; x++ ){
            for(int y = 0 ; y < 4096 / 256; y++ ){
                visited[x][y] = false;
            }
        }
        /********************************************************************************************
         START OF YOUR CODE
        ********************************************************************************************/
        if(algorithm==0){
            fringe= new A1083331_checkpoint7_BlockStack();
            fringe.add(root);
        }
        else if(algorithm==1){
            fringe= new A1083331_checkpoint7_BlockQueue();
            fringe.add(root);
        }
        else if(algorithm==2){
            fringe = new A1083331_checkpoint7_BlockPriorityQueue(new Comparator<A1083331_checkpoint7_Block>(){
                public int compare(A1083331_checkpoint7_Block a,A1083331_checkpoint7_Block b){
                        return a.getCost()-b.getCost();
                }
            });
            fringe.add(root);
        }

        
        accumulatedCost.put(root,root.getCost());
        visited[root.getX()][root.getY()]=true;
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/
    }
    private A1083331_checkpoint7_Block search(){
        /*********************************The TODO (A1083331_Checkpoint7)********************************
         * 
         * TODO(14.1): For the TODO here, you have to implement the searching funciton;
         * TODO(14.2): You MUST print the message of "Searching at (x, y)" in order to let us check if you sucessfully do it.
         * TODO(14.3): After you find the target, you just need to return the target block.
         * //System.out.println("Searching at ("+Integer.toString(YOURBLOCK.getX())+", "+Integer.toString(YOURBLOCK.getY())+")");
         * 
         * Hint(1): If the target can not be search you should return null(that means failure).
         * 
         * pseudo code is provided here: 
         *   1. get the block from fringe.
         *   2. print the message
         *   3. if that block equals target return it.
         *   4. if not, expand the block and insert then into fringe.
         *   5. return to 1. until the fringe does not have anyting in it.
         * 
         **********************************The End of the TODO**************************************/
        
        /********************************************************************************************
         START OF YOUR CODE
        ********************************************************************************************/
        while(!(fringe.isEmpty())){
            A1083331_checkpoint7_Block currentBlock =new A1083331_checkpoint7_Block(0,0,"type",0);       
            currentBlock=fringe.remove();
            System.out.println("Searching at ("+currentBlock.getX()+", "+currentBlock.getY()+")");
            if(currentBlock==target){
                return currentBlock;
            }
            else{
                ArrayList<A1083331_checkpoint7_Block> blockList = new ArrayList<A1083331_checkpoint7_Block>();
                blockList=expand(currentBlock,ParentBlock,visited);
                for(int i=0;i<blockList.size();i++){
                    fringe.add(blockList.get(i));
                }
            }
        }
        return target;
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/

    }
    private ArrayList<A1083331_checkpoint7_Block> expand(A1083331_checkpoint7_Block currentBlock,HashMap<A1083331_checkpoint7_Block, A1083331_checkpoint7_Block> ParentBlock, boolean[][] visited){
        /*********************************The TODO This Time (A1083331_Checkpoint7)*****************************
         * 
         * TODO(15.1): For the TODO here, you have to implement the expand funciton and return the Blocks(successor);
         * TODO(15.2): the order that you expand is North(Up) West(Left) South(Down) East(Right).
         * TODO(15.3): before adding the block into successor, you have to check if it is valid by checkBlock().
         * TODO(15.4): For the TODO here, you have to calculate the cost of the path that the player walked from 
         * root to new blocks and set it into the HashMap accumulatedCost.
         * 
         * Hint(1): While the block is valid, before you add the block into successor, 
         *        you should set its ParentBlock (We prepare a HashMap to implement this).
         *        And you should also set it is visited. (We prepare 2D boolean array for you) (the (x,y) block <--> visited[x][y] )
         **********************************The End of the TODO**************************************/

        /********************************************************************************************
         START OF YOUR CODE
        ********************************************************************************************/
        int curX=currentBlock.getX();
        int curY=currentBlock.getY();


        ArrayList<A1083331_checkpoint7_Block> blockList = new ArrayList<>();
        if(curY-1>=0){
            A1083331_checkpoint7_Block blockListN = map[curX][curY-1];
            if(!(visited[curX][curY-1]) && !(parentFrame.ClickCheckGridLocation(curX,curY-1,false))){
                accumulatedCost.put(blockListN,blockListN.getCost());
                ParentBlock.put(blockListN,currentBlock);
                visited[curX][curY-1]=true;
                blockList.add(blockListN);
            }
        }
        if(curX-1>=0){
            A1083331_checkpoint7_Block blockListW = map[curX-1][curY];
            if(!(visited[curX-1][curY]) && !(parentFrame.ClickCheckGridLocation(curX-1,curY,false))){
                accumulatedCost.put(blockListW,blockListW.getCost());
                ParentBlock.put(blockListW,currentBlock);
                visited[curX-1][curY]=true;
                blockList.add(blockListW);
            }
        }
        if(curY+1<16){
            A1083331_checkpoint7_Block blockListS = map[curX][curY+1];
            if(!(visited[curX][curY+1]) && !(parentFrame.ClickCheckGridLocation(curX,curY+1,false))){
                accumulatedCost.put(blockListS,blockListS.getCost());
                ParentBlock.put(blockListS,currentBlock);
                visited[curX][curY+1]=true;
                blockList.add(blockListS);
            }
        }
        if(curX+1<16){
            A1083331_checkpoint7_Block blockListE = map[curX+1][curY];
            if(!(visited[curX+1][curY]) && !(parentFrame.ClickCheckGridLocation(curX+1,curY,false))){
                accumulatedCost.put(blockListE,blockListE.getCost());
                ParentBlock.put(blockListE,currentBlock);
                visited[curX+1][curY]=true;
                blockList.add(blockListE);
            }
        }
        return blockList;
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/

    }

    public A1083331_checkpoint7_RouteLinkedList createRoute(){
        /******************************The TODO This Time (A1083331_Checkpoint7)*****************************
         * 
         * TODO(16): For the TODO here, you have to trace back the route and return the route;
         * 
         * Hint1: You can get the parent block of target by HashMap ParentBlock, thus you can calculate
         * the last step of the route. And then you get the parent block of  target, 
         * you can calculate the backward step and so on. 
         * 
         * presudo code is provided here:
         *      1. get parent block
         *      2. calculate the delta location
         *      3. insert into head
         *      4. make the target equals its parent block and so on.
         * 
         **********************************The End of the TODO**************************************/

        /********************************************************************************************
         START OF YOUR CODE
        ********************************************************************************************/
        A1083331_checkpoint7_Block parentBlock= new A1083331_checkpoint7_Block(0,0,"type",0);
        A1083331_checkpoint7_Block targetBlock= new A1083331_checkpoint7_Block(0,0,"type",0);
        A1083331_checkpoint7_Block currentBlock= new A1083331_checkpoint7_Block(0,0,"type",0);
        A1083331_checkpoint7_RouteLinkedList routeLinkedList = new A1083331_checkpoint7_RouteLinkedList();
        targetBlock=this.search();        
        currentBlock=targetBlock;
        parentBlock=ParentBlock.get(currentBlock);
        while(ParentBlock.containsKey(currentBlock)){ 
            int deltaX=0,deltaY=0;
            deltaX=currentBlock.getX()-parentBlock.getX();
            deltaY=currentBlock.getY()-parentBlock.getY();
            if(deltaY==0){
               A1083331_checkpoint7_Node node = new A1083331_checkpoint7_Node(deltaX,0);
               A1083331_checkpoint7_Node headNode = new A1083331_checkpoint7_Node(0,0);
               if(routeLinkedList.getHead()==null){
                    routeLinkedList.setHead(node);
               }             
               else{
                headNode=routeLinkedList.getHead();
                routeLinkedList.insert(headNode.getAxis(),headNode.getDirection(),node.getAxis(),node.getDirection());
               }
            }
            else if(deltaX==0){
               A1083331_checkpoint7_Node node = new A1083331_checkpoint7_Node(deltaY,1);
               A1083331_checkpoint7_Node headNode = new A1083331_checkpoint7_Node(0,0);
               if(routeLinkedList.getHead()==null){
                    routeLinkedList.setHead(node);
               }             
               else{
                headNode=routeLinkedList.getHead();
                routeLinkedList.insert(headNode.getAxis(),headNode.getDirection(),node.getAxis(),node.getDirection());
               }
            }
            currentBlock=parentBlock;
            parentBlock=ParentBlock.get(currentBlock);

        }
        return routeLinkedList;
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/
    }
}
