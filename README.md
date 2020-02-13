# Privacy-preserving-web-app

### An Online Todo List Application

Link for the web dashboard: 

http://18.191.193.247/ToDoList/index.html

The online todo list application support multi-users to add, read, edit and delete their online todo lists which support user authentication and emoji inputs.

### Screenshots

The Login page /Main entrance:

<img src="images\loginPage.jpg" style="zoom:75%;" />

Registering options:

<img src="images\register.jpg" style="zoom:75%;" />

The main todo list:

<img src="images\main.jpg" style="zoom:75%;" />

Edit or remove items:

<img src="images\editing.jpg" style="zoom:75%;" />






#### File Structure

#### Backend

File tree for the backend part:

|TodoList: the application folder

||Src: Java source code

|||db

**||||DBConnection.Java**: Java interface for establishing connections with databases

**||||DBConnectionFactory.Java**: The tool class for choosing between different supported databases.

|||db.mysql

**||||MySQLConnection.Java**: Tool class for database adding, editing, reading and deleting operations.

**||||MySQLDBUtil.java**: Util class for database configuration information including hostname, port, etc.

**||||MySQLTableCreation.Java**: Helper class for creating tables in the database, according to the specific database schema.

|||entity

**||||Item.Java**: Java class for representing todo items. Each object represents a need to do item, contains information including User_id, item_id and the content of the item.

|||rpc

**||||Login.java**： Backend get and post requests handler for login purposes.

**||||Logout.java**： Backend get and post requests handler for logout purposes.

**||||Register.java**： Backend get and post requests handler for registering purposes.

**||||RocHelper.java**: Tool class for parsing Json and creating Json data.

**||||SearchItem.java**: Tool class for reading all the todo items from the database and parsing the data from the server.



#### Frontend

File tree for the frontend part:

|TodoList: the application folder

||WebContent: the front code part

|||scripts: the folder for all the scripts

**||||jquery.min.js**: Jquery v1.11.1 script

**||||md5.js**: java script for md5 hash function

**||||myjs.js**:java script for all the customized logic and operations for the todolist application

**|||Index.html**: The html file for the main extrance of the TodoList application

|||styles: folder for styles

||||fonts: folder for the fonts we used for the website.

**||||font-awesome.min.css**:Corresponding css file for the fonts we use.

**||||stye.css**: The css file for our todolist designs

