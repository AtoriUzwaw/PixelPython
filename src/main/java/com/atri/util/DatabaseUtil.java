package com.atri.util;

import lombok.extern.java.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Log
public class DatabaseUtil {

    private static final String DB_FOLDER_NAME = "db";             // 数据库文件夹名
    private static final String DB_FILE_NAME = "pixel_python.db";  // 数据库文件名

    /**
     * 获取数据库文件的路径。如果文件夹不存在，则会自动创建。
     */
    public static Path getDatabaseFilePath() throws IOException {
        // 获取 JAR 文件所在的目录
        Path jarDir = null;
        try {
            jarDir = Paths.get(DatabaseUtil.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
        } catch (URISyntaxException e) {
            log.warning(e.getMessage());
        }

        @SuppressWarnings("all")
        // 获取目标文件夹路径（数据库存放路径）
        Path dbFolderPath = jarDir.resolve(DB_FOLDER_NAME);

        // 如果文件夹不存在，则创建它
        if (!Files.exists(dbFolderPath)) {
            Files.createDirectories(dbFolderPath);
            System.out.println("数据库文件夹已创建: " + dbFolderPath.toAbsolutePath());
        }

        // 获取数据库文件路径
        Path dbFilePath = dbFolderPath.resolve(DB_FILE_NAME);

        // 如果数据库文件不存在，则从资源文件中复制到目标文件夹
        if (!Files.exists(dbFilePath)) {
            try (InputStream dbInputStream = DatabaseUtil.class.getResourceAsStream("/" + DB_FILE_NAME)) {
                if (dbInputStream == null) {
                    throw new IOException("无法找到源数据库文件: " + DB_FILE_NAME);
                }
                // 将资源文件内容复制到目标路径
                Files.copy(dbInputStream, dbFilePath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("数据库文件已复制到: " + dbFilePath.toAbsolutePath());
            }
        } else {
            System.out.println("数据库文件位于: " + dbFilePath.toAbsolutePath());
        }

        return dbFilePath;
    }
}

