package com.lym.gd.DTO;

import com.lym.gd.entity.StudentWork;
import com.lym.gd.entity.StudentWorkAttachment;
import com.lym.gd.entity.User;
import lombok.Data;

import java.util.List;

/**
 * @author liuyaming
 * @date 2018/5/2 上午10:00
 */
@Data
public class CheckWorkDTO {

    private User user;

    private StudentWork studentWork;

    private List<StudentWorkAttachment> studentWorkAttachments;

}
