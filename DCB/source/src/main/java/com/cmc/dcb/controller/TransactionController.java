package com.cmc.dcb.controller;

import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.dcb.common.TransactionStatus;
import com.cmc.dcb.common.UrlConstants;
import com.cmc.dcb.dto.request.ChargingRequest;
import com.cmc.dcb.dto.response.ChargingResponse;
import com.cmc.dcb.service.TransactionService;

@RestController
@RequestMapping(value = UrlConstants.URL_API)
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping(value = UrlConstants.URL_CHARGE)
	public ResponseEntity<?> transactionCharge(@RequestBody ChargingRequest chargingRequest) {
		try {
			ChargingResponse chargingResponse = transactionService.sendToOperator(chargingRequest.getChargingRequest());
			
			return ResponseEntity.ok(chargingResponse);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(TransactionStatus.SUCCESS.getMessage());
	}
}
