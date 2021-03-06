package com.lym.gd.repository;

import com.lym.gd.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author liuyaming
 * @date 2018/3/11 下午5:31
 */
public interface UserRepository extends JpaRepository<User,String>{

    User findByUserId(String id);

    User findByUserPhoneAndUserPassword(String phone,String password);
}
