<?php
	define("LED_TEST", "usr/bin/sudo /home/pi/ledtest");
	define("ADDRESS", "IP Address");
	define("USER", "pi");
	define("PASSWORD", "password");

	if(!is_null($_GET["num"])&&!is_null($_GET["stat"])) {
		$sconnection=ssh2_connect(ADDRESS, 22);
		ssh2_auth_password($sconnection, USER, PASSWORD);

		$command=LED_TEST." ".$_GET["num"]." ".$_GET["stat"];
		$stdio_stream=ssh2_exec($sconnection, $command);
	}
?>