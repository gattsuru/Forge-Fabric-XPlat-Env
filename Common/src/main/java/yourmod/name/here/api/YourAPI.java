package yourmod.name.here.api;

import com.google.common.base.Suppliers;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public interface YourAPI {
    String MOD_ID = "yourmod";
    Logger LOGGER = LogManager.getLogger(MOD_ID);

    Supplier<YourAPI> INSTANCE = Suppliers.memoize(() -> {
        try {
            return (YourAPI) Class.forName("yourmod.name.here.common.impl.YourAPIImpl")
                .getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            LogManager.getLogger().warn("Unable to find YourAPIImpl, using a dummy");
            return new YourAPI() {
            };
        }
    });

    static YourAPI instance() {
        return INSTANCE.get();
    }

    static ResourceLocation modLoc(String s) {
        return new ResourceLocation(MOD_ID, s);
    }
}
