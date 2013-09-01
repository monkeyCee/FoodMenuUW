<?php

$response = array();

if( 	isset($_POST['diet_type']) && 
	isset($_POST['name']) && 
	isset($_POST['pid']) &&
   	isset($_POST['ingredients']) &&
	isset($_POST['servingsize']) &&
	isset($_POST['calories']) &&
	isset($_POST['totalfat']) &&
	isset($_POST['totalfatpercent']) &&
	isset($_POST['fatsaturated']) &&
	isset($_POST['fatsaturatedpercent']) &&
	isset($_POST['cholesterol']) &&
	isset($_POST['sodium']) &&
	isset($_POST['sodiumpercent']) &&
	isset($_POST['carbo']) &&
	isset($_POST['carbopercent']) &&
	isset($_POST['protein']) &&
	isset($_POST['vitamina']) &&
	isset($_POST['vitaminc']) &&
	isset($_POST['calcium']) &&
	isset($_POST['iron'])
){

	$diet = mysql_real_escape_string($_POST['diet_type']);
	$name = mysql_real_escape_string($_POST['name']);
	$pid = mysql_real_escape_string($_POST['pid']);
	$ingredients = mysql_real_escape_string($_POST['ingredients']);
	$servingsize = mysql_real_escape_string($_POST['servingsize']);
	$calories = mysql_real_escape_string($_POST['calories']);
	$totalfat = mysql_real_escape_string($_POST['totalfat']);
	$totalfatpercent = mysql_real_escape_string($_POST['totalfatpercent']);
	$fatsaturated = mysql_real_escape_string($_POST['fatsaturated']);
	$fatsaturatedpercent = mysql_real_escape_string($_POST['fatsaturatedpercent']);
	$cholesterol = mysql_real_escape_string($_POST['cholesterol']);
	$sodium = mysql_real_escape_string($_POST['sodium']);
	$sodiumpercent = mysql_real_escape_string($_POST['sodiumpercent']);
	$carbo = mysql_real_escape_string($_POST['carbo']);
	$carbopercent = mysql_real_escape_string($_POST['carbopercent']);	
	$protein = mysql_real_escape_string($_POST['protein']);
	$vitamina = mysql_real_escape_string($_POST['vitamina']);
	$vitaminc = mysql_real_escape_string($_POST['vitaminc']);
	$calcium = mysql_real_escape_string($_POST['calcium']);
	$iron = mysql_real_escape_string($_POST['iron']);

	require_once __DIR__ . '/db_connect.php';

	$db = new DB_CONNECT();
	mysql_query("ALTER TABLE productInfo AUTO_INCREMENT = 1");

	$result = mysql_query("INSERT INTO productInfo VALUES((select id from restaurantMenu where item = '$pid'),
                                                        '$diet',
							'$name', 
							'$pid',
							'$ingredients',
							'$servingsize',
							'$calories',
							'$totalfat',
							'$totalfatpercent',
							'$fatsaturated',
							'$fatsaturatedpercent',
							'$cholesterol',
							'$sodium',
							'$sodiumpercent',
							'$carbo',
							'$carbopercent',
							'$protein',
							'$vitamina',
							'$vitaminc',
							'$calcium',
							'$iron')");
	
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
