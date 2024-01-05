package com.britr.simpleRestaurant.service;

import com.britr.simpleRestaurant.constants.SimpleRestaurantConstants;
import com.britr.simpleRestaurant.dto.DeleteResponse;
import com.britr.simpleRestaurant.dto.FetchOrderResponse;
import com.britr.simpleRestaurant.dto.OrderRequest;
import com.britr.simpleRestaurant.dto.OrderResponse;
import com.britr.simpleRestaurant.model.*;
import com.britr.simpleRestaurant.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private TableMasterRepository tableMasterRepository;

    @Mock
    private UserDetailRepository userDetailRepository;

    @Mock
    private MenuMasterRepository menuMasterRepository;

    @Mock
    private DeviceMasterRepository deviceMasterRepository;

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Mock
    private OrderTranRepository orderTranRepository;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getTablesList() {
        TableMaster tableMaster = new TableMaster();
        tableMaster.setId(1);
        tableMaster.setTableName("table01");
        List<TableMaster> tableMasterList = new ArrayList<>();
        tableMasterList.add(tableMaster);
        Mockito.when(tableMasterRepository.findAll()).thenReturn(tableMasterList);
        List<TableMaster> response = orderService.getTablesList();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1,response.size());
        Assertions.assertEquals(1,response.get(0).getId());
    }

    @Test
    void getAllMenu() {
        MenuMaster menuMaster = new MenuMaster();
        menuMaster.setId(1);
        menuMaster.setCategory(new FoodCategory());
        menuMaster.setType(new FoodType());
        menuMaster.setItemName("Item01");
        menuMaster.setPrice(Double.valueOf("100"));
        menuMaster.setPrepTime(15);
        menuMaster.setCreatedDateTime(LocalDateTime.now());
        List<MenuMaster> menuMasterList = new ArrayList<>();
        menuMasterList.add(menuMaster);
        Mockito.when(menuMasterRepository.findAll()).thenReturn(menuMasterList);
        List<MenuMaster> response = orderService.getAllMenu();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1,response.size());
        Assertions.assertEquals(1,response.get(0).getId());
    }

    @Test
    void createOrder() {
        OrderTrans orderTrans = new OrderTrans();
        orderTrans.setId("1");
        orderTrans.setOrderStatus(SimpleRestaurantConstants.ORDER_CONFIRM);
        orderTrans.setOrderReceivedDatetime(LocalDateTime.now());
        orderTrans.setTotalPrice(Double.valueOf(100));
        orderTrans.setPaymentStatus(SimpleRestaurantConstants.PAYMENT_INCOMPLETE);
        Mockito.when(orderTranRepository.save(Mockito.any())).thenReturn(orderTrans);

        Mockito.when(orderDetailRepository.saveAll(Mockito.anyCollection())).thenReturn(createOrderDetailList());

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setUserId(1);
        orderRequest.setTableId(1);
        orderRequest.setQuantity(2);
        orderRequest.setMenuId(1);
        orderRequest.setPrice(100);
        orderRequest.setDeviceId(1);
        List<OrderRequest> orderRequestList = new ArrayList<>();
        OrderResponse orderResponse = orderService.createOrder(orderRequestList, 1, 1);

        Assertions.assertNotNull(orderResponse);
    }

    @Test
    void getOrderByOrderId() {
        Mockito.when(orderDetailRepository.findAllByOrderId(Mockito.anyString())).thenReturn(createOrderDetailList());
        FetchOrderResponse orderResponse = orderService.getOrderByOrderId("1");
        Assertions.assertNotNull(orderResponse);
        Assertions.assertEquals("1",orderResponse.getOrderId());

    }

    @Test
    void deleteOrder() {
        DeleteResponse deleteResponse = new DeleteResponse();
        DeleteResponse.DeleteResponseDetails deleteResponseDetails = new DeleteResponse.DeleteResponseDetails();
        deleteResponseDetails.setMenuId(1);
        deleteResponseDetails.setTableId(1);
        deleteResponseDetails.setDeletionStatus("");
        deleteResponseDetails.setMessage("");
        List<DeleteResponse.DeleteResponseDetails> deleteResponseDetailsList = new ArrayList<>();
        deleteResponseDetailsList.add(deleteResponseDetails);
        deleteResponse.setOrderId("1");
        //Mockito

    }

    public List<OrderDetail> createOrderDetailList(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(1);
        orderDetail.setOrder(new OrderTrans("1",LocalDateTime.now(),SimpleRestaurantConstants.ORDER_CONFIRM,Double.valueOf(100),SimpleRestaurantConstants.PAYMENT_INCOMPLETE));
        orderDetail.setOrderStatus(SimpleRestaurantConstants.PREP_IN_PROGRESS);
        orderDetail.setQuantity(2);
        orderDetail.setUser(new UserDetail());
        orderDetail.setDevice(new DeviceMaster());
        orderDetail.setTable(new TableMaster());
        orderDetail.setMenu(new MenuMaster(1,
                "Item01",
                new FoodCategory(),
                new FoodType(),
                15,
                LocalDateTime.now(),
                Double.valueOf(100)));
        orderDetail.setCreatedDate(LocalDateTime.now());
        List<OrderDetail> savedOrderList = new ArrayList<>();
        savedOrderList.add(orderDetail);
        return savedOrderList;
    }
}
