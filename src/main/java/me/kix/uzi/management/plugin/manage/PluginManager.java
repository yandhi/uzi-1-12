package me.kix.uzi.management.plugin.manage;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import me.kix.uzi.Uzi;
import me.kix.uzi.api.manager.ListManager;
import me.kix.uzi.api.plugin.AbstractPlugin;
import me.kix.uzi.api.plugin.Plugin;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.network.TPSTracker;
import me.kix.uzi.management.plugin.internal.Commands;
import me.kix.uzi.management.plugin.internal.Keybinds;
import me.kix.uzi.management.plugin.internal.Rainbow;
import me.kix.uzi.management.plugin.internal.TwoDimensionalRenderManager;
import me.kix.uzi.management.plugin.internal.toggleable.combat.*;
import me.kix.uzi.management.plugin.internal.toggleable.combat.KillAura;
import me.kix.uzi.management.plugin.internal.toggleable.miscellaneous.*;
import me.kix.uzi.management.plugin.internal.toggleable.movement.*;
import me.kix.uzi.management.plugin.internal.toggleable.player.*;
import me.kix.uzi.management.plugin.internal.toggleable.render.*;
import me.kix.uzi.management.plugin.internal.toggleable.server.*;
import me.kix.uzi.management.plugin.internal.toggleable.world.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class PluginManager extends ListManager<Plugin> {

    private Path directory;


    public PluginManager(Path directory) {
        this.directory = directory;
    }

    public void init() {
        Uzi.INSTANCE.getEventManager().register(TPSTracker.getTracker());
        getContents().add(new TwoDimensionalRenderManager());
        getContents().add(new Commands());
        getContents().add(new Rainbow());
        getContents().add(new Keybinds());
        getContents().add(new Overlay());
        getContents().add(new Fullbright());
        getContents().add(new KillAura());
        getContents().add(new AntiVelocity());
        getContents().add(new Sprint());
        getContents().add(new Criticals());
        getContents().add(new AntiHunger());
        getContents().add(new Triggerbot());
        getContents().add(new Aimbot());
        getContents().add(new Nuker());
        getContents().add(new ESP());
        getContents().add(new Nametags());
        getContents().add(new Tracers());
        getContents().add(new AutoLog());
        getContents().add(new AutoTotem());
        getContents().add(new AutoWalk());
        getContents().add(new AutoRespawn());
        getContents().add(new AutoFish());
        getContents().add(new Sneak());
        getContents().add(new Speed());
        getContents().add(new ChestStealer());
        getContents().add(new AutoArmor());
        getContents().add(new Flight());
        getContents().add(new AntiBot());
        getContents().add(new Speedmine());
        getContents().add(new AutoSwim());
        getContents().add(new AutoPayload());
        getContents().add(new AutoPotion());
        getContents().add(new AutoSoup());
        getContents().add(new AntiWeather());
        getContents().add(new Murderer());
        getContents().add(new FastPlace());
        getContents().add(new Step());
        getContents().add(new Auto4Chan());
        getContents().add(new Freecam());
        getContents().add(new Jesus());
        getContents().add(new FastUse());
        getContents().add(new FastBow());
        getContents().add(new Storage());
        getContents().add(new FriendImmunity());
        getContents().add(new NameProtect());
        getContents().add(new BowAimbot());
        getContents().add(new SafeWalk());
        getContents().add(new Scaffold());
        getContents().add(new Lawnmower());
        getContents().add(new Tractor());
        getContents().add(new Shear());
        getContents().add(new Breed());
        getContents().add(new Farmer());
        getContents().add(new NoFall());
        getContents().add(new ChunkUpdates());
        getContents().add(new ElytraFly());
        getContents().add(new Saddle());
        getContents().add(new AutoAccept());
        getContents().add(new NoTab());
        getContents().add(new AntiAim());
        getContents().add(new InventoryMove());
        getContents().add(new Wallhack());
        getContents().add(new NoSlow());
        getContents().add(new CivBreak());
        getContents().add(new AutoClimb());
        getContents().add(new PacketFly());
        getContents().add(new Waypoints());
        getContents().add(new Bomber());
        getContents().add(new Boxer());
        getContents().add(new Glow());
        getContents().add(new Timer());
        getContents().add(new Stamp());
        getContents().add(new ColorSigns());
        getContents().add(new PortalGodMode());
        getContents().add(new EntitySpeed());
        getContents().add(new Blink());
        getContents().add(new AutoMine());
        getContents().add(new BucketFall());
        getContents().add(new AntiBookBan());
        getContents().add(new Hurtcam());
        getContents().add(new Bossbar());
        getContents().add(new Portal());
        getContents().add(new Scoreboard());
        getContents().add(new Pumpkin());
        getContents().add(new Potions());
        getContents().add(new Chams());
        getContents().add(new WorldEditCUI());
        getContents().add(new Mentions());
        getContents().add(new Accusation());
        getContents().add(new Greeter());
        getContents().add(new AntiVanish());
        getContents().add(new SourceMovement());
        getContents().add(new Doorbell());
        getContents().add(new SoundLag());
        getContents().add(new AntiSoundSploit());
        getContents().add(new CapabilityFly());
        getContents().add(new SmartChat());
        getContents().add(new AntiDesync());
        getContents().add(new BedAura());
        getContents().add(new PearlLogger());
        getContents().add(new Strike());
        getContents().add(new Phase());
        getContents().add(new ServerResponding());
        getContents().add(new NoPush());
        getContents().add(new Compass());
        getContents().add(new Clock());
        getContents().add(new XCarry());
        getContents().add(new NoHandshake());
        getContents().add(new Names());
        getContents().add(new Search());
        getContents().add(new BuildHelper());
        getContents().add(new AntiLevitate());
        getContents().add(new AutoTool());
        getContents().add(new BlockOutline());
        getContents().add(new PistonStems());
        getContents().add(new AntiPhysics());
        getContents().add(new NoReceiveParticles());
        getContents().add(new TrueDura());
        getContents().add(new LogoutLocation());
        getContents().add(new Vanish());
        getContents().add(new Signs());
        getContents().add(new FakeLag());
        getContents().add(new AutoSell());
        getContents().add(new Chinahat());
        getContents().add(new Sombrero());
        getContents().add(new Autoclicker());

        getContents().forEach(Plugin::initPlugin);
        load();
    }

    public Optional<Plugin> getPlugin(String label) {
        return getContents()
                .stream()
                .filter(p -> p.getLabel().equalsIgnoreCase(label))
                .findFirst();
    }

    /**
     * Saves plugin data on shutdown.
     *
     * @since revised 11/12/2022
     */
    public void save() {
        try {
            if (Files.exists(directory)) {
                Stream<Path> filesStream = Files.list(directory);
                if (filesStream != null) {
                    filesStream.forEach(file -> {
                        try {
                            Files.delete(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            } else {
                Files.createDirectories(directory);
            }

            getContents().forEach(plugin -> {
                JsonObject jsonObject = new JsonObject();
                plugin.save(jsonObject);

                Path file = Paths.get(this.directory.toString(), plugin.getLabel() + ".json").toAbsolutePath();

                try {
                    Files.createFile(file);
                    Files.write(file, new GsonBuilder().setPrettyPrinting().create().toJson(jsonObject).getBytes(StandardCharsets.UTF_8));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        this.getContents().forEach(module -> {
            Path file = Paths.get(this.directory.toString(), module.getLabel() + ".json").toAbsolutePath();
            if (!Files.exists(file)) {
                return;
            }
            try {
                JsonElement node = new JsonParser().parse(new JsonReader(Files.newBufferedReader(file)));
                if (!node.isJsonObject()) {
                    return;
                }
                module.load(node.getAsJsonObject());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
