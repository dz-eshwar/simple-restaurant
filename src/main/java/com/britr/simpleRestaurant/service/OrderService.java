package com.britr.simpleRestaurant.service;

import com.britr.simpleRestaurant.constants.SimpleRestaurantConstants;
import com.britr.simpleRestaurant.dto.*;
import com.britr.simpleRestaurant.exception.APIAbortedException;
import com.britr.simpleRestaurant.exception.ApiException;
import com.britr.simpleRestaurant.model.*;
import com.britr.simpleRestaurant.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class OrderService {

    @Autowired
    private TableMasterRepository tableMasterRepository;

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private MenuMasterRepository menuMasterRepository;

    @Autowired
    private DeviceMasterRepository deviceMasterRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderTranRepository orderTranRepository;

    public List<TableMaster> getTablesList(){
        List<TableMaster> tableMasters = tableMasterRepository.findAll();
        return tableMasters;
    }

    public List<MenuMaster> getAllMenu(){
        return menuMasterRepository.findAll();
    }

    @Transactional
    public OrderResponse createOrder(List<OrderRequest> orderList,Integer userId,Integer deviceId){
            try {
                List<OrderDetail> orderDetailList = new ArrayList<>();
                OrderTrans trans = new OrderTrans();
                trans.setId(UUID.randomUUID().toString());
                trans.setOrderStatus(SimpleRestaurantConstants.ORDER_CONFIRM);
                trans.setOrderReceivedDatetime(LocalDateTime.now());
                trans.setTotalPrice(Double.valueOf(orderList.stream().map(orderRequest -> orderRequest.getPrice() * orderRequest.getQuantity()).collect(Collectors.summingInt(Integer::intValue))));
                OrderTrans savedTran = orderTranRepository.save(trans);
                for (OrderRequest orderRequest : orderList) {
                    OrderDetail detail = new OrderDetail();
                    detail.setMenu(menuMasterRepository.findById(orderRequest.getMenuId()).get());
                    detail.setTable(tableMasterRepository.findById(orderRequest.getTableId()).get());
                    detail.setUser(userDetailRepository.getById(userId));
                    detail.setDevice(deviceMasterRepository.findById(deviceId).get());
                    detail.setQuantity(orderRequest.getQuantity());
                    detail.setCreatedDate(LocalDateTime.now());
                    detail.setOrderStatus(SimpleRestaurantConstants.ORDER_CONFIRM);
                    detail.setOrder(savedTran);
                    orderDetailList.add(detail);
                }

                OrderResponse orderResponse = new OrderResponse();
                List<OrderResponse.ResponseDetails> responseList = new ArrayList<>();
                List<OrderDetail> savedList = orderDetailRepository.saveAll(orderDetailList);
                savedList.stream().forEach(order -> {
                    OrderResponse.ResponseDetails response = new OrderResponse.ResponseDetails();
                    response.setMenuId(order.getMenu().getId());
                    response.setMenuName(order.getMenu().getItemName());
                    response.setQuantity(order.getQuantity());
                    response.setTimeToPrep(calculateTimeToPrep(order.getQuantity(), order.getMenu().getPrepTime()));
                    responseList.add(response);
                });
                orderResponse.setOrderId(trans.getId());
                orderResponse.setResponseDetails(responseList);
                return orderResponse;
            }catch(ApiException ex){
                log.error("Exception Details::",ex);
                throw new APIAbortedException(ex.getHttpStatus(), ex.getErrorMessage(), ex.getErrorCode());
            }catch (Exception ex){
                log.error("Exception Details::",ex);
                throw new APIAbortedException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
            }

    }

    private Integer calculateTimeToPrep(Integer quantity, Integer prepTime){
        if(quantity<=3){
            return prepTime;
        } else if (3<quantity && quantity<=6) {
            return prepTime*2;
        }
        else{
            return prepTime*3;
        }
    }

    public FetchOrderResponse getOrderByOrderId(String orderId){
        try {
            List<OrderDetail> orderDetailList = orderDetailRepository.findAllByOrderId(orderId);
            if (CollectionUtils.isEmpty(orderDetailList)) {
                throw new ApiException(HttpStatus.OK, "Data not found for passed orderId", "ERR02");
            }
            return createFetchOrderResponse(orderId, orderDetailList);
        }catch(ApiException ex){
            log.error("Exception Details::",ex);
            throw new APIAbortedException(ex.getHttpStatus(), ex.getErrorMessage(), ex.getErrorCode());
        }catch (Exception ex){
            log.error("Exception Details::",ex);
            throw new APIAbortedException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    public FetchTableOrderResponse getOrderByTableId(Integer tableId){
        try {
            List<OrderDetail> orderDetailList = orderDetailRepository.findAllByTableId(tableId);

            //filtering out the old orders
            List<OrderDetail> validOrderDetailList = orderDetailList.stream().filter(orderDetail -> ObjectUtils.isEmpty(orderDetail.getOrder().getPaymentStatus()) ||
                            !SimpleRestaurantConstants.PAYMENT_COMPLETE.contentEquals(orderDetail.getOrder().getPaymentStatus()))
                    .collect(Collectors.toList());

            if (CollectionUtils.isEmpty(validOrderDetailList)) {
                throw new ApiException(HttpStatus.OK, "Data not found for passed tableId", "ERR01");
            }

            Set<String> orderIdList = validOrderDetailList.stream().map(OrderDetail::getOrder).map(OrderTrans::getId).collect(Collectors.toSet());
            List<FetchOrderResponse> fetchOrderResponseList = new ArrayList<>();
            for (String orderId : orderIdList) {
                FetchOrderResponse fetchOrderResponse = createFetchOrderResponse(orderId, validOrderDetailList.stream().filter(orderDetail -> orderDetail.getOrder().getId().contentEquals(orderId)).collect(Collectors.toList()));
                fetchOrderResponseList.add(fetchOrderResponse);
            }
            FetchTableOrderResponse fetchTableOrderResponse = new FetchTableOrderResponse();
            fetchTableOrderResponse.setTableId(tableId);
            fetchTableOrderResponse.setOrderResponseList(fetchOrderResponseList);
            return fetchTableOrderResponse;
        }catch(ApiException ex){
            log.error("Exception Details::",ex);
            throw new APIAbortedException(ex.getHttpStatus(), ex.getErrorMessage(), ex.getErrorCode());
        }catch (Exception ex){
            log.error("Exception Details::",ex);
            throw new APIAbortedException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    private FetchOrderResponse createFetchOrderResponse(String requestId, List<OrderDetail> orderDetailList)
    {
        FetchOrderResponse fetchOrderResponse = new FetchOrderResponse();
        List<FetchOrderResponse.FetchOrderDetails> fetchOrderDetailsList = new ArrayList<>();
        try {
            fetchOrderResponse.setOrderId(requestId);
            for (OrderDetail orderDetail : orderDetailList) {
                FetchOrderResponse.FetchOrderDetails fetchOrderDetails = new FetchOrderResponse.FetchOrderDetails();
                fetchOrderDetails.setMenuId(orderDetail.getMenu().getId());
                fetchOrderDetails.setItemName(orderDetail.getMenu().getItemName());
                fetchOrderDetails.setQuantity(orderDetail.getQuantity());
                fetchOrderDetails.setTableId(orderDetail.getTable().getId());
                if (!orderDetail.getOrder().getOrderStatus().contentEquals(SimpleRestaurantConstants.ORDER_COMPLETE)) {
                    Integer totalTimeToPrep = calculateTimeToPrep(orderDetail.getQuantity(), orderDetail.getMenu().getPrepTime());
                    Integer timeRemainingForPrepComplete = Math.toIntExact(ChronoUnit.MINUTES.between(LocalDateTime.now(), orderDetail.getCreatedDate().plusMinutes(totalTimeToPrep)));
                    fetchOrderDetails.setTimeLeftForPrepComplete(timeRemainingForPrepComplete);
                }
                fetchOrderDetailsList.add(fetchOrderDetails);
            }
            fetchOrderResponse.setOrderDetails(fetchOrderDetailsList);
        }catch(Exception ex)
        {
            log.error("Exception in delete:: ",ex);
            throw new APIAbortedException(HttpStatus.INTERNAL_SERVER_ERROR,"Error while saving data. please check logs","ERR03");
        }
        return fetchOrderResponse;
    }

    @Transactional
    public DeleteResponse deleteOrder(DeleteRequest request){
        DeleteResponse deleteResponse = new DeleteResponse();
        List<DeleteResponse.DeleteResponseDetails> deleteResponseDetailsList = new ArrayList<>();
        try {
            for (DeleteRequest.DeleteRequestDetails requestDetails : request.getDeleteRequestDetailsList()) {
                OrderDetail orderDetail = orderDetailRepository.findByOrderIdAndMenuIdAndTableId(request.getOrderId(), requestDetails.getMenuId(), requestDetails.getTableId());
                DeleteResponse.DeleteResponseDetails deleteResponseDetails = new DeleteResponse.DeleteResponseDetails();
                OrderDetail updatedOrder = new OrderDetail();
                if (!orderDetail.getOrderStatus().contentEquals(SimpleRestaurantConstants.ORDER_COMPLETE)) {
                    if (orderDetail.getQuantity() > requestDetails.getQuantity()) {
                        Integer quantity = orderDetail.getQuantity() - requestDetails.getQuantity();
                        orderDetail.setQuantity(quantity);
                        updatedOrder = orderDetailRepository.save(orderDetail);
                        deleteResponseDetails.setDeletionStatus(SimpleRestaurantConstants.SUCCESS);
                        deleteResponseDetails.setMenuId(updatedOrder.getMenu().getId());
                        deleteResponseDetails.setTableId(updatedOrder.getTable().getId());
                        deleteResponseDetailsList.add(deleteResponseDetails);

                    } else if (orderDetail.getQuantity() == requestDetails.getQuantity()) {
                        Integer result = orderDetailRepository.deleteByOrderIdAndMenuIdAndTableId(request.getOrderId(), requestDetails.getMenuId(), requestDetails.getTableId());
                        if(result>0){
                            deleteResponseDetails.setMenuId(requestDetails.getMenuId());
                            deleteResponseDetails.setTableId(requestDetails.getTableId());
                            deleteResponseDetails.setDeletionStatus(SimpleRestaurantConstants.SUCCESS);
                            deleteResponseDetailsList.add(deleteResponseDetails);
                        }
                        else{
                            throw new APIAbortedException(HttpStatus.CONFLICT,"Exception during deletion please check logs","ERR06");
                        }
                    } else{
                        throw new ApiException(HttpStatus.CONFLICT,"Invalid quantity in request","ERR05");
                    }
                }


            }

        }catch(ApiException ex){
            log.error("Exception in delete:: ",ex);
            throw new APIAbortedException(ex.getHttpStatus(),ex.getErrorMessage(), ex.getErrorCode());
        }
        catch(Exception ex){
            log.error("Exception in delete:: ",ex);
            throw new APIAbortedException(HttpStatus.INTERNAL_SERVER_ERROR,"Error while deleting data. please check logs","ERR04");
        }
        deleteResponse.setDeleteResponseDetailsList(deleteResponseDetailsList);
        return deleteResponse;
    }
}
