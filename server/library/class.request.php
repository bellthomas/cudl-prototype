<?php
/**
 * Create Emergencie's Request Object.
 */
 
class EmergencieRequest {
	
	public $request_type;
	protected $allowed_request_types = array('LatLongToLocal',
											);
	
	
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
}
