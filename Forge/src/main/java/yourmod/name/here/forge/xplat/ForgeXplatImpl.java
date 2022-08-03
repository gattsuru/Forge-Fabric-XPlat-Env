package yourmod.name.here.forge.xplat;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import yourmod.name.here.xplat.IXplatAbstractions;
import yourmod.name.here.xplat.IXplatTags;
import yourmod.name.here.xplat.Platform;

import java.util.Optional;

public class ForgeXplatImpl implements IXplatAbstractions {
    @Override
    public Platform platform() {
        return Platform.FORGE;
    }

    @Override
    public boolean isPhysicalClient() {
        return FMLLoader.getDist() == Dist.CLIENT;
    }

    @Override
    public boolean isModPresent(String id) {
        return ModList.get().isLoaded(id);
    }

    @Override
    public void initPlatformSpecific() {
    }

    @Override
    public ResourceLocation getID(Block block) {
        return block.getRegistryName();
    }

    @Override
    public ResourceLocation getID(Item item) {
        return item.getRegistryName();
    }

    @Override
    public IXplatTags tags() {
        return TAGS;
    }

    private static final IXplatTags TAGS = new IXplatTags() {
    };

    @Override
    public String getModName(String namespace) {
        if (namespace.equals("c")) {
            return "Common";
        }
        Optional<? extends ModContainer> container = ModList.get().getModContainerById(namespace);
        if (container.isPresent()) {
            return container.get().getModInfo().getDisplayName();
        }
        return namespace;
    }
}
