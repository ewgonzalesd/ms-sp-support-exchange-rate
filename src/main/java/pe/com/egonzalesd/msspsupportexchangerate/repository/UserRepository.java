package pe.com.egonzalesd.msspsupportexchangerate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.egonzalesd.msspsupportexchangerate.model.UserModel;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    List<UserModel> findUserModelByUsername(String username);
}
