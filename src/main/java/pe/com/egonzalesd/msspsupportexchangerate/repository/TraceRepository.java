package pe.com.egonzalesd.msspsupportexchangerate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.egonzalesd.msspsupportexchangerate.model.TraceModel;

@Repository
public interface TraceRepository extends JpaRepository<TraceModel, Long> {
}
