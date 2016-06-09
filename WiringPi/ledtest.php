<?php

	ini_set('display_errors', 1);
	error_reporting(E_ALL);
	
	define("LED_TEST", "/usr/bin/sudo /home/pi/ledtest");
	define("ADDRESS", "192.168.11.61");
	define("USER", "pi");
	define("PASSWORD", "123");

	try {
		if(!is_null($_GET["num"])&&!is_null($_GET["stat"])) {
			$sconnection=ssh2_connect(ADDRESS, 22);
			ssh2_auth_password($sconnection, USER, PASSWORD);
			
			$command=LED_TEST." ".$_GET["num"]." ".$_GET["stat"];
			$stdio_stream=ssh2_exec($sconnection, $command);
		}
	} catch (Exception $e) {
		echo $e->getMessage();
	}
	
?>
