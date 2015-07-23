<?php
  /**
   * If you are looking to change the news lines, go to "newsContent.php"
   * And if your are looking to change the options, go to "newsOptions.php"
   * 
   * There is nothing to change here.
   */
  include 'newsContent.php';
  include 'newsOptions.php';
  $type = $_GET['type'];
  $page = $_GET['page'];

  //Options part
  echo $Tab1Title."#".$Tab1ButtonName."#".$Tab2Title."#".$Tab2ButtonName."#".count($content1)."#".count($content2)."/";

  //Content part
  //Left Tab
  if($type==0) {
    if($page<1) {
      echo $error2[0].($page+1).$error2[1]."/".$errorSpacing;
    }
    elseif($page>count($content1)) {
      echo $error2[0].($page+1).$error2[1]."/".$errorSpacing;
    }
    else {
      echo $content1[$page-1]."/".$spacing1[$page-1];
    }
  }
  //Right Tab
  elseif($type==1) {
    if($page<1) {
      echo $error2[0].($page+1).$error2[1]."/".$errorSpacing;
    }
    elseif($page>count($content2)) {
      echo $error2[0].($page+1).$error2[1]."/".$errorSpacing;
    }
    else {
      echo $content2[$page-1]."/".$spacing2[$page-1];
    }
  }
  //Unknown Tab
  else {
    echo $error1[0].$type.$error1[1]."/".$errorSpacing;
  }

  /**
   * Made by AziasCreations
   * aziascreations.deviantart.com
   */
?>