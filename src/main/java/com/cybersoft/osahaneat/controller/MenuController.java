package com.cybersoft.osahaneat.controller;

import com.cybersoft.osahaneat.payload.ResponseData;
import com.cybersoft.osahaneat.service.imp.FileStorgeServiceImp;
import com.cybersoft.osahaneat.service.imp.MenuServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/menu")
public class MenuController {
    // add menu cho phep tạo ra menu mới và upload hình ảnh của menu này
    // lấy ds menu và hình ảnh menu
    @Autowired
    MenuServiceImp menuServiceImp;

    @Autowired
    FileStorgeServiceImp fileStorgeServiceImp;
    // form-data: stream tiết kiệm bộ nhớ
    // request body chuyển file về base64 -> sẽ bị x1.5 dung lượng file
    // lưu ở database file chuyển base64, file ở dạng byte -> không khuyến khích
    //  lưu ở ổ đĩa file upload lưu vào ổ đĩa cứng --> luôn sử dụng

    @PostMapping("")
    public ResponseEntity<?> addMenu(@RequestParam MultipartFile file, @RequestParam String name,
                                     @RequestParam String description, @RequestParam int price,
                                     @RequestParam String instruction, @RequestParam int cate_res_id) {
        //System.out.println("kiểm tra " + file.getOriginalFilename());
        ResponseData responseData = new ResponseData();
        boolean isSuccess = menuServiceImp.insertFood(file, name, description, price, instruction, cate_res_id);
        responseData.setData(isSuccess);

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllMenu() {
        ResponseData responseData = new ResponseData();
        responseData.setData(menuServiceImp.getAllFood());

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }


    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource resource = (Resource) fileStorgeServiceImp.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }

}
