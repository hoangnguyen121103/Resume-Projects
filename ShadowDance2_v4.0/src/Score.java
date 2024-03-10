import bagel.Font;

/**
 * Handles logic for when the player scores a note
 */
public class Score {
    //Window size
    private final static double WINDOW_WIDTH = 1024;
    private final static double WINDOW_HEIGHT = 768;

    private final double STATIONARY_Y = 657;

    //Messages font
    private final Font SCORE = new Font("res/FSO8BITR.TTF",30);
    private final Font SCORE_MESSAGE = new Font("res/FSO8BITR.TTF",40);

    //Class main attributes
    private int Score = 0;
    private int ScoreModifier = 1;
    private String Message = null;

    /**
     * Method to print "SCORE" in the top corner of the window
     */
    void DrawScore(){
        SCORE.drawString("SCORE",30,30);
        SCORE.drawString(Integer.toString(Score),160,30);
    }

    /**
     * Function to update player's score
     * @param y_coordinate yPos of the note at the moment when the player presses an input key
     */
    void ScoreUpdate(double y_coordinate){
        if (Math.abs(y_coordinate - STATIONARY_Y) <= 15){
            Score = Score + ScoreModifier * 15;
            Message = "PERFECT";
        }
        else if ((Math.abs(y_coordinate - STATIONARY_Y) > 15)
                && (Math.abs(y_coordinate - STATIONARY_Y) <=50)){
            Score = Score + ScoreModifier * 5;
            Message = "GOOD";
        }
        else if ((Math.abs(y_coordinate - STATIONARY_Y))> 50
                && (Math.abs(y_coordinate - STATIONARY_Y)) <= 100){
            Score = Score - 1;
            Message =  "BAD";
        }
        else if ((Math.abs(y_coordinate - STATIONARY_Y) > 100)
                && (Math.abs(y_coordinate - STATIONARY_Y)) <= 200){
            if (y_coordinate != 768) {
                Score = Score - 5;
                Message = "MISS";
            }
        }
        //Note has reached the end of the window
        if (y_coordinate == 768){
            Score = Score - 5;
            Message =  "MISS";
        }
    }

    /**
     * Method to print messages on screen
     */
    void PrintMessage() {
        if (Message != null) {
            SCORE_MESSAGE.drawString(Message, (WINDOW_WIDTH - SCORE_MESSAGE.getWidth(Message)) / 2, (WINDOW_HEIGHT - 40) / 2);
        }
    }

    /***** Setters *****/
    void setScoreModifier(int modifier){
        ScoreModifier = modifier;
    }
    void setMessage(String input){
        Message = input;
    }
    void setMessageNull(){
        Message = null;
    }
    /***** Getters *****/
    int getScoreModifier (){return ScoreModifier;}
    String getMessage(){
        return Message;
    }
    int getScore(){
        return Score;
    }
}
