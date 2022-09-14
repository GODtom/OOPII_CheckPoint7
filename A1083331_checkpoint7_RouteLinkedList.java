public class A1083331_checkpoint7_RouteLinkedList{
    private A1083331_checkpoint7_Node head;
    //Description : the constructor of leading the head Node as null.
    public A1083331_checkpoint7_RouteLinkedList(){
        this.head = null;
    }
    //Description : the constructor of input a Node as the head node.
    public A1083331_checkpoint7_RouteLinkedList(A1083331_checkpoint7_Node head){
        this.head = head;
    }
    public void delete(int axis, int direction){ 
        /*********************************The TODO This Time (A1083331_Checkpoint7)***************************
        //TODO(7):      Input value of Node as the reference Node,
        //              you have to delete the first Node that is same as the reference Node,
        //              and connect the following one and the previous one.
        /********************************************************************************************
         START OF YOUR CODE
        ********************************************************************************************/
        A1083331_checkpoint7_Node deleteNode=this.search(axis,direction);
        A1083331_checkpoint7_Node current=head.getNext();
        if(deleteNode==head){
            head=head.getNext();
            this.setHead(head);
        }   
        else{
            while(current.getNext()!=null){
                if(current.getNext()==deleteNode){
                    current.setNext(deleteNode.getNext());
                    return ;
                }
                current=current.getNext();
            }
        }
        /********************************************************************************************
         END OF YOUR CODE
        ********************************************************************************************/
    }

    public A1083331_checkpoint7_Node search(int axis, int direction){ 
        /*******************************The TODO This Time (A1083331_Checkpoint7)*****************************
        //TODO(8):      Input value of Node as the reference Node,
        //              you have to find the first Node that is same as the reference Node,
        //              and return it.
        /********************************************************************************************
         START OF YOUR CODE
        ********************************************************************************************/
        A1083331_checkpoint7_Node current = head;
        while(current.getNext()!=null){
            if(current.getAxis()==axis && current.getDirection()==direction){
                return current;
            }
            current=current.getNext();
        }   
        return current;
        /********************************************************************************************
         END OF YOUR CODE
        ********************************************************************************************/
    }
    public void insert(int referenceAxis, int referenceDirection, int axis, int direction){ 
        /******************************The TODO This Time (A1083331_Checkpoint7)******************************
        //TODO(9):      Input value of Node as the reference Node,
        //              and insert a Node BEFORE the first Node same as the reference Node,
        //              and connect the following one and the previous one.
        //Hint          The value of the Node is int variable axis and dirsction.
        //Hint2         If there is no reference node in linkedlist, print "Insertion null".
        /********************************************************************************************
         START OF YOUR CODE
        ********************************************************************************************/
        A1083331_checkpoint7_Node current = head;
        A1083331_checkpoint7_Node referenceNode = this.search(referenceAxis,referenceDirection);
        A1083331_checkpoint7_Node insertNode =  new A1083331_checkpoint7_Node(direction,axis);      
        if(referenceNode==null){
            System.out.println("Insertion null");
            return;
        }

        if(referenceAxis==head.getAxis() && referenceDirection==head.getDirection()){
            insertNode.setNext(referenceNode);
            this.setHead(insertNode);
            return;
        }
        while(current.getNext()!=null){
            if(current.getNext()==referenceNode){
                insertNode.setNext(referenceNode);
                current.setNext(insertNode);
                return;
            }
            current=current.getNext();
        }
        /********************************************************************************************
         END OF YOUR CODE
        ********************************************************************************************/
    }
    public int length(){
        /******************************The TODO This Time (A1083331_Checkpoint7)******************************
        //TODO(10):      return how long the LinkedList is.
        /********************************************************************************************
         START OF YOUR CODE
        ********************************************************************************************/
        A1083331_checkpoint7_Node current = head;
        int con=0;
        if(head == null){
            return 0;
        }
        else{
            while(current != null){
                con+=1;
                current=current.getNext();
            }
        }
        return con;
        /********************************************************************************************
         END OF YOUR CODE
        ********************************************************************************************/
    }
    public void append(int axis, int direction){
        A1083331_checkpoint7_Node current = head;
        A1083331_checkpoint7_Node newNode = new A1083331_checkpoint7_Node(direction,axis);
        if(head == null){
            head = newNode;
        }else {
            while(current.getNext() != null){
                current=current.getNext();
            }
            current.setNext(newNode);
        }
    }
    public A1083331_checkpoint7_Node getHead(){
        return this.head;
    }
    public void setHead(A1083331_checkpoint7_Node head){
        this.head = head;
    }
}
    

