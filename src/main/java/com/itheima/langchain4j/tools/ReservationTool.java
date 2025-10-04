package com.itheima.langchain4j.tools;

import com.itheima.langchain4j.Pojo.Reservation;
import com.itheima.langchain4j.Service.VolunteerService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReservationTool {
    @Autowired
    private VolunteerService volunteerService;

    //1.工具方法: 添加预约信息
    @Tool("预约志愿填报服务")
    public void addReservation(
            @P("考生姓名") String name,
            @P("考生性别") String gender,
            @P("考生手机号") String phone,
            @P("预约沟通时间,格式为: yyyy-MM-dd'T'HH:mm") String communicationTime,
            @P("考生所在省份") String province,
            @P("考生预估分数") Integer estimatedScore
    ) {
        Reservation reservation = new Reservation(null, name, gender, phone, LocalDateTime.parse(communicationTime), province, estimatedScore);
        volunteerService.insert(reservation);
    }

    //2.工具方法: 查询预约信息
    //@Tool 注解标明这是查询工具及功能，@P 注解说明参数需要考生手机号，大模型会从用户输入提取手机号，自动传入方法，调用 reservationService 的查询方法获取并返回预约单，实现自动查询功能。
    @Tool("根据考生手机号查询预约单")
    public Reservation findReservation(@P("考生手机号") String phone) {return volunteerService.findByPhone(phone);}

}
