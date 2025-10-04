package com.itheima.langchain4j.Service;

import com.itheima.langchain4j.Mapper.VolunteerMapper;
import com.itheima.langchain4j.Pojo.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VolunteerService {
    @Autowired
    private VolunteerMapper volunteerMapper;
    //1.添加预约信息的方法
    public void insert(Reservation reservation) {
        volunteerMapper.insert(reservation);
    }
    //2.查询预约信息的方法(根据手机号查询)
    public Reservation findByPhone(String phone) {
        return volunteerMapper.findByPhone(phone);
    }

}
