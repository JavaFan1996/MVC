package com.atguigu.mvcapp.dao;

import com.atguigu.mvcapp.domain.Customer;

import java.util.List;

public interface CustomerDAO{


    public List<Customer> getForListWithCriteriaCUstomer(CriteriaCustomer cc);


    public List<Customer> getAll();

    public void save(Customer customer);

    public Customer get(Integer id);

    public void delete(Integer id);

    /**
     * 返回与name相等的记录数
     * @param name
     * @return
     */
    public long getCountWithName(String name);



}
