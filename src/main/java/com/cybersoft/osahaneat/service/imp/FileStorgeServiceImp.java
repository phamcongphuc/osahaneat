package com.cybersoft.osahaneat.service.imp;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorgeServiceImp {
    boolean saveFile(MultipartFile file);
    Resource load(String fileName);
}
