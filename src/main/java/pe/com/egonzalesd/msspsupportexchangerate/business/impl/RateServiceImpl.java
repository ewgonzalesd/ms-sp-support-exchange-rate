package pe.com.egonzalesd.msspsupportexchangerate.business.impl;

import io.reactivex.rxjava3.core.Flowable;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.egonzalesd.msspsupportexchangerate.business.RateService;
import pe.com.egonzalesd.msspsupportexchangerate.expose.response.RateResponse;
import pe.com.egonzalesd.msspsupportexchangerate.model.RateModel;
import pe.com.egonzalesd.msspsupportexchangerate.repository.RateRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
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
}
