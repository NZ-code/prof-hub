package pl.zenev.profhub.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import lombok.NoArgsConstructor;

import java.io.*;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class FileService {

    public void writeBytesToFile(String filePath, byte[] data){

        try(FileOutputStream stream = new FileOutputStream(filePath)){
            stream.write(data);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public byte[] readBytesFromFile(String filePath){

        try(FileInputStream stream = new FileInputStream(filePath)){
            return stream.readAllBytes();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteImage(String fileName) {
        File myObj = new File(fileName);
        if (myObj.delete()) {
            System.out.println("Deleted the file: " + myObj.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }
    }
}
