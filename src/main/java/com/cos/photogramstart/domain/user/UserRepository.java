package com.cos.photogramstart.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

// 어노테이션 없어도JpaRepository 상속하면 ioc등록이 자동으로 된다.
// 제네릭의 첫번째 인자는 오브젝트 그다음 인자는 프라이머리키 전략의 타입
public interface UserRepository extends JpaRepository<User,Integer> {
    // JPA query method
        User findByUsername(String username);
}
