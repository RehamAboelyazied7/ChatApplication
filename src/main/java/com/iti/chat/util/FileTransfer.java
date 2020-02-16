package com.iti.chat.util;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileTransfer {
    private static final int BUFFER_SIZE = 1024 * 1024;
    public static void download(String path, RemoteInputStream remoteInputStream) throws IOException {
        InputStream fileData = RemoteInputStreamClient.wrap(remoteInputStream);
        ReadableByteChannel from = Channels.newChannel(fileData);
        ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
        WritableByteChannel to = FileChannel.open(Paths.get(path), StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);
        int bytes = 0;
        long totalBytes = 0;
        while ((bytes = from.read(buffer)) != -1)
        {
            totalBytes += bytes;
            buffer.flip();
            while (buffer.hasRemaining()) {
                to.write(buffer);
            }
            buffer.clear();
        }
        from.close();
        to.close();
        fileData.close();
    }

    public static Image downloadImage(RemoteInputStream remoteInputStream) throws IOException {
        InputStream imageData = RemoteInputStreamClient.wrap(remoteInputStream);
        return new Image(imageData);
    }
}
