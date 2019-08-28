package com.chinasofti.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import com.chinasofti.bean.User;
import com.chinasofti.dao.UserDao;
import com.chinasofti.bean.*;
import com.chinasofti.dao.*;
import net.sf.json.JSONObject;


/**
 * Servlet implementation class UserRegistServlet
 */
@WebServlet("/userRegist")
public class UserRegistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserRegistServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out =response.getWriter();
            UserDao registDao=new UserDao();
            String username=request.getParameter("username");
            String password=request.getParameter("password");

            boolean isRgist=registDao.registUser(username,password);

            User user=new User();
            if (isRgist) {
                user.setCode(200);
                user.setUsername(username);
                user.setPassword(password);
            }else {
                user.setCode(100);
            }
            out.print(JSONObject.fromObject(user));
        }
    

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
