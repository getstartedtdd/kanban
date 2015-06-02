package com.kanban.controller;

import com.kanban.core.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

/**
 * Created by L.x on 15-5-29.
 */
@RestController
public class UserController {

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List list() {
        return Collections.emptyList();
    }

}
