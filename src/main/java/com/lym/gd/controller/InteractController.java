package com.lym.gd.controller;

import com.alibaba.fastjson.JSONObject;
import com.lym.gd.service.InteractService;
import com.lym.gd.utils.IdUtils;
import com.lym.gd.utils.ResultVOUtil;
import com.lym.gd.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyaming
 * @date 2018/4/10 下午3:32
 */
@Controller
@Slf4j
public class InteractController {
    @Autowired
    private InteractService interactService;

    @Value("${hostAddress}")
    private String hostAddress;

    @GetMapping("/interact")
    public String interactView(String name, Model model,HttpSession httpSession){
        String channel = IdUtils.getUserAndSessionId(httpSession);

        model.addAttribute("className",name);
        model.addAttribute("channel",channel);

        return "other/interact";
    }


    /**
     * 判断是否存在课堂
     * @return
     */
    @GetMapping("/existClass")
    @ResponseBody
    public ResultVO existClass(){
        ResultVO resultVO = ResultVOUtil.success();
        Map<String,String> map = new HashMap<>();

        String value = interactService.existClass();

        String name = null;
        if (StringUtils.isNotEmpty(value)){
            name = interactService.getClassName();
        }

        map.put("name",name);

        resultVO.setData(map);

        return resultVO;
    }

    /**
     * 创建课堂（Redis中添加标识）
     * @param jsonObject
     * @return
     */
    @PostMapping("/startClass")
    @ResponseBody
    public ResultVO startClass(@RequestBody JSONObject jsonObject){
        ResultVO resultVO = ResultVOUtil.success();

        interactService.startClass(jsonObject.getString("name"));

        return resultVO;
    }

  /**
   * 课堂二维码
   *
   * @param response
   */
  @GetMapping("/classQRCode")
  public void QRCode(HttpSession httpSession ,HttpServletResponse response) throws IOException {

      String userAndSessionId = IdUtils.getUserAndSessionId(httpSession);

      String url = "http://" + hostAddress + ":8080/chat?h=" + userAndSessionId;

      url = url.replace("#", "%23");

      QRCode qrCode = QRCode.from(url);
      qrCode.withSize(500,500);
      ByteArrayOutputStream ByteArrayOutputStream = qrCode.to(ImageType.PNG).stream();
      response.setContentType("image/png");
      response.setContentLength(ByteArrayOutputStream.size());

      OutputStream outStream = response.getOutputStream();
      outStream.write(ByteArrayOutputStream.toByteArray());

      outStream.flush();
      outStream.close();


  }

    /**
     * 获取当前用户当前会话中已存在课堂的所有talks
     *
     * @return map
     */
    @GetMapping("/talks")
    @ResponseBody
    public Map<String, String> getTalks() {

        return interactService.getTalks();
    }

    /**
     * 删除指定键值对
     * @param jsonObject
     * @return
     */
    @DeleteMapping("/talk")
    @ResponseBody
    public ResultVO delTalk(@RequestBody JSONObject jsonObject){
        String id = jsonObject.getString("id");

        interactService.delTalk(id);

        return ResultVOUtil.success();
    }

    /**
     * 删除该课堂所有talk
     * @return
     */
    @DeleteMapping("/talks")
    @ResponseBody
    public ResultVO delTalks(){

        interactService.delTalks();

        return ResultVOUtil.success();
    }

    @PostMapping("/talk")
    @ResponseBody
    public ResultVO saveTalk(@RequestBody JSONObject jsonObject){

        interactService.saveTalk(jsonObject);

        log.info("将TALK存储至Redis中"+jsonObject.toJSONString());

        return ResultVOUtil.success();
    }

}
