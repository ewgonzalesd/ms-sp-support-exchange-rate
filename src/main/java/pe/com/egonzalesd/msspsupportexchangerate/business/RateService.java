package pe.com.egonzalesd.msspsupportexchangerate.business;

import io.reactivex.rxjava3.core.Flowable;
import pe.com.egonzalesd.msspsupportexchangerate.expose.response.RateResponse;

public interface RateService {
    Flowable<RateResponse> rates(String base);
}
