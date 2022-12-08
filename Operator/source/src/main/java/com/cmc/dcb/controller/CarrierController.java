package com.cmc.dcb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.dcb.common.UrlConstants;
import com.cmc.dcb.dto.request.PaymentRequest;
import com.cmc.dcb.dto.response.PaymentResponse;
import com.cmc.dcb.dto.response.PaymentResponseDetail;
import com.cmc.dcb.service.CarrierService;

@RestController
@RequestMapping(value = UrlConstants.URL_CARRIER)
public class CarrierController {

	@Autowired
	private CarrierService carrierService;

	@PostMapping(value = UrlConstants.URL_PAYMENT)
	public ResponseEntity<?> carrierPayment(@RequestBody PaymentRequest paymentRequest) {

		PaymentResponseDetail transactionInfo = carrierService.doPayment(paymentRequest);

		PaymentResponse response = new PaymentResponse();
		response.setTransactionInfo(transactionInfo);
		return ResponseEntity.ok(response);
	}
}
