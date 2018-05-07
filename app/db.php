<?php
session_start();

try{
    $db = new PDO('mysql:host=127.0.0.1;dbname=androidmemo;charset=utf8;','root','');
    $db->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);
    $db->setAttribute(PDO::ATTR_EMULATE_PREPARES,false);
}catch(PDOEXception $e){
    die("An error founded".$e->getMessage());
}
?>