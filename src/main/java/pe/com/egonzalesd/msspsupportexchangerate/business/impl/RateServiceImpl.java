package pe.com.egonzalesd.msspsupportexchangerate.business.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pe.com.egonzalesd.msspsupportexchangerate.business.RateService;
import pe.com.egonzalesd.msspsupportexchangerate.business.exception.BusinessException;
import pe.com.egonzalesd.msspsupportexchangerate.expose.request.RateRequest;
import pe.com.egonzalesd.msspsupportexchangerate.expose.response.AuditResponse;
import pe.com.egonzalesd.msspsupportexchangerate.expose.response.RateResponse;
import pe.com.egonzalesd.msspsupportexchangerate.model.RateModel;
import pe.com.egonzalesd.msspsupportexchangerate.model.TraceModel;
import pe.com.egonzalesd.msspsupportexchangerate.repository.RateRepository;
import pe.com.egonzalesd.msspsupportexchangerate.repository.TraceRepository;
import pe.com.egonzalesd.msspsupportexchangerate.utils.enums.EnumEvent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class RateServiceImpl implements RateService {

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private TraceRepository traceRepository;

    @Override
    public Flowable<RateResponse> rates(String base, String authorization) {

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

        AuditResponse auditResponse = getClaims.apply(authorization);
        traceRepository.save(TraceModel.builder()
                .event(EnumEvent.SELECT.getInstruction())
                .eventUser(auditResponse.getUsername())
                .entity("rates")
                .eventDate(new Date())
                .build());


        if (base == null || base.isEmpty()){
            return Flowable.fromIterable(list);
        } else {
            return Flowable.fromIterable(list)
                    .filter(f -> f.getFrom().equals(base));
        }
    }

    @Override
    public Maybe<Integer> saveRates(RateRequest rateRequest, String authorization) {

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

            AuditResponse auditResponse = getClaims.apply(authorization);
            traceRepository.save(TraceModel.builder()
                    .entity("rates")
                    .event(EnumEvent.INSERT.getInstruction())
                    .eventUser(auditResponse.getUsername())
                    .eventDate(new Date())
                    .build());

            return Maybe.just(202);
        } else {
            return Maybe.error(new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR,
                    UUID.randomUUID().toString(),"RETO","Taza debe ser un monto mayor a 0"));
        }
    }

    @Override
    public Maybe<Integer> updateRates(RateRequest rateRequest, String authorization) {

        List<RateModel> exits = rateRepository.findRateModelByFromCurrencyAndToCurrencyAndDate(rateRequest.getFrom().name(),
                rateRequest.getTo().name(), rateRequest.getDate());

        if(exits.size() > 0) {
           RateModel rateModel = exits.stream().findFirst().orElse(RateModel.builder()
                    .build());
           rateModel.setRate(rateRequest.getRate());
           rateRepository.save(rateModel);

            AuditResponse auditResponse = getClaims.apply(authorization);
            traceRepository.save(TraceModel.builder()
                    .event(EnumEvent.UPDATE.getInstruction())
                    .eventUser(auditResponse.getUsername())
                    .entity("rates")
                    .eventDate(new Date())
                    .build());

        } else {
            return Maybe.error(new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR,
                    UUID.randomUUID().toString(),"RETO","No existe registro actualizar"));
        }
        return Maybe.just(202);
    }

    Function<String, AuditResponse> getClaims = (token) -> {

        String[] value = token.split(" ");
        DecodedJWT jwt = JWT.decode(value[1]);

        return AuditResponse
                .builder()
                .numDoc(jwt.getClaims().get("typeDoc").asString())
                .typeDoc(jwt.getClaims().get("numDoc").asString())
                .username(jwt.getClaims().get("username").asString())
                .build();
    };

}
