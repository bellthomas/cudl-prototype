<?php 
/*
 * Initiate Global Variables and Definitions
 */
 
global $Notices;
global $db;
global $MainExecuted;
$MainExecuted = false;

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


$request = new EmergencieRequest('LatLongToLocal');

if($_GET['lat'] && $_GET['long']) {
	//if($request->LatLongToPostCode(53.298056, -2.988281) === TRUE) {
	$postcode = $request->LatLongToPostCode($_GET['lat'], $_GET['long']);
	if($postcode === TRUE) {
		$postcode_data = ($request->GetParameter('translated_postcode'));
		echo '<br>Latitude: ' . $postcode_data->lat;
		echo '<br>Longitude: ' . $postcode_data->lng;
		echo '<br>Postcode: ' . $postcode_data->postalCode;
		echo '<br>Place Name: ' . $postcode_data->placeName;
		echo '<br>Distance Away: ' . ($postcode_data->distance) * 1000 . 'm';
	}
	else {
		echo $postcode;	
	}
}
/*
 * Pass test data to Request object
 
 
$emie_actions_test = array('LatLongToLocal', 'LatLongToLocal');
$emie_parameters_test = array(
	array('lat' => 53.298056, 'long' => -2.988281 ),	// Liverpool
	array('lat' => 38.897676, 'long' => -77.036530 ),	// White House, Washington DC
);

PrettyPrint($emie_actions);
PrettyPrint($emie_parameters);
*/

/*
 * Loop through data, creating instantiated objects
 */
function MainExecuteRequest($emie_actions, $emie_parameters) {
	global $MainExecuted;
	if(sizeof($emie_actions) == sizeof($emie_parameters)) :
	
		$i = 0;

		foreach($emie_actions as $action) {
			
			//generate unique name based on iteration of loop
			$individual_variable_name = 'request' . $i; 
			
			//instantiated class with variable name
			$$individual_variable_name = new EmergencieRequest($emie_actions[$i]);
			
			
			
			//generate unique name for URL return variable
			$individual_request_url_name = $individual_variable_name . '_url';
			
			//assign parameters using iteration count to match the reflected arrays
			$$individual_request_url_name = $$individual_variable_name->RequestParameters($emie_parameters[$i]);
			
			
			
			//create individual output variable name
			$individual_output_name = $individual_variable_name . '_output';
			
			//get execute return, assign to unique variable
			$$individual_output_name = $$individual_variable_name->Execute($$individual_request_url_name);
			
			echo '<h3>'.$i.'</h3>';
			PrettyPrint(($$individual_variable_name));
			PrettyPrint(($$individual_request_url_name));
			PrettyPrint(json_decode($$individual_output_name));
			
			$i++;
		}
	else :
		echo 'Mismatched Actions and Parameters arrays - the lengths are different! Corrupt data request, whole request ignored.';
	endif;
	$MainExecuted = true;
}
if(!$MainExecuted && isset($emie_actions_test) && isset($emie_parameters_test))
	MainExecuteRequest($emie_actions_test, $emie_parameters_test);


/*
$LatLongToLocal = new EmergencieRequest('LatLongToLocal');
$parameters = array('lat' => 53.298056, 'long' => -2.988281 );
$ResuestURL = $LatLongToLocal->RequestParameters($parameters);
PrettyPrint($LatLongToLocal->Execute($ResuestURL));

$request_notices = $LatLongToLocal->GetNotices();
*/

PrettyPrint($request_notices);


ShowNotices();
$db->AddSystemNoticesToLog();