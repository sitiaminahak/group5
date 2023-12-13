package com.ntu.edu.group5.ecommerce;

import org.springframework.stereotype.Component;

import com.ntu.edu.group5.ecommerce.entity.Cart;
import com.ntu.edu.group5.ecommerce.entity.CartItem;
import com.ntu.edu.group5.ecommerce.entity.Category;
import com.ntu.edu.group5.ecommerce.entity.Customer;
import com.ntu.edu.group5.ecommerce.entity.Product;
import com.ntu.edu.group5.ecommerce.entity.Seller;
import com.ntu.edu.group5.ecommerce.entity.Status;
import com.ntu.edu.group5.ecommerce.repository.CartItemRepository;
import com.ntu.edu.group5.ecommerce.repository.CartRepository;
import com.ntu.edu.group5.ecommerce.repository.CustomerRepository;
import com.ntu.edu.group5.ecommerce.repository.OrderRepository;
import com.ntu.edu.group5.ecommerce.repository.ProductRepository;
import com.ntu.edu.group5.ecommerce.repository.SellerRepository;
import com.ntu.edu.group5.ecommerce.service.CartItemService;
import com.ntu.edu.group5.ecommerce.service.CartItemServiceImpl;
import com.ntu.edu.group5.ecommerce.service.CartServiceImpl;
import com.ntu.edu.group5.ecommerce.service.OrderService;
import com.ntu.edu.group5.ecommerce.service.OrderServiceImpl;

import jakarta.annotation.PostConstruct;

@Component
public class DataLoaderJian {
        private CustomerRepository customerRepository;
        private ProductRepository productRepository;
        private CartRepository cartRepository;
        private SellerRepository sellerRepository;
        private CartItemRepository cartItemRepository;
        private OrderRepository orderRepository;
        private OrderServiceImpl orderServiceImpl;
        private CartItemServiceImpl cartItemServiceImpl;
        private CartServiceImpl cartServiceImpl;

        public DataLoaderJian(CustomerRepository customerRepository, ProductRepository productRepository,
                        CartRepository cartRepository, SellerRepository sellerRepository,
                        CartItemRepository cartItemRepository,
                        OrderRepository orderRepository,
                        OrderServiceImpl orderServiceImpl,
                        CartItemServiceImpl cartItemServiceImpl,
                        CartServiceImpl cartServiceImpl){
                this.customerRepository = customerRepository;
                this.productRepository = productRepository;
                this.cartRepository = cartRepository;
                this.sellerRepository = sellerRepository;
                this.cartItemRepository = cartItemRepository;
                this.orderRepository = orderRepository;
                this.orderServiceImpl = orderServiceImpl;
                this.cartItemServiceImpl=cartItemServiceImpl;
                this.cartServiceImpl =cartServiceImpl;
        }

