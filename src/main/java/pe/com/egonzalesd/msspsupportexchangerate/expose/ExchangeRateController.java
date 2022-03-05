package pe.com.egonzalesd.msspsupportexchangerate.expose;

import io.reactivex.rxjava3.core.Maybe;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.com.egonzalesd.msspsupportexchangerate.business.ExchangeService;
import pe.com.egonzalesd.msspsupportexchangerate.expose.request.ExchangeRequest;
import pe.com.egonzalesd.msspsupportexchangerate.expose.response.ExchangeResponse;

@RestController
@RequestMapping("/exchange")
@Slf4j
@RequiredArgsConstructor
public class ExchangeRateController {

   @Autowired
   private ExchangeService exchangeService;

   @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
   public Maybe<ExchangeResponse> evaluateExchange(@RequestBody ExchangeRequest exchangeRequest){
        return exchangeService.validateExchange(exchangeRequest);
   }
}
