<?php 
/**
 * Create Emergencie's Database Object.
 */
 
class EmergencieDatabase {
	
	public $database_object = NULL;
	public $database_host = '';
	public $database_name = '';
	public $database_user = '';
	public $database_password = '';
	public $db_prefix = 'emergencie_';
	
	
	function __construct() {
		
		$this->database_host = 'db537596565.db.1and1.com';
		$this->database_name = 'db537596565';
		$this->database_user = 'dbo537596565';
		$this->database_password = 'emErgency1984';
		
		try {
			$pdo = new PDO("mysql:host=".$this->database_host.";dbname=".$this->database_name."", $this->database_user, $this->database_password);
		}
		catch (PDOException $e) {
			exit($e . ' - Error Connecting To Database');
		}
		
		if($pdo) {
			$this->database_object = $pdo;
			$this->DatabaseSetup();
		} else {
			return FALSE;	
		}
	}
	
	
	/**
	 * Check if a table exists in the current database.
	 *
	 * @param string $table Table to search for.
	 * @return bool TRUE if table exists, FALSE if no table found.
	 */
	function TableExists($table) {
	
		// Try a select statement against the table
		// Run it in try/catch in case PDO is in ERRMODE_EXCEPTION.
		try {
			$result = $this->database_object->query("SELECT 1 FROM $table LIMIT 1");
		} catch (Exception $e) {
			// We got an exception == table not found
			$GLOBALS['Notices'][] = array('Table has not been found', time(), __FILE__);
			return FALSE;
		}
	
		// Result is either boolean FALSE (no table found) or PDOStatement Object (table found)
		return $result !== FALSE;
	}
	
	
	
	/**
	 * Create table
	 *
	 * @param string $table Table to search for.
	 * @return bool TRUE if table created, FALSE if not.
	 */
	function CreateTable($table, $columns) {
		global $Notices;
		// Try a execution
		// Run it in try/catch in case PDO is in ERRMODE_EXCEPTION.
		try {
			$createTable = $this->database_object->exec("CREATE TABLE $table ($columns)");
		} catch (Exception $e) {
			PrettyPrint($e);
			return FALSE;
		}
		PrettyPrint($createTable);
		
		if ($createTable) 
			$Notices[] = array("Table $table - Created!", time(), __FILE__);
			
		else {
			$Notices[] = array("Table $table not successfully created!", time(), __FILE__);
			//PrettyPrint($this->database_object->errorInfo());
		}
		// Result is either boolean FALSE (no table found) or PDOStatement Object (table found)
		return $result !== FALSE;
	}
	
	/**
	 * Setup Database Environment
	 *
	 * @return bool TRUE if no problems, FALSE if error occured.
	 */
	function DatabaseSetup() {
		
		$tables = array(
			array(
				'table_name' => $this->db_prefix . 'logs',
				'columns' => 'ID INT( 11 ) AUTO_INCREMENT PRIMARY KEY' 
							  /*'Prename VARCHAR( 50 ) NOT NULL, 
							  Name VARCHAR( 250 ) NOT NULL,
							  StreetA VARCHAR( 150 ) NOT NULL, 
							  StreetB VARCHAR( 150 ) NOT NULL, 
							  StreetC VARCHAR( 150 ) NOT NULL, 
							  County VARCHAR( 100 ) NOT NULL, 
							  Postcode VARCHAR( 50 ) NOT NULL, 
							  Country VARCHAR( 50 ) NOT NULL'*/,
			),
			array(
				'table_name' => $this->db_prefix . 'system_logs',
				'columns' => 'ID INT( 11 ) AUTO_INCREMENT PRIMARY KEY,
							  SystemTime VARCHAR( 50 ) , 
							  Message VARCHAR( 250 ),
							  SystemFile VARCHAR( 50 ) ',
			),
		);
		
		foreach($tables as $table) {
			if( !$this->TableExists($table['table_name']) )
				$this->CreateTable($table['table_name'], $table['columns']);
		}
	}
	
	
	
	/**
	 * Method to add a data row to a table
	 *
	 * @return bool TRUE if no problems, FALSE if error occured.
	 */
	 
	function AddSystemNoticesToLog() {
		global $Notices;
		foreach($Notices as $message) {
			$file = explode('emergencie', $message[2]);
			$SQL = "INSERT INTO `db537596565`.`".$this->db_prefix."system_logs` (`ID`, `SystemTime`, `Message`, `SystemFile`) VALUES (NULL, '".$message[1]."', '".$message[0]."', '".$file[1]."')";
			$InsertNotice = $this->database_object->exec($SQL);	
			unset($SQL);
			if(!$InsertNotice){}
				//die('');
		}
	}
	
	
	function getAllTableData($table) {
		$table = $this->db_prefix . $table;
            $query = $this->database_object->prepare("SELECT * FROM $table");
            $query->execute();
            return $query->fetchAll();
	}
	
	 
 }
 