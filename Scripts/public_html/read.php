<?php

$response = array();

require_once __DIR__ . '/db_connect.php';

$db = new DB_CONNECT();

if(isset($_GET["id"])){
	$id = $_GET["id"];
	$result = mysql_query("SELECT * FROM products WHERE id = '$id'");
	if(!empty($result)){
		
		$result = mysql_fetch_array($result);
		
		$row = array();
		$row["id"] = $result["id"];
		$row["name"] = $result["name"];
		$response["success"] = 1;
		
		$response["row"] = array();
		array_push($response["row"], $row);
		
		echo json_encode($response);
		return $response;
	}
	else {
            $response["success"] = 0;
            $response["message"] = "No product found";
            echo json_encode($response);
			return $response;
        }
}
else{
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
    echo json_encode($response);
}
	
?>