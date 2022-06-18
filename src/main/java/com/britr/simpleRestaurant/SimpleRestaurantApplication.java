package com.britr.simpleRestaurant;

import com.britr.simpleRestaurant.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = "com.britr.simpleRestaurant.*")
@EnableJpaRepositories(basePackages = "com.britr.simpleRestaurant.repository")
public class SimpleRestaurantApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleRestaurantApplication.class, args);
		ExecutorService executorServiceForAuthentication = Executors.newSingleThreadExecutor();
		ExecutorService executorServiceForOrderCreation = Executors.newFixedThreadPool(6);
		Collection<Callable<Object>> callableArrayList = new ArrayList<>();
		AuthenticationRequest authenticationRequest = new AuthenticationRequest();
		authenticationRequest.setUsername("manager3");
		authenticationRequest.setPassword("P@ssw0rd");
		final String[] token = {""};
		Runnable runnableForAuthentication = () -> {
			try {
				RestTemplate restTemplate = new RestTemplate();
				HttpEntity requestEntity = new HttpEntity(authenticationRequest);
				URI uri = new URI ("http://localhost:9000/user/authenticate");
				ResponseEntity<AuthenticationResponse> response = restTemplate.exchange(uri, HttpMethod.POST,requestEntity, AuthenticationResponse.class);
				AuthenticationResponse authenticationResponse = response.getBody();
				token[0] = authenticationResponse.getJwt();
				System.out.println("token is generated"+token[0]);
			} catch (URISyntaxException e) {
				throw new RuntimeException(e);
			}

		};



		final String[] orderIdList = {""};
		Runnable runnableForOrderCreation1 = () -> {
			try {
				OrderRequest orderRequest1 = new OrderRequest();
				orderRequest1.setMenuId(2);
				orderRequest1.setQuantity(2);
				orderRequest1.setTableId(1);
				orderRequest1.setPrice(150);
				OrderRequest orderRequest2 = new OrderRequest();
				orderRequest2.setMenuId(6);
				orderRequest2.setQuantity(2);
				orderRequest2.setTableId(1);
				orderRequest2.setPrice(150);
				RestTemplate restTemplate = new RestTemplate();
				List<OrderRequest> orderRequestList = new ArrayList<>();
				orderRequestList.add(orderRequest1);
				orderRequestList.add(orderRequest2);
				HttpHeaders headers = new HttpHeaders();
				headers.set("Authorization","Bearer "+token[0]);
				HttpEntity requestEntity = new HttpEntity(orderRequestList,headers);
				URI uri = new URI ("http://localhost:9000/order/addOrder");
				ResponseEntity<OrderResponse> response = restTemplate.exchange(uri, HttpMethod.POST,requestEntity, OrderResponse.class);
				orderIdList[0] = response.getBody().getOrderId();
				} catch (URISyntaxException e) {
				throw new RuntimeException(e);
			}

		};

		callableArrayList.add(Executors.callable(runnableForOrderCreation1));


		Runnable runnableForOrderCreation2 = () -> {
			try {
				OrderRequest orderRequest1 = new OrderRequest();
				orderRequest1.setMenuId(2);
				orderRequest1.setQuantity(1);
				orderRequest1.setTableId(2);
				orderRequest1.setPrice(150);
				OrderRequest orderRequest2 = new OrderRequest();
				orderRequest2.setMenuId(6);
				orderRequest2.setQuantity(4);
				orderRequest2.setTableId(3);
				orderRequest2.setPrice(150);
				RestTemplate restTemplate = new RestTemplate();
				List<OrderRequest> orderRequestList = new ArrayList<>();
				orderRequestList.add(orderRequest1);
				orderRequestList.add(orderRequest2);
				HttpHeaders headers = new HttpHeaders();
				headers.set("Authorization","Bearer "+token[0]);
				HttpEntity requestEntity = new HttpEntity(orderRequestList,headers);
				URI uri = new URI ("http://localhost:9000/order/addOrder");
				ResponseEntity<OrderResponse> response = restTemplate.exchange(uri, HttpMethod.POST,requestEntity, OrderResponse.class);
				orderIdList[1] = response.getBody().getOrderId();
			} catch (URISyntaxException e) {
				throw new RuntimeException(e);
			}

		};
		callableArrayList.add( Executors.callable(runnableForOrderCreation2));

		Runnable fetchOrderByTableId = () -> {
			try {
				RestTemplate restTemplate = new RestTemplate();
				HttpHeaders headers = new HttpHeaders();
				headers.set("Authorization","Bearer "+token[0]);
				HttpEntity requestEntity = new HttpEntity(headers);
				URI uri = new URI ("http://localhost:9000/order/getOrderByTableId/1");
				ResponseEntity<FetchTableOrderResponse> response = restTemplate.exchange(uri, HttpMethod.GET,requestEntity, FetchTableOrderResponse.class);
			} catch (URISyntaxException e) {
				throw new RuntimeException(e);
			}

		};
		callableArrayList.add( Executors.callable(fetchOrderByTableId));

		Runnable runnableForOrderCreation3 = () -> {
			try {
				OrderRequest orderRequest1 = new OrderRequest();
				orderRequest1.setMenuId(3);
				orderRequest1.setQuantity(5);
				orderRequest1.setTableId(5);
				orderRequest1.setPrice(150);
				OrderRequest orderRequest2 = new OrderRequest();
				orderRequest2.setMenuId(6);
				orderRequest2.setQuantity(4);
				orderRequest2.setTableId(6);
				orderRequest2.setPrice(150);
				RestTemplate restTemplate = new RestTemplate();
				List<OrderRequest> orderRequestList = new ArrayList<>();
				orderRequestList.add(orderRequest1);
				orderRequestList.add(orderRequest2);
				HttpHeaders headers = new HttpHeaders();
				headers.set("Authorization","Bearer "+token[0]);
				HttpEntity requestEntity = new HttpEntity(orderRequestList,headers);
				URI uri = new URI ("http://localhost:9000/order/addOrder");
				ResponseEntity<OrderResponse> response = restTemplate.exchange(uri, HttpMethod.POST,requestEntity, OrderResponse.class);
				orderIdList[2] = response.getBody().getOrderId();
			} catch (URISyntaxException e) {
				throw new RuntimeException(e);
			}

		};
		callableArrayList.add( Executors.callable(runnableForOrderCreation3));

		Runnable runnableForDeleteOrder = () -> {
			try {
				DeleteRequest deleteRequest = new DeleteRequest();
				System.out.println("orderId::"+orderIdList[1]);
				deleteRequest.setOrderId(orderIdList[1]);
				DeleteRequest.DeleteRequestDetails deleteRequestDetails = new DeleteRequest.DeleteRequestDetails();
				deleteRequestDetails.setMenuId(2);
				deleteRequestDetails.setQuantity(1);
				deleteRequestDetails.setTableId(2);
				List<DeleteRequest.DeleteRequestDetails> deleteRequestDetailsList = new ArrayList<>();
				deleteRequestDetailsList.add(deleteRequestDetails);
				deleteRequest.setDeleteRequestDetailsList(deleteRequestDetailsList);
				RestTemplate restTemplate = new RestTemplate();
				HttpHeaders headers = new HttpHeaders();
				headers.set("Authorization","Bearer "+token[0]);
				HttpEntity requestEntity = new HttpEntity(deleteRequest,headers);
				URI uri = new URI ("http://localhost:9000/order/deleteOrder");
				ResponseEntity<DeleteResponse> response = restTemplate.exchange(uri, HttpMethod.POST,requestEntity, DeleteResponse.class);
			} catch (URISyntaxException e) {
				throw new RuntimeException(e);
			}

		};
		callableArrayList.add( Executors.callable(runnableForDeleteOrder));

		Runnable runnableToFetchOrderByOrderId = () -> {
			try {
				RestTemplate restTemplate = new RestTemplate();
				HttpHeaders headers = new HttpHeaders();
				headers.set("Authorization","Bearer "+token[0]);
				HttpEntity requestEntity = new HttpEntity(headers);
				System.out.println("orderId::"+orderIdList[1]);
				URI uri = new URI ("http://localhost:9000/order/getOrderByOrderId/"+orderIdList[1]);
				ResponseEntity<DeleteResponse> response = restTemplate.exchange(uri, HttpMethod.POST,requestEntity, DeleteResponse.class);
			} catch (URISyntaxException e) {
				throw new RuntimeException(e);
			}

		};
		callableArrayList.add( Executors.callable(runnableToFetchOrderByOrderId));

		try {
			executorServiceForAuthentication.execute(runnableForAuthentication);
			executorServiceForOrderCreation.invokeAll(callableArrayList);
		}catch(Exception ex)
		{
		log.error("Exceptions while executing threads::",ex);
		}
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*public static interface MyCallable extends Callable{}
	public static class Task implements MyCallable{

		@Override
		public Object call() throws Exception {
			System.out.printf("%s: Staring new task at \n", LocalDateTime.now());

			try {
				long duration = (long) (Math.random() * 10);
				System.out.printf("Waiting %d seconds for results.\n", duration);
				TimeUnit.SECONDS.sleep(duration);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}
	}*/



}
