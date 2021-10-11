/*
Ethan Nephew
Due: September 27, 2021
AccessorFacade.java
*/
package com.hibernate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/***
 * This class will be used as a Facade to create, display, and delete todoitems in the database.
 * @author Ethan J. Nephew
 */
public class AccessorFacade {

    private List<ToDoItem> toDoItems = new ArrayList<ToDoItem>();
    private SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(ToDoItem.class).addAnnotatedClass(Low.class).addAnnotatedClass(Medium.class).addAnnotatedClass(High.class).addAnnotatedClass(ToDo.class).buildSessionFactory();

    public void killSessionFactory(SessionFactory sessionFactory)
    {
        if (sessionFactory != null && sessionFactory.isOpen())
        {
        	sessionFactory.close();
        }
    }
    
    /***
     * Walks the user through the necessary steps to create a new to-do item. Valid to-do items will be inserted into the database. 
     */
    public void addToDoItem(String factoryType, String description, String completeDate) {
        ToDoAbstractFactory factory;
        factory = ToDoAbstractFactory.getFactory(factoryType);
        ToDoItem item = factory.createToDo();
        item.setDescription(description);
        item.setCompleteDate(completeDate);
        // Begin Session
        Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(item);
		session.getTransaction().commit();
		session.close();
		killSessionFactory(sessionFactory);
        // End Session
        System.out.println("To-do item has been added to the database.");
    }

    /***
     * Walks the user through the necessary steps to delete a database item.
     */
    public void deleteToDoItem(long id) {
    	// Begin Session
    	Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.createQuery("delete from ToDo where id=" + id).executeUpdate();
		session.getTransaction().commit();
		session.close();
		killSessionFactory(sessionFactory);
		// End Session
    }

    /***
     * Displays currently saved to-do items to the user and builds the ArrayList of items.
     */
    @SuppressWarnings("unchecked")
	public String getToDoItemList() {
        // Begin Session
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        toDoItems = session.createQuery("from ToDo").getResultList();
        List<ToDoItem> returnList = new ArrayList<>();
        
        for(int i = 0; i < toDoItems.size(); i++) {
		 	ToDoAbstractFactory factory = ToDoAbstractFactory.getFactory(toDoItems.get(i).getPriority());
		    ToDoItem item = factory.createToDo();
		    item.setDescription(toDoItems.get(i).getDescription());
		    item.setCompleteDate(toDoItems.get(i).getCompleteDate());
		    item.setId(toDoItems.get(i).getId());
		 	returnList.add(item);
        }
        
        session.getTransaction().commit(); 
        session.close();
        killSessionFactory(sessionFactory);
		// End Session
        if(returnList.isEmpty()){
            System.out.println("You have no items in your to-do list. Please begin by adding an item.");
        }
        
        String tableRows = "";
		for(int i = 0; i < toDoItems.size(); i++) {
			tableRows += "<form name=\"row" + i + "\" action=\"index.jsp\" method=\"post\">"
					+ "<tr>\r\n"
					+ "<td hidden><input type=\"text\" name=\"deleteID\" value=\"" + toDoItems.get(i).getId() + "\" size=\"5\" /></td>\n"
					+ "	<td>" + i + "</td>\r\n"
					+ "	<td>" + toDoItems.get(i).getDescription() + "</td>\r\n"
					+ "	<td class=\"" + toDoItems.get(i).getPriority().toLowerCase() + "\">" + toDoItems.get(i).getPriority() + "</td>\r\n"
					+ "	<td class=\"" + checkDate(toDoItems.get(i).getCompleteDate()) + "\">" + toDoItems.get(i).getCompleteDate() + "</td>\r\n"
					+ "	<td><input type=\"submit\" value=\"Delete\" /></td>\r\n"
					+ "</tr>\n"
					+ "</form>\n";
		}
        
        return tableRows;
    }

    /***
     * 
     * @param date
     * @return
     */
    public String checkDate(String date) {
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateInString = date;
        try {
        	Date today = new Date();
            Date inputDate = formatter.parse(dateInString);
            if(today.after(inputDate)) {
            	return "red";
            }
            return "green";
            
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "null";
    }
    
    
}
