// This is the main class of checkpoint this time.
public class A1083331_checkpoint7_Game {
    public static void main(String[] args) {
        // The TODO This Time (Checkpoint7) :You need to initialize GameFrame object (int FWidth, int FHeight,
        // String mapID, int jfScaler, int algorithm)
        // the size of JFrame is 500*500, mapID and jfScaler are the values that get
        // from the command line.
        // Finally, set JFrame visible.
        // Hint: Beware of the value that you should put in GameFrame object all
        // correct.
        /********************************************************************************************
         * START OF YOUR CODE
         ********************************************************************************************/
         try{
            int inputScaler=Integer.valueOf(args[1]);
            int algorithm=Integer.valueOf(args[2]);
            A1083331_checkpoint7_GameFrame mapJframe =new A1083331_checkpoint7_GameFrame(500,500,args[0],inputScaler,algorithm);
            mapJframe.setVisible(true);
        }
        catch(NumberFormatException e){
             System.out.println("Must input Integer,please try again.");
             System.exit(0);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Missing input,please try again.");
            System.exit(0);
        }
        catch(Exception e){
            System.out.println("Fatal error,please try again.");
            System.exit(0);         
        }   
        /********************************************************************************************
         * END OF YOUR CODE
         ********************************************************************************************/

    }
}
