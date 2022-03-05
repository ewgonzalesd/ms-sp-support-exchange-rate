package pe.com.egonzalesd.msspsupportexchangerate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.egonzalesd.msspsupportexchangerate.model.RateModel;


import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<RateModel, Long> {

    List<RateModel> findRateModelByFromCurrencyAndDate(String fromCurrency,
                                                       String date);


}