        @PostConstruct
        public void loadData() {
                // clear the database first
                customerRepository.deleteAll();
                productRepository.deleteAll();

                // load data here
                // [Activity 2 - validation]
                customerRepository.save(new Customer("Catherine", "Tiong", "12345678", 2000));
                customerRepository.save(new Customer("Siti ","Aminah", "56771234", 2001));
                customerRepository.save(new Customer("Sariha", "IDK", "12783456", 2002));
                customerRepository.save(new Customer("Sara", "Saranya", "56781234", 2003));
                customerRepository.save(new Customer("ZJ", "Lee", "56234781", 2004));
                customerRepository.save(new Customer("Sam", "Altman", "88888888", 2000));
                customerRepository.save(new Customer("Mark ","Zuckerberg", "11111111", 1990));
                customerRepository.save(new Customer("Linus", "Torvald", "22222222", 1991));
                customerRepository.save(new Customer("Elon", "Musk", "3333333", 1992));
                customerRepository.save(new Customer("Andrew", "Ng", "11111111", 1993));


                // productRepository 1-10
                productRepository.save(
                                new Product("Ipad", 1, "Technological Product from Apple", Category.ELECTRONICS,
                                Status.ACTIVE, 900.99, "Apple Inc"));
                productRepository.save(
                                new Product("Apple Pen", 2, "Technological stylus from Apple", Category.ELECTRONICS,
                                Status.ACTIVE, 98.99, "Apple Inc"));
                productRepository.save(new Product("Sumsung Galaxy watch", 1, "Technological Product from Samsung",
                                Category.ELECTRONICS, Status.ACTIVE, 499.0,
                                "Samsung Electronics"));
                productRepository.save(new Product("Ideapad Slim 5", 1, "Laptop",
                                Category.ELECTRONICS, Status.ACTIVE, 1200.0,
                                "Lenovo"));
                productRepository.save(new Product("Surface Pro", 1, "Tablet PC",
                                Category.ELECTRONICS, Status.ACTIVE, 1600.0,
                                "MiloSoft"));
                productRepository.save(new Product("Macbook", 1, "Apple product",
                                Category.ELECTRONICS, Status.ACTIVE, 1900.0,
                                "Ping Guo Inc"));
                productRepository.save(new Product("Macbook Pro", 1, "Apple product",
                                Category.ELECTRONICS, Status.ACTIVE, 3700.0,
                                "Ping Guo Inc"));
                productRepository.save(new Product("Samsung Fold", 1, "Android Folding Phone",
                                Category.ELECTRONICS, Status.ACTIVE, 2200.0,
                                "Somesong"));
                productRepository.save(new Product("Iphone 15 Max", 1, "Apple Phone",
                                Category.ELECTRONICS, Status.ACTIVE, 2300.0,
                                "Ping Guo Inc"));
                productRepository.save(new Product("Google Pixel", 1, "Android Phone",
                                Category.ELECTRONICS, Status.ACTIVE, 1800.0,
                                "Googol"));
                // product 11 to 20
                productRepository.save(
                                new Product("Ipad 9", 1, "Technological Product from Apple", Category.ELECTRONICS,
                                Status.ACTIVE, 1000.99, "Apple Inc"));
                productRepository.save(
                                new Product("Apple Pen 5", 2, "Technological stylus from Apple", Category.ELECTRONICS,
                                Status.ACTIVE, 200.99, "Apple Inc"));
                productRepository.save(new Product("Sumsung Galaxy watch 5", 1, "Technological Product from Samsung",
                                Category.ELECTRONICS, Status.ACTIVE, 599.0,
                                "Samsung Electronics"));
                productRepository.save(new Product("Ideapad Slim 6", 1, "Laptop",
                                Category.ELECTRONICS, Status.ACTIVE, 1300.0,
                                "Lenovo"));
                productRepository.save(new Product("Surface Pro 10", 1, "Tablet PC",
                                Category.ELECTRONICS, Status.ACTIVE, 1900.0,
                                "MiloSoft"));
                productRepository.save(new Product("Macbook 10", 1, "Apple product",
                                Category.ELECTRONICS, Status.ACTIVE, 1800.0,
                                "Ping Guo Inc"));
                productRepository.save(new Product("Macbook Pro 10", 1, "Apple product",
                                Category.ELECTRONICS, Status.ACTIVE, 3900.0,
                                "Ping Guo Inc"));
                productRepository.save(new Product("Samsung Fold 2", 1, "Android Folding Phone",
                                Category.ELECTRONICS, Status.ACTIVE, 2200.0,
                                "Somesong"));
                productRepository.save(new Product("Iphone 20 Max", 1, "Apple Phone",
                                Category.ELECTRONICS, Status.ACTIVE, 2500.0,
                                "Ping Guo Inc"));
                productRepository.save(new Product("Google Pixel 10", 1, "Android Phone",
                                Category.ELECTRONICS, Status.ACTIVE, 2000.0,
                                "Googol"));

                // create orders
                orderServiceImpl.createOrder(1);
                orderServiceImpl.createOrder(2);
                orderServiceImpl.createOrder(3);
                orderServiceImpl.createOrder(4);
                orderServiceImpl.createOrder(5);
                orderServiceImpl.createOrder(6);
                orderServiceImpl.createOrder(7);
                orderServiceImpl.createOrder(8);
                orderServiceImpl.createOrder(9);
                orderServiceImpl.createOrder(10);

                // IMPORTANT NOTE!!!!
                /*231212 meeting with Wilmond**** need to create Cart first, and then only create cart item !!!!!!! cart id is inside cart item ****
                Follow the hierachy - create the master first (no entity dependency), and then the slave (the one with dependency, it holds the key)
                *231313  this works!! will follow.
                */

                // cartRepository
                cartServiceImpl.createCartById(1);
                cartServiceImpl.createCartById(2);
                cartServiceImpl.createCartById(3);
                cartServiceImpl.createCartById(4);
                cartServiceImpl.createCartById(5);
                cartServiceImpl.createCartById(6);
                cartServiceImpl.createCartById(7);
                cartServiceImpl.createCartById(8);
                cartServiceImpl.createCartById(9);
                cartServiceImpl.createCartById(10);

                // cartItemRepository
                cartItemServiceImpl.createCartItem(1,1,1,11);
                cartItemServiceImpl.createCartItem(2,1,2,22);
                cartItemServiceImpl.createCartItem(3,1,3,33);
                cartItemServiceImpl.createCartItem(4,1,4,44);
                cartItemServiceImpl.createCartItem(5,2,5,55);
                cartItemServiceImpl.createCartItem(6,2,6,66);
                cartItemServiceImpl.createCartItem(7,2,7,77);
                cartItemServiceImpl.createCartItem(8,3,8,88);
                cartItemServiceImpl.createCartItem(9,3,9,99);
                cartItemServiceImpl.createCartItem(10,3,10,100);

                // cartRepository.save(new Cart(14.55));
                // cartRepository.save(new Cart(5.84));

                // SellerRepository
                // sellerRepository.save(new Seller("Sally", "Wong", "67912380"));
                // sellerRepository.save(new Seller("Martin", "Goh", "67944321"));
                // sellerRepository.save(new Seller("Daniel", "Tan", "67988457"));

        }
}
