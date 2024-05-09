public class Room {
    private char type;
    private boolean hasVisited;
    private boolean north, south, east, west;

    // Constructor
    public Room(char type) {
        this.type = type;
        this.hasVisited = false;
        this.north = false;
        this.south = false;
        this.east = false;
        this.west = false;
    }

    // Getters and Setters
    public char getType() {
        return type;
    }

    public boolean hasVisited() {
        return hasVisited;
    }

    public void setType(char type) {
        this.type = type;
    }

    public void setHasVisited(boolean hasVisited) {
        this.hasVisited = hasVisited;
    }

    // Movement Logic
    public boolean canMove(Direction direction) {
        switch (direction) {
            case NORTH:
                return north;
            case SOUTH:
                return south;
            case EAST:
                return east;
            case WEST:
                return west;
            default:
                return false;
        }
    }

    public void setDirection(Direction direction, boolean value) {
        switch (direction) {
            case NORTH:
                north = value;
                break;
            case SOUTH:
                south = value;
                break;
            case EAST:
                east = value;
                break;
            case WEST:
                west = value;
                break;
        }
    }

    // Directions Enum
    public enum Direction {
        NORTH, SOUTH, EAST, WEST
    }
}
