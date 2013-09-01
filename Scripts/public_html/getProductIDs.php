<?php

$response = array();

require_once __DIR__ . '/db_connect.php';
 
$db = new DB_CONNECT();

$result = mysql_query("SELECT item from restaurantMenu WHERE NOT item = '0'") or die(mysql_error());
 
if (mysql_num_rows($result) > 0) {
    $response["products"] = array();
    $product = array();
    while ($row = mysql_fetch_array($result)) {
        $product[] = $row["item"];	        
    }
    array_push($response["products"], $product);
    $response["success"] = 1;
 	
    echo json_encode($response);	
    return json_encode($product);
} else {

    $response["success"] = 0;
    $response["message"] = "No products found";
 
    echo json_encode($response);
    return json_encode($response);
}

?>
