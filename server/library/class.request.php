<?php
/**
 * Create Emergencie's Request Object.
 */
 
class EmergencieRequest {
	
	public $request_type;
	public $request_notices = NULL;
	protected $allowed_request_types = array('LatLongToLocal',
											);
											
	protected $geonames_username = 'harribellthomas';
	public $translated_postcode = NULL; // null normally, but if lat/long to postcode conversion happens, this holds the return data.
	
	
	/**
	 * Create New Request
	 *
	 * @param string $type Check if this action is valid.
	 * @return bool TRUE if valid request, FALSE if not.
	 */
	function __construct( $type ) {
		
		// If no database connection, abort.
		if(!$GLOBALS['db'])
			exit();
			
		if(in_array($type, $this->allowed_request_types)) {
			$this->request_type = $type;
			return TRUE;
		} else 
			return FALSE;
	}



	/**
	 * Retrieve Request Notices
	 *
	 * @return string array $request_notices if notices are present, or FALSE if all is sparky.
	 */
	function GetNotices() {
		if($this->request_notices)
			return $this->request_notices;
		else
			return FALSE;
	}



	/**
	 * Retrieve parameters
	 *
	 * @return depends on parameters...
	 */
	function GetParameter($name) {
		if(isset($this->$name)) return $this->$name;
		else return FALSE;
	}



	/**
	 * Assign parameters and generate request URL.
	 *
	 * @param string array $parameters Array of parameters to be parsed.
	 * @return string $RequestURL, valid and to be executed.
	 */

	function RequestParameters($parameters = NULL) {
		$RequestURL = '';
		switch ($this->request_type) :
		
			/**
	 		 * Case: LatLongToLocal
			 *
			 * @param float/integer $parameters['lat'] (Latitude for Request), float/integer $parameters['long'] (Longitude for Request)
			 */
			case 'LatLongToLocal' :
				if($parameters) :
					if(isset($parameters['lat']) && isset($parameters['long'])) {
						if($this->AreLatAndLongValid($parameters['lat'], $parameters['long']))
							$RequestURL = 'http://api.geonames.org/findNearbyPlaceNameJSON?lat='.$parameters["lat"].'&lng='.$parameters["long"];
						else
							$this->request_notices[] = 'LatLongToLocal - lat and long set but not valid.';	
					} else {
						$this->request_notices[] = 'LatLongToLocal - lat and long not set, but parameters variable passed.';	
					}
				else :
					$this->request_notices[] = 'LatLongToLocal - no parameters set.';
				endif;
				break;
		
		endswitch;
		
		if($RequestURL !== '') $RequestURL = $RequestURL . '&username=' . $this->geonames_username;
		
		return $RequestURL;
	}
	
	
	/**
	 * Execute Generated URL
	 *
	 * @param string $URL URL to be cURL-ed
	 * @return string $output, cURL return data
	 */

	function Execute($URL) {
		
		$output = FALSE;
		
		if(isset($URL) && filter_var($URL, FILTER_VALIDATE_URL)) :
		
			$ch = curl_init(); 
			curl_setopt($ch, CURLOPT_URL, $URL); 
			curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1); 
			$output = curl_exec($ch); 
			curl_close($ch);    

		else:
			$this->request_notices[] = 'Execute - URL passed is NOT valid. No cURL retrieval attempted. URL is ' . $URL;
		endif;
		
		return $output;
	}
	
	
		
	/**
	 * Are Latitude and Longitude Values Valid?
	 *
	 * @param float/integer $lat (Latitude), float/integer $long (Longitude)
	 * @return TRUE if both params are valid, FALSE if not.
	 */

	function AreLatAndLongValid($lat, $long) {
		$valid = true; // innocent until proved guilty
		
		if(!is_numeric($lat) || (int)$lat > 90 || (int)$lat < -90 )
			$valid = false;
		if(!is_numeric($long) || (int)$long > 180 || (int)$long < -180 )
			$valid = false;
			
		return $valid;
	}
	
	
		
	/**
	 * Convert Latitude and Longitude to Postal Code
	 *
	 * @param float/integer $lat (Latitude), float/integer $long (Longitude)
	 * @return TRUE if successful conversion, string if not. (NOTE to check for success need to use === )
	 *
	 * extension to do - cache conversion.
	 */

	function LatLongToPostCode($lat, $long) {
		if(isset($lat) && isset($long)) {
			if( $this->AreLatAndLongValid($lat, $long) ) {
				$RequestURL = 'http://api.geonames.org/findNearbyPostalCodesJSON?lat='.$lat.'&lng='.$long.'&username='. $this->geonames_username;
				$return = json_decode($this->Execute($RequestURL));
				$this->translated_postcode = $return->postalCodes[0];
				return TRUE;
			} else {
				return 'Invalid Latitude and Longitude';	
			}
		} else { 
			return 'Latitude and Longitude not set.';
		} 
	}
		
}
