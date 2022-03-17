# springMCV/JPA study

### 의존성

- Sring Boot DevTools
- Lombok
- Spring Data JPA
- jdbc(mysql) Driver
- Spring Security
- Spring Web
- oauth2-client

### 태그라이브러리

```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
```

## csrf토큰
- csrf토큰은 스프링시큐리티에서 post요청이 올때 요청 바디 데이터에 달려있는 토큰을 확인하는데 시큐리티가 가지고 있는 토큰과 일치해야정상적인 응답을 해준다.
- 이는 post맨과같은 부적절한 요청방식을 막기 위한 방법.

## form data 받기
- form data는 각 태그의 name값을 받는다.

## di시에 디펜던시 사이클오류
- ioc에서 빌드할때 ioc컨테이너에 등록되는 순서에따라 사이클이 안맡물리면 a라는 파일에 b클래스를 di하고 있는데 해당 클래스가 메모리에 없어서 해당오류가뜬다.
- 이럴땐 di말고 new로 생성하는것으로 해결했다.
