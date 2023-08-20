package org.zerock.guestbook.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.entity.QGuestbook;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GuestbookRepositoryTest {
    @Autowired
    private GuestbookRepository guestbookRe1pository;

    @Test
    public void insertDummies(){
        IntStream.rangeClosed(1, 300).forEach(i ->{
            Guestbook guestbook = Guestbook.builder()
                            .title("Title..." + i)
                            .content("content..." + i)
                            .writer("user..." + (i % 10))
                            .build();
            System.out.println(guestbookRe1pository.save(guestbook));
        });
    }

    @Test
    public void updateTest(){
        Optional<Guestbook> result = guestbookRe1pository.findById(301L);

        if(result.isPresent()){
            Guestbook guestbook = result.get();
            guestbook.changeTitle("Change Title v_v");
            guestbook.changeContent("change Content v_v");

            System.out.println(guestbookRe1pository.save(guestbook));
        }
    }

    @Test
    public void testQuery1(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
        QGuestbook qGuestbook = QGuestbook.guestbook; // 1. title, content 함수를 사용하기 위해 호출
        String keyword = "3";
        BooleanBuilder builder = new BooleanBuilder(); //where문에 들어가는 조건들을 넣어주는 컨테이너라고 간주
        BooleanExpression expression = qGuestbook.title.contains(keyword); //원하는 조건을 결합해서 생성

        builder.and(expression); //만들어진 조건은 where문에 and나 or같은 키워드와 결합시킨다.
        Page<Guestbook> result = guestbookRe1pository.findAll(builder, pageable);

        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }

    @Test
    public void testQuery2(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook;
        String keyword = "3";

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression exTitle = qGuestbook.title.contains(keyword);
        BooleanExpression exContent = qGuestbook.content.contains(keyword);
        BooleanExpression exAll = exTitle.or(exContent);

        builder.and(exAll);

        builder.and(qGuestbook.gno.gt(301L));

        Page<Guestbook> result = guestbookRe1pository.findAll(builder, pageable);

        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }
}