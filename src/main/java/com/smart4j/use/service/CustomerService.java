package com.smart4j.use.service;

import com.smart4j.use.model.Customer;
import com.smart4j.use.util.DataBaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/17.
 */
public class CustomerService {
    private  final Logger LOGGER= LoggerFactory.getLogger(CustomerService.class);
    public List<Customer> getCustomerList(){
        Connection conn=null;
        List<Customer> customerList=new ArrayList<Customer>();
        try {
        conn= DataBaseHelper.getConnection();
        String sql="SELECT * FROM CUSTOMER";
        PreparedStatement statement=conn.prepareStatement(sql);
        ResultSet resultSet=statement.executeQuery();
        while (resultSet.next()){
            Customer customer=new Customer();
            customer.setId(resultSet.getLong("ID"));
            customer.setName(resultSet.getString("NAME"));
            customer.setEmail(resultSet.getString("EMAIL"));
            customer.setContact(resultSet.getString("CONTACT"));
            customer.setTelphone(resultSet.getString("TELPHONE"));
            customer.setRemark(resultSet.getString("REMARK"));
            customerList.add(customer);
        }
        } catch (SQLException e) {
            LOGGER.error("查询商户列表失败，请检查",e);
        }finally {
            DataBaseHelper.closeConnection(conn);
        }
        return  customerList;
    }
    public Customer getCustomer(long id){
        return null;
    }
    public boolean createCustomer( Map<String, Object> fieldMap){
        return false;
    }
    public boolean updateCustomer(long id, Map<String ,Object> map){
        return false;
    }
    public boolean deleteCustomer(long id){
        return false;
    }
}
