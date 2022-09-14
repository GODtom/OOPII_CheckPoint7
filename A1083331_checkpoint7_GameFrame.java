import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

public class A1083331_checkpoint7_GameFrame extends JFrame {
    // Description : Width of Frame
    private int FWidth;
    // Description : Height of Frame
    private int FHeight;
    // Description : the displaysize of the map
    public int jfScaler = 2;
    // Description : the obstacle images set. bar_id -> obstacle image
    private HashMap<Integer, Image> obstacleImg = new HashMap<>();
    // Description : the filenames of the obstacle image set. bar_id -> filename
    private HashMap<Integer, String> typeChar = new HashMap<Integer, String>();
    // Description : the obstacle location set queryed from database
    private ArrayList<Integer[]> obstacleDataStructure;
    // Description : the obstacle location set in GUI index version.
    private ArrayList<Integer[]> obstacleList;
    // Description : the object to query data.
    private A1083331_checkpoint7_QueryDB querydb;
    private static ArrayList<A1083331_checkpoint7_House> houseList = new ArrayList<A1083331_checkpoint7_House>();
    private static ArrayList<A1083331_checkpoint7_Barrack> barrackList = new ArrayList<A1083331_checkpoint7_Barrack>();
    private static ArrayList<A1083331_checkpoint7_Pyramid> pyramidList = new ArrayList<A1083331_checkpoint7_Pyramid>();
    private static int PressedX = 0;
    private static int PressedY = 0;
    private static int ReleasedX = 0;
    private static int ReleasedY = 0;
    private static int ClickedX = 0;
    private static int ClickedY = 0;
    private static int keytype = 1;
    
