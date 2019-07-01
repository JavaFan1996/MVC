package com.atguigu.mvcapp.servlet;

import com.atguigu.mvcapp.dao.CriteriaCustomer;
import com.atguigu.mvcapp.dao.CustomerDAO;
import com.atguigu.mvcapp.dao.impl.CustomerDAOJdbcImpl;
import com.atguigu.mvcapp.domain.Customer;
import com.mysql.cj.x.protobuf.MysqlxCrud;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class CustomerServlet extends HttpServlet {
    /**
     *
     */

    private CustomerDAO customerDAO = new CustomerDAOJdbcImpl();
    private static final long serialVersionUID = 1L;

    //	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//        String method = request.getParameter("method");
//        switch (method) {
//            case "add":
//                add(request, response);
//                break;
//            case "query":
//                query(request, response);
//                break;
//            case "delete":
//                delete(request, response);
//                break;
//        }
//
//    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        String methodName = servletPath.substring(1);
        methodName = methodName.substring(0, methodName.length() - 3);

        try {
            Method method= getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);


    }


    private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idstr = request.getParameter("id");
        int id = 0;
        try {
            id = Integer.parseInt(idstr);
            System.out.println(id);
            customerDAO.delete(id);


        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("query.do");


    }


    private void addCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");

        long count = customerDAO.getCountWithName(name);
        System.out.println(count);
        if (count > 0) {
            request.setAttribute("message","用户的名字已被占用,请重新输入");
            request.getRequestDispatcher("/newcustomer.jsp").forward(request,response);
            return;
        }else {
            Customer customer = new Customer(name, address, phone);
            System.out.println(customer);
            customerDAO.save(customer);
            response.sendRedirect("success.jsp");
        }


    }

    private void query(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, ServletException, IOException {

        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");

        CriteriaCustomer cc = new CriteriaCustomer(name, address, phone);
        List<Customer> customers = customerDAO.getForListWithCriteriaCUstomer(cc);

        request.setAttribute("customers",customers);
        request.getRequestDispatcher("/index.jsp").forward(request,response);





    }
}
