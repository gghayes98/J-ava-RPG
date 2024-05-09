public class Item {
    protected String name;
    protected String description;
    protected String type; // Weapon, armor, item, etc
    protected int value; // Strength of effect

    // Constructor
    public Item() {};
    public Item(String newName, String newDesc, String newType, int newValue) {
        this.name = newName;
        this.description = newDesc;
        this.type = newType;
        this.value = newValue;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getType() {
        return type;
    }
    public int getValue() {
        return value;
    }

    public void setName(String newName) {
        name = newName;
    }
    public void setDescription(String newDesc) {
        description = newDesc;
    }
    public void setType(String newType) {
        type = newType;
    }
    public void setValue(int newValue) {
        value = newValue;
    }

    public void use(Entity target) {
        switch (type.toLowerCase()) {
            case "damage potion":
                System.out.println(target.getName() + " has been hit by the " + this.name + " for " + this.value + " damage.");
                target.takeDamage(value);
            case "healing potion":
                System.out.println(target.getName() + " has been affected by the " + this.name + " for " + this.value + " health.");
                target.setHealth(target.getHealth() + this.value);
            case "armor potion":
                System.out.println(target.getName() + " has been affected by the " + this.name + " for " + this.value + " armor repair.");
                target.setArmor(target.getArmor() + this.value);
            default:
                System.out.println(this.name + " cannot be used on " + target.getName() + ".");
        }
    }
}
