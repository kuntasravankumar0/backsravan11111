package com.project1.www.UserRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project1.www.UserModel.LinksModel;

import java.util.Optional;

@Repository
public interface LinksRepository extends JpaRepository<LinksModel, Long> {

    Optional<LinksModel> findByLinknumber(String linknumber);

    void deleteByLinknumber(String linknumber);

    boolean existsByLinknumber(String linknumber);
}
