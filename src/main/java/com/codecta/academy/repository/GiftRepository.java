package com.codecta.academy.repository;

import com.codecta.academy.repository.entity.Gift;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional(Transactional.TxType.MANDATORY)
public class GiftRepository extends Repository<Gift, Integer> {
    public GiftRepository() {
        super(Gift.class);
    }
}
