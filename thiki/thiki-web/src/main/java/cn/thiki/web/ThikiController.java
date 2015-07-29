/*
 * Copyright (C), 2002-2015, 苏宁易购电子商务有限公司
 * FileName: ThikiController.java
 * Author:   14051173
 * Date:     2015-7-28 下午03:24:47
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package cn.thiki.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.thiki.dao.UserDao;
import cn.thiki.dmo.User;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author 14051173
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller
public class ThikiController {

	@Autowired
	private UserDao userDao;
	
    @RequestMapping("load")
    public @ResponseBody Object getJSON(Model model) {  
        List<String> tournamentList = new ArrayList<String>();
        tournamentList.add("this");
        tournamentList.add("is");
        tournamentList.add("restful");
        tournamentList.add("project");
        tournamentList.add("thiki");
        model.addAttribute("items", tournamentList);  
        model.addAttribute("status", 0);
        User user = new User();
        user.setUserName("xiaoming");
        user.setPassword("123456");
        user.setEmail("1@qq.com");
        userDao.insert(user);
        return model;  
    }
}
