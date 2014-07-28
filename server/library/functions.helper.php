<?php
/*
 * Helper Functions definition
 *
 */

function PrettyPrint($content) {
	echo '<pre>';
	print_r($content);
	echo '</pre>';	
}

function ShowNotices() {
	if(DEBUG) 
		PrettyPrint($GLOBALS['Notices']);
}
