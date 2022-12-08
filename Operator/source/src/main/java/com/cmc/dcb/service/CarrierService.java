package com.cmc.dcb.service;

import com.cmc.dcb.dto.request.PaymentRequest;
import com.cmc.dcb.dto.response.PaymentResponseDetail;

public interface CarrierService {

	PaymentResponseDetail doPayment(PaymentRequest paymentRequest);
}
