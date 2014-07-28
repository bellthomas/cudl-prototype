<?php 
/*
 *
 *
 *
 *
 */
 
 class EmergencieDatabase {
	
	public $database_object = NULL;
	public $database_host = '';
	public $database_name = '';
	public $database_user = '';
	public $database_password = '';
	
	
	function __construct() {
		try {
			$pdo = new PDO('mysql:host='. $this->database_host .';dbname='.$this->database_name, $this->database_name);
		}
		catch (PDOException $e) {
			exit('Error Connecting To DataBase');
		}
		
		if($pdo)
			$this->database_object = $pdo;
	}
	
	function getData() {
            $query = $this->pdo->prepare('SELECT * FROM database');
            $query->execute();
            return $query->fetchAll();
	}
	
	 
 }
 