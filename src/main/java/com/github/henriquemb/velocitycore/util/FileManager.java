package com.github.henriquemb.velocitycore.util;

import lombok.NonNull;

public class FileManager {
    private FileConfig fileConfig = null;

    public FileManager(@NonNull String file) {
        this.fileConfig = new FileConfig(file);
    }

    public void save() {
        try {
            fileConfig.save();
        } catch (Exception e) {
            throw new RuntimeException("Failed to save configuration file", e);
        }
    }

    public void clear() {
        try {
            fileConfig.clear();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clear configuration file", e);
        }
    }

    public void set(String path, Object value) {
        try {
            fileConfig.set(path, value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set value in configuration file", e);
        }
    }

    public String get(String path) {
        try {
            return fileConfig.get(String.class, path);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get value from configuration file", e);
        }
    }

    public boolean contains(String path) {
        try {
            return fileConfig.contains(path);
        } catch (Exception e) {
            throw new RuntimeException("Failed to check if path exists in configuration file", e);
        }
    }

    public void reload() {
        try {
            fileConfig.reload();
        } catch (Exception e) {
            throw new RuntimeException("Failed to reload configuration file", e);
        }
    }
}
