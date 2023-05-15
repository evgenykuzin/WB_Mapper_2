package ru.jekajops.wbtablemapper.util;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class FileManager {
    public synchronized static File getFromResources(String name) {
        var str = String.format("%s/%s", getResourcesDir().getAbsolutePath(), name);
        return new File(str);
    }

    public synchronized static File getOrCreateIfNotExist(String name, String suffix, boolean deleteOnExit) {
        File file;
        file = getFromResources(name+suffix);
        if (!file.exists()) {
            try {
                if (name.length() < 3) {
                    name = name + "___";
                }
                file = File.createTempFile(name, suffix, getResourcesDir());
                if (deleteOnExit) {
                    file.deleteOnExit();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public synchronized static File getOrCreateIfNotExist(String name, String suffix) {
        return getOrCreateIfNotExist(name, suffix, true);
    }

    public synchronized static boolean containsInResources(String name) {
        var res = getFromResources("");
        var resFiles = res.listFiles();
        if ((resFiles != null ? resFiles.length : 0) > 0) {
            for (File file : resFiles) {
                if (file.getName().contains(name)) return true;
            }
        }
        return false;
    }

    public synchronized static void save(InputStream is, String fileName, String suffix) throws IOException {
        Path path = getOrCreateIfNotExist(fileName, suffix).toPath();
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        LinkedList<String> lines = new LinkedList<>();
        String readLine;
        while ((readLine = in.readLine()) != null) {
            lines.add(readLine);
        }
        Files.write(path, lines, StandardCharsets.UTF_8, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    public synchronized static void save(InputStream is, String fileNameFull) throws IOException {
        String[] split = fileNameFull.split("\\.");
        save(is, split[0], String.format(".%s", split[1]));
    }

    public synchronized static File download(String urlString, String fileName, String suffix) throws IOException {
        Path path = Files.createTempFile(fileName, suffix);
        return download(urlString, path.toFile());
    }

    public synchronized static File download(String urlString, File file) throws IOException {
        URL url = new URL(urlString);
        ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        return file;
    }

    public synchronized static List<String> readFile(File file) {
        try {
            return Files.readAllLines(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public synchronized static void writeToFile(File file, String text) {
        try {
            Files.writeString(file.toPath(), text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void writeNextToFile(File file, String string) {
        try (FileOutputStream fos = new FileOutputStream(file, true)) {
            var line = string.contains("\n") ? string : string + "\n";
            fos.write(line.getBytes(StandardCharsets.UTF_8));
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized static File getResourcesDir() {
        var path = String.format("%s/src/main/resources", System.getProperty("user.dir"));
        return new File(path);
    }
}
