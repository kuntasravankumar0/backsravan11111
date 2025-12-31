package com.project1.www.UserRepository;

import java.util.Optional;
import com.project1.www.UserModel.usermodel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userrepository extends JpaRepository<usermodel, Long> {

 

    boolean existsByUseremail(String useremail);
    boolean existsByNumber(Long number);
Optional<usermodel> findByNumber(Long number);
    boolean existsByCustomerId(String customerId);
    Optional<usermodel> findByUseremail(String email);
    Optional<usermodel> findByUseremailAndNumber(String useremail, Long number);


}
