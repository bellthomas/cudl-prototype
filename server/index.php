<?php 
/*
 * Initiate Global Variables and Definitions
 */
 
global $Notices;
global $db;

define(DEBUG, false);


/*
 * Include Helper Functions
 */
require_once('library/functions.helper.php');

/*
 * Require DB Class and Instantiate
 */
require_once('library/class.database.php');
$db = new EmergencieDatabase;


/*
 * Require Request Class and Instantiate
 */
require_once('library/class.request.php');
$request = new EmergencieRequest;



PrettyPrint($_REQUEST);


ShowNotices();
$db->AddSystemNoticesToLog();