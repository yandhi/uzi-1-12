package me.kix.uzi.management.plugin.manage;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.kix.uzi.api.manager.ListManager;
import me.kix.uzi.api.plugin.Plugin;
import me.kix.uzi.management.plugin.internal.*;
import me.kix.uzi.management.plugin.internal.toggleable.combat.*;
import me.kix.uzi.management.plugin.internal.toggleable.miscellaneous.*;
import me.kix.uzi.management.plugin.internal.toggleable.movement.*;
import me.kix.uzi.management.plugin.internal.toggleable.player.*;
import me.kix.uzi.management.plugin.internal.toggleable.render.*;
import me.kix.uzi.management.plugin.internal.toggleable.render.persistant.*;
import me.kix.uzi.management.plugin.internal.toggleable.world.*;

import java.io.*;
import java.util.Optional;

public class PluginManager extends ListManager<Plugin> {

    private File directory;

    public PluginManager(File directory) {
        this.directory = directory;
    }

    public void init() {
        getContents().add(new Overlay());
        getContents().add(new TwoDimensionalRenderManager());
        getContents().add(new Commands());
        getContents().add(new Keybinds());
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
        getContents().add(new ChatEmotes());
        getContents().add(new Tiles());
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
        getContents().add(new ItemESP());
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
        load();
    }

    public Optional<Plugin> getPlugin(String label) {
        return getContents()
                .stream()
                .filter(p -> p.getLabel().equalsIgnoreCase(label))
                .findFirst();
    }

    public void save() {
        if (this.getContents().isEmpty()) {
            this.directory.delete();
        }
        File[] files = this.directory.listFiles();
        if (!this.directory.exists()) {
            this.directory.mkdir();
        } else if (files != null) {
            for (final File file : files) {
                file.delete();
            }
        }
        this.getContents().forEach(module -> {
            final File file = new File(this.directory, module.getLabel() + ".json");
            final JsonObject node = new JsonObject();
            module.save(node);
            if (node.entrySet().isEmpty()) {
                return;
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            try (final Writer writer = new FileWriter(file)) {
                writer.write(new GsonBuilder().setPrettyPrinting().create().toJson((JsonElement) node));
            } catch (IOException e) {
                e.printStackTrace();
                file.delete();
            }
        });
        files = this.directory.listFiles();
        if (files == null || files.length == 0) {
            this.directory.delete();
        }
    }

    public void load() {
        this.getContents().forEach(module -> {
            final File file = new File(this.directory, module.getLabel() + ".json");
            if (!file.exists()) {
                return;
            }
            try (final Reader reader = new FileReader(file)) {
                final JsonElement node = new JsonParser().parse(reader);
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
