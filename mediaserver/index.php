<?php

include("config.php");

mysql_connect($dbserver,$username,$password);
@mysql_select_db($database) or die( "Unable to select database");

session_start();
$do=$_REQUEST['do'];

if ($do=="loginPass"){

	$sql = "SELECT pass FROM users WHERE uname='".$_REQUEST['username']."'";

	$result = mysql_query($sql);

	if ($row = mysql_fetch_assoc($result)) {
		if ($row['pass'] == md5($_REQUEST['pass'])) {
			$_SESSION['uname']=$_REQUEST['username'];
			die("SUCCESS");
		}
	}

	die("UNAUTHORIZED");

}

if ($do=="loginPhoto"){

	$fr   = @fopen('php://input', 'r');
	$fw   = @fopen('photos/'.time().'.jpg', 'w');

	if ($fr)
	{
	  while (!feof($fr))
	  {
	    $s = fread($fr, 1024);
	    fwrite($fw,  $s);
	  }
	  fclose($fr);
	  fclose($fw);
	}

	//possible to add function for user identification

	die("UNAUTHORIZED");

}

if (!isset($_SESSION['uname'])) die("UNAUTHORIZED");
$uname = $_SESSION['uname'];

if ($do=="playlistTV"){

	$sql = "SELECT mychans FROM users WHERE uname = '".$uname."'";
	
	$result = mysql_query($sql);

	if (!$result) {
	    die("FAILURE - cannot get mychans");
	}
	
	while ($row = mysql_fetch_assoc($result)) {
		$chans = explode(',',$row["mychans"]);
		$chans = implode("','",$chans);
	}

	mysql_free_result($result);

	$sql = "SELECT * FROM channels WHERE id IN ('".$chans."') ORDER BY FIELD (id,'".$chans."')";

	$result = mysql_query($sql);

	if (!$result) {
	    die("FAILURE - cannot select user channels");
	}

	echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<playlist version=\"1\" xmlns=\"http://xspf.org/ns/0/\">\n<trackList>\n";
	
	while ($row = mysql_fetch_assoc($result)) {

		echo "<track>\n";
		echo "<location>".$row["uri"]."</location>\n";
		echo "<title>".$row["name"]."</title>\n";
		echo "<annotation>";

		$sqlepg = "SELECT * FROM epg WHERE channel='".$row["id"]."' AND start LIKE CONCAT(CURDATE() + 0, '%') ORDER BY start";

		$resepg = mysql_query($sqlepg);

		$nextepg = mysql_fetch_assoc($resepg);

		while ($rowepg = $nextepg) {
			$nextepg = mysql_fetch_assoc($resepg);
			if (substr($nextepg["start"], 8, 4) > date("Hi")){
				echo substr($rowepg["start"], 8, 2).":".substr($rowepg["start"], 10, 2). " - ".substr($rowepg["stop"], 8, 2).":".substr($rowepg["stop"], 10, 2)." ";
				echo $rowepg["title"]."\n";
				echo substr($nextepg["start"], 8, 2).":".substr($nextepg["start"], 10, 2). " - ".substr($nextepg["stop"], 8, 2).":".substr($nextepg["stop"], 10, 2)." ";
				echo $nextepg["title"];
				break;
			}

		}

		echo "</annotation>\n";
		echo "<image>".$row["icon"]."</image>\n";
		echo "</track>\n";
	}


	echo "</trackList>\n</playlist>";

	mysql_free_result($result);
	
	die();

}

if ($do=="playlistVOD"){

	$sql = "SELECT * FROM vod ";

	$result = mysql_query($sql);

	if (!$result) {
	    die("FAILURE - cannot get vods");
	}

	echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<playlist version=\"1\" xmlns=\"http://xspf.org/ns/0/\">\n<trackList>\n";

	while ($row = mysql_fetch_assoc($result)) {

		echo "<track>\n";
		echo "<location>".$row["uri"]."</location>\n";
		echo "<title>".$row["title"]."</title>\n";
		echo "<annotation>".$row["desc"]."</annotation>\n";
		echo "<image>".$row["img"]."</image>\n";
		echo "</track>\n";
	}

	echo "</trackList>\n</playlist>";

	mysql_free_result($result);

	die();

}

if ($do=="getEPG"){

	$date=$_REQUEST['date'];
	$channel=$_REQUEST['channel'];

	$sql = "SELECT * FROM epg WHERE start LIKE '".$date."%' AND channel='".$channel."'";

	$result = mysql_query($sql);

	if (!$result) {
	    die("FAILURE - cannot get epg programs");
	}

	echo "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n<tv>\n";

	while ($row = mysql_fetch_assoc($result)) {
		echo "<programme start=\"".$row["start"]."\" stop=\"".$row["stop"]."\" channel=\"".$row['channel']."\">\n";
		echo "<title>".$row["title"]."</title>\n";
		echo "<desc>".$row["desc"]."</desc>\n";
		echo "</programme>\n";
	}

	$sql = "SELECT * FROM channels WHERE id='".$channel."'";

	$result = mysql_query($sql);

	if (!$result) {
	    die("FAILURE - cannot get epg channels");
	}

	while ($row = mysql_fetch_assoc($result)) {

		echo "<channel id=\"".$row["id"]."\">\n";
		echo "<display-name lang=\"sk\">".$row["name"]."</display-name>\n";
		echo "<icon src=\"".$row["icon"]."\" />\n</channel>\n";
	}

	echo "</tv>";

	mysql_free_result($result);

	die();

}

if ($do=="getProfile"){

	$sql = "SELECT * FROM users WHERE uname='".$_SESSION['uname']."'";

	$result = mysql_query($sql);

	if (!$result) {
	    die("FAILURE - cannot get user profile");
	}

	
	if ($row = mysql_fetch_assoc($result)) {

		echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		echo "<profile>\n";
		echo "	<uname>".$row['uname']."</uname>\n";
		echo "	<fullname>".$row['fullname']."</fullname>\n";
		echo "	<email>".$row['email']."</email>\n";
		echo "	<mychans>".$row['mychans']."</mychans>\n";
		echo "	<menu>".$row['menu']."</menu>\n";
		echo "	<invert_gravity>".$row['invert_gravity']."</invert_gravity>\n";
		echo "	<disable_gravity>".$row['disable_gravity']."</disable_gravity>\n";
		echo "</profile>";
	}
	
	die();

}

if ($do=="setProfile"){

	$var = $_REQUEST['var'];
	$val = $_REQUEST['val'];

	if ($var == "password"){
		$var = "pass";
		$val = md5($_REQUEST['val']);
	}

	$sql = "UPDATE users SET ".$var."='".$val."' WHERE uname='".$_SESSION['uname']."' LIMIT 1";

	$result = mysql_query($sql);

	if (!$result) {
	    die("FAILURE - cannot set profile variable");
	} else {
	    die("SUCCESS");
	}

}

if ($do=="getExtern"){
	
	$url=$_REQUEST['url'];
	readfile($url);

	die();

}

if ($do=="getChannels"){

	$sql = "SELECT id,name FROM channels ORDER BY name";

	$result = mysql_query($sql);

	if (!$result) {
	    die("FAILURE - cannot get list of channels");
	}

	echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
	echo "<channels>\n";	

	while ($row = mysql_fetch_assoc($result)) {


		echo "<channel>\n";
		echo "	<id>".$row['id']."</id>\n";
		echo "	<name>".$row['name']."</name>\n";
		echo "</channel>\n";
	}
	echo "</channels>";

	die();

}


