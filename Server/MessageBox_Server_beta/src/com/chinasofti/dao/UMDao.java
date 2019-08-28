package com.chinasofti.dao;


import com.chinasofti.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UMDao {
    int flag=0;
    public boolean bangding(String username,String phone){
        Connection connection= JDBCUtil.getConnection();
        String sql="insert into um values(null,?,?);";
        PreparedStatement preparedStatement;
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,phone);

            flag=preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag!=0;
    }
}