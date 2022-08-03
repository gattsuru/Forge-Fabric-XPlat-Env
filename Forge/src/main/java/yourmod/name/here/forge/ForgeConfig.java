package yourmod.name.here.forge;

import net.minecraftforge.common.ForgeConfigSpec;
import yourmod.name.here.api.YourConfig;

public class ForgeConfig implements YourConfig.CommonConfigAccess {

    public ForgeConfig(ForgeConfigSpec.Builder builder) {
    }

    public static class Client implements YourConfig.ClientConfigAccess {

        public Client(ForgeConfigSpec.Builder builder) {
        }
    }

    public static class Server implements YourConfig.ServerConfigAccess {
        public Server(ForgeConfigSpec.Builder builder) {
        }
    }
}
