public class Entity {
    protected int health;
    protected int maxHealth;
    protected int armor;
    protected int maxArmor;
    protected int level;
    protected String name;
    protected boolean isDead;
    protected Equippable weapon;
    protected Equippable equippedArmor;
    
    public Entity() {
        health = 0;
        maxHealth = 0;
        level = 0;
        name = "ERROR";
    }
    // Constructor - Maybe do others (ie default)
    public Entity(int newHealth, int newLevel, String newName, int newMaxArmor) {
        this.maxHealth = newHealth;
        this.health = newHealth;
        this.maxArmor = newMaxArmor;
        this.armor = maxArmor;
        this.level = newLevel;
        this.name = newName;
        this.isDead = false;
    }

    // Getters and Setters
    public int getHealth() {
        return health;
    }
    public int getMaxHealth() {
        return maxHealth;
    }
    public int getArmor() {
        return armor;
    }
    public int getMaxArmor() {
        return maxArmor;
    }
    public int getLevel() {
        return level;
    }
    public String getName() {
        return name;
    }
    public Equippable getEquippedArmor() {
        return equippedArmor;
    }
    public Equippable getWeapon() {
        return weapon;
    }

    public void setHealth(int newHealth) {
        if (newHealth > maxHealth) {
            health = maxHealth;
        }
        else {
            health = newHealth;
        }
    }
    public void setMaxHealth(int newMaxHealth) {
        maxHealth = newMaxHealth;
    }
    public void setArmor(int newArmor) {
        armor = newArmor;
    }
    public void setMaxArmor(int newMaxArmor) {
        maxArmor = newMaxArmor;
    }
    public void setLevel(int newLevel) {
        level = newLevel;
    }
    public void setName(String newName) {
        name = newName;
    }
    public void setEquippedArmor(Equippable newEquippedArmor) {
        equippedArmor = newEquippedArmor;
    }
    public void setWeapon(Equippable newWeapon) {
        weapon = newWeapon;
    }

    protected void takeDamage(int damage) {
        // Check for armor
        if (armor > 0) {
            if (damage >= armor) {
                System.out.println(this.name + "'s armor protected them from " + armor + " damage but left it useless.");
                damage -= armor;
                armor = 0;
            }
            else {
                armor -= damage;
                damage = 0;
                System.out.println(this.name + "'s armor protected them from all damage! " + armor + " remains.");
            }
        }
        
        if (health - damage <= 0) {
            health = 0;
            isDead = true;
            System.out.println(this.name + " dies from the attack!");
        }
        else {
            health -= damage;
            System.out.println(this.name + "'s lack of armor left them exposed to " + damage + " damage leaving them with " + health + " health.");
        }
    }

    public boolean isDead() {
        return isDead;
    }
}
