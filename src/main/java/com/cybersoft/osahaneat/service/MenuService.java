package com.cybersoft.osahaneat.service;

import com.cybersoft.osahaneat.dto.FoodDTO;
import com.cybersoft.osahaneat.entity.CategoryRestaurant;
import com.cybersoft.osahaneat.entity.Food;
import com.cybersoft.osahaneat.repository.FoodRepository;
import com.cybersoft.osahaneat.service.imp.FileStorgeServiceImp;
import com.cybersoft.osahaneat.service.imp.MenuServiceImp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService implements MenuServiceImp {
    @Autowired
    FoodRepository foodRepository;

    @Autowired
    FileStorgeServiceImp fileStorgeServiceImp;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public boolean insertFood(MultipartFile file, String name, String description,
                              double price, String instruction, int cate_res_id) {
        boolean isInsertSuccess = false;
        boolean isSuccess = fileStorgeServiceImp.saveFile(file);
        if (isSuccess) {
            // lưu dũ liệu khi file lưu thành công
            try {
                Food food = new Food();
                food.setName(name);
                food.setDescription(description);
                food.setPrice(price);
                food.setIntruction(instruction);
                food.setImage(file.getOriginalFilename());

                CategoryRestaurant categoryRestaurant = new CategoryRestaurant();
                categoryRestaurant.setId(cate_res_id);

                food.setCategoryRestaurant(categoryRestaurant);

                foodRepository.save(food);
                isInsertSuccess = true;
            } catch (Exception e) {
                System.out.println("lưu food thất bại" + e.getMessage());
            }
        }
//        System.out.println("lưu hình thất bại");
        return isInsertSuccess;
    }

    @Override
    //   @Cacheable("food")
    public List<FoodDTO> getAllFood() {
        List<Food> list;
        List<FoodDTO> dtoList = new ArrayList<>();

        Gson gson = new Gson();
        String data = (String) redisTemplate.opsForValue().get("foods");

        if (data == null) {
            list = foodRepository.findAll();

            for (Food food : list) {
                FoodDTO foodDTO = new FoodDTO();
                foodDTO.setName(food.getName());
                foodDTO.setImage(food.getImage());
                foodDTO.setDesc(food.getDescription());

                dtoList.add(foodDTO);
            }

            redisTemplate.opsForValue().set("foods", gson.toJson(dtoList));
        } else {
            dtoList = gson.fromJson(data, new TypeToken<List<FoodDTO>>() {
            }.getType());
        }
//        System.out.println("check list: " + data);
        return dtoList;
    }
}
