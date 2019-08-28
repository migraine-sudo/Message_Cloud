package com.chinasofti.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chinasofti.bean.Message;
import com.chinasofti.dao.MessageDao;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class ReadMessageServlet
 */
@WebServlet("/readMessage")
public class ReadMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReadMessageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out =response.getWriter();
        
        ArrayList <Message> messageList=new ArrayList<Message>();
        messageList=new MessageDao().ReadMessage(request.getParameter("username"));
        //ππ‘Ïjson
        out.print("{\"package\":[");
        for(int i=0;i<messageList.size();i++)
        {
        	if(i!=0)
        	{
        		out.print(",");
        	}
        	Message message=messageList.get(i);
        	//out.println("msg="+message.getMessage().toString());
        	out.print(JSONObject.fromObject(message).toString());
        }
        out.print("]}");

    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
