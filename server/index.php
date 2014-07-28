<?php 
/*
 * Establish database connection
 *
 *
 *
 */
 
require_once('library/class.database.php');
global $db;
$db = new EmergencieDatabase;

echo '<pre>';
print_r($db);
echo '</pre>';
 

 