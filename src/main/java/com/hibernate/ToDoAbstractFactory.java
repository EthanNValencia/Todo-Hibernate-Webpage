/*
Ethan Nephew
Due: September 27, 2021
AbstractFactory.java
*/
package com.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

/***
 * The parent abstract class for a ToDoItem.
 * @author Ethan J. Nephew
 */
@MappedSuperclass
abstract class ToDoItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="todo_description")
    private String description;
	@Column(name="complete_Date")
    private String completeDate;
	@Column(name="priority")
    protected String priority;
	
	/***
	 * Accessor method for the priority variable. 
	 * @return The priority Low, Medium, High, or Null.
	 */
    public String getPriority() {
		return priority;
	}

    /***
     * Mutator method for the priority variable. 
     * @param priority The string that will be assigned to priority. 
     */
	public void setPriority(String priority) {
		this.priority = priority;
	}

	/***
     * Accessor method for the ToDoItem ID.
     * @return The ToDoItem ID.
     */
    public Long getId() {
		return id;
	}
    
    /***
     * Mutator method for the ToDoItem ID.
     * @param id The ID of the ToDoItem.
     */
	public void setId(Long id) {
		this.id = id;
	}

	/***
     * Accessor method for the ToDoItem description.
     * @return The ToDoItem description.
     */
    public String getDescription() {
        return description;
    }

    /***
     * Mutator method for the ToDoItem description.
     * @param description The description of the ToDoItem.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /***
     * Accessor method for the ToDoItem completion time. The completion time can be a date, like "4-21-2022", but it can also be word like "Today".
     * @return The ToDoItem completion time.
     */
    public String getCompleteDate() {
        return completeDate;
    }

    /***
     * Mutator method for the ToDoItem completion time.
     * @param completeDate The completion time can be a date, like "4-21-2022", but it can also be word like "Today".
     */
    public void setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
    }

    /***
     * Overridden toString from the base object. This is used for display list data of ToDoItems to the user.
     * @return Returns the string that contains data of the contained variables.
     */
    @Override
    public String toString() {
        return "[Description = " + description + ", Completion Date = " + completeDate + ", ID = " + id + ", ";
    }
}

/***
 * Child class definition of the Low. Low is a child class of the ToDoItem.
 */
@Entity
@Table(name = "todo_table")
class Low extends ToDoItem {

	/***
	 * Constructor for Low. Assigns the class name to the superclass name variable. 
	 */
	public Low() {
		super.priority = this.getClass().getSimpleName();
	}
	
    /***
     * Returns a string that draws data from the parent class definition.
     * @return Returns the description, completion date, and the name of the object type, which is Low.
     */
    @Override
    public String toString() {
        return super.toString() + "Priority = " + this.getClass().getSimpleName() + "]";
    }

}

/***
 * Child class definition of the Medium. Medium is a child class of the ToDoItem.
 */
@Entity
@Table(name = "todo_table")
class Medium extends ToDoItem {

	/***
	 * Constructor for Medium. Assigns the class name to the superclass name variable. 
	 */
	public Medium() {
		super.priority = this.getClass().getSimpleName();
	}
    /***
     * Returns a string that draws data from the parent class definition.
     * @return Returns the description, completion date, and the name of the object type, which is Medium.
     */
    @Override
    public String toString() {
        return super.toString() + "Priority = " + this.getClass().getSimpleName() + "]";
    }

}

/***
 * Child class definition of the High. High is a child class of the ToDoItem.
 */
@Entity
@Table(name = "todo_table")
class High extends ToDoItem {
	
	/***
	 * Constructor for High. Assigns the class name to the superclass name variable. 
	 */
    public High() {
		super.priority = this.getClass().getSimpleName();
	}

	/***
     * Returns a string that draws data from the parent class definition.
     * @return Returns the description, completion date, and the name of the object type, which is High.
     */
    @Override
    public String toString() {
        return super.toString() + "Priority = " + this.getClass().getSimpleName() + "]";
    }

}

/***
 * Child class definition of the Null. Null is a child class of the ToDoItem. This is used for creating a todo object that can be copied to. 
 */
@Entity
@Table(name = "todo_table")
class ToDo extends ToDoItem {
	
	/***
	 * Constructor for a ToDo object. 
	 */
    public ToDo() {
		
	}

}

/***
 * The toolkit that creates a Low priority ToDoItem.
 */
class LowToolkit extends ToDoAbstractFactory {

    /***
     * Method that returns a newly instantiated Low priority ToDoItem.
     * @return Returns a newly instantiated Low priority ToDoItem.
     */
    @Override
    public ToDoItem createToDo() {
        return new Low();
    }

}

/***
 * The toolkit that creates a Medium priority ToDoItem.
 */
class MediumToolkit extends ToDoAbstractFactory {

    /***
     * Method that returns a newly instantiated Medium priority ToDoItem.
     * @return Returns a newly instantiated Medium priority ToDoItem.
     */
    @Override
    public ToDoItem createToDo() {
        return new Medium();
    }

}

/***
 * The toolkit that creates a High priority ToDoItem.
 */
class HighToolkit extends ToDoAbstractFactory {

    /***
     * Method that returns a newly instantiated High priority ToDoItem.
     * @return Returns a newly instantiated High priority ToDoItem.
     */
    @Override
    public ToDoItem createToDo() {
        return new High();
    }

}

/***
 * The toolkit that creates a High priority ToDoItem.
 */
class NullToolKit extends ToDoAbstractFactory {

    /***
     * Method that returns a newly instantiated High priority ToDoItem.
     * @return Returns a newly instantiated High priority ToDoItem.
     */
    @Override
    public ToDoItem createToDo() {
        return new ToDo();
    }

}

/***
 * Abstract factory that is used to instantiate and return any of the priority level ToDoItem objects.
 */
public abstract class ToDoAbstractFactory {

    private static final LowToolkit LOW_TOOLKIT = new LowToolkit();
    private static final MediumToolkit MEDIUM_TOOLKIT = new MediumToolkit();
    private static final HighToolkit HIGH_TOOLKIT = new HighToolkit();
    private static final NullToolKit NULL_TOOLKIT = new NullToolKit();

    /***
     * This method is used to select the AbstractFactory that is needed to create the specified ToDoItem child.
     * @param priority The priority of the ToDoItem object is expressed with 1 (Low), 2 (Medium), 3 (High), 4 (Null).
     * @return Returns the AbstractFactory with the toolkit to build the specified ToDoItem.
     */
    static ToDoAbstractFactory getFactory(int priority) {
        ToDoAbstractFactory factory = null;
        switch (priority) {
            case 1:
                factory = LOW_TOOLKIT;
                break;
            case 2:
                factory = MEDIUM_TOOLKIT;
                break;
            case 3:
                factory = HIGH_TOOLKIT;
                break;
            case 4: 
            	factory = NULL_TOOLKIT;
            	break;
        }
        return factory;
    }
    
    /***
     * This is a zero parameter method that returns null as a factory type. 
     * @return Returns null. 
     */
    static ToDoAbstractFactory getFactory() {
        ToDoAbstractFactory factory;
        return factory = NULL_TOOLKIT;
    }
    
    /***
     * An alternative method of assigning a toolkit. Rather than using a number it uses the string of the ToDoItem subclass name. 
     * @param priority Requires the string of the name of the ToDoItem subclass (Low, Medium, or High). 
     * @return Returns the AbstractFactory with the toolkit to build the specified ToDoItem.
     */
    static ToDoAbstractFactory getFactory(String priority) {
        ToDoAbstractFactory factory = null;
        if(priority.toUpperCase().equals("LOW")) {
        	factory = LOW_TOOLKIT;
        } else if(priority.toUpperCase().equals("MEDIUM")) {
        	factory = MEDIUM_TOOLKIT;
        } else if(priority.toUpperCase().equals("HIGH")) {
        	factory = HIGH_TOOLKIT;
        } else if(priority.toUpperCase().equals("NULL")) {
        	factory = NULL_TOOLKIT;
        }
        return factory;
    }

    /**
     * Child classes must implement their own method that is used to instantiate and return a ToDoItem.
     * @return Returns a newly instantiated ToDoItem.
     */
    public abstract ToDoItem createToDo();
}
