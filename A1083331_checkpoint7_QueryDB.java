import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class A1083331_checkpoint7_QueryDB {
    // //Description : the driver description of mysql
    // private static final String driver = "com.mysql.cj.jdbc.Driver";
    // //Description : the protocol description of mysql
    // private static final String protocol = "jdbc:mysql://140.127.220.220:3306/";
    // Description : the obstacle set queried from database.
    private static ArrayList<Integer[]> data = new ArrayList<Integer[]>();
    // Description : the filename of obstacle image queried from database.
    private static HashMap<Integer, String> typeChar = new HashMap<Integer, String>();
    // Description : the primary key of map in database.
    private static String mapID = "0";

    public static void queryData(ArrayList<Integer[]> data, HashMap<Integer, String> typeChar) {
        // TODO(Past): Querying the barrier location from the server, and set it into
        // Arraylist data.
        // TODO(Past): Querying the bar_type and the corresponding file_name from the
        // server, and set it into HashMap.
        /********************************************************************************************
         * START OF YOUR CODE
         ********************************************************************************************/
	      final String protocol="jdbc:postgresql://140.127.220.226:5432/oopiickp";
	     Connection conn=null;

		try{
			conn=DriverManager.getConnection(protocol,"fallckp","2021OOPIIpwd");
			Statement s =conn.createStatement();
			ResultSet rs=null; 


			rs=s.executeQuery("SELECT x_coordinate,y_coordinate,obstacle_type FROM obstacle_info WHERE map_id="+mapID);
			while (rs.next()) {
				int x=rs.getInt("x_coordinate");
				int y=rs.getInt("y_coordinate");
				int ot=rs.getInt("obstacle_type");
				Integer[] temp= new Integer[3];
				temp[1]=x;
				temp[0]=y;
				temp[2]=ot;
				data.add(temp);
			}
			rs.close();

			rs=s.executeQuery("SELECT obstacle_type,filename FROM obstacle_style");
			while (rs.next()) {
				int otNum=rs.getInt("obstacle_type");
				String fileName=rs.getString("filename");
				typeChar.put(otNum,fileName);
			}  
			rs.close();	
			conn.close();
		
		}
		catch(SQLException err){
			System.out.println(err.getMessage());
			System.exit(0);
		}
		catch(Exception err){
			System.out.println("Fatal error,please try again.");
			System.exit(0);
		}  
        /********************************************************************************************
         * END OF YOUR CODE
         ********************************************************************************************/

    }

    public ArrayList getObstacle() {
        return this.data;
    }

    public void setObstacle(ArrayList<Integer[]> data) {
        this.data = data;
    }

    public String getMapID() {
        return this.mapID;
    }

    public void setMapID(String mapID) {
        this.mapID = mapID;
    }

    public HashMap getObstacleImg() {
        return this.typeChar;
    }

    public void setObstacleImg(HashMap<Integer, String> typeChar) {
        this.typeChar = typeChar;
    }
}
