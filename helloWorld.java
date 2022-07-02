package com.study.controller;

import com.study.dao.DepartmentDao;
import com.study.dao.EmployeeDao;
import com.study.pojo.Department;
import com.study.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;

/**
 * @-*- coding = utf-8 -*-
 * @Time : 2022/6/28 16:51
 * #@Author : 彭传
 * #@File : EmployeeController.java
 * #@Software :IntelliJ IDEA
 */
@Controller
public class EmployeeController {

    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    DepartmentDao departmentDao;

    //展示页面
    @RequestMapping("/list")
    public String list(Model model) {
        Collection<Employee> employees = employeeDao.getAll();

        System.out.println(new Date() + "|employees = " + employees);
        model.addAttribute("emps", employees);
        return "emp/list";
    }

    //查询信息给页面       提供下拉框信息展示
    @GetMapping("/toAdd")
    public String listAddGet(Model model) {
        //查询出所有的部门信息
        Collection<Department> departments = departmentDao.gatherDepartments();
        model.addAttribute("departments", departments);

        System.out.println("给页面的部门信息 = " + model);
        return "emp/add";
    }

    //添加员工  post方法
    @PostMapping("/toAdd")
    public String listAddPost(Employee employee, Model model) {
        System.out.println("添加员工 = post命令" + new Date());
        //添加员工 保存员工信息
        employeeDao.sase(employee);
        return "redirect:/list";  //重定向,并展示页面3
    }

    //去修改员工的页面   提供信息
    @GetMapping("/emp/{id}")
    public String toUpdate(@PathVariable("id") Integer id, Model model) {
        System.out.println(new Date() + "进入修改员工信息页面");

        //给页面员工的信息
        Employee employee = employeeDao.getEmployeeById(id);
        model.addAttribute("temp",employee);

        //查询出所有的部门信息
        Collection<Department> departments = departmentDao.gatherDepartments();
        model.addAttribute("departments", departments);

        return "emp/update";
    }

    //修改员工信息
    @PostMapping("/emp")
    public String toUpdateEnd(Employee employee, Model model) {

        System.out.println(new Date() + "修改员工信息并提交");
        employeeDao.sase(employee);


        return "redirect:/list";
    }



    @GetMapping("/detectEmp/{id}")
    public String detectEmp(@PathVariable("id") Integer id) {
        System.out.println(new Date()+"| 删除一个员工的id"+id);
        employeeDao.delete(id);  //删除员工

        return "redirect:/list";
    }

}
