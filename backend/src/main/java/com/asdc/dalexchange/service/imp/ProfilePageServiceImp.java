package com.asdc.dalexchange.service.imp;
import com.asdc.dalexchange.dto.*;
import com.asdc.dalexchange.mappers.impl.*;
import com.asdc.dalexchange.model.*;
import com.asdc.dalexchange.repository.*;
import com.asdc.dalexchange.service.*;
import com.asdc.dalexchange.specification.ProductWishlistSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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
    public ProductRepository productRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public ProductRatingService productRatingService;

    @Autowired
    public SoldItemService soldItemService;

    @Autowired
    public ProductWishlistRepository productWishlistRepository;

    @Autowired
    private ProductRatingRepository productRatingRepository;


    @Autowired
    private PurchaseProductMapperImpl purchaseProductMapper;


    @Autowired
    private SavedProductMapperImpl savedProductMapper;

    @Autowired
    private SoldItemMapperImpl soldItemMapper;

    @Autowired
    private ProfilePageMapperImpl profilePageMapper;

    @Autowired
    private ProductRatingMapperImpl productRatingMapper;

    @Autowired
    private EditProfileMapperImpl editProfileMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProfilePageDTO ProfileDetails(Long userid) {
        User user = userRepository.findById(userid).orElseThrow(() -> new RuntimeException("User not found"));
        return profilePageMapper.mapTo(user);
    }

   /* @Override
    public List<SavedProductDTO> GetAllsavedProduct(Long userid) {
        List<Product> allSavedProduct = productWishlistService.getProductIdsByUserId(userid);
        return allSavedProduct.stream()
                .map(savedProductMapper::mapTo)
                .collect(Collectors.toList());
    }*/

    @Override
    public List<SavedProductDTO> GetAllsavedProduct(Long userId) {
        Specification<ProductWishlist> spec = ProductWishlistSpecification.byUserId(userId);
        List<ProductWishlist> allWishlistedProducts = productWishlistRepository.findAll(spec);

        List<Long> productIds = allWishlistedProducts.stream()
                .map(ProductWishlist::getProductId)
                .map(Product::getProductId)
                .collect(Collectors.toList());

        List<Product> allSavedProducts = productRepository.findByProductIdIn(productIds);

        return allSavedProducts.stream()
                .map(savedProductMapper::mapTo)
                .collect(Collectors.toList());
    }

/*
    @Override
    public List<SoldItemDTO> GetallSoldProduct(Long userid) {
        List<SoldItem> allSoldItems = soldItemService.getSoldItemsBySellerId(userid);

        return allSoldItems.stream()
                .map(soldItemMapper::mapTo)
                .collect(Collectors.toList());
    }*/


    @Override
    public List<PurchaseProductDTO> GetallPurchasedProduct(Long userid) {
        List<OrderDetails> orderDetailsList = orderRepository.findByBuyerUserId(userid);
        return orderDetailsList.stream()
                .map(purchaseProductMapper::mapTo)
                .collect(Collectors.toList());

    }



    @Override
    public List<ProductRatingDTO> GetAllProductRating(Long userid) {
        List<ProductRating> allProductRatings = productRatingRepository.findByIdUserId(userid);
        return allProductRatings.stream()
                .map(productRatingMapper::mapTo)
                .collect(Collectors.toList());
    }


    @Override
    public EditProfileDTO editUserDetails(Long userId, EditProfileDTO editProfileDTO) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            modelMapper.map(editProfileDTO, user);
            userRepository.save(user);
            return editProfileMapper.mapTo(user);
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
