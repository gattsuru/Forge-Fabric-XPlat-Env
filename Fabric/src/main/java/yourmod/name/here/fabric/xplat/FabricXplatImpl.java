package yourmod.name.here.fabric.xplat;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import yourmod.name.here.xplat.IXplatAbstractions;
import yourmod.name.here.xplat.IXplatTags;
import yourmod.name.here.xplat.Platform;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class FabricXplatImpl implements IXplatAbstractions {
    @Override
    public Platform platform() {
        return Platform.FABRIC;
    }

    @Override
    public boolean isPhysicalClient() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }

    @Override
    public boolean isModPresent(String id) {
        return FabricLoader.getInstance().isModLoaded(id);
    }

    @Override
    public void initPlatformSpecific() {
//        if (this.isModPresent() {
//        }
    }

    @Override
    public ResourceLocation getID(Block block) {
        return Registry.BLOCK.getKey(block);
    }

    @Override
    public ResourceLocation getID(Item item) {
        return Registry.ITEM.getKey(item);
    }

    private static CreativeModeTab TAB = null;

    // do a stupid hack from botania
    private static List<ItemStack> stacks(Item... items) {
        return Stream.of(items).map(ItemStack::new).toList();
    }

    private static final List<List<ItemStack>> HARVEST_TOOLS_BY_LEVEL = List.of(
        stacks(Items.WOODEN_PICKAXE, Items.WOODEN_AXE, Items.WOODEN_HOE, Items.WOODEN_SHOVEL),
        stacks(Items.STONE_PICKAXE, Items.STONE_AXE, Items.STONE_HOE, Items.STONE_SHOVEL),
        stacks(Items.IRON_PICKAXE, Items.IRON_AXE, Items.IRON_HOE, Items.IRON_SHOVEL),
        stacks(Items.DIAMOND_PICKAXE, Items.DIAMOND_AXE, Items.DIAMOND_HOE, Items.DIAMOND_SHOVEL),
        stacks(Items.NETHERITE_PICKAXE, Items.NETHERITE_AXE, Items.NETHERITE_HOE, Items.NETHERITE_SHOVEL)
    );

    private static final IXplatTags TAGS = new IXplatTags() {
    };

    @Override
    public IXplatTags tags() {
        return TAGS;
    }

    @Override
    public String getModName(String namespace) {
        if (namespace.equals("c")) {
            return "Common";
        }
        Optional<ModContainer> container = FabricLoader.getInstance().getModContainer(namespace);
        if (container.isPresent()) {
            return container.get().getMetadata().getName();
        }
        return namespace;
    }
}
