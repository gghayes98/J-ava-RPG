import java.util.Random;

public class Dungeon {
    private Room[][] rooms;
    private Room currentRoom;
    private int width, height;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        rooms = new Room[height][width];
        generateDungeon(width, height);
        currentRoom = rooms[0][0];  // Assume start at top-left corner and it's always available
    }

    private void generateDungeon(int width, int height) {
        Random rand = new Random();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                boolean isWall = rand.nextBoolean();  // Randomly decide if a room is a wall
                rooms[i][j] = new Room(isWall ? '#' : 'O');
            }
        }

        ensureConnectivity(width, height);  // Ensure at least one viable path exists
        establishConnections();
    }

    // Ensure at least one clear path from top-left to bottom-right
    private void ensureConnectivity(int width, int height) {
        int i = 0;
        int j = 0;
        while (i < height-1 && j < width-1) {
            rooms[i][j].setType('O');  // Make room movable
            if (Math.random() < 0.5 && i < height - 1) {
                i++;
            } else if (j < width - 1) {
                j++;
            }
        }
    }

    private void establishConnections() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (rooms[i][j].getType() == 'O') {
                    if (i > 0 && rooms[i - 1][j].getType() == 'O') {
                        rooms[i][j].setDirection(Room.Direction.NORTH, true);
                        rooms[i - 1][j].setDirection(Room.Direction.SOUTH, true);
                    }
                    if (i < height - 1 && rooms[i + 1][j].getType() == 'O') {
                        rooms[i][j].setDirection(Room.Direction.SOUTH, true);
                        rooms[i + 1][j].setDirection(Room.Direction.NORTH, true);
                    }
                    if (j > 0 && rooms[i][j - 1].getType() == 'O') {
                        rooms[i][j].setDirection(Room.Direction.WEST, true);
                        rooms[i][j - 1].setDirection(Room.Direction.EAST, true);
                    }
                    if (j < width - 1 && rooms[i][j + 1].getType() == 'O') {
                        rooms[i][j].setDirection(Room.Direction.EAST, true);
                        rooms[i][j + 1].setDirection(Room.Direction.WEST, true);
                    }
                }
            }
        }
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public boolean movePlayer(String direction) {
        int currentX = getCurrentX();
        int currentY = getCurrentY();

        switch (direction.toLowerCase()) {
            case "north":
                if (currentY > 0 && rooms[currentY][currentX].canMove(Room.Direction.NORTH)) {
                    currentRoom.setType('x');
                    currentRoom = rooms[currentY - 1][currentX];
                    return true;
                }
                else {
                    System.out.println("That's a wall.");
                    return false;
                }
            case "south":
                if (currentY < height - 1 && rooms[currentY][currentX].canMove(Room.Direction.SOUTH)) {
                    currentRoom.setType('x');
                    currentRoom = rooms[currentY + 1][currentX];
                    return true;
                }
                else {
                    System.out.println("That's a wall.");
                    return false;
                }
            case "east":
                if (currentX < width - 1 && rooms[currentY][currentX].canMove(Room.Direction.EAST)) {
                    currentRoom.setType('x');
                    currentRoom = rooms[currentY][currentX + 1];
                    return true;
                }
                else {
                    System.out.println("That's a wall.");
                    return false;
                }
            case "west":
                if (currentX > 0 && rooms[currentY][currentX].canMove(Room.Direction.WEST)) {
                    currentRoom.setType('x');
                    currentRoom = rooms[currentY][currentX - 1];
                    return true;
                }
                else {
                    System.out.println("That's a wall.");
                    return false;
                }
            default:
                System.out.println("Invalid direction! Try again.");
                return false;
        }
    }

    private int getCurrentX() {
        for (int i = 0; i < rooms.length; i++) {
            for (int j = 0; j < rooms[i].length; j++) {
                if (rooms[i][j] == currentRoom) {
                    return j;
                }
            }
        }
        return -1; // Default error case
    }

    private int getCurrentY() {
        for (int i = 0; i < rooms.length; i++) {
            for (int j = 0; j < rooms[i].length; j++) {
                if (rooms[i][j] == currentRoom) {
                    return i;
                }
            }
        }
        return -1; // Default error case
    }

    public void displayDungeon() {
        StringBuilder sb = new StringBuilder();

        // Add top border
        sb.append("/");
        for (int j = 0; j < width; j++) {
            sb.append("--");
        }
        sb.append("/)\n");

        // Add dungeon grid
        for (int i = 0; i < height; i++) {
            sb.append("|");
            for (int j = 0; j < width; j++) {
                if (rooms[i][j] == currentRoom) {
                    sb.append("p ");
                } else {
                    sb.append(rooms[i][j].getType() + " ");
                }
            }
            sb.append("|\n");
        }

        // Add bottom border
        sb.append("\\");
        for (int j = 0; j < width; j++) {
            sb.append("--");
        }
        sb.append("\\)\n");

        System.out.println(sb.toString());
    }

    public String getAvailableDirections() {
        StringBuilder directions = new StringBuilder("Available directions: ");
        Room current = getCurrentRoom();
        if (current.canMove(Room.Direction.NORTH)) directions.append("North ");
        if (current.canMove(Room.Direction.SOUTH)) directions.append("South ");
        if (current.canMove(Room.Direction.EAST)) directions.append("East ");
        if (current.canMove(Room.Direction.WEST)) directions.append("West ");
        return directions.toString();
    }
}