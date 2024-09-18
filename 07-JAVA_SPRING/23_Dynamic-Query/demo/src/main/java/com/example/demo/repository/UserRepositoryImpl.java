package com.example.demo.repository;

import com.example.demo.entity.QUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.example.demo.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<User> findUserByDynamic(String name, Integer age){
        QUser user = QUser.user;
        BooleanBuilder builder = new BooleanBuilder();
        if(name != null){
            builder.and(user.name.eq(name));
        }
        if(age != null){
            builder.and(user.age.eq(age));
        }

        return queryFactory.selectFrom(user)
                .where(builder)
                .fetch();

    }
}
