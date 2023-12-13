package com.ntu.edu.group5.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ntu.edu.group5.ecommerce.entity.*;
import com.ntu.edu.group5.ecommerce.repository.*;



@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    private CustomerRepository customerRepo;
    private OrderRepository orderRepo;
    private CartItemRepository cartItemRepo;
    //a dummy product repo only for Order testing purpose. not to be confused with ProductRepo
    private ProductRepository productRepo;

    @Autowired
    public OrderServiceImpl(CustomerRepository customerRepo,
        OrderRepository orderRepo ,ProductRepository productRepo,
        CartItemRepository cartItemRepo){
        this.customerRepo = customerRepo;
        this.orderRepo = orderRepo;
        this.cartItemRepo=cartItemRepo;
        this.productRepo = productRepo;
    }

    // @Override
    // public Order createOrder (long customerId, long cartItemId){
    //     Customer foundCustomer = customerRepo.findById(customerId)
    //         .orElseThrow(()-> new RuntimeException("Cannot find customer " + customerId));
    //     System.out.println("🔵(a) POST foundCustomer " + foundCustomer);
    //     CartItem foundCartItem = cartItemRepo.findById(cartItemId)
    //         .orElseThrow(()-> new RuntimeException("Cannot find cartItem " + cartItemId));
    //     System.out.println("🔵(b) POST foundCartItem " + foundCartItem);
    //     Order newOrder = new Order();
    //     newOrder.setOrderingCustomer(foundCustomer);
    //     ArrayList <CartItem> cartItemList = new ArrayList<>();
    //     cartItemList.add(foundCartItem);
    //     System.out.println("🔵(c) POST cartItemList " + cartItemList);
    //     newOrder.setOrderedItems(cartItemList);

    //     try {
    //         orderRepo.save(newOrder);
    //         System.out.println("🔵(d) POST newOrder saved in orderRepo " + newOrder);
    //     }
    //     catch (DataAccessException e){
    //         e.printStackTrace();
    //         logger.error("🔴 Error saving order", e);
    //         throw new RuntimeException("Error saving order",e);
    //     }
    //     return newOrder;
    // }

    // 231211 -1541 - working ok
    @Override
    public Order createOrder (long customerId){

        Customer foundCustomer = null;
        logger.info("🧾🔵 createOrder requested customerId " + customerId);
        try {
            foundCustomer = customerRepo.findById(customerId)
                .orElseThrow(()-> new RuntimeException("Cannot find customer " + customerId));
            logger.info("🧾🔵 foundCustomer " + foundCustomer);
        }catch (RuntimeException e){
             logger.info(" 🧾🔴 error finding foundCustomer " + foundCustomer + " with error message " + e);
        }

        Order newOrder = new Order();
        newOrder.setOrderingCustomer(foundCustomer);

        // set up an empty ArrayList inside Order class for newly created order
        ArrayList<CartItem> newCartItems = new ArrayList<CartItem>();
        newOrder.setOrderedItems(newCartItems);

        try {
            orderRepo.save(newOrder);
            logger.info("🧾🟢(d) POST newOrder saved in orderRepo " + newOrder);
        }
        catch (Exception e){
            e.printStackTrace();
            logger.error("🧾🔴 Error saving order", e);
            throw new RuntimeException("Error saving order",e);
        }
        return newOrder;
    }

    // Finds an order, finds the List inside that order, add in another CartItem inside list, and then
    // save that List inside the found Order
    // // public List<CartItem> addCartItem(long orderId, long cartItemId){
    // //     Order foundOrder = orderRepo.findById(orderId)
    // //         .orElseThrow(()-> new RuntimeException("Cannot find order !" + orderId){});
    // //     //logger.info("✅(a) foundOrder " + foundOrder);
    // //     CartItem foundCartItem = cartItemRepo.findById(cartItemId)
    // //         .orElseThrow(()-> new RuntimeException("Cannot find cartItem " + cartItemId));
    // //     //logger.info("✅(b) foundCartItem " + foundCartItem);
    // //     List<CartItem> foundCartItemsList = foundOrder.getOrderedItems();
    // //     foundCartItemsList.add(foundCartItem);
    // //     //logger.info("✅(c) foundCartItemsList " + foundCartItemsList);
    // //     foundOrder.setOrderedItems(foundCartItemsList);
    // //     //logger.info("✅(d) foundOrder set " + foundOrder.toString());

    // //     try {
    // //         orderRepo.save(foundOrder);
    // //     }
    // //     catch (DataAccessException e){
    // //         e.printStackTrace();
    // //         logger.error("🔴Error saving order",e));
    // //         throw new RuntimeException("Error saving order",e);
    // //     }
    // //     return foundCartItemsList;

    // }

    // 231211 - 1548 - addCartItem not working, foundOrder = null although order
    // is already in DB. If order is not in DB, not found error willl be displayed
    // instead of null. Refer to *** below
    public List<CartItem> addCartItem(long orderId, long productId, int quantity){
        logger.info("🧾🔵 addCartItem into Order requested orderId " + orderId
            + " productId " + productId + " quantity " + quantity + " .... ");
        //find the order to be updated and then set it to the newCartItem
        Order foundOrder = null;
        try{
            orderRepo.findById(orderId)
            .orElseThrow(()-> new RuntimeException("Cannot find order !" + orderId){});
            logger.info("🧾🔵(a) foundOrder " + foundOrder);
            /*
             * 16:02:42.614 [http-nio-8080-exec-3] WARN  o.h.e.jdbc.spi.SqlExceptionHelper - SQL Error: 0, SQLState: 23502
             * 16:02:42.615 [http-nio-8080-exec-3] ERROR o.h.e.jdbc.spi.SqlExceptionHelper - ERROR: null value in column "order_id" of relation "cart_item" violates not-null constraint
             * Detail: Failing row contains (200, 6, null, 6).
             *
             * 16:02:42.620 [http-nio-8080-exec-3] ERROR c.n.e.g.e.service.OrderServiceImpl - 🧾🔴 unable to save newCartItemorg.springframework.dao.DataIntegrityViolationException: could not execute statement [ERROR: null value in column "order_id" of relation "cart_item" violates not-null constraint
             * Detail: Failing row contains (200, 6, null, 6).] [insert into cart_item (order_id,product_id,quantity) values (?,?,?)]; SQL [insert into cart_item (order_id,product_id,quantity) values (?,?,?)]; constraint [order_id" of relation "cart_item]
             * Jian Note :- after getting this error in PutMapping, i did another GetMapping and can receive info below
             * {
                "orderId": 5,
                "orderDate": "2023-12-11T08:00:24.033+00:00",
                "orderingCustomer": {
                    "id": 2,
                    "firstName": "Siti Aminah",
                    "lastName": "Nurhaliza",
                    "email": null,
                    "contactNo": "56771234",
                    "jobTitle": null,
                    "yearOfBirth": 2001
                },
                "orderedItems": [],
                "orderStatus": "PENDING",
                "total": 0.0
                }
             */
        }catch(RuntimeException e){
            logger.error("🧾🔴 Error finding foundOrder" + e);
        }

        //find the product to update the new cartItem
        Product foundProduct = null;
        try {
            foundProduct = productRepo.findById(productId)
            .orElseThrow(()-> new RuntimeException("Cannot find order !" + orderId){});
            logger.info("🧾🔵(b) foundProduct " + foundProduct);
        }catch (RuntimeException e){
            logger.error("🧾🔴 Error finding foundProduct" + e);
        }

        CartItem  newCartItem = new CartItem();
        newCartItem.setOrder(foundOrder);
        newCartItem.setProduct(foundProduct);
        newCartItem.setCartItemQuantity(quantity);

        //save the newCartItem into cart item repo
        try{
            cartItemRepo.save(newCartItem);
            logger.info("🧾🔵(b2) newCartItem " + newCartItem.toString());
        }
        catch (RuntimeException e){
            logger.error("🧾🔴 unable to save newCartItem" + e);
        }

        // find back the same order after creating a new cart item. the arraylist should have a new cart item inside.
        Order updatedFoundOrder = null;
        try {
            updatedFoundOrder = orderRepo.findById(orderId)
                .orElseThrow(()-> new RuntimeException("Cannot find order !" + orderId){});
            logger.info("🧾🔵(b3) updatedFoundOrder " + updatedFoundOrder.toString());
        }catch (RuntimeException e){
            logger.error("🧾🔴 Error finding updatedFoundOrder" + e);
        }

        ArrayList <CartItem> foundCartItemsList = (ArrayList<CartItem>)updatedFoundOrder.getOrderedItems();

        // if (foundOrder.getOrderedItems() == null){
        //     ArrayList<CartItem> foundCartItemsList = new ArrayList<>();
        //     foundOrder.setOrderedItems(foundCartItemsList);
        // }

        // ArrayList<CartItem> foundCartItemsList = (ArrayList<CartItem>) foundOrder.getOrderedItems();
        // foundCartItemsList.add(newCartItem);
        // newCartItem.setOrder(foundOrder);

        // problem below!
        logger.info("🧾🔵(c) foundCartItemsList " + foundCartItemsList);
        updatedFoundOrder.setOrderedItems(foundCartItemsList);
        logger.info("🧾🔵(d) foundOrder set " + updatedFoundOrder.toString());

        try {
            orderRepo.save(updatedFoundOrder);
            logger.info("🧾🔵( updatedFoundOrder saved into repo " + updatedFoundOrder.toString());
        }
        catch (DataAccessException e){
            e.printStackTrace();
            logger.error("🧾🔴 Error saving order",e);
            throw new RuntimeException("Error saving order",e);
        }
        return foundCartItemsList;

    }

    //231211 - 1553 - tested working
    @Override
    public Order getOrder(long id){
        Order foundOrder = null;
        logger.info("🧾🔵 finding foundOrder ... " + id);
        try{
            foundOrder =orderRepo.findById(id).orElseThrow(()-> new RuntimeException("Order not found"){});
            foundOrder.getOrderingCustomer().getId();
            foundOrder.getOrderedItems();
        }catch (RuntimeException e){
            logger.error("🧾🔴 Error finding order" + id + "error message " + e);
        }
        logger.info("🧾🟢 Success finding foundOrder " + foundOrder.toString());
        return foundOrder;
    }
    //231211 - 1553 - tested working
    @Override
    public ArrayList<Order> getAllOrders(){
        ArrayList<Order> getAllOrders= new ArrayList<>();
        try {
            logger.info("🧾🔵 finding getAllOrders ... ");
            getAllOrders = (ArrayList<Order>) orderRepo.findAll();
        } catch (Exception e){
            logger.error("🧾🔴 Error finding getAllOrders "+ e);
        }
        return getAllOrders;
    }
    // 231211 - 1611 - not working. Cannot find cartItem although it exist in DB. no exceptions thrown.
    // if cartItem is not in DB, will receive RuntimeException as expected.
    @Override
    public Order setOrder(long id, long customerId , long cartItemId){
        Order setOrder = null;
        logger.info("🧾🔵 setOrder request with  orderId "+ id + " customerId " + customerId + " cartItemId " + cartItemId);
        try{
             setOrder = orderRepo.findById(id).orElseThrow(()-> new RuntimeException("Order not found"){});
            logger.info("🧾🔵 found setOrder ... "+ setOrder.toString());
        }catch (RuntimeException e){
            logger.error("🧾🔴 Error finding setOrder " + id + " error message "+ e);
        }

        Customer setCustomer = null;
        try {
            setCustomer = customerRepo.findById(customerId).orElseThrow(()-> new RuntimeException("Customer not found"){});
            logger.info("🧾🔵 found setCustomer ... "+ setCustomer.toString());
        } catch (RuntimeException e){
            logger.error("🧾🔴 Error finding setCustomer " + customerId + " error message "+ e);
        }

        CartItem setCartItem = null;
        try{
            setCartItem= cartItemRepo.findById(cartItemId).orElseThrow(()-> new RuntimeException("CartItem not found"));
            logger.info("🧾🔵 found setCartItem ... "+ setCartItem.toString());
            /*
             * 16:10:35.094 [http-nio-8080-exec-1] ERROR c.n.e.g.e.service.OrderServiceImpl - 🧾🔴 Error finding setCartItem 0 
             * error message java.lang.RuntimeException: CartItem not found
             */
        } catch(RuntimeException e){
            logger.error("🧾🔴 Error finding setCartItem " + cartItemId + " error message "+ e);
        }

        setOrder.setOrderingCustomer(setCustomer);
        List<CartItem> cartItemList = setOrder.getOrderedItems();
        cartItemList.add(setCartItem);
        setOrder.setOrderedItems(cartItemList);

        try{
            setOrder = orderRepo.save(setOrder);
            logger.info("🧾🟢 Success saving setOrder " + setOrder);
        }catch(Exception e){
            logger.error("🧾🔴 Error saving setOrder " + setOrder + " error message "+ e);
        }

        return setOrder;

    }

    //231211 - 1631 - working as expected
    @Override
    public Order deleteOrder(long id){
        Order delOrder = null;
        try{
            delOrder = orderRepo.findById(id).orElseThrow(()-> new RuntimeException("Order not found!"){});
            orderRepo.deleteById(id);
            logger.info("🧾🟢🗑 delOrder " + id);
        }catch(RuntimeException e){
            logger.error("🧾🔴 Error saving deleting delOrder error message "+ e);
        }

        return delOrder;
    }

}
