<?php

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

if($hb_db->HeartbeatUpdate(84756, 3, 87)) echo 'Heartbeat successful.';
else echo 'error';

if( isset($_REQUEST['emie_heartbeat']) /*&& isset($_REQUEST['UID']) && isset($_REQUEST['lat']) && isset($_REQUEST['long'])*/ ) :

echo 'good';

else: 

//echo 'bad';
endif;






ShowNotices();
$hb_db->AddSystemNoticesToLog();
?>