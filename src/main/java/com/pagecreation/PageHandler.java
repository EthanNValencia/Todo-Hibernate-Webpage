/*
Ethan Nephew
Due: October 11, 2021
PageHandler.java
*/

package com.pagecreation;

import java.util.List;
import com.hibernate.AccessorFacade;
import com.hibernate.ToDoAbstractFactory;

/***
 * This class is behind the front page. It populates the rows in the table. It also initiates data deletion and addition. 
 * @author Ethan J. Nephew
 */
public class PageHandler {
	
	/***
	 * Conducts basic data checks. If the data passes the checks 
	 * @param priority
	 * @param description
	 * @param completeDate
	 */
	public static void addItem(String priority, String description, String completeDate) {
		if(description != null && priority != null && completeDate != null) {
			if(description != "" && priority != "" && completeDate != "") {
				AccessorFacade accessor = new AccessorFacade();
				accessor.addToDoItem(priority, description, completeDate);
			}
		}
	}
	
	/***
	 * Deletes the specified item based on database id.
	 * @param id Requires the database id.
	 */
	public static void deleteItem(String id) {
		if(id != null) {
			if(id != "") {
				AccessorFacade accessor = new AccessorFacade();
				long deleteId = Long.parseLong(id);
				accessor.deleteToDoItem(deleteId);
			}
		}
	}
	
	/***
	 * Method that calls the accessor facade and returns the prepared html row code.
	 * @return returns the prepared html row code.
	 */
	public static String addRows() {
		AccessorFacade accessor = new AccessorFacade();
		return accessor.getToDoItemList();
	}
		
}