    //Description : the cost of sand weight;
    private final int GRASSWEIGHT = 3;
    //Description : the cost of space weight;
    private final int SAPCEWEIGHT = 1;
    // Description : The main panel.
    public A1083331_checkpoint7_GamePanel gamePanel;
    // Description : The UI panel of spawnMenu.
    public A1083331_checkpoint7_SpawnMenu spawnMenu;
    // Description : The soldier that is selected.
    public A1083331_checkpoint7_Soldier selectedSoldier;
    //Description : the map with all blocks.
    //You can get the location block you want with typing map[x][y].
    private A1083331_checkpoint7_Block[][] map;
    // Description : The route searching algorithm.
    public  int algorithm;
    public A1083331_checkpoint7_GameFrame(int FWidth, int FHeight, String mapID, int jfScaler, int algorithm) throws HeadlessException {
        this.FWidth = FWidth;
        this.FHeight = FHeight;
        this.setTitle("Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(FWidth, FHeight);
        this.jfScaler = jfScaler;
        this.obstacleList = new ArrayList<Integer[]>();
        this.obstacleDataStructure = new ArrayList<Integer[]>();
        this.querydb = new A1083331_checkpoint7_QueryDB();
        this.querydb.setMapID(mapID);
        this.algorithm = algorithm;
        /********************************** The TODO (A1083331_Checkpoint7) ********************************
         * 
         * TODO(1): This time you need to create a map  recording  the info. of every blocks.   
         * Hint 1: You could use "createMap" after using "toGUIIdx" to create the map. 
         * 
        ********************************** The End of the TODO **************************************/
        // TODO(Past): You need to get the obstacle from database and transform it into
        // GUI index version and set your map(panel) on the frame.
        // Hint: In order to build Hashmap obstacleImg, key means the bar_type from
        // database and value equals the Image class that load from the corresponding
        // filepath.
        // Hint2: To get the obstacle set from database, we need you to realize the
        // queryData() in the object QueryDB and get the result.
        // Hint3: obstacle is transformed by obstacleDataStructure via toGUIIdx() in
        // order to let the location transformed from database to panel location.(GUI
        // index version)
        // Hint4: ObstacleDataStructure is a Integer array ([row, column, bartype]) like
        // ArrayList.
        // Obstacle is a Integer array ([x_coordinate, y_coordinate, bartype]) like
        // ArrayList.
        // TODO(Past): This time you need to add a spawnMenu at the bottom of main frame, and set the parent frame.
        // Hint 1: You could use "BorderLayout.SOUTH" to add something at the bottom of main frame.

        /********************************************************************************************
         * START OF YOUR CODE
         ********************************************************************************************/
         querydb.queryData(querydb.getObstacle(),querydb.getObstacleImg());
         typeChar=querydb.getObstacleImg();
         obstacleDataStructure=querydb.getObstacle();
         toGUIIdx(obstacleDataStructure,obstacleList);
         this.map=createMap(16,16);         
         for(int i=0;i<typeChar.size();i++){
         	Image mapImg = new ImageIcon("Resource/"+typeChar.get(i)).getImage();
         	obstacleImg.put(i,mapImg);        	
         }
         gamePanel=new A1083331_checkpoint7_GamePanel(houseList,barrackList,pyramidList,obstacleList,obstacleImg,jfScaler);      
         add(gamePanel);  

         spawnMenu =new  A1083331_checkpoint7_SpawnMenu();
         add(spawnMenu,BorderLayout.SOUTH);
        /********************************************************************************************
         * END OF YOUR CODE
         ********************************************************************************************/

        this.addComponentListener(new ComponentAdapter() {
            @Override
            // Description : While resizing the windows, the evnet will be happenned(Reset
            // the location of player).
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                if (houseList.size() != 0) {
                    int x = gamePanel.getWidth() / 2 - gamePanel.getCenterX();
                    int y = gamePanel.getHeight() / 2 - gamePanel.getCenterY();
                    for (A1083331_checkpoint7_House house : houseList) {
                        house.setLocation(x + house.getlocationX() * gamePanel.getGridLen(),
                                y + house.getlocationX() * gamePanel.getGridLen());
                    }
                }
            }
        });
        // TODO(Past): For key event here, you should implement key pressed here.
        // Hint1: For example, after pressing 'b', the building should change to barrack
        // when mouse clicked.
        // Hint2: You should get keyChar and set it into keytype.
        // Hint3: 'h' represents to house, 'b' represents to barrack, and others
        // represent to pyramid.
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("pressed!");
                char key = e.getKeyChar();
                if (key == 'h') {
                    keytype = 1;
                } else if (key == 'b') {
                    keytype = 2;
                } else {
                    keytype = 3;
                }
            }

        });
        // TODO(Past): For mouse event here, you should implement map drag here.
        // Hint: For example, if you click on the top and release in the bottom, the map
        // should be dragged from up to down.
        // Hint: You should got both pressed location and release location and than
        // calculate the moving.
        gamePanel.addMouseListener(new MouseAdapter() {
            // Description : the event happenned while mouse be pressed.
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                // TODO(Past) Get the location of mousePressed.
                /********************************************************************************************
                 * START OF YOUR CODE
                 ********************************************************************************************/
              	PressedX=e.getX();
             	PressedY=e.getY();  
                /********************************************************************************************
                 * END OF YOUR CODE
                 ********************************************************************************************/
            }

            // Description : the event happenned while mouse be released
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                // TODO(Past). Get the location of mouseReleased.
                // TODO(Past) The map displacement will be calculated by Released location minus
                // Pressed location
                // TODO(Past) And then make the map moving by controlling it's location variable
                // and repaint the map via repaint() in object JPanel.
                /********************************************************************************************
                 * START OF YOUR CODE
                 ********************************************************************************************/
               	ReleasedX=e.getX();
             	ReleasedY=e.getY();
             	int currentX=gamePanel.getCenterX();
             	int currentY=gamePanel.getCenterY();
             	gamePanel.setCenterX(currentX-(ReleasedX-PressedX));
             	gamePanel.setCenterY(currentY-(ReleasedY-PressedY)); 
             	gamePanel.repaint();  
                /********************************************************************************************
                 * END OF YOUR CODE
                 ********************************************************************************************/
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                /********************************** The TODO (Past) ********************************
                 * TODO(Past): Aside from achieving all the functions before, you also need make the selected soldier
                 * move to the target destination.
                 * Hint1: Instead of directly calling the detectRoute() and startMove(), you should first get control
                 * of the thread of the selected soldier, then set the destination for the selected soldier, and finally
                 * use notify() to resume that thread.
                 * Hint2: Noted that you could only build when there are no soldiers being selected.
                 * Hint3: You could use "synchronized (selectedSoldier){}" to get the lock, then notify the other thread 
                 * that is waiting for this lock back to runnable state.
                 ***************************************** The End of the TODO**************************************/
                // TODO(Past): This time you need to implement the multi-thread in constructing
                // buildings! Once you click to build a house/barrack/pyramid, it starts a thread to build one. 
                // (Past)Hint: You should make a Thread object then make it start.
                // (Past)Hint: After you get the location that you clicked, use
                // ClickCheckMouseLocation() method to check if the place you clicked is available for
                // building.
                // (Past)Hint2: If the place you clicked is available, calculate the grid
                // locationX
                // and grid locationY where you clicked on map.
                // For example, if I clicked (2,1) on the map, then grid locationX is 2, grid
                // locationY is 1.
                // (Past)Hint3: There are total three diffirent types of building, including
                // house,
                // barrack and pyramid.According to diffirent keytype, diffirent types of
                // building object should be
                // initialized individually.
                // (Past)Hint4: For example, if keytype is 1, initialize a A1083331_checkpoint7_House
                // object(int locationX, int locationY, String text, int scaler,int
                // horizontalAlignment) and set it into houseList.
                // Then add it into panel, use revalidate() method refresh panel and set
                // houseList into panel through setHouseList(ArrayList<A1083331_checkpoint7_House>
                // houseList) method.
                // (Past)Hint5: The text of each type of building needs to be numbered in
                // ascending order of that type.
                /********************************************************************************************
                 * START OF YOUR CODE
                 ********************************************************************************************/
              	ClickedX=e.getX();
             	ClickedY=e.getY();
		        int clickMapX=0;
		        int clickMapY=0;
		        int gridLen=0;
		        String text="";
                boolean isBuilding=true;
		        gridLen=gamePanel.getGridLen();
		        clickMapX=ClickedX+(gamePanel.getCenterX()-gamePanel.getWidth()/2);
		        clickMapY=ClickedY+(gamePanel.getCenterY()-gamePanel.getHeight()/2);  
                if(selectedSoldier!=null){
                    selectedSoldier.setDestination(clickMapX/gamePanel.getGridLen(),clickMapY/gamePanel.getGridLen());   
                    synchronized(selectedSoldier){
                        selectedSoldier.notify();
                    }
                    selectedSoldier.resetSelectedSoldier();                    
                    return;
                }         	
             	if(!(locationVarify(clickMapX,clickMapY,isBuilding))){
	             	if(keytype==2){
    	             		text=String.valueOf(barrackList.size());
    	         			A1083331_checkpoint7_Barrack newBarrack= new  A1083331_checkpoint7_Barrack(clickMapX/gridLen,clickMapY/gridLen,text,jfScaler,SwingConstants.CENTER);
    	         			barrackList.add(newBarrack);
    	         			gamePanel.setBarrackList(barrackList);
    	         			gamePanel.add(newBarrack);
    	         			Thread thread = new Thread(newBarrack);
    	         			thread.start(); 	
	             		}
	             	else if(keytype==1){
    	             		text=String.valueOf(houseList.size());
    	         			A1083331_checkpoint7_House newHouse= new  A1083331_checkpoint7_House(clickMapX/gridLen,clickMapY/gridLen,text,jfScaler,SwingConstants.CENTER);
    	         			houseList.add(newHouse);
    	         			gamePanel.setHouseList(houseList);
    	         			gamePanel.add(newHouse);
    	         			Thread thread = new Thread(newHouse);
    	         			thread.start(); 	         		        			
	             		}
	             	else{
    	             		text=String.valueOf(pyramidList.size());
    	         			A1083331_checkpoint7_Pyramid newPyramid= new  A1083331_checkpoint7_Pyramid(clickMapX/gridLen,clickMapY/gridLen,text,jfScaler,SwingConstants.CENTER);
    	         			pyramidList.add(newPyramid);
    	         			gamePanel.setPyramidList(pyramidList);
    	         			gamePanel.add(newPyramid);
    	         			Thread thread = new Thread(newPyramid);
    	         			thread.start(); 		         			
	             	}
	            }                 
             	revalidate(); 
             	repaint();
                /********************************************************************************************
                 * END OF YOUR CODE
                 ********************************************************************************************/
            }
        });
        gamePanel.addMouseMotionListener(new MouseAdapter() {
            // Description : the event happenned while mouse be dragged.
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                // TODO(Past) we hope you can drag the map smoothly, you can override this
                // function
                // instead of mousePressed
                /********************************************************************************************
                 * START OF YOUR CODE
                 ********************************************************************************************/
              	ReleasedX=e.getX();
             	ReleasedY=e.getY();
             	int currentX=gamePanel.getCenterX();
             	int currentY=gamePanel.getCenterY();
             	gamePanel.setCenterX(currentX-(ReleasedX-PressedX));
             	gamePanel.setCenterY(currentY-(ReleasedY-PressedY)); 
             	gamePanel.repaint();
                PressedX=ReleasedX;
             	PressedY=ReleasedY;    
                /********************************************************************************************
                 * END OF YOUR CODE
                 ********************************************************************************************/
            }

        });
        this.setFocusable(true);
        this.requestFocusInWindow();

    }

    // Description : transform the obstacle location from database version to GUI
    // index version
    // data is the database one, and the other.
    public static void toGUIIdx(ArrayList<Integer[]> data, ArrayList<Integer[]> dataGui) {
        for (Integer[] x : data) {
            dataGui.add(new Integer[] { x[1] - 1, x[0] - 1, x[2] });
        }
    }
    /********************************** The TODO (A1083331_Checkpoint7) ********************************
    * TODO(2): At this time, grass isn't an obstacles, so you have to return false at the situation.
    * 
    /********************************** The TODO (Past) ********************************
    * TODO(Past): Here you will implement the method to check if the grid location passed in is empty.
    * If the location is empty, return false, else return true. The variable "isBuilding" is to check 
    * if you need to take building's  construction scope into consideration. If the "isBuilding" is true,
    * it means that now it's going to build a building, you need to take this building's construction scope
    * into consideration. On the other hand, you only need to check that grid location is empty or not, if
    * "isBuilding" is false.
    * Rules: There are several situations that will cause the location is not empty.
    * 1. There are obstacles on the location.
    * 2. There are buildings on the location.
    * 3. There are soldiers on the location.
    * Hint 1: There are diffirent construction scope for diffirent types of building.
    * Houses are 1*1 grid; Barracks are 1*1 grid; Pyramids are 2*2 grid.
    * Hint 2: You should consider about diffirent types of buildings' situation
    * while checking if there exists obstacle or building in buildings' construction scope.
    * For example, pyramid construction scope is 2*2, In other words, there should
    * be empty in pyramid construction scope.
    ***************************************** The End of the TODO**************************************/
    private boolean locationVarify(int locationX,int locationY,boolean isBuilding){
        /********************************************************************************************
        * START OF YOUR CODE
        ********************************************************************************************/
        ArrayList<A1083331_checkpoint7_Soldier> soldierList = gamePanel.getSoldierList();
 
        if(isBuilding){
            if(locationX<0 || locationX/gamePanel.getGridLen()>15 || locationY<0 || locationY/gamePanel.getGridLen()>15){
                return true;            
            }
            if(keytype==3){
                if(locationX<0 || locationX/gamePanel.getGridLen()>14 || locationY<0 || locationY/gamePanel.getGridLen()>14){
                    return true;            
                }           
            }

            for(int i=0;i<houseList.size();i++){
                int obX=0;
                int obY=0;
                obX=houseList.get(i).getlocationX()*gamePanel.getGridLen();
                obY=houseList.get(i).getlocationY()*gamePanel.getGridLen();
                if(obX<= locationX&& obX+gamePanel.getGridLen()>=locationX && obY<= locationY && obY+gamePanel.getGridLen()>=locationY&& (keytype==2 || keytype==1)){
                    return true;
                }
                if(obX<= locationX+gamePanel.getGridLen()&& obX+gamePanel.getGridLen()>=locationX && obY<= locationY+gamePanel.getGridLen() && obY+gamePanel.getGridLen()>=locationY&& keytype==3){
                    return true;
                }           
            }
            for(int i=0;i<barrackList.size();i++){
                int obX=0;
                int obY=0;
                obX=barrackList.get(i).getlocationX()*gamePanel.getGridLen();
                obY=barrackList.get(i).getlocationY()*gamePanel.getGridLen();
                if(obX<= locationX&& obX+gamePanel.getGridLen()>=locationX && obY<= locationY && obY+gamePanel.getGridLen()>=locationY&& (keytype==2 || keytype==1)){
                    return true;
                }
                if(obX<= locationX+gamePanel.getGridLen()&&obX+gamePanel.getGridLen()>=locationX && obY<= locationY+gamePanel.getGridLen() && obY+gamePanel.getGridLen()>=locationY&& keytype==3){
                    return true;
                }
            }
            for(int i=0;i<pyramidList.size();i++){
                int obX=0;
                int obY=0;
                obX=pyramidList.get(i).getlocationX()*gamePanel.getGridLen();
                obY=pyramidList.get(i).getlocationY()*gamePanel.getGridLen();
                if(obX<= locationX&& obX+gamePanel.getGridLen()*2>=locationX && obY<= locationY && obY+gamePanel.getGridLen()*2>=locationY&& (keytype==2 || keytype==1)){
                    return true;
                }
                if(obX<= locationX+gamePanel.getGridLen()&& obX+gamePanel.getGridLen()*2>=locationX && obY<= locationY+gamePanel.getGridLen() && obY+gamePanel.getGridLen()*2>=locationY&& keytype==3){
                    return true;
                }
            }
            for(int i=0;i<soldierList.size();i++){
                int obX=0;
                int obY=0;
                obX=soldierList.get(i).getlocationX()*gamePanel.getGridLen();
                obY=soldierList.get(i).getlocationY()*gamePanel.getGridLen();
                if(obX<= locationX&& obX+gamePanel.getGridLen()>=locationX && obY<= locationY && obY+gamePanel.getGridLen()>=locationY&& (keytype==2 || keytype==1)){
                    return true;
                }
                if(obX<= locationX+gamePanel.getGridLen()&&obX+gamePanel.getGridLen()>=locationX && obY<= locationY+gamePanel.getGridLen() && obY+gamePanel.getGridLen()>=locationY&& keytype==3){
                    return true;
                }
            } 
            for(int i=0;i<obstacleList.size();i++){
                int obX=0;
                int obY=0;
                obX=obstacleList.get(i)[0]*gamePanel.getGridLen();
                obY=obstacleList.get(i)[1]*gamePanel.getGridLen();
                if(obX<= locationX&& obX+gamePanel.getGridLen()>=locationX && obY<= locationY && obY+gamePanel.getGridLen()>=locationY && (keytype==2 || keytype==1)){
                    if(obstacleList.get(i)[2]==2){
                        return false;
                    }
                    return true;
                }
                if(obX<= locationX+gamePanel.getGridLen() && obX+gamePanel.getGridLen()>=locationX && obY<= locationY+gamePanel.getGridLen() && obY+gamePanel.getGridLen()>=locationY && keytype==3 && obstacleList.get(i)[2]!=2){
                    return true;
                }
            } 
            return false;
        }


        else if(!(isBuilding)){
            if(locationX<0 || locationX/gamePanel.getGridLen()>15 || locationY<0 || locationY/gamePanel.getGridLen()>15){
                return true;            
            }
            for(int i=0;i<houseList.size();i++){
                int obX=0;
                int obY=0;
                obX=houseList.get(i).getlocationX()*gamePanel.getGridLen();
                obY=houseList.get(i).getlocationY()*gamePanel.getGridLen();
                if(obX== locationX && obY==locationY){
                    return true;
                }         
            }
            for(int i=0;i<barrackList.size();i++){
                int obX=0;
                int obY=0;
                obX=barrackList.get(i).getlocationX()*gamePanel.getGridLen();
                obY=barrackList.get(i).getlocationY()*gamePanel.getGridLen();
                if(obX== locationX && obY== locationY ){
                    return true;
                }
            }
            for(int i=0;i<pyramidList.size();i++){
                int obX=0;
                int obY=0;
                obX=pyramidList.get(i).getlocationX()*gamePanel.getGridLen();
                obY=pyramidList.get(i).getlocationY()*gamePanel.getGridLen();
                if(obX<= locationX&& obX+gamePanel.getGridLen()*2>locationX && obY<= locationY && obY+gamePanel.getGridLen()*2>locationY){
                    return true;
                }
            }
            for(int i=0;i<soldierList.size();i++){
                int obX=0;
                int obY=0;
                obX=soldierList.get(i).getlocationX()*gamePanel.getGridLen();
                obY=soldierList.get(i).getlocationY()*gamePanel.getGridLen();
                if(obX== locationX && obY==locationY ){
                    return true;
                }
            } 
            for(int i=0;i<obstacleList.size();i++){
                int obX=0;
                int obY=0;
                obX=obstacleList.get(i)[0]*gamePanel.getGridLen();
                obY=obstacleList.get(i)[1]*gamePanel.getGridLen();
                if(obX== locationX && obY== locationY ){
                    if(obstacleList.get(i)[2]==2){
                        return false;
                    }
                    return true;
                }
            } 
            return false;
        }
        return false;
        /********************************************************************************************
         * END OF YOUR CODE
         ********************************************************************************************/
    }

    /********************************** The TODO (Past) ********************************
    * 
    * TODO(Past): Here you need to check if the passed in location is empty or not, and the passed in variable
    * "isBuilding" is to determine if it needs to take buildings' construction scope into consideration.
    * You should return false if the location is empty.
    * The location passed in is in grid location format, so you could directly call locationVarify().
    * Hint 1: Grid location format is something like-->[0,0] ,[2,8].
    ***************************************** The End of the TODO**************************************/
    public boolean ClickCheckGridLocation(int locationX, int locationY,boolean isBuilding) {
        /********************************************************************************************
        * START OF YOUR CODE
        ********************************************************************************************/
        return locationVarify(locationX*gamePanel.getGridLen(),(locationY)*gamePanel.getGridLen(),isBuilding); 
        /********************************************************************************************
         * END OF YOUR CODE
         ********************************************************************************************/
    }
    /********************************** The TODO (Past) ********************************
    * 
    * TODO(Past): Here you need to check if the passed in location is empty or not  and the passed in variable
    * "isBuilding" is to determine if it needs to take buildings' construction scope into consideration.
    * You should return false if the location is empty.
    * The location passed in is in mouse location format, you should convert it into grid location
    * format before calling locationVarify().
    *
    ***************************************** The End of the TODO**************************************/
    public boolean ClickCheckMouseLocation(int ClickX, int ClickY,boolean isBuilding) {
        /********************************************************************************************
         * START OF YOUR CODE
         ********************************************************************************************/
        int locationX=0;
        int locationY=0;
        int gridLen=0;
        String text="";
        gridLen=gamePanel.getGridLen();
        locationX=ClickedX+(gamePanel.getCenterX()-gamePanel.getWidth()/2);
        locationY=ClickedY+(gamePanel.getCenterY()-gamePanel.getHeight()/2);          
        return locationVarify(locationX,locationY,isBuilding);
        /********************************************************************************************
         * END OF YOUR CODE
         ********************************************************************************************/
    }
    //Description : create the map, if each of the loaction will be tag as "grass", "obstacle" or "space".
    public A1083331_checkpoint7_Block[][] createMap(int height,int width){
        A1083331_checkpoint7_Block[][] map = new A1083331_checkpoint7_Block[width][height];
        for (Integer[] block: obstacleList){
            int cost = block[2] == 2 ? GRASSWEIGHT : 100;
            String type = block[2] == 2 ? "grass" : "obstacle";
            map[block[0]][block[1]] = new A1083331_checkpoint7_Block(block[0], block[1], type, cost);
        }
        for(int x = 0; x<width; x++){
            for(int y = 0; y<height; y++){
                if(map[x][y] == null){
                    map[x][y] = new A1083331_checkpoint7_Block(x,y,"space",SAPCEWEIGHT);
                }
            }
        }
        return map;
    }
    public A1083331_checkpoint7_Block[][] getMap(){
        return this.map;
    }
}
