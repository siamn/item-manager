package uk.ac.ucl.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Uploader implements Serializable
{

    String upload(HttpServletRequest request) throws ServletException, IOException
    {
        Part filePart = request.getPart("file");
        InputStream stream = filePart.getInputStream();
        String path = "images/" + filePart.getSubmittedFileName();
        String fullPath = request.getServletContext().getRealPath("") + path;
        File file = new File(fullPath);
        Files.copy(stream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        System.out.println("File was uploaded successfully");
        return path;
    }
}