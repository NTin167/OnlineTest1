package com.ptithcm.onlinetest.service;

import com.ptithcm.onlinetest.model.Take;
import com.ptithcm.onlinetest.repository.TakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TakeService implements ITakeService{
    @Autowired
    TakeRepository takeRepository;
    @Override
    public Take addTake(Take take) {
        return takeRepository.save(take);
    }
}
