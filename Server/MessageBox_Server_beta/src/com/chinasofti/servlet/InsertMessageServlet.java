package com.chinasofti.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chinasofti.bean.Message;
import com.chinasofti.dao.MessageDao;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class InsertMessageServlet
 */
@WebServlet("/insertMessage")
public class InsertMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertMessageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out =response.getWriter();

        MessageDao messageDao=new MessageDao();
        String message=request.getParameter("message");
        String phone=request.getParameter("username");

        boolean isRgist=messageDao.InsertMsg(message,phone);

        Message msg=new Message();
        if (isRgist) {
            msg.setCode(200);
            msg.setMessage(message);
            msg.setPhone(phone);
        }else {
            msg.setCode(100);
        }
        out.print(JSONObject.fromObject(msg));
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
