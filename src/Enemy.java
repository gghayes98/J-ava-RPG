// TODO: EXTRA: Add description
public class Enemy extends Entity {
    private int attackPower;
    private int xpGiven;

    // Constructors
    public Enemy() {};
    public Enemy(int newLevel, String newName, int newAttackPower, int newXpGiven, int newArmor) {
        this.name = newName;
        this.level = (newLevel >= 0) ? newLevel : 0;
        this.maxHealth = 10 + 10 * level;
        this.health = maxHealth;
        this.attackPower = newAttackPower + newAttackPower * level;
        this.xpGiven = newXpGiven + newXpGiven * level;
        this.maxArmor = newArmor + newArmor * level;
        this.armor = maxArmor;
    }

    // Getters and Setters
    public int getAttackPower() {
        return attackPower;
    }
    public int getXpGiven() {
        return xpGiven;
    }

    public void setAttackPower(int newAttackPower) {
        attackPower = newAttackPower;
    }
    public void setXpGiven(int newXpGiven) {
        xpGiven = newXpGiven;
    }
}
