<?php
// not a valid request
//if( !isset($_REQUEST['emie_heartbeat'])) die();


global $hb_db;

// import db credentials
require_once('../git-ignore/credentials.php');


/*
 * Include Helper Functions
 */
require_once('../library/functions.helper.php');


/*
 * Require DB Class and Instantiate
 */
require_once('../library/class.database.php');
$hb_db = new EmergencieDatabase('heartbeat');

//if($hb_db->HeartbeatUpdate(84756, 23, 87)) echo '1';
//else echo '0';

if( isset($_REQUEST['emie_heartbeat']) ) :

	$request_data = json_decode($_REQUEST['emie_heartbeat']);
	//PrettyPrint($request_data);
	
	//$request_data->emie_heartbeat;  // array, 2 values, uuid stuff
	//$request_data->emie_location;  // array, 2 values, lat and long
	
	
	if($hb_db->HeartbeatUpdate($request_data->emie_heartbeat[0] . $request_data->emie_heartbeat[1], 
	$request_data->emie_location[0], 
	$request_data->emie_location[1])) echo 1;
	
	else echo 0;


else: 
echo 1;

endif;

ShowNotices();
$hb_db->AddSystemNoticesToLog();
?>