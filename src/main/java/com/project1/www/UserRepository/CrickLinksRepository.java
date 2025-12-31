package com.project1.www.UserRepository;

import com.project1.www.UserModel.CrickLinks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface CrickLinksRepository extends JpaRepository<CrickLinks, Long> {

    List<CrickLinks> findByCustomerId(String customerId);

    void deleteByCustomerId(String customerId);
}
