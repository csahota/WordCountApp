package com.github.charsahota.servlet;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.charsahota.client.WordCountClient;

/**
 * Servlet implementation class DocumentServlet.
 * 
 * Post method is used to submit text.
 * Get method is used to retrieve word data.
 * 
 * @author Char Sahota
 * @date Oct. 22, 2019
 *
 */
@WebServlet("/DocumentServlet")
public class DocumentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DocumentServlet() {
        super();
    }

	/**
	 * Retrieve the count for a word and the first and last times that word was entered.
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    	
    	String word = request.getParameter("word").toString();
		try {
			response.getWriter().write(WordCountClient.consumeJsonWord(word));
			
		} catch (IOException e) {

			e.printStackTrace();
			
		} catch(Exception e) {
			
		}
    }

	/**
	 * Submit text to process for word counts.
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		
		String words = request.getParameter("words").toString();
		
		try {
			response.getWriter().write(WordCountClient.produceMessage(words));
			
		} catch (IOException e) {

			e.printStackTrace();
			
		} catch(Exception e) {
			
		}
	}

}
