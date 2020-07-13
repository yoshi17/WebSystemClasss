package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Objects;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/Keijiban")
public class Keijiban extends HttpServlet {

	private static int id = 0;

	private static final long serialVersionUID = 1L;

	public Keijiban() {
			super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("not used");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String action = request.getParameter("action");

		if (action.equals("リセット")) {
			terminateSession(request, response);
		} else if (action.equals("送信")) {
			submit(request, response);
		} else if (action.contentEquals("ダウンロード")) {
			download(request, response);
		}
	}

	ArrayList<String> mesgList = new ArrayList<String>();

	void submit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String msg = request.getParameter("msg");
		if(msg == "") {
			msg = "ここに本文を記入してください";
		}

		ArrayList<String> msgList = (ArrayList<String>) session.getAttribute("msgList");
		if(Objects.isNull(msgList)) {
			msgList = new ArrayList<String>();
			session.setAttribute("msgList", msgList);
		}

		msgList.add(0, msg);

		mesgList = msgList;

		RequestDispatcher dispatcher = request.getRequestDispatcher("/keijiban.jsp");
		dispatcher.forward(request, response);
	}

	void download(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		FileInputStream fis = null;
		InputStreamReader isr = null;

		OutputStream os = null;
		OutputStreamWriter osw = null;

		try {
			File file = new File("message.txt");
			try{
				  FileWriter filewriter = new FileWriter(file);

				  for(String mesg : mesgList) {
					  filewriter.write(mesg + "\n");
				  }

				  filewriter.close();
				}catch(IOException e){
				  System.out.println(e);
				}

			if (!file.exists() || !file.isFile()) {
			}

			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=" +
				new String("message.txt".getBytes("Windows-31J"), "UTF-8"));

			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis, "UTF-8");

			os = response.getOutputStream();
			osw = new OutputStreamWriter(os, "UTF-8");

			int i;
			while ((i = isr.read()) != -1) {
				osw.write(i);
			}
		} catch (FileNotFoundException e) {
		} catch (UnsupportedEncodingException e) {
		} catch (IOException e) {
		} finally {
			try {
				if (osw != null) {
					osw.close();
				}
				if (os != null) {
					os.close();
				}
				if (isr != null) {
					isr.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
			}
		}
	}

	void terminateSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		RequestDispatcher dispatcher = request.getRequestDispatcher("/keijiban.jsp");
		dispatcher.forward(request, response);
	}

}
