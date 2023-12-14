package com.ntu.edu.group5.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import com.ntu.edu.group5.ecommerce.entity.CartItem;
import com.ntu.edu.group5.ecommerce.entity.Product;
import com.ntu.edu.group5.ecommerce.entity.Seller;
import com.ntu.edu.group5.ecommerce.entity.Category;
import com.ntu.edu.group5.ecommerce.entity.Status;
import com.ntu.edu.group5.ecommerce.exception.CartItemNotFoundException;
import com.ntu.edu.group5.ecommerce.exception.ProductNotFoundException;
import com.ntu.edu.group5.ecommerce.exception.SellerNotFoundException;
import com.ntu.edu.group5.ecommerce.repository.CartItemRepository;
import com.ntu.edu.group5.ecommerce.repository.ProductRepository;
import com.ntu.edu.group5.ecommerce.repository.SellerRepository;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private ProductRepository productRepository;
    private SellerRepository sellerRepository;
    private CartItemRepository cartItemRepository;
    private Seller seller;
    private CartItem cartItem;

    public ProductServiceImpl(ProductRepository productRepository, SellerRepository sellerRepository,
            CartItemRepository cartItemRepository) {
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public ArrayList<Product> searchProducts(String name) {
        List<Product> foundProducts = productRepository.findByName(name);
        return (ArrayList<Product>) foundProducts;
    }

    @Override
    public Product createProduct(Product product) {
        Product newProduct = productRepository.save(product);
        return newProduct;
    }

    //231214 -zj add in
    public Product createProductSetSeller(long sellerId, String name, int quantity, String description, Category category, Status status, double price, String manufacturer ) {
        Product newProduct = new Product(name,quantity,description,category,status,price,manufacturer);
        Seller foundSeller = null;
        try {
            logger.info("🧾🔵 finding foundSeller ... " + sellerId);
            foundSeller =  sellerRepository.findById(sellerId).orElseThrow(()-> new SellerNotFoundException(sellerId));;
        } catch (Exception e){
            logger.error("🧾🔴 Error finding foundSeller "+ e);
        }
        newProduct.setSeller(foundSeller);
        productRepository.save(newProduct);
        return newProduct;
    }

    @Override
    public ArrayList<Product> getAllProducts() {
        List<Product> allProducts = productRepository.findAll();
        return (ArrayList<Product>) allProducts;
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Product productToUpdate = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        // update the product retrieved fromn the database
        productToUpdate.setName(product.getName());
        productToUpdate.setQuantity(product.getQuantity());
        productToUpdate.setDescription(product.getDescription());
        productToUpdate.setCategory(product.getCategory());
        productToUpdate.setStatus(product.getStatus());
        productToUpdate.setPrice(product.getPrice());
        productToUpdate.setManufacturer(product.getManufacturer());
        productToUpdate.setSeller(product.getSeller());
        // Optional<Seller> seller =
        // sellerRepository.findById(product.getSeller().getId());
        // if (seller.isPresent())
        // productToUpdate.setSeller(seller.get());

        // save the updated product back to the database
        return productRepository.save(productToUpdate);
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }
}
