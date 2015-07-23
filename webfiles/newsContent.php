<?php
  /**
   *This file is where all the news are stored.
   *To add a new one, just put it in the correct Array.
   *
   *NOTE:
   * -Don't forget to add the spacing String
   * -Don't mess up the php syntax
   */
  
  //Left Tab Lines
  $content1 = array(
    "§lUpdate 1.3§r - Content Update#*Updated to Minecraft 1.8##*Added Scrolling buttons and keys#*Added Individual Line Position System##*Revamped \"§onews.php§r\"#*Revamped \"§oNews Writer§r\"#*Revamped the text formatting symbols#§oUse the ones from minecraft##*Removed ApiColors class#*Removed StopWatch class#*Removed leftovers",
    "§lUpdate 1.2§r - Bug Killer Update#*Added keyboard controls#*Added the possibility to have <13 lines#*Added a Debug menu#*Added error messages#*Added more options:#§oTab Locking, Exit on Error and Key Nav##*Improved Errors Handling##*Changed Package for 2.0 Release",
    "§lUpdate 1.1§r - Options Update#*Updated to Minecraft 1.7.10##*Added MaxPages Option to Each Tab#*Added Customizable Tab Name#*Added Options File in WebFiles##*Optimized Text Loading##*Removed Some Leftovers",
    "§lUpdate 1.0§r - Public Update#*Added new text formatting options#*Added customizable Buttons and Titles#*Added a \"§oMaxPages§r\" option##*Reworked the News System (13x Faster)##*Fixed the \"§oMouse Position Reset§r\" bug",
    "§lUpdate 0.3#*Improved The Text Position System#*Added New Text Colors#*Added a Page Limitation#*Changed the GUI Texture#*The Text is now Hosted Online##*Fixed the Page Number Display#*Fixed the Reset Text Format##§lUpdate 0.2#*Added the \"§oEvent§r\" Tab and Button#*Added a Text Position System"
    );
  
  //Right Tab Lines
  $content2 = array(
    "§lHunger Games Event#§nDate:§r 18th September#§nEntrance:§r 1 Emerald#§nPlace:§r In the Lost Wood#§nReward:#*1st: 10 Emeralds#*2nd: 5 Emeralds#*3rd: 2 Emeralds##§nDescription:#A lots of players have to fight#to survive into the wild.#There's only 1 survivor.",
    "§lMob Arena Event#§nDate:§r 31th October#§nEntrance:§r It's Free :)#§nPlace:§r In Endenfield Arena#§nReward:#*1st: A Diamond Stuff#*2nd: An Iron Stuff#*3rd: A Wood and Leather Stuff##§nDescription:#You have to survive as long as you can#against various waves of monsters.#No custom stuff allowed !",
    "§lChristmas Treasure Hunt Event#§nDate:§r 25th September#§nEntrance:§r It's Free :)#§nPlace:§r In all the Middle Earth#§nReward:#*1st: The Big Treasure#*2nd: A Bag of Gold#*3rd: Some Sticks##§nDescription:#It's a long quest with tons of puzzles,# traps and secrets.#Don't forget to bring your spade !"
    );

  //Left Tab Spacings
  $spacing1 = array(
    "01111111311111",
    "01111131111",
    "0111111111",
    "01111111",
    "0111111111011"
    );
  
  //Right Tab Spacings
  $spacing2 = array(
    "0111122211222",
    "0111122211222",
    "0111122211222"
    );
  
  //Error Messages - You shouldn't have to modify them
  $error1 = array("§lYou encountered a wild bug !##§lCause: §rWrong \"§onewsType§r\" Number -> ","##§lPossible Reasons:#*You Edited the PHP or Java code#*?##§lNotes:#*If you're a user, contact the dev asap#*For further help contact Azias#§oEmail in the Readme File");
  $error2 = array("§lYou encountered a wild bug !##§lPossible Reasons :#*You may be missing a page in: '§onews.php§r'#*The '§oTab","MaxPages§r' may be to high#*Your code can be broken if it was edited## #§lNotes:#*If you're a user, contact the dev asap#*For further help contact Azias#§oEmail in the Readme File");
  $errorSpacing = "0111111111111";

  /**
   * Made by AziasCreations
   * aziascreations.deviantart.com
   */
?>