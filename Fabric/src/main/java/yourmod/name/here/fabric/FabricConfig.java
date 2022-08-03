package yourmod.name.here.fabric;

import io.github.fablabsmc.fablabs.api.fiber.v1.builder.ConfigTreeBuilder;
import io.github.fablabsmc.fablabs.api.fiber.v1.exception.ValueDeserializationException;
import io.github.fablabsmc.fablabs.api.fiber.v1.serialization.FiberSerialization;
import io.github.fablabsmc.fablabs.api.fiber.v1.serialization.JanksonValueSerializer;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.ConfigTree;
import yourmod.name.here.api.YourAPI;
import yourmod.name.here.api.YourConfig;
import yourmod.name.here.xplat.IXplatAbstractions;

import java.io.*;
import java.nio.file.*;

// https://github.com/VazkiiMods/Botania/blob/1.18.x/Fabric/src/main/java/vazkii/botania/fabric/FiberBotaniaConfig.java
public class FabricConfig {
    private static final Common COMMON = new Common();
    private static final Client CLIENT = new Client();
    private static final Server SERVER = new Server();

    private static void writeDefaultConfig(ConfigTree config, Path path, JanksonValueSerializer serializer) {
        try (OutputStream s = new BufferedOutputStream(
            Files.newOutputStream(path, StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW))) {
            FiberSerialization.serialize(config, s, serializer);
        } catch (FileAlreadyExistsException ignored) {
        } catch (IOException e) {
            YourAPI.LOGGER.error("Error writing default config", e);
        }
    }

    private static void setupConfig(ConfigTree config, Path p, JanksonValueSerializer serializer) {
        writeDefaultConfig(config, p, serializer);

        try (InputStream s = new BufferedInputStream(
            Files.newInputStream(p, StandardOpenOption.READ, StandardOpenOption.CREATE))) {
            FiberSerialization.deserialize(config, s, serializer);
        } catch (IOException | ValueDeserializationException e) {
            YourAPI.LOGGER.error("Error loading config from {}", p, e);
        }
    }

    public static void setup() {
        try {
            Files.createDirectory(Paths.get("config"));
        } catch (FileAlreadyExistsException ignored) {
        } catch (IOException e) {
            YourAPI.LOGGER.warn("Failed to make config dir", e);
        }

        var serializer = new JanksonValueSerializer(false);
        var common = COMMON.configure(ConfigTree.builder());
        setupConfig(common, Paths.get("config", YourAPI.MOD_ID + "-common.json5"), serializer);
        YourConfig.setCommon(COMMON);

        // We care about the client only on the *physical* client ...
        if (IXplatAbstractions.INSTANCE.isPhysicalClient()) {
            var client = CLIENT.configure(ConfigTree.builder());
            setupConfig(client, Paths.get("config", YourAPI.MOD_ID + "-client.json5"), serializer);
            YourConfig.setClient(CLIENT);
        }
        // but we care about the server on the *logical* server
        // i believe this should Just Work without a guard? assuming we don't access it from the client ever
        var server = SERVER.configure(ConfigTree.builder());
        setupConfig(server, Paths.get("config", YourAPI.MOD_ID + "-server.json5"), serializer);
        YourConfig.setServer(SERVER);

    }

    private static final class Common implements YourConfig.CommonConfigAccess {
        public ConfigTree configure(ConfigTreeBuilder bob) {

            return bob.build();
        }
    }

    private static final class Client implements YourConfig.ClientConfigAccess {

        public ConfigTree configure(ConfigTreeBuilder bob) {
            return bob.build();
        }
    }

    private static final class Server implements YourConfig.ServerConfigAccess {

        public ConfigTree configure(ConfigTreeBuilder bob) {
            return bob.build();
        }
    }
}
