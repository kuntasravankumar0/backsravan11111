package com.project1.www.UserRepository;

import com.project1.www.UserModel.Adminpagemodel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface Adminpagerepo extends JpaRepository<Adminpagemodel, Long> {

    Adminpagemodel findByEmail(String email);

    List<Adminpagemodel> findByStatus(String status);

    Adminpagemodel findByCustomerId(String customerId);

    @Transactional
    void deleteByCustomerId(String customerId);
}
