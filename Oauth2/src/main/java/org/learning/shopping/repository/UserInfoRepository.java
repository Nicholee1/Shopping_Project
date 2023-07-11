package org.learning.shopping.repository;

import org.learning.shopping.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {
    UserInfo findByUsername(String username);
}
