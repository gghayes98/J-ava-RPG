import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GameManager {
    private Character player;
    private Dungeon dungeon;
    private Scanner scanner;
    private boolean wyvernSlain = false;

    public GameManager() {
        dungeon = new Dungeon(10, 10);
        scanner = new Scanner(System.in);
    }

    // Utility methods
    public static void CLEAR() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public static void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            // Handle exception if needed, or simply ignore it
            Thread.currentThread().interrupt(); // Reset the interrupted status
        }
    }
    private int getIntInput(int min, int max) {
        int input = -1;
        while (input < min || input > max) {
            try {
                input = Integer.parseInt(scanner.nextLine());
                if (input < min || input > max) {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                    System.out.print("> ");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
                System.out.print("> ");
            }
        }
        return input;
    }

    // This is the loop which runs the game. It runs until the player is dead or slays the Wyvern.
    public void startGame() {
        System.out.println("You awaken in a dark room with a headache. Your head pulses painfully as you desperately try to remember how you got here and who you are. All you can \n remember is that you're here to slay a wyvern. Slowly, it all comes back to you. Who are you?\n");
        pause(1000);
        createCharacter();
        while(!player.isDead() && !wyvernSlain) {
            System.out.println("\nWhat would you like to do?");
            System.out.println("1. Open map\n2. Use/Equip item\n3. Move\n4. View character sheet");
            System.out.print("> ");
            int choice = getIntInput(1, 4);
            CLEAR();
            playerAction(choice);
        }
        if(player.isDead()) {
            CLEAR();
            System.out.println("Game Over");
        }
        else {
            CLEAR();
            System.out.println("Congratulations! " + player.getName() + " has slain the wyvern, freeing themself from this dangerous dungeon!");
        }
        System.out.println("Here are the final stats of " + player.getName());
        player.getCharacterSheet();
    }

    // Character creation
    private void createCharacter() {
        System.out.println("Create your characer: ");
        System.out.println("1. Warrior\n2. Berserker\n3. Ranger\n4. Thief\n5. Custom");
        System.out.print("> ");
        int choice = getIntInput(1, 5);
        switch (choice) {
            case 1:
                player = new Character(6, 8, 2, 4, "Warrior");
                break;
            case 2:
                player = new Character(8, 4, 6, 2, "Berserker");
                break;
            case 3:
                player = new Character(2, 4, 6, 8, "Ranger");
                break;
            case 4:
                player = new Character(4, 2, 8, 6, "Thief");
                break;
            case 5:
                int total = 0;
                int pointsLeft = 20;
                int str = 0;
                int con = 0;
                int agi = 0;
                int per = 0;
                while (total < 20 && pointsLeft != 0) {
                    System.out.println("You have " + pointsLeft + " points to allocate between the four attributes (Strength, Constitution, Agility, Perception).");
                    System.out.println("Which attribute would you like to increase? ");
                    System.out.print("> ");
                    String attincr = scanner.nextLine();
                    System.out.println("How many points would you like to put into " + attincr + "?");
                    System.out.print("> ");
                    int pointincr = getIntInput(0, pointsLeft);

                    switch (attincr.toLowerCase()) {
                        case "strength":
                            str += pointincr;
                            break;
                        case "constitution":
                            con += pointincr;
                            break;
                        case "agility":
                            agi += pointincr;
                            break;
                        case "perception":
                            per += pointincr;
                            break;
                        default:
                            System.out.println("Invalid attribute. Try again.");
                    }
                    total += pointincr;
                    pointsLeft -= pointincr;
                }
                System.out.println("What is the name of your character? ");
                System.out.print("> ");
                String charName = scanner.nextLine();
                player = new Character(str, con, agi, per, charName);

                CLEAR();

                player.getCharacterSheet();
                return;
        }
        System.out.println("\nWhat is the name of your character? ");
        System.out.print("> ");
        String charName = scanner.nextLine();
        player.setName(charName);

        CLEAR();

        player.getCharacterSheet();
    }

    // Entering a new room
    private void handleRoomInteraction() {
        Room currentRoom = dungeon.getCurrentRoom();
        if (!currentRoom.hasVisited()) {
            currentRoom.setHasVisited(true);
            Random rand = new Random();
            int event = rand.nextInt(10); // 0: Empty, 1-3: Item, 4-10: Enemy
            switch (event) {
                case 0:
                    System.out.println("This room is empty fortunately.");
                    player.gainExperience(5);
                    break;
                case 1: case 2: case 3:
                    int item_search = rand.nextInt(50);  // Random item roll
                    if (item_search < 25) {  // 50% chance for an item
                        Item item = GameItems.items.get(rand.nextInt(GameItems.items.size()));
                        System.out.println("You found an item: " + item.getName());
                        player.addItemToInventory(item);
                    } else if (item_search < 40) {  // 15% chance for a weapon
                        Equippable weapon = GameItems.weapons.get(rand.nextInt(GameItems.weapons.size()));
                        System.out.println("You found a weapon: " + weapon.getName() + ". A weapon scaling off of " + weapon.getAttributeModifier());
                        player.addItemToInventory(weapon);
                    } else {  // 10% chance for armor
                        Equippable armor = GameItems.armors.get(rand.nextInt(GameItems.armors.size()));
                        System.out.println("You found armor: " + armor.getName() + ". Armor scaling off of " + armor.getAttributeModifier());
                        player.addItemToInventory(armor);
                    }
                    break;
                case 4: case 5: case 6: case 7: case 8: case 9: case 10:
                    System.out.println("An enemy appears!");
                    int enemy_selection = rand.nextInt(20); //- player level - difficultyModifier // 15-20: Goblin, 10-14: Orc, 6-9 Demon, 3-5: Troll, 1-2 Wyvern
                    int enemy = (enemy_selection - player.getLevel() >= 0) ? enemy_selection : 0;
                    switch (enemy) {
                        case 0: case 20: case 19: case 18: case 17: case 16: case 15:
                            System.out.println("It's a goblin!");
                            pause(1000);
                            combat(new Enemy(player.getLevel() + (rand.nextInt(1+1) - 1), "Goblin", 1, 5, 0));
                            break;
                        case 14: case 13: case 12: case 11: case 10:
                            System.out.println("It's an orc!");
                            pause(1000);
                            combat(new Enemy(player.getLevel() + (rand.nextInt(1+1) - 1) + 5, "Orc", 3, 7, 1));
                            break;
                        case 9: case 8: case 7: case 6:
                            System.out.println("It's a demon!");
                            pause(1000);
                            combat(new Enemy(player.getLevel() + (rand.nextInt(1+1) - 1) + 7, "Demon", 5, 10, 0));
                            break;
                        case 5: case 4: case 3:
                            System.out.println("It's a troll!");
                            pause(1000);
                            combat(new Enemy(player.getLevel() + (rand.nextInt(1+1) - 1) + 10, "Troll", 10, 20, 3));
                            break;
                        case 2: case 1:
                            System.out.println("It's the wyvern!");
                            pause(1000);
                            combat(new Enemy(player.getLevel() + (rand.nextInt(1+1) - 1) + 20, "Wyvern", 15, 30, 5));
                            break;
                    }
            }
        }
    }

    // Combat logic
    private void combat(Enemy enemy) {
        CLEAR();
        if (enemy.getName() != "Wyvern") {
            System.out.println(player.getName() + " comes across a violent " + enemy.getName());
        }
        else {
            System.out.println(player.getName() + " has finally found the Wyvern. Slaying it will finally end their quest!");
        }
        while (!enemy.isDead() && !player.isDead()) {
            System.out.println("\nThe " + enemy.getName() + " remains before you with " + enemy.getHealth() + " health remaining.");
            System.out.println("\nChoose an action: \n1. Attack\n2. Use item\n3. Check stats");
            System.out.println("> ");
            int choice = getIntInput(1, 3);
            CLEAR();
            switch (choice) {
                case 1:
                    System.out.println(player.getName() + " attacks the " + enemy.getName() + " with their " + player.getWeapon().getName() + "!\n");
                    enemy.takeDamage(player.getWeapon().getValue() + (player.getAttribute(player.getWeapon().getAttributeModifier()) * 2));
                    break;
                case 2:
                    useItem(new Enemy());
                    break;
                case 3:
                    player.getCharacterSheet();
                    break;
                default:
                    System.out.println("Invalid choice. Your hesitancy will cost you.");
            }
            if (!enemy.isDead() && choice != 3 && choice != 2) {
                System.out.println("\n" + enemy.getName() + " attacks " + player.getName() + "!\n");
                player.takeDamage(enemy.getAttackPower());
            }
        }
        if (player.isDead()) {
            System.out.println("You died!");
            return;
        }
        if (enemy.getName() == "Wyvern") {
            wyvernSlain = true;
        }
    }

    // Player movement
    private void moveCharacter(String direction) {
        if(dungeon.movePlayer(direction)) {
            handleRoomInteraction();
        }
        pause(1000);
    }

    // Handles player input
    private void playerAction(int choice) {
        switch (choice) {
            // Open map
            case 1:
                System.out.println(player.getName() + " unfurls their map.\nP = player, O = unvisited room, X = visited room, # = wall");
                dungeon.displayDungeon();
                pause(1000);
                break;
            // Use item
            case 2:
                useItem(new Enemy());
                break;
            // Move
            case 3:
                System.out.println("Which direction would you like to move (" + dungeon.getAvailableDirections() + ")?");
                System.out.print("> ");
                String direction = scanner.nextLine();
                CLEAR();
                moveCharacter(direction);
                break;
            case 4:
                player.getCharacterSheet();
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }

    private void useItem(Enemy enemy) {
        List<Item> inventory = player.getInventory();
        if (inventory.isEmpty()) {
            System.out.println(player.getName() + "'s inventory is empty.");
            return;
        }

        // Display all items in inventory with enumeration
        System.out.println("Select an item to use:");
        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i);
            System.out.println((i + 1) + ". " + item.getName() + " (" + item.getDescription() + ")");
        }

        // Prompt user to select an item
        System.out.print("> ");
        int itemSelection = getIntInput(1, inventory.size());

        // Validate input and use the selected item
        if (itemSelection >= 1 && itemSelection <= inventory.size()) {
            Item selectedItem = inventory.get(itemSelection - 1);
            // If it's an equippable item, equip it and remove it from the inventory
            if (selectedItem instanceof Equippable) {
                Equippable equip = (Equippable) selectedItem;
                equip.use(player);
                }
            // If it's a damage item, use it on the target
            else if (selectedItem.getType() == "damage potion") {
                // Make sure player is fighting
                if (enemy.getName() == null) {
                    System.out.println("There is no one to use this on.");
                }
                else {
                    selectedItem.use(enemy);
                }
            }
            // If it's a health or armor potion, use on player
            else if (selectedItem.getType() == "health potion" || selectedItem.getType() == "armor potion") {
                selectedItem.use(player);
            }
             else {
            System.out.println("Invalid selection. Please try again.");
            }
        }
        pause(2000);
        CLEAR();
    }
}