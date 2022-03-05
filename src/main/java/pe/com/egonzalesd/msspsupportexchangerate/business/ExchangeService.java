package pe.com.egonzalesd.msspsupportexchangerate.business;

import io.reactivex.rxjava3.core.Maybe;
import pe.com.egonzalesd.msspsupportexchangerate.expose.request.ExchangeRequest;
import pe.com.egonzalesd.msspsupportexchangerate.expose.response.ExchangeResponse;

public interface ExchangeService {
    Maybe<ExchangeResponse> validateExchange(ExchangeRequest exchangeRequest);
}
