package me.miquiis.scarymyths.common.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import net.minecraft.client.Minecraft;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FileManager {

    public static class VersionChecker {

        private final String VERSION;

        public VersionChecker(String version) {
            this.VERSION = version;
        }

        public String getVersion() {
            return VERSION;
        }
    }

    private Gson gson;
    private Gson deserializer;

    private File minecraftFolder;
    private File mainFolder;
    private boolean isFirstTime;

    private VersionChecker versionChecker;

    public FileManager(String filePath, String version) {
        this.minecraftFolder = Minecraft.getInstance().gameDir;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.deserializer = new GsonBuilder().setPrettyPrinting().create();
        this.mainFolder = new File(minecraftFolder, filePath);
        this.versionChecker = new VersionChecker(version);
        this.isFirstTime = createFolder();
    }

    public FileManager(String filePath, File directory, String version) {
        this.minecraftFolder = directory;

        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();

        this.gson = gsonBuilder.create();
        this.deserializer = new GsonBuilder().setPrettyPrinting().create();
        this.mainFolder = new File(minecraftFolder, filePath);
        this.versionChecker = new VersionChecker(version);
        this.isFirstTime = createFolder();
    }

    private boolean createFolder() {

        if (!mainFolder.exists()) {
            boolean a = mainFolder.mkdir();
            saveObject("version", versionChecker);
            return a;
        }

        VersionChecker lastVersion = loadObject("version", VersionChecker.class, false);
        if (lastVersion == null) return true;

        if (!lastVersion.VERSION.equals(versionChecker.getVersion()))
        {
            saveObject("version", versionChecker);
            return true;
        }

        return false;
    }

    public boolean deleteObject(String fileName, boolean hasExtension)
    {
        final File objectFile = new File(mainFolder, fileName + (!hasExtension ? ".json" : ""));
        return objectFile.delete();
    }

    public boolean saveObject(String fileName, Object object)
    {
        final String json = deserializer.toJson(object);

        File objectFile = new File(mainFolder, fileName + ".json");
        objectFile.delete();

        try {
            Files.write(objectFile.toPath(), json.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<String> peakFiles(String extension)
    {
        List<String> files = new ArrayList<>();
        Arrays.stream(mainFolder.listFiles()).forEach(file -> {
            if (extension.equalsIgnoreCase(FilenameUtils.getExtension(file.getName()))) {
                files.add(FilenameUtils.getBaseName(file.getName()));
            }
        });
        return files;
    }

    public <T> List<T> loadObjects(Class<T> objectClass)
    {
        final List<T> toReturn = new ArrayList<>();

        File[] files = mainFolder.listFiles();

        for (File file : files)
        {
            toReturn.add(loadObject(file.getName(), objectClass, true));
        }

        toReturn.removeIf(Objects::isNull);

        return toReturn;
    }

    public <T> T loadObject(String fileName, Class<T> objectClass, boolean hasExtension)
    {
        final File objectFile = new File(mainFolder, fileName + (!hasExtension ? ".json" : ""));

        if (!objectFile.exists()) return null;

        try {
            JsonReader jsonReader = new JsonReader(new FileReader(objectFile));
            T object = gson.fromJson(jsonReader, objectClass);
            jsonReader.close();
            return object;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean isFirstTime() {
        return isFirstTime;
    }
}
