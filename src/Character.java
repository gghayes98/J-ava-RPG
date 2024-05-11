import java.util.ArrayList;
import java.util.Scanner;

public class Character extends Entity{
    private int strength;
    private int constitution;
    private int agility;
    private int perception;
    private int experience;
    private int XPtoNextLevel;
    private ArrayList<Item> inventory;

    // Constructors
    public Character() {};
    public Character(int newStr, int newCon, int newAgi, int newPer, String newName) {
        this.name = newName;
        this.level = 0;
        this.strength = newStr;
        this.constitution = newCon;
        this.agility = newAgi;
        this.perception = newPer;
        this.experience = 0;
        this.XPtoNextLevel = 10;
        this.inventory = new ArrayList<>();
        this.maxHealth = constitution * 5;
        this.health = maxHealth;
        this.equippedArmor = new Equippable("Tunic", "Just a plain linen tunic offering little to no protection.", "Armor", 0, "Agility");
        this.weapon = new Equippable("Fists", "Your bare fists.", "Weapon", 1, "Strength");
    }

    // Getters and Setters
    public int getAttribute(String attribute) {
        switch (attribute.toLowerCase()) {  
            case "strength":
                return strength;
            case "constitution":
                return constitution;
            case "agility":
                return agility;
            case "perception":
                return perception;
        }
        return -1;
    }
    public int getExperience() {
        return experience;
    }
    public int getXPtoNextLevel() {
        return XPtoNextLevel;
    }
    public ArrayList<Item> getInventory() {
        return inventory;
    }


    public void setStrength(int newStr) {
        strength = newStr;
    }
    public void setConstitution(int newCon) {
        constitution = newCon;
    }
    public void setAgility(int newAgi) {
        agility = newAgi;
    }
    public void setPerception(int newPer) {
        perception = newPer;
    }
    public void setExperience(int newXP) {
        experience = newXP;
    }
    public void setXPtoNextLevel(int newXptoNextLevel) {
        XPtoNextLevel = newXptoNextLevel;
    }
    public void addItemToInventory(Item item) {
        this.inventory.add(item);
    }

    // Experience and level management
    public void gainExperience(int xp) {
        this.experience += xp;
        System.out.println("\n" + this.name + " has gained " + xp + " xp!");
        if (this.experience >= this.XPtoNextLevel) {
            this.level++;
            levelUp();
            this.experience -= this.XPtoNextLevel;
            this.XPtoNextLevel = 100 * this.level;
            this.maxHealth += this.constitution * 5;
            this.health = this.maxHealth;
        }
        System.out.println(this.name + " now has " + xp + " xp!");
    }

    private void levelUp() {
        Scanner myObj = new Scanner(System.in);
        System.out.println(name + " has reached level " + level + 1 + "! You have one extra attribute point to distribute. Which attribute would you like to increase? \n");
        System.out.println("Strength is currently: " + strength + "\nConstitution is currently: " + constitution + "\nAgility is currently: " + agility + "\nPerception is currently: " + perception);
        System.out.println("> ");
        String attributeToIncrease = myObj.nextLine();
        myObj.nextLine();

        switch (attributeToIncrease.toLowerCase()) {
            case "strength":
                this.strength += 1;
                break;
            case "constitution":
                this.constitution += 1;
                break;
            case "agility":
                this.agility += 1;
                break;
            case "perception":
                this.perception += 1;
                break;
            default:
                System.out.println("Input not recognized. Please type either \"Strength\", \"Constitution\", \"Agility\", or \"Perception\".");
                levelUp();
                break;
        }
        myObj.close();
    }

    public void getCharacterSheet() {
        System.out.println("\n===== Character Sheet =====");
        System.out.println("Name: " + name);
        System.out.println("Level: " + level);
        System.out.println("Experience: " + experience + "/" + XPtoNextLevel);
        System.out.println("Armor: " + armor + "/" + maxArmor);
        System.out.println("Health: " + health + "/" + maxHealth);
        System.out.println("Strength: " + strength);
        System.out.println("Constitution: " + constitution);
        System.out.println("Agility: " + agility);
        System.out.println("Perception: " + perception);
        System.out.println("\nInventory:");
        if (inventory.isEmpty()) {
            System.out.println("- No items");
        } else {
            for (Item item : inventory) {
                System.out.println("- " + item.getName() + ": " + item.getDescription());
            }
        }
        System.out.println("\nEquipped Armor: " + equippedArmor.getName());
        System.out.println("\t" + equippedArmor.getDescription());
        System.out.println("\tAffected by: " + equippedArmor.getAttributeModifier());
        System.out.println("\nEquipped Weapon: " + weapon.getName());
        System.out.println("\t" + weapon.getDescription());
        System.out.println("\tAffected by: " + weapon.getAttributeModifier());
        System.out.println("===========================\n");
    }

}
