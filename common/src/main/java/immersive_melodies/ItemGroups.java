package immersive_melodies;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ItemGroups {
    public static ItemGroup GROUP;

    public static Identifier getIdentifier() {
        return Common.locate(Common.MOD_ID + "_tab");
    }

    public static ItemStack getIcon() {
        return Items.LUTE.get().getDefaultStack();
    }
}
