package com.asdc.dalexchange.service.imp;
import com.asdc.dalexchange.dto.*;
import com.asdc.dalexchange.model.*;
import com.asdc.dalexchange.repository.OrderRepository;
import com.asdc.dalexchange.repository.UserRepository;
import com.asdc.dalexchange.service.*;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProfilePageServiceImp implements ProfilePageService {


    @Autowired
    public ProductWishlistService productWishlistService;

    @Autowired
    public OrderRepository orderRepository;

    @Autowired
    public OrderService orderService;

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

    @Override
    public List<PurchaseProductDTO> GetallPurchasedProduct(Long userid) {
        List<OrderDetails> orderDetailsList = orderService.getOrdersByUserId(userid);

        // Custom ModelMapper for this method only
        ModelMapper customMapper = new ModelMapper();
        customMapper.addMappings(new PropertyMap<OrderDetails, PurchaseProductDTO>() {
            @Override
            protected void configure() {
                map().setTitle(source.getProductId().getTitle());
                map().setCategory(source.getProductId().getCategory().getName());
            }
        });

        return orderDetailsList.stream()
                .map(orderDetail -> customMapper.map(orderDetail, PurchaseProductDTO.class))
                .collect(Collectors.toList());

    }


    @Override
    public List<ProductRatingDTO> GetAllProductRating(Long userid) {
        List<ProductRating> allProductRating = productRatingService.getProductRatingsByUserId(userid);

        // Custom ModelMapper for this method only
        ModelMapper customMapper = new ModelMapper();
        customMapper.addMappings(new PropertyMap<ProductRating, ProductRatingDTO>() {
            @Override
            protected void configure() {
                map().setTitle(source.getProduct().getTitle());
            }
        });

        return allProductRating.stream()
                .map(productRating -> customMapper.map(productRating, ProductRatingDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public EditProfileDTO editUserDetails(Long userId, EditProfileDTO editProfileDTO) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(editProfileDTO.getUsername());
            user.setPassword(editProfileDTO.getPassword());
            user.setEmail(editProfileDTO.getEmail());
            user.setPhoneNo(editProfileDTO.getPhoneNo());
            user.setFullName(editProfileDTO.getFullName());
            user.setProfilePicture(editProfileDTO.getProfilePicture());
            user.setBio(editProfileDTO.getBio());
            userRepository.save(user);
            return new EditProfileDTO(
                    user.getUsername(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getPhoneNo(),
                    user.getFullName(),
                    user.getProfilePicture(),
                    user.getBio()
            );
        } else {
            throw new RuntimeException("User not found with id " + userId);
        }
    }

    @Override
    public EditProfileDTO editGetUserDetails(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return modelMapper.map(user, EditProfileDTO.class);
    }
}
