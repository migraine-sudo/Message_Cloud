package com.chinasofti.dao;


import com.chinasofti.bean.Message;
import com.chinasofti.utils.JDBCUtil;

import net.sf.json.JSONObject;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MessageDao {
    int flag=0;
    public boolean InsertMsg(String message,String username){
        Connection connection= JDBCUtil.getConnection();
        String sql2="select * from message where message= ? and username= ? ;";
        PreparedStatement preparedStatement;
        try {
        	//判断信息是否上传过
        	preparedStatement=connection.prepareStatement(sql2);
            preparedStatement.setString(1,message);
            preparedStatement.setString(2, username);
            ResultSet resultSet;
            resultSet=preparedStatement.executeQuery();
        	if(resultSet.next())
        		System.out.println("收到重复信息");
        		return false;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        String sql="insert into message values(null,?,?);";
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(2,message);
            preparedStatement.setString(1,"unknow");
            flag=preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResultSet resultSet;
        String msg_id = null;
        String sql3="select MAX(message_id) from message;";
        try {
            preparedStatement=connection.prepareStatement(sql3);
            resultSet=preparedStatement.executeQuery();
           
            while (resultSet.next()){
                msg_id=resultSet.getString(1);
            }
            System.out.println("get msg_id");
        } catch (Exception e) {
            //message.setCode(100);
        	System.out.println("fail to get msg_id");
            e.printStackTrace();
        }
            
        String sql4="select userid from userm where username= ? ;";
        String userid = null;
        try {
            preparedStatement=connection.prepareStatement(sql4);
            preparedStatement.setString(1, username);
            resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){          	
                 userid=resultSet.getString(1);
                 System.out.println("get user_id");
            }
        } catch (Exception e) {
            //message.setCode(100);
        	System.out.println("fail to get user_id");
            e.printStackTrace();
        }
        System.out.print("userid="+userid+"  msg_id="+msg_id);
        
        String sql5="insert into um values(?,?);";
        try {
            preparedStatement=connection.prepareStatement(sql5);
            preparedStatement.setString(2,msg_id);
            preparedStatement.setString(1,userid);
            flag=preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        return flag!=0;
    }
    public ArrayList <Message> ReadMessage(String username) {
        
        String sql="select message_id,message from message where message_id in(select message_id from userm,um where userm.userid=um.userid and username=?);";
        Connection connection= JDBCUtil.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        ArrayList <Message> messageList=new ArrayList<Message>();
        //Message message=new Message();
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,username);

            resultSet=preparedStatement.executeQuery();
           
            while (resultSet.next()){
            	Message message=new Message();
                String msg_id=resultSet.getString(1);
                String msg=resultSet.getString(2);
                
                System.out.println("msg="+msg);
                //璧嬪��
                message.setMessage(msg);
                message.setCode(200);
                messageList.add(message);
            }
        } catch (Exception e) {
            //message.setCode(100);
            e.printStackTrace();

        }
        return messageList;
    }
    public boolean deleteMsg(String phone){
        Connection connection= JDBCUtil.getConnection();
        String sql="delete from message where phone=?;";
        PreparedStatement preparedStatement;
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,phone);
            flag=preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag!=0;
    }
}
