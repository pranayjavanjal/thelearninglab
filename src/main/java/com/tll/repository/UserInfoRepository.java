package com.tll.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tll.models.UserInfo;

@Repository(value = "userInfoRepository")
public interface UserInfoRepository extends JpaRepository<UserInfo, Serializable> {

    public UserInfo findByEmail(String email);

    public List<UserInfo> findAllByOrderById();

    public UserInfo findById(int id);

    @Transactional
    @Modifying
    @Query(value = "update userinfo set active=false where id=?", nativeQuery = true)
    public void blockUser(int id);

    @Transactional
    @Modifying
    @Query(value = "update userinfo set active=true where id=?", nativeQuery = true)
    public void unBlockUser(int id);
    
    @Query(nativeQuery = true, value = "select * from userinfo where department=(select department from userinfo where email=?) and role='MANAGER'")
    public UserInfo findManagerByEmail(String email);
    
    @Transactional
    @Modifying
    @Query(value = "update userinfo set first_name=?,last_name=?,department=? where email=?", nativeQuery = true)
    public void updateUser(String fisrtName,String lastName,String email);
    
    @Transactional
    @Modifying
    @Query(value = "update userinfo set active=false where email=?", nativeQuery = true)
    public void blockUserByEmail(String email);
}
