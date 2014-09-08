package servlet;

import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SuppressWarnings("serial")
public abstract class BaseServlet extends HttpServlet {
	protected static ApplicationContext ctx = 
			new ClassPathXmlApplicationContext("applicationContext.xml");
}
