import java.util.ArrayList;
import java.util.List;

public class GameItems {
    public static List<Item> items = new ArrayList<>();
    public static List<Equippable> weapons = new ArrayList<>();
    public static List<Equippable> armors = new ArrayList<>();

    static {
        // Define weapons
        weapons.add(new Equippable("Sword", "A sharp blade.", "weapon", 10, "strength"));
        weapons.add(new Equippable("Axe", "A heavy, two-handed axe.", "weapon", 15, "strength"));
        weapons.add(new Equippable("Bow", "A long range bow.", "weapon", 8, "perception"));
        weapons.add(new Equippable("Dagger", "A long, pointy dagger.", "weapon", 8, "agility"));

        // Define armors
        armors.add(new Equippable("Leather Armor", "Lightweight leather armor.", "armor", 5, "agility"));
        armors.add(new Equippable("Chainmail", "Sturdy chainmail armor.", "armor", 10, "constitution"));
        armors.add(new Equippable("Plate Armor", "Heavy plate armor.", "armor", 15, "strength"));
        armors.add(new Equippable("Cloak", "A light cloak.", "armor", 3, "agility"));

        // Define items
        items.add(new Item("Weak Healing Potion", "A small healing vial to heal 5 health.", "healing potion", 5));
        items.add(new Item("Healing Potion", "A healing vial to heal 10 health.", "healing potion", 10));
        items.add(new Item("Strong Healing Potion", "A large healing vial to heal 20 health.", "healing potion", 20));
        items.add(new Item("Small Fire Pot", "A small clay pot that contains living fire. Deals 5 damage.", "potion", 5));
        items.add(new Item("Large Fire Pot", "A large clay pot that contains living fire. Deals 15 damage.", "potion", 15));
        items.add(new Item("Weak Armor Potion", "A small armor potion that increases armor by 5 temporarily.", "armor potion", 5));
        items.add(new Item("Armor Potion", "An armor potion that increases armor by 7 temporarily.", "armor potion", 7));
        items.add(new Item("Strong Armor Potion", "A large armor potion that increases armor by 10 temporarily.", "armor potion", 10));
    }
}