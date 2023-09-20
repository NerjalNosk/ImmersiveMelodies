package immersive_melodies;

import immersive_melodies.client.animation.ItemAnimators;
import immersive_melodies.client.animation.animators.Animator;
import immersive_melodies.cobalt.registration.Registration;
import immersive_melodies.item.InstrumentItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public interface Items {
    List<Supplier<Item>> items = new LinkedList<>();
    List<Identifier> customInventoryModels = new LinkedList<>();

    Supplier<Item> BAGPIPE = register("bagpipe", () -> new InstrumentItem(baseProps(), Sounds.BAGPIPE, 200, 0.5f, 0.1f));
    Supplier<Item> DIDGERIDOO = register("didgeridoo", () -> new InstrumentItem(baseProps(), Sounds.DIDGERIDOO, 200, 1.0f, -0.45f));
    Supplier<Item> FLUTE = register("flute", () -> new InstrumentItem(baseProps(), Sounds.FLUTE, 100, 0.9f, 0.1f));
    Supplier<Item> LUTE = register("lute", () -> new InstrumentItem(baseProps(), Sounds.LUTE, 200, 0.5f, 0.0f));
    Supplier<Item> PIANO = register("piano", () -> new InstrumentItem(baseProps(), Sounds.PIANO, 300, 0.5f, 0.25f));
    Supplier<Item> TRIANGLE = register("triangle", () -> new InstrumentItem(baseProps(), Sounds.TRIANGLE, 300, 0.6f, 0.0f));
    Supplier<Item> TRUMPET = register("trumpet", () -> new InstrumentItem(baseProps(), Sounds.TRUMPET, 100, 1.4f, 0.2f));

    static Supplier<Item> register(String name, Supplier<Item> item) {
        Identifier identifier = Common.locate(name);
        Supplier<Item> register = Registration.register(Registries.ITEM, identifier, item);
        items.add(register);
        customInventoryModels.add(identifier);
        return register;
    }

    /**
     * Open method to create custom items, for addons
     * @param namespace Your addon's namespace
     * @param name Your addon's item name
     * @param animator Your item's animator. Allows to define how the entity
     *                 model should be animated when playing the instrument.
     * @param sustain Determines the instrument's note sustain capacity.
     *                Defines how many ticks can a note hold for the longest
     *                with your custom instrument.
     * @param hOffset Determines the horizontal offset from the player's location
     *                of the position at which a note particle should be displayed.
     * @param vOffset Determines the vertical offset from the player's location
     *                of the position at which a note particle should be displayed.
     * @return The registered item's provider.
     */
    static @Nullable Supplier<Item> register(@NotNull String namespace, @NotNull String name, Animator animator,
                                             long sustain, float hOffset, float vOffset) {
        Identifier identifier = Identifier.of(namespace, name);
        if (identifier == null) {
            return null;
        }
        Supplier<Item> supplier = register(namespace, name, sustain, hOffset, vOffset);
        ItemAnimators.register(identifier, animator);
        return supplier;
    }

    /**
     * Open method to create custom items, for addons.
     * If using this method, make sure to also register an {@link Animator} for your item.
     * @param namespace Your addon's namespace
     * @param name Your addon's item name
     * @param sustain Your item's animator. Allows to define how the entity
     *                model should be animated when playing the instrument.
     * @param hOffset Determines the horizontal offset from the player's location
     *                of the position at which a note particle should be displayed.
     * @param vOffset Determines the vertical offset from the player's location
     *                of the position at which a note particle should be displayed.
     * @return The registered item's provider.
     */
    static @Nullable Supplier<Item> register(@NotNull String namespace, @NotNull String name,
                                             long sustain, float hOffset, float vOffset) {
        Identifier identifier = Identifier.of(namespace, name);
        if (identifier == null) {
            return null;
        }
        Sounds.Instrument instrument = new Sounds.Instrument(namespace, name);
        Supplier<Item> itemSupplier = () -> new InstrumentItem(baseProps(), instrument, sustain, hOffset, vOffset);
        Supplier<Item> supplier = Registration.register(Registries.ITEM, identifier, itemSupplier);
        items.add(supplier);
        customInventoryModels.add(identifier);
        return supplier;
    }

    static void bootstrap() {
        // nop
    }

    static Item.Settings baseProps() {
        return new Item.Settings().maxCount(1);
    }

    static Collection<ItemStack> getSortedItems() {
        return items.stream().map(i -> i.get().getDefaultStack()).toList();
    }
}
