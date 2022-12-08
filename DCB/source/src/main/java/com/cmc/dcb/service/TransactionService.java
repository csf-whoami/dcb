package com.cmc.dcb.service;

import java.net.URISyntaxException;

import com.cmc.dcb.dto.request.ChargingRequestDetail;
import com.cmc.dcb.dto.response.ChargingResponse;

public interface TransactionService {

	ChargingResponse sendToOperator(ChargingRequestDetail chargingRequest) throws URISyntaxException;
}
