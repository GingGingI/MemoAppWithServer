<?php
    include('db.php');
    date_default_timezone_set('Asia/Seoul');
    
    $idx = urldecode($_POST['idx']);
    $title = urldecode($_POST['title']);
    $content = urldecode($_POST['content']);

    $sql = "Update memos set memoTitle='$title',memoContent='$content',memoTime=now() Where idx = '$idx' LIMIT 1;";

    $re = $db->query($sql);

    if($re){
        echo "200";
    }else{
        echo "";
    }
?>