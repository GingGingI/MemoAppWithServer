<?php
    include('db.php');
    date_default_timezone_set('Asia/Seoul');

    $title = urldecode($_POST['title']);
    $content = urldecode($_POST['content']);
    
    $sql = "INSERT INTO memos set memoTitle='$title',memoContent='$content',memoTime=now()";

    $re = $db->query($sql);

    if($re){
    echo "200";
    }else{
    echo "";
    }
/*

    $date = date("Y-m-d H:i:s");

데이터 확인.
    echo "Data :
    title : $title
    content : $content
    date : $date
    ";*/
?>