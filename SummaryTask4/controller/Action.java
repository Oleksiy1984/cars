package ua.nure.orlovskyi.SummaryTask4.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The Action should follow the Strategy pattern.
 * It needs to be defined as an interface type which should
 * do the work based on the passed-in arguments of the abstract method
 * (this is the difference with the Command pattern, where in the
 * abstract/interface type should do the work based on the arguments
 * which are been passed-in during the creation of the implementation.
 */
public interface Action {
	
    /**
     * Where the logic of the action is executed.
     
     *
     * @return a string representing the logical result of the execution.
     *        
     * @throws Exception thrown if a system level exception occurs.
     *                 
     */
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception;

  
}
