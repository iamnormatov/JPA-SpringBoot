package com.example.jpa.util;

import com.example.jpa.model.Card;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class CardRepositoryImpl {

    private final EntityManager entityManager;

    public Page<Card> searchUserByMoreParams(Map<String, String> params){
        int page = 0, size = 10;
        if (params.containsKey("page")) {
            page = Integer.parseInt(params.get("page"));
        }
        if (params.containsKey("size")) {
            size = Integer.parseInt(params.get("size"));
        }

        String firstQuery = "select c from Card as c where true ";
        String secondQuery = "select count(cardId) from Card";

        StringBuilder firstBuildQuery = buildParam(params);

        Query queryOne = this.entityManager.createQuery(firstQuery + firstBuildQuery);
        Query queryTwo = this.entityManager.createQuery(secondQuery + firstBuildQuery);

        queryOne.setFirstResult(page * size);
        queryTwo.setMaxResults(size);

        setMoreQuery(params, queryOne);
        setMoreQuery(params, queryTwo);

        long total = (long) queryTwo.getSingleResult();

        return new PageImpl<Card>(queryOne.getResultList(), PageRequest.of(page, size), total);
    }

    private void setMoreQuery(Map<String, String> params, Query query){
        if (params.containsKey("id")){
            query.setParameter("id", params.get("id"));
        }
        if (params.containsKey("n")){
            query.setParameter("n", params.get("n"));
        }
        if (params.containsKey("cn")){
            query.setParameter("cn", params.get("cn"));
        }
        if (params.containsKey("t")){
            query.setParameter("t", params.get("t"));
        }
        if (params.containsKey("a")){
            query.setParameter("a", params.get("a"));
        }
    }

    private StringBuilder buildParam(Map<String, String> params){
        StringBuilder stringBuilder = new StringBuilder();
        if (params.containsKey("id")){
            stringBuilder.append(" and c.cardId = :id");
        }
        if (params.containsKey("n")){
            stringBuilder.append(" and c.cardName = :n");
        }
        if (params.containsKey("cn")){
            stringBuilder.append(" and c.cardNumber = :cn");
        }
        if (params.containsKey("t")){
            stringBuilder.append(" and c.type = :t");
        }
        if (params.containsKey("a")){
            stringBuilder.append(" and c.amount = :a");
        }
        return stringBuilder;
    }
}
