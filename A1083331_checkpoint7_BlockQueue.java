import java.util.*;

public class A1083331_checkpoint7_BlockQueue implements A1083331_checkpoint7_Fringe {
    Queue<A1083331_checkpoint7_Block> queue;
    //Description : the constuctor of BlockQueue.
    public A1083331_checkpoint7_BlockQueue(){
        //The TODO(4) This Time (A1083331_Checkpoint7) : Initialize the queue.
        /********************************************************************************************
         START OF YOUR CODE
        ********************************************************************************************/
         queue = new ArrayDeque<A1083331_checkpoint7_Block>();
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/

    }
    //Description : add the input object into Fringe
    public void add(A1083331_checkpoint7_Block block){
        //The TODO(4) This Time (A1083331_Checkpoint7) : add block into the queue.
        /********************************************************************************************
         START OF YOUR CODE
        ********************************************************************************************/
         queue.offer(block);
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/
    }
    //Description : return and remove the object from Fringe.
    public A1083331_checkpoint7_Block remove(){
        //The TODO(4) This Time (A1083331_Checkpoint7) :First check the queue is empty or not and return and remove the object from the queue.
        // If queue is empty return null.
        /********************************************************************************************
         START OF YOUR CODE
        ********************************************************************************************/
         if(queue.peek()==null){
            return null;
         }
         return queue.poll();
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/

    }
    //Description : Check if the Fringe has a object at least. if it is empty return true, vice versa. 
    public boolean isEmpty(){
        //The TODO(4) This Time (A1083331_Checkpoint7) :Check the queue is empty or not.
        /********************************************************************************************
         START OF YOUR CODE
        ********************************************************************************************/
         if(queue.isEmpty()){
            return true;
         }
         return false;
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/

    }
}