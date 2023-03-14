package com.cybersoft.osahaneat.service;

import com.cybersoft.osahaneat.service.imp.FileStorgeServiceImp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

@Service
public class FileStoregeService implements FileStorgeServiceImp {
    @Value("${upload.path}")
    private String path;

    private Path root ;

    @Override
    public boolean saveFile(MultipartFile file) {
        //resole => /upload
        try {
            init();
            Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);

         //   file.getInputStream().close();
            return true;
        } catch (IOException e) {
            System.out.println("lỗi save file: " + e.getMessage());
        }
        return false;
    }

    private void init() {
        try {
            root = Paths.get(path);
            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }

        } catch (Exception e) {
            System.out.println("error create folder: " + e.getMessage());
        }
    }

    @Override
    public Resource load(String fileName) {
        try {
            Path file = root.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                 return resource;
            }else {
                return null ;
            }
        } catch (Exception e) {
            System.out.println("lỗi load file: " +   e.getMessage());
            return null;
        }
    }
}
