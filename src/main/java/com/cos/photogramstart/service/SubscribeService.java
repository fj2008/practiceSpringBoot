package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.subscribe.Subscribe;
import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomAPIException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final EntityManager em; // 모든 Repositorysms entityManager를 구현해서 만들어져 있는 구현체

    @Transactional(readOnly = true)
    public List<SubscribeDto> 구독리스트(int principalId,int pageUserId){
        StringBuffer sb= new StringBuffer();
        sb.append("SELECT u.id, u.username , u.profileImageUrl, ");
        sb.append("IF ((SELECT 1 FROM subscribe WHERE fromUserId = ? AND toUserId =u.id),1,0) subscribeState, ");
        sb.append("IF ((?=u.id),1,0) equalUserState ");
        sb.append("FROM user u INNER JOIN subscribe s ");
        sb.append("ON u.id = s.toUserId ");
        sb.append("WHERE s.fromUserId = ?");


        //바인딩하기전에 물음표로 넣어줄 변수위치 확인
        //1.물음표 principalId
        //2.물음표 principalId
        //3.마지막 물음표 pageUserId

        //쿼리 완성
        //javax.persistens에 Query를 임포트해준다.
        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, principalId)
                .setParameter(2, principalId)
                .setParameter(3, pageUserId);

        //쿼리실행
        //QLRM라이브러리 필요 =DTO에 DB결과를 메핑하기 위해서
        JpaResultMapper result = new JpaResultMapper();
        List<SubscribeDto> subscribeDtos =  result.list(query, SubscribeDto.class);
        return subscribeDtos;
    }


    @Transactional
    public void 구독하기(int fromUserId, int toUserId) {
        try{
            subscribeRepository.mSubscribe(fromUserId,toUserId);

        }catch (Exception e){
            throw new CustomAPIException("이미 구독을 하였습니다.");
        }

    }
    @Transactional
    public void 구독취소하기(int fromUserId ,int toUserId){
        subscribeRepository.mUnSubscribe(fromUserId,toUserId);
    }
}
