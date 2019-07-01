package com.atguigu.mvcapp.dao.impl;

import com.atguigu.mvcapp.dao.CriteriaCustomer;
import com.atguigu.mvcapp.dao.CustomerDAO;
import com.atguigu.mvcapp.dao.DAO;
import com.atguigu.mvcapp.domain.Customer;

import java.util.List;

public class CustomerDAOJdbcImpl extends DAO<Customer>  implements CustomerDAO {

    @Override
    public List<Customer> getForListWithCriteriaCUstomer(CriteriaCustomer cc) {

        String sql = "SELECT id,name,address,phone FROM customers WHERE name LIKE ? AND address LIKE ? AND phone lIKE ? ";
        return getForList(sql,cc.getName(),cc.getAddress(),cc.getPhone());
    }

    @Override
    public List<Customer> getAll() {
        String sql = "select id,name,address,phone from customers";
        return getForList(sql);
    }

    @Override
    public void save(Customer customer) {
        String sql = "INSERT INTO customers(name,address,phone) VALUES(?,?,?)";
        update(sql,customer.getName(),customer.getAddress(),customer.getPhone());

    }

    @Override
    public Customer get(Integer id) {
        String sql = "select id,name,address,phone from customers where id=?";
        return get(sql,id);
    }

    @Override
    public void delete(Integer id) {

        String sql = "delete from customers where id= ?";
        update(sql,id);
    }

    @Override
    public long getCountWithName(String name) {
        String sql = "Select count(id) from customers where name=?";
        return  getForValue(sql,name);
    }
}
