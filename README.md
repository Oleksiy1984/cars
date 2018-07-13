Summary Task4 - rental cars.
Build project management tool - Apache Maven.
Use Client-side web libraries (jQuery, Bootstrap, datatables) in WebJars.
Unit Testing  with JUnit and Mockito. Use in-memory H2 database in development profile.
You can reserve car even if it has been already reserved in case this vehicle is available at required time span.
Admin also can uploaded car images.
Locale support  russian and english.
Interactive sort in all tables.
Controller consist of only single servlet. This servlet implements front controller pattern. It delegates logic implementation to action classes.
Executing the action classes return logical name of views or redirect in case of a view change as result of the action (PRG pattern).
The Action follow the Strategy pattern. It do the work based on the passed-in arguments of the abstract method execute.
From the DAOFactory (located into the servlet context for global use in the application) I have access to every DAO in the DAO layer using
 factory method getDAO.
