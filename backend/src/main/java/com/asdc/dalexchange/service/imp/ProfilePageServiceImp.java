package com.asdc.dalexchange.service.imp;
import com.asdc.dalexchange.dto.*;
import com.asdc.dalexchange.model.*;
import com.asdc.dalexchange.repository.UserRepository;
import com.asdc.dalexchange.service.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProfilePageServiceImp implements ProfilePageService {


    @Autowired
    public ProductWishlistService productWishlistService;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public ProductRatingService productRatingService;

    @Autowired
    public SoldItemService soldItemService;

    @Autowired
    public ModelMapper modelMapper;

    @Override
    public ProfilePageDTO ProfileDetails(Long userid) {
        User user = userRepository.findById(userid).orElseThrow(() -> new RuntimeException("User not found"));
        ProfilePageDTO profilePageDTO = new ProfilePageDTO();
        modelMapper.map(user, profilePageDTO);
        return profilePageDTO;
    }

    @Override
    public List<SavedProductDTO> GetAllsavedProduct(Long userid) {
        List<Product> allSavedProduct = productWishlistService.getProductIdsByUserId(userid);

        // Custom ModelMapper for this method only
        ModelMapper customMapper = new ModelMapper();
        customMapper.addMappings(new PropertyMap<Product, SavedProductDTO>() {
            @Override
            protected void configure() {
                map().setTitle(source.getTitle());
                map().setPrice(source.getPrice());
                map().setCategory(source.getCategory().getName());
                map().setProductCondition(source.getProductCondition());
                map().setUseDuration(source.getUseDuration());
                map().setQuantityAvailable(source.getQuantityAvailable());
            }
        });

        return allSavedProduct.stream()
                .map(product -> customMapper.map(product, SavedProductDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public List<SoldItemDTO> GetallSoldProduct(Long userid) {
        List<SoldItem> allSoldItem = soldItemService.getSoldItemsBySellerId(userid);

        // Custom ModelMapper for this method only
        ModelMapper customMapper = new ModelMapper();
        customMapper.addMappings(new PropertyMap<SoldItem, SoldItemDTO>() {
            @Override
            protected void configure() {
                map().setTitle(source.getProduct().getTitle());
                map().setPrice(source.getProduct().getPrice());
            }
        });

        return allSoldItem.stream()
                .map(soldItem -> customMapper.map(soldItem, SoldItemDTO.class))
                .collect(Collectors.toList());
    }


}
