package com.lym.gd.repository;

import com.lym.gd.entity.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author liuyaming
 * @date 2018/3/11 下午6:12
 */
public interface StudentCourseRepository extends JpaRepository<StudentCourse,String> {

    /**
     * 某用户是否已选该门课程
     * @param courseId
     * @param studentId
     * @return 如果是null说明没选
     */
    StudentCourse findByCourseIdAndStudentId(String courseId,String studentId);

    /**
     * 通过学生id以及选课状态查找
     * @param studentId
     * @param courseStatus
     * @return
     */
    List<StudentCourse> findByStudentIdAndCourseStatus(String studentId,String courseStatus);

    List<StudentCourse> findByCourseId(String courseId);
}
