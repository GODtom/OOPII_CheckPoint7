import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class A1083331_checkpoint7_SpawnMenu extends JPanel {
    // Description : The root frame.
    private A1083331_checkpoint7_GameFrame parentFrame;
    // Description : Base location for spawning the soldier.
    private int baseLocationX,baseLocationY;

    /*********************************** The TODO (Past) ********************************
     * 
     * TODO(Past): Upon the Soldier button being clicked, you need to first check if location is suitable for 
     * spawning a soldier. A suitable place is defined as one grid below the base is empty.
     * If the location is suitable for spawning soldier, you need to first create a object of A1083331_checkpoint7_Soldier
     * and make the thread start. Also you need to update the soldier list in the gamePanel and add to
     * gamepanel. Last, after a soldier is spawned,close the spawn menu.
     * Hint1: You could use ClickCheckGridLocation() to check whether the location is suitable.
     ********************************** The End of the TODO**************************************/

    public A1083331_checkpoint7_SpawnMenu() {
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton soldierButton = new JButton("Soldier");
        soldierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /********************************************************************************************
                 * START OF YOUR CODE
                 ********************************************************************************************/
                 setParentFrame();
                 A1083331_checkpoint7_GamePanel gamePanel=parentFrame.gamePanel;
                 if(!(parentFrame.ClickCheckGridLocation(baseLocationX,baseLocationY+1,false))){
                    A1083331_checkpoint7_Soldier newSoldier= new A1083331_checkpoint7_Soldier(baseLocationX,baseLocationY+1,parentFrame.jfScaler,SwingConstants.CENTER,parentFrame.algorithm);
                    gamePanel.addToSoldierList(newSoldier);
                    gamePanel.add(newSoldier);
                    Thread thread = new Thread(newSoldier);
                    thread.start();
                 }
                 parentFrame.revalidate();
                 parentFrame.repaint();
                 closeMenu();                
                /********************************************************************************************
                 * END OF YOUR CODE
                 ********************************************************************************************/
            }
        });
        this.add(soldierButton);
        this.setFocusable(true);
        this.requestFocusInWindow();
        setVisible(false);


}
    // Description : To set the base location for spawning the soldier.
    public void setBase(int baseLocationX,int baseLocationY){
        this.baseLocationX=baseLocationX;
        this.baseLocationY=baseLocationY;
    }
    // Description : To set root frame.
    public void setParentFrame(){
        this.parentFrame =(A1083331_checkpoint7_GameFrame) SwingUtilities.getWindowAncestor(this);
    }
    // Description : Close the spawnMenu.
    private void closeMenu() {
        parentFrame.spawnMenu.setVisible(false);
    }
    // Description : Open the spawnMenu.
    private void showMenu() {
        parentFrame.spawnMenu.setVisible(true);
    }

}
