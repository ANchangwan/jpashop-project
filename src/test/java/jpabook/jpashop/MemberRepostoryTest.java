package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
public class MemberRepostoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(false)
    @DisplayName("연결 테스트")
    public void testMeber(){
        Member member = new Member();
        member.setUwsername("changwan");
        Long id = memberRepository.save(member);
        Member member1 = memberRepository.findById(id);

        Assertions.assertThat(member1.getId()).isNotNull();
        Assertions.assertThat(member1.getUwsername()).isEqualTo(member.getUwsername());
    }

}
