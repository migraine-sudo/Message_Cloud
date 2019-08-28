package com.chinasofti.dao;

import com.chinasofti.bean.User;
import com.chinasofti.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {
    int flag=0;

    public boolean registUser(String username,String password){
        Connection connection= JDBCUtil.getConnection();
        String sql="insert into userm values(null,?,?)";
        String sql2="select * from userm where username=?;";
        PreparedStatement preparedStatement;
        try {
        	//判断用户是否注册过
        	preparedStatement=connection.prepareStatement(sql2);
            preparedStatement.setString(1,username);
            ResultSet resultSet;
            resultSet=preparedStatement.executeQuery();
        	if(resultSet.next())
        		return false;
        	
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);

            flag=preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag!=0;
}

    public User querryUser(String username, String password) {
        User user=new User();
        String sql="select * from userm where username= ? and password= ? ;";
        Connection connection= JDBCUtil.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);

            resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                String id=resultSet.getString(1);
                String userName=resultSet.getString(2);
                String passWord=resultSet.getString(3);
                //璧嬪�糩
                user.setUserid(id);
                user.setUsername(userName);
                user.setPassword(passWord);
                user.setCode(200);
            }
        } catch (Exception e) {
            user.setCode(100);
            e.printStackTrace();

        }
        return user;
    }

    public boolean updatePassword(String username,String Oldpassword,String Newpassword){
        Connection connection= JDBCUtil.getConnection();
        String sql="update userm set password=? where username=? and password=?;";
        System.out.println(sql);
        PreparedStatement preparedStatement;
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,Newpassword);
            preparedStatement.setString(2,username);
            preparedStatement.setString(3,Oldpassword);
            flag=preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag!=0;
    }

    public boolean delete(String username){
        Connection connection= JDBCUtil.getConnection();
        String sql="delete from userm where username=?;";
        PreparedStatement preparedStatement;
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            flag=preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag!=0;
    }
}
