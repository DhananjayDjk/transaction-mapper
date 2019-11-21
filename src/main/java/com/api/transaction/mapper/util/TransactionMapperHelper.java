package com.api.transaction.mapper.util;

import com.api.transaction.mapper.domain.OpenBankTransactionResponse;
import com.api.transaction.mapper.exception.OpenBankTransactionNotFoundException;

public class TransactionMapperHelper {
	
	public static void validateOpenBankTransactionResponse(OpenBankTransactionResponse transactionResponse) {
		if(null == transactionResponse || null == transactionResponse.getTransactions() || transactionResponse.getTransactions().size() == 0) {
			throw new OpenBankTransactionNotFoundException();
		}
	}

}
