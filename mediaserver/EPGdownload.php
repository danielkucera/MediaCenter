<?php

include("config.php");

mysql_connect($dbserver,$username,$password);

@mysql_select_db($database) or die( "Unable to select database");

set_time_limit ( 3000 );

$dom=new DOMDocument;
// load XML data from existing file
$dom->load('http://dl.dropbox.com/u/441189/tv/xmltv/sk/sk.xmltv');

$xml = simplexml_import_dom($dom);

if (false){  //channels
    mysql_query("truncate channels");

    for($i=0; $i< count($xml->channel) ; $i++){
	    $query = "INSERT INTO channels VALUES ('".$xml->channel->{$i}->attributes()->id.
	    "','".$xml->channel->{$i}->{'display-name'}.
	    "','".$xml->channel->{$i}->icon->attributes()->src.
	    "','')";
	    mysql_query($query);
	    echo " ch:".$i;
	    ob_flush();
    }
} 

if (true){ //programmes
    mysql_query("truncate epg");

    for($i=0; $i< count($xml->programme) ; $i++){
	$query = "INSERT INTO epg VALUES ('".$xml->programme->{$i}->attributes()->start.
	"','".$xml->programme->{$i}->attributes()->stop.
	"','".$xml->programme->{$i}->attributes()->channel.
	"','".$xml->programme->{$i}->title.
	"','".$xml->programme->{$i}->desc.
	"','')";
	mysql_query($query);
	echo " p:".$i;
	ob_flush();
    }
} 

mysql_close();

?>
