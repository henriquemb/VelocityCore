package com.github.henriquemb.velocitycore.util;

import com.github.henriquemb.velocitycore.VelocityCore;
import lombok.NonNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.nio.file.Files;
import java.nio.file.Path;

public class FileConfig {
    private static final Path dataDirectory = VelocityCore.getDataDirectory();
    private CommentedConfigurationNode node;
    private final String fileName;
    private Path filePath;

    public FileConfig(@NonNull String name) {
        this.fileName = name;
        createFile(name);
        load();
    }

    public void load() {
        try {
            node = getLoader().load();
        } catch (Exception e) {
            VelocityCore.getLogger().error("Failed to load configuration file: {}", getFileName(), e);
        }
    }

    public void save() {
        try {
            getLoader().save(node);
        } catch (Exception e) {
            VelocityCore.getLogger().error("Failed to save configuration file: {}", getFileName(), e);
        }
    }

    public void clear() {
        YamlConfigurationLoader loader = getLoader();

        try {
            CommentedConfigurationNode node = loader.createNode();
            loader.save(node);
        } catch (Exception e) {
            VelocityCore.getLogger().error("Failed to clear configuration file: {}", fileName, e);
        }
    }

    public void set(@NonNull String path, @NonNull Object value) {
        try {
            node.node(parsePath(path)).set(value);
            save();
        } catch (Exception e) {
            VelocityCore.getLogger().error("Failed to set value in configuration file: {}", getFileName(), e);
        }
    }

    public <T> T get(@NonNull Class<T> type, @NonNull String path) {
        try {
            return node.node(parsePath(path)).get(type);
        } catch (Exception e) {
            VelocityCore.getLogger().error("Failed to get value from configuration file: {}", getFileName(), e);
            return null;
        }
    }

    public boolean contains(@NonNull String path) {
        try {
            return !node.node(parsePath(path)).virtual();
        } catch (Exception e) {
            VelocityCore.getLogger().error("Failed to check if path exists in configuration file: {}", getFileName(), e);
            return false;
        }
    }

    public void reload() {
        load();
        save();
    }

    private YamlConfigurationLoader getLoader() {
        return YamlConfigurationLoader.builder()
                .path(filePath)
                .nodeStyle(NodeStyle.BLOCK)
                .build();
    }

    private void createFile(@NonNull String name) {
        createDirectory(dataDirectory.toString());

        filePath = dataDirectory.resolve(getFileName());

        if (Files.notExists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (Exception e) {
                VelocityCore.getLogger().error("Failed to create file: {}", filePath, e);
            }
        }
    }

    private void createDirectory(@NonNull String path) {
        Path dataDirectory = Path.of(path);

        if (Files.notExists(dataDirectory)) {
            try {
                Files.createDirectories(dataDirectory);
            } catch (Exception e) {
                VelocityCore.getLogger().error("Failed to create data directory: {}", dataDirectory, e);
            }
        }
    }

    private String getFileName() {
        return fileName + ".yml";
    }

    private String[] parsePath(@NonNull String path) {
        return path.split("\\.");
    }
}
