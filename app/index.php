<?php
    include('db.php');

//       아레코드는 json으로 사용할시에 주석처리
       header('Content-Type: application/json');
    $sql = "select * from memos order by idx asc";
    $i = 0;
    $re = $db->query($sql);

    $memorows = array();
    foreach($re as $data){
        $memoColums = array(
            "idx" => $data['idx'], 
            "Time" => $data['memoTime'], 
            "Title" => $data['memoTitle'], 
            "Content" => $data['memoContent']
        );
        array_push($memorows,$memoColums);
    }
    $JsonData = array(
        "memoCounts" => count($memorows),
        "memos" => $memorows
    );
        
    $jsonout = json_encode($JsonData);
//    $json_String = json_encode($jsonout, JSON_PRETTY_PRINT);
    echo $jsonout;
?>