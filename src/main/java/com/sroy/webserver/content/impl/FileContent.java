package com.sroy.webserver.content.impl;

import com.sroy.webserver.content.Content;
import com.sroy.webserver.exception.InternalServerErrorContentException;
import com.sroy.webserver.exception.NotFoundContentException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by shamalroy
 */
public class FileContent implements Content {
    private static final Logger LOGGER = Logger.getLogger(FileContent.class.getName());
    private String[] docRoots;
    private String path;

    public FileContent(String[] docRoots) {
        this.docRoots = docRoots;
    }

    public String[] getDocRoots() {
        return docRoots;
    }

    public void setDocRoots(String[] docRoots) {
        this.docRoots = docRoots;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public byte[] loadContent() throws InternalServerErrorContentException, NotFoundContentException {
        byte[] data;
        try {
            Path filePath = null;
            boolean filePathExist = false;
            for (String docRoot : docRoots) {
                filePath = Paths.get(docRoot + getPath());
                if (Files.exists(filePath)) {
                    filePathExist = true;
                    break;
                }
            }

            if (filePathExist) {
                data = Files.readAllBytes(filePath);
            } else {
                throw new NotFoundContentException("File Not Found");
            }
        } catch (NotFoundContentException e) {
            LOGGER.log(Level.WARNING, "File Not Found : " + getPath());
            throw e;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Internal Server Error : " + getPath(), e);
            throw new InternalServerErrorContentException("Internal Server Error", e);
        }

        return data;
    }
}
