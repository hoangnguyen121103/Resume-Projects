import bagel.Image;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Read lanes from the corresponding csv files and draw them on screen
 */
public class Lanes {
    //lanes images
    private static final Image LANE_DOWN = new Image("res/laneDown.png");
    private static final Image LANE_LEFT = new Image("res/laneLeft.png");
    private static final Image LANE_RIGHT = new Image("res/laneRight.png");
    private static final Image LANE_UP = new Image("res/laneUp.png");
    private static final Image LANE_SPECIAL = new Image("res/laneSpecial.png");

    //Class main attributes
    double Coordinates_x;
    double Coordinates_y = 384;
    String LaneType;

    Lanes(double coordinates_x, String LaneType) {
        this.Coordinates_x = coordinates_x;
        this.LaneType = LaneType;
    }

    /**
     * Reads CSV file
     * @param LevelSelected Level Selected by the player
     * @return A list of lanes for the selected level
     */
    static ArrayList <Lanes> LaneReadCSV(int LevelSelected){
        String ReadFileLevel = "res/level1.csv";
        ArrayList <Lanes> lane = new ArrayList<Lanes>();
        if (LevelSelected == 1) {
            ReadFileLevel = "res/level1.csv";
        }
        else if(LevelSelected == 2){
            ReadFileLevel = "res/level2.csv";
        }
        else if (LevelSelected == 3){
            ReadFileLevel = "res/level3.csv";
        }
        try (BufferedReader br = new BufferedReader(new FileReader(ReadFileLevel))) {
            String text = null;
            while ((text = br.readLine()) != null) {
                String words[] = text.split(",");
                if (words[0].equals("Lane")) {
                    double x = Double.parseDouble(words[2]);
                    lane.add(new Lanes(x, words[1]));
                } else {
                    break;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return lane;
    }

    /**
     * Draw lanes on the screen
     * @param LevelSelected Level selected by the player
     */
    static void DrawLanes(int LevelSelected){
        ArrayList <Lanes> lanesArrayList = new ArrayList<Lanes>();
        lanesArrayList = LaneReadCSV(LevelSelected);
        for (Lanes laneIterator : lanesArrayList ){
            if (laneIterator.LaneType.equals("Left")) {
                LANE_LEFT.draw(laneIterator.Coordinates_x,laneIterator.Coordinates_y);
            }
            else if (laneIterator.LaneType.equals("Right")){
                LANE_RIGHT.draw(laneIterator.Coordinates_x,laneIterator.Coordinates_y);
            }
            else if(laneIterator.LaneType.equals("Up")) {
                LANE_UP.draw(laneIterator.Coordinates_x,laneIterator.Coordinates_y);
            }
            else if(laneIterator.LaneType.equals("Down")) {
                LANE_DOWN.draw(laneIterator.Coordinates_x,laneIterator.Coordinates_y);
            }
            else if(laneIterator.LaneType.equals("Special")){
                LANE_SPECIAL.draw(laneIterator.Coordinates_x,laneIterator.Coordinates_y );
            }
        }
    }
}