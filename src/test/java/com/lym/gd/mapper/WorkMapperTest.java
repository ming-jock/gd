package com.lym.gd.mapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lym.gd.DTO.UserWorkCourseDTO;
import com.lym.gd.repository.WorkRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author liuyaming
 * @date 2018/4/22 下午2:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("com.lym.gd.mapper")
public class WorkMapperTest {

    @Autowired
    private WorkMapper workMapper;

    @Test
    public void getAll() {

        PageHelper.startPage(1,0);

        List<UserWorkCourseDTO> userWorkCourseDTOS = workMapper.getNormalWorkByStudent("456");

        PageInfo<UserWorkCourseDTO> pageInfo = new PageInfo<>(userWorkCourseDTOS);

        userWorkCourseDTOS.forEach(System.out::println);
    }

    @Test
    public void getNormalWorkByWorkIdTest(){
        System.out.println(workMapper.getNormalWorkByWorkId("work4caf246408694138bb378c88a06aae19"));
    }

    @Test
    public void getUserAndStudentWorkByWorkIdTest(){
        System.out.println(workMapper.getUserAndStudentWorkByWorkId("work4caf246408694138bb378c88a06aae19"));
    }
}