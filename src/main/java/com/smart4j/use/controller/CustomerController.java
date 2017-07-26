package com.smart4j.use.controller;

import com.smart4j.framework.annotation.Action;
import com.smart4j.framework.annotation.Controller;
import com.smart4j.framework.annotation.Inject;
import com.smart4j.framework.bean.View;
import com.smart4j.use.model.Customer;
import com.smart4j.use.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/7/17.
 */
@Controller
public class CustomerController {
    @Inject
    private CustomerService customerService;

    @Action("GET:/customerList")
    public View getCustomerList() throws ServletException, IOException {
        View view = new View("Hello.jsp");
        return view;
    }
}
