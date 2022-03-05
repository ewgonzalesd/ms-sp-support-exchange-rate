package pe.com.egonzalesd.msspsupportexchangerate.expose;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.com.egonzalesd.msspsupportexchangerate.business.RateService;
import pe.com.egonzalesd.msspsupportexchangerate.expose.request.RateRequest;
import pe.com.egonzalesd.msspsupportexchangerate.expose.response.RateResponse;

@RestController
@RequestMapping("/rate")
@Slf4j
@RequiredArgsConstructor
public class RateController {
    @Autowired
    private RateService rateService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flowable<RateResponse> rates(@RequestParam(required = false) String base){
        return rateService.rates(base);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Maybe<Integer> saveRate(@RequestBody RateRequest rateRequest){
        return rateService.saveRates(rateRequest);
    }

    @PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Maybe<Integer> updateRate(@RequestBody RateRequest rateRequest){
        return rateService.updateRates(rateRequest);
    }
}
