package org.example.teatransport.service;

import lombok.RequiredArgsConstructor;
import org.example.teatransport.dto.OrderRequestDTO;
import org.example.teatransport.dto.SellsDTO;
import org.example.teatransport.entity.OrderReqDetails;
import org.example.teatransport.entity.Product;
import org.example.teatransport.entity.SellProduct;
import org.example.teatransport.entity.User;
import org.example.teatransport.repository.OrderReqRipocitory;
import org.example.teatransport.repository.SellOrderRipo;
import org.example.teatransport.repository.SellRipocitory;
import org.example.teatransport.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SellService {

    @Autowired
    private final SellOrderRipo sellRipocitory;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final SellRipocitory productRipocitory;

    @Autowired
    private final OrderReqRipocitory orderReqRipocitory;


    @Transactional
    public void createOrder(User user, OrderRequestDTO orderRequestDTO) {

        OrderReqDetails orderReqDetails = new OrderReqDetails();
        orderReqDetails.setOID(UUID.randomUUID().toString());
        orderReqDetails.setCustName(orderRequestDTO.getCustName());
        orderReqDetails.setCustAddress(orderRequestDTO.getCustAddress());
        orderReqDetails.setCustTel(orderRequestDTO.getCustTel());
        orderReqDetails.setTotalPrice(orderRequestDTO.getTotalPrice());
        orderReqDetails.setUID(user.getId());



        orderReqRipocitory.save(orderReqDetails);

        for (SellsDTO item : orderRequestDTO.getCartItem()) {
            SellProduct sellProduct = new SellProduct();
            sellProduct.setCount(item.getCount());
            sellProduct.setDate(LocalDate.now().toString());
            sellProduct.setProductId(item.getProductId());
            sellProduct.setPrice(item.getPrice());
            sellProduct.setId(UUID.randomUUID().toString()); // primary key
            sellProduct.setUId(user.getId());

            sellProduct.setOrder(orderReqDetails);
            sellRipocitory.save(sellProduct);

            Product product = productRipocitory.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            int newStock = product.getCount() - item.getCount();
            if (newStock < 0) {
               throw new RuntimeException("Insufficient stock " + product.getType());
            }




            product.setCount(newStock);
            productRipocitory.save(product);

        }

    }
}
