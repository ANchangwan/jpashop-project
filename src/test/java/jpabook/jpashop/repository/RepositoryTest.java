package jpabook.jpashop.repository;

import jakarta.persistence.OneToMany;
import jpabook.jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional
    @DisplayName("저장 테스트")
    void TestSave(){
        Member member = new Member();
        member.setName("test");
        memberRepository.save(member);
        Long id = memberRepository.findOne(member.getId()).getId();

        assertThat(id).isNotNull();

    }

}
