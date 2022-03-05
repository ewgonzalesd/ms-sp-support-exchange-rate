package pe.com.egonzalesd.msspsupportexchangerate.business.impl;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.egonzalesd.msspsupportexchangerate.business.ExchangeService;
import pe.com.egonzalesd.msspsupportexchangerate.expose.request.ExchangeRequest;
import pe.com.egonzalesd.msspsupportexchangerate.expose.response.ExchangeResponse;
import pe.com.egonzalesd.msspsupportexchangerate.model.RateModel;
import pe.com.egonzalesd.msspsupportexchangerate.repository.RateRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class ExchangeServiceImpl implements ExchangeService {

    @Autowired
    private RateRepository rateRepository;

    @Override
    public Maybe<ExchangeResponse> validateExchange(ExchangeRequest exchangeRequest) {


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        List<RateModel> rateModelList = rateRepository
                .findRateModelByFromCurrencyAndDate(exchangeRequest.getFrom().name(),
                        formatter.format(new Date()));

        return Flowable.fromIterable(rateModelList)
                .filter(f -> f.getToCurrency().equals(exchangeRequest.getTo().name()))
                .map(o -> ExchangeResponse.builder()
                        .exchangeRateAmount(o.getRate().multiply(exchangeRequest.getAmount()))
                        .amount(exchangeRequest.getAmount())
                        .from(exchangeRequest.getFrom())
                        .to(exchangeRequest.getTo())
                        .date(formatter.format(new Date()))
                        .exchangeRate(o.getRate())
                        .build())
                .firstElement();
    }
}
