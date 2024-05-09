public class Equippable extends Item {
    private String attributeModifier;
    private boolean isEquipped;

    // Constructors
    Equippable() {};
    Equippable(String newName, String newDescription, String newType, int newValue, String newAttributeModifier ) {
        super(newName, newDescription, newType, newValue);
        this.attributeModifier = newAttributeModifier;
        this.isEquipped = false;
    }

    // Getters and Setters
    public String getAttributeModifier() {
        return attributeModifier;
    }
    public boolean isEquipped() {
        return isEquipped;
    }
    
    public void setAttributeModifier(String newAttributeModifier) {
        attributeModifier = newAttributeModifier;
    }
    public void setIsEquipped(boolean equipped) {
        isEquipped = equipped;
    }

    // Override use method for Equippable items
    @Override
    public void use(Entity target) {
        System.out.println(target.getName() + " equips " + this.name + " as their " + this.type + ".");
        if (this.type.equalsIgnoreCase("weapon")) {
            target.setWeapon(this);
        } else if (this.type.equalsIgnoreCase("armor")) {
            target.setEquippedArmor(this);
            target.setMaxArmor(value);
            target.setArmor(value);
        } else {
            System.out.println(target.getName() + " cannot equip " + this.name + ".");
        }
    }
}