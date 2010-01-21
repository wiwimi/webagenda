<?

/*Check to see if the values from the form are filled out*/

if(!$_POST['username'] || !$_POST['password'])
{
	header("Location: index.html");
	exit;
}

if($_POST['username'] == "admin" && $_POST['password'] == "password")
{
	header("Location: ../wa_dashboard/dashboard.html");
}
else
{
	header("Location: index.html");
	exit;
}

/*Create the variables to hold the database information*/

$dbName = "databaseNameHere";

/*Table name that we're checking against*/

$dbTable = "tableNameHere";

/*Database username*/

$dbUsername = "databaseuserNameHere";

/*Database Password*/

$dbPassword = "databasePasswordHere";

$username = $_POST('[username]');
$password = $_POST('[password]');

/*This next block is to prevent SQL injection hacks*/
$username = stripslashes($username);
$password = stripslashes($password);

$username = mysql_real_escape_string($username);
$password = mysql_real_escape_string($password);

/*Connect to the Database*/

/*$connection = @mysql_connect("localhost", $dbUsername, $dbPassword) or die(mysql_error());*/

/*Select the database you want to access*/

/*$db = @mysql_select_db($dbName, $connection) or die(mysql_error());*/

/*Select statement from the database to see if the user is in the system*/

$sqlQuery = "SELECT * FROM $table_name WHERE username = '$username'
AND password = password('$password')";

/*Create a variable to hold the results of the SQL query*/

/*$result = @mysql_query($sqlQuery, $connection) or die(mysql_error());*/

/*Check the number of rows returned from the query*/

/*$num = mysql_num_rows($result);*/

/*If the number of rows is not equal to 0 then authenticate the user*/

/*if ($num != 0) 
{	
	//Create a session for the username and password
	session_register($username);
	session_register($password);
	
	session_start();
	if(!session_is_registered($username))
	{
		header("location:login.html");
	}
	else
	{
		//because the user is authenticated move them to the dashboard
		header("Location: ../wa_dashboard/dashboard.html");
	}
}
else
{
	header("Location: index.html");
	exit;
}*/
?>

