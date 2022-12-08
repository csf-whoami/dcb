package com.cmc.dcb.service.impl;

import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.Cache;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cmc.dcb.common.Constants;
import com.cmc.dcb.common.TransactionStatus;
import com.cmc.dcb.common.UrlConstants;
import com.cmc.dcb.dto.JwtResponse;
import com.cmc.dcb.dto.mapper.PaymentMapper;
import com.cmc.dcb.dto.request.ChargingRequestDetail;
import com.cmc.dcb.dto.request.PaymentRequest;
import com.cmc.dcb.dto.response.ChargingResponse;
import com.cmc.dcb.dto.response.PaymentResponse;
import com.cmc.dcb.dto.response.PaymentResponseDetail;
import com.cmc.dcb.entity.PaymentStatusEntity;
import com.cmc.dcb.entity.PaymentTransactionHistoryEntity;
import com.cmc.dcb.repository.PaymentStatusRepository;
import com.cmc.dcb.repository.PaymentTransactionHistoryRepository;
import com.cmc.dcb.service.TransactionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private PaymentStatusRepository statusRepository;
	@Autowired
	private PaymentTransactionHistoryRepository transactionHistoryRepository;
	@Autowired
	private CacheManager cacheManager;

	@Value("${dcb.service-key}")
	private String serviceKey;
	@Value("${dcb.service-name}")
	private String serviceName;
	@Value("${operator.service-url}")
	private String operatorUrl;

	@Override
	public ChargingResponse sendToOperator(ChargingRequestDetail chargingRequest) throws URISyntaxException {

		ChargingResponse resultReturn = null;

		CompletableFuture<String> transactionHistory = completableSaveTransactionHistory(chargingRequest);
		CompletableFuture<ChargingResponse> chargingResponse = completableDoPayment(chargingRequest);
		CompletableFuture.allOf(transactionHistory, chargingResponse).join();

		String historyId = transactionHistory.join();
		if(StringUtils.hasText(historyId)) {
			log.info(Constants.FAIL_HISTORY_MESSAGE);
		} else {
			completableSaveTransactionHistory(chargingRequest).whenComplete((result, throwable) -> {
				if (throwable != null) {
					log.info(Constants.FAIL_HISTORY_MESSAGE);
				} else {
					log.info(Constants.SUCCESS_HISTORY_MESSAGE);
				}
			});
		}

		resultReturn = chargingResponse.join();
		if (resultReturn == null) {
			resultReturn = completableDoPayment(chargingRequest).join();
			if (resultReturn == null) {
				resultReturn = PaymentMapper.toChargingResponse(chargingRequest);
				resultReturn.getChargingResponse().getTransactionInfo()
							.setResponseStatus(TransactionStatus.FAILED.getMessage());
				resultReturn.getChargingResponse().getTransactionInfo()
							.setResponseDesc(Constants.FAIL_MESSAGE);
			} else {
				log.info(Constants.SUCCESS_MESSAGE);
			}
		} else {
			resultReturn.getChargingResponse()
						.getTransactionInfo()
						.setResponseStatus(TransactionStatus.SUCCESS.getMessage());
			resultReturn.getChargingResponse()
						.getTransactionInfo()
						.setResponseDesc(Constants.SUCCESS_MESSAGE);
			log.info(Constants.SUCCESS_MESSAGE);
		}

		return resultReturn;
	}

	private CompletableFuture<ChargingResponse> completableDoPayment(ChargingRequestDetail chargingRequest) {
		return CompletableFuture.completedFuture(doPayment(chargingRequest));
	}

	private CompletableFuture<String> completableSaveTransactionHistory(ChargingRequestDetail chargingRequest) {
		return CompletableFuture.completedFuture(saveTransactionHistory(chargingRequest));
	}

	private ChargingResponse doPayment(ChargingRequestDetail chargingRequest) {
		PaymentRequest paymentRequest = PaymentMapper.toPaymentRequest(chargingRequest);

		HttpHeaders headers = getHeaderInfo(chargingRequest.getCustomerInfo().getMobileNo());
		if(headers == null) {
			return responseTransactionFail(chargingRequest);
		}

		// request body parameters
		Map<String, Object> map = new HashMap<>();
		map.put(Constants.PARAM_TRANSACTION, paymentRequest.getTransactionInfo());

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
		try {
			return callToOperator(entity);
		} catch (HttpClientErrorException notFoundException) {
			return responseTransactionFail(chargingRequest);
		} catch (ResourceAccessException notFoundException) {
			try {
				return callToOperator(entity);
			} catch (Exception e) {
				return responseTransactionFail(chargingRequest);
			}
		} catch (Exception e) {
			return responseTransactionFail(chargingRequest);
		}
	}

	private HttpHeaders getHeaderInfo(String msisdn) {

		String accessToken = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		// Check cache.
		Cache caches = cacheManager.getCache(Constants.CACHE_ALIAS);
		if(caches.get(msisdn) != null) {
			accessToken = caches.get(msisdn).toString();
		} else {
			// Call to Operator get access_token
			JwtResponse token = fetchAccessToken(serviceName, serviceKey);
			if(token == null) {
				// return fail transaction.
				return null;
			}
			caches.put(msisdn, token.getAccessToken());
			accessToken = token.getAccessToken();
		}

		headers.set(Constants.AUTHORIZATION, accessToken);
		return headers;
	}

	private JwtResponse fetchAccessToken(String serviceName, String serviceKey) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		// request body parameters
//		Map<String, Object> map = new HashMap<>();
//		map.put(Constants.PARAM_SERVICE_NAME, serviceName);
//		map.put(Constants.PARAM_SERVICE_KEY, serviceKey);

		StringBuilder url = new StringBuilder();
		url.append(operatorUrl);
		url.append(UrlConstants.URL_TOKEN);
		url.append("?service_key=");
		url.append(serviceKey);
		url.append("&service_name=");
		url.append(serviceName);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(new HashMap<>(), headers);
		try {
			
			ResponseEntity<JwtResponse> response = restTemplate.exchange(url.toString(),
																		 HttpMethod.GET,
																		 entity,
																		 JwtResponse.class);
			if (response.getStatusCode() == HttpStatus.OK) {
				JwtResponse tokenInfo = response.getBody();
				return tokenInfo;
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	private ChargingResponse responseTransactionFail(ChargingRequestDetail chargingRequest) {
		ChargingResponse chargingResponse = PaymentMapper.toChargingResponse(chargingRequest);
		chargingResponse.getChargingResponse().getTransactionInfo()
						.setResponseStatus(TransactionStatus.FAILED.getMessage());
		chargingResponse.getChargingResponse().getTransactionInfo()
						.setResponseDesc(Constants.FAIL_MESSAGE);
		return chargingResponse;
	}
	private ChargingResponse callToOperator(HttpEntity<Map<String, Object>> entity
			) throws HttpClientErrorException, ResourceAccessException, Exception {
		ResponseEntity<PaymentResponse> response = restTemplate.postForEntity(operatorUrl + UrlConstants.URL_CARRIER_PAYMENT,
																			  entity,
																			  PaymentResponse.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			PaymentResponse paymentInfo = response.getBody();
			logPaymentStatus(paymentInfo.getTransactionInfo());

			return PaymentMapper.toChargingResponse(paymentInfo.getTransactionInfo());
		}
		return null;
	}

	private String saveTransactionHistory(ChargingRequestDetail chargingRequest) {
		PaymentTransactionHistoryEntity transactionHistory = PaymentMapper.toPaymentTransactionHistory(chargingRequest);

		Optional<HttpServletRequest> currentRequest = Optional.ofNullable(RequestContextHolder.getRequestAttributes())
				.filter(ServletRequestAttributes.class::isInstance)
				.map(ServletRequestAttributes.class::cast)
				.map(ServletRequestAttributes::getRequest);
		if(currentRequest.isPresent()) {
			transactionHistory.setRequestUrl(currentRequest.get().getRequestURL().toString());
			transactionHistory.setAgentInfo(currentRequest.get().getHeader(Constants.USER_AGENT));
		}

		transactionHistoryRepository.save(transactionHistory);
		return transactionHistory.getId();
	}

	private boolean logPaymentStatus(PaymentResponseDetail paymentInfo) {
		PaymentStatusEntity paymentStatus = new PaymentStatusEntity();
		paymentStatus.setId(String.valueOf(UUID.randomUUID()));
		paymentStatus.setMsisdn(paymentInfo.getMsisdn());
		paymentStatus.setRefId(paymentInfo.getRefId());
		paymentStatus.setResponseCode(
				paymentInfo.getResponseCode().equalsIgnoreCase(TransactionStatus.SUCCESS.getCode()) 
					? TransactionStatus.SUCCESS.getCode() : TransactionStatus.FAILED.getCode());
		return statusRepository.save(paymentStatus) == null;
	}
}
