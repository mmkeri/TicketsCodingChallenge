public class Position {

    /**
     * integer value of an x coordinate
     */
    private int xCoord;
    /**
     * integer value of a y coordinate
     */
    private int yCoord;

    /**
     * constructor for the Position object
     * @param x {int} x-coordinate
     * @param y {int} y-coordinate
     */
    public Position(int x, int y){
        xCoord = x;
        yCoord = y;
    }

    /**
     * returns the x-coordinate value for this Position object
     * @return {int}
     */
    public int getxCoord(){
        return xCoord;
    }

    /**
     * returns the y-coordinate value for this Position object
     * @return {int}
     */
    public int getyCoord(){
        return yCoord;
    }
}
