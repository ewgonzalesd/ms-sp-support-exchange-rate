package pe.com.egonzalesd.msspsupportexchangerate.business.impl;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pe.com.egonzalesd.msspsupportexchangerate.business.RateService;
import pe.com.egonzalesd.msspsupportexchangerate.business.exception.BusinessException;
import pe.com.egonzalesd.msspsupportexchangerate.expose.request.RateRequest;
import pe.com.egonzalesd.msspsupportexchangerate.expose.response.RateResponse;
import pe.com.egonzalesd.msspsupportexchangerate.model.RateModel;
import pe.com.egonzalesd.msspsupportexchangerate.repository.RateRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class RateServiceImpl implements RateService {

    @Autowired
    private RateRepository rateRepository;

    @Override
    public Flowable<RateResponse> rates(String base) {

        Map<String, Map<String, List<RateModel>>> ratesMap = rateRepository.findAll()
                .stream()
                .collect(
                        Collectors.groupingBy(RateModel::getFromCurrency,
                                Collectors.groupingBy(RateModel::getDate)));

        List<RateResponse> list = new ArrayList<>();

        for (Map.Entry<String, Map<String, List<RateModel>>>entryFrom : ratesMap.entrySet()){

            for(Map.Entry<String, List<RateModel>> entryDate: entryFrom.getValue().entrySet()) {
                list.add(RateResponse.builder()
                        .from(entryFrom.getKey())
                        .date(entryDate.getKey())
                        .rates(entryDate.getValue().stream()
                        .collect(Collectors.toMap(RateModel::getToCurrency, RateModel:: getRate)))
                        .build());
            }
        }


        if (base == null || base.isEmpty()){
            return Flowable.fromIterable(list);
        } else {
            return Flowable.fromIterable(list)
                    .filter(f -> f.getFrom().equals(base));
        }
    }

    @Override
    public Maybe<Integer> saveRates(RateRequest rateRequest) {

        if(rateRequest.getRate().compareTo(BigDecimal.ZERO) > 0) {

            List<RateModel> exits = rateRepository.findRateModelByFromCurrencyAndToCurrencyAndDate(rateRequest.getFrom().name(),
                    rateRequest.getTo().name(), rateRequest.getDate());

            if (exits.size() > 0) {
                return Maybe.error(new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR,
                        UUID.randomUUID().toString(),"RETO","Ya existe esta configuracion"));
            }


            rateRepository.save(RateModel.builder()
                    .date(rateRequest.getDate())
                    .rate(rateRequest.getRate())
                    .fromCurrency(rateRequest.getFrom().name())
                    .toCurrency(rateRequest.getTo().name())
                    .build());
            return Maybe.just(202);
        } else {
            return Maybe.error(new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR,
                    UUID.randomUUID().toString(),"RETO","Taza debe ser un monto mayor a 0"));
        }



    }

    @Override
    public Maybe<Integer> updateRates(RateRequest rateRequest) {

        List<RateModel> exits = rateRepository.findRateModelByFromCurrencyAndToCurrencyAndDate(rateRequest.getFrom().name(),
                rateRequest.getTo().name(), rateRequest.getDate());

        if(exits.size() > 0) {
           RateModel rateModel = exits.stream().findFirst().orElse(RateModel.builder()
                    .build());
           rateModel.setRate(rateRequest.getRate());
           rateRepository.save(rateModel);
        } else {
            return Maybe.error(new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR,
                    UUID.randomUUID().toString(),"RETO","No existe registro actualizar"));
        }
        return Maybe.just(202);
    }
}
