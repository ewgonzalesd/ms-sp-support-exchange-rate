package pe.com.egonzalesd.msspsupportexchangerate.business;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import pe.com.egonzalesd.msspsupportexchangerate.expose.request.RateRequest;
import pe.com.egonzalesd.msspsupportexchangerate.expose.response.RateResponse;

public interface RateService {
    Flowable<RateResponse> rates(String base);
    Maybe<Integer> saveRates(RateRequest rateRequest);
    Maybe<Integer> updateRates(RateRequest rateRequest);
}
