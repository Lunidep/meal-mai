package ru.lunidep.micro.meal.users.repo;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lunidep.micro.meal.entity.User;
import ru.lunidep.micro.meal.entity.User;

import java.util.Date;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findFirstByEmail(String email);

    void deleteByEmail(String email);

    @Query("SELECT u FROM User.java u where " +
            "(:email is null or :email='' or lower(u.email) like lower(concat('%', :email,'%'))) and" +
            " (:username is null or :username='' or lower(u.username) like lower(concat('%', :username,'%')))"
    )

    Page<User> findByParams(@Param("email") String email,
                            @Param("username") String username,
                            Pageable pageable
    );

}
