<?php

$response = array();

if(isset($_POST['id']) && isset($_POST['location']) && isset($_POST['timings'])){

	$id = $_POST['id'];
	$location = $_POST['location'];
	$timings = $_POST['timings'];

	require_once __DIR__ . '/db_connect.php';

	$db = new DB_CONNECT();

	$result = mysql_query("INSERT INTO locations(id,location,timings) VALUES('$id', '$location', '$timings')");

	if($result){
		$response['success'] = 1;
		$response['message'] = "Created Row";
	
		echo json_encode($response);
		return json_encode($response);
	
	}
	else{
		$response["success"] = 0;
		$response["message"] = "Error inserting.";
		echo json_encode($response);
		return json_encode($response);
	}
}
else{
	$response["success"] = 0;
    	$response["message"] = "Missing parameters";
 
    	echo json_encode($response);
	return json_encode($response);
}


?>