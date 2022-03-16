package com.cos.photogramstart.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe,Integer> {

    @Modifying // db변경을 주는 네이티브쿼리는 해당 어노테이션이 필요하다.
    @Query(value = "INSERT INTO subscribe(fromUserId,toUserId,createDate) VALUES(:fromUserId,:toUserId,now())",nativeQuery = true)
    void mSubscribe(int fromUserId, int toUserId);

    @Modifying
    @Query(value = "DELETE FROM subscribe WHERE fromUserId=:fromUserId AND toUserID=:toUserId",nativeQuery = true)
    void mUnSubscribe(int fromUserId, int toUserId);

    @Query(value = "select count(*) from subscribe where fromUserId =:principalId and toUserId =:pageUserId", nativeQuery = true)
    int mSubscribeState(int principalId, int pageUserId);

    @Query(value = "select count(*) from subscribe where fromUserId =:pageUserId", nativeQuery = true)
    int mSubscribeCount(int pageUserId);
}
