<?php
$page = $_GET['page'];
$type = $_GET['type'];

include 'newsOptions.php';
echo $Tab1Title."#".$Tab1ButtonName."#".$Tab2Title."#".$Tab2ButtonName."#".$Tab1MaxPages."#".$Tab2MaxPages."/";

switch ($type) {
	// Type 0 (Updates - Left)
	case 0:
		if($page == 1){echo "&xbUpdate 1.1#*Upgraded to 1.7.10# #*Added MaxPages Option to Each Tab#*Added Customizable Tab Name#*Added Options File in WebFiles#*Optimized Text Loading# #*Removed Some Leftovers# #&xbUpcoming Features :#*Individual Line Position System#*Scroll Bar & 13+ Lines per Page";}
		else if($page == 2){echo "&xbUpdate 1.0#*Added a MaxPages Option#*Customizable Buttons and Titles#*Reworked the News System (13x Faster)#*New formatting options and colors# #*Fixed the Mouse Position Reset# # # # # # ";}
		else if($page == 3){echo "&xbUpdate 0.3#*Improved The Text Position System#*Added New Text Colors#*Added a Page Limitation#*Changed the GUI Texture#*The Text is now Hosted Online# #*Fixed the Page Number Display#*Fixed the Reset Text Format# # # # ";}
		else if($page == 4){echo "&xbUpdate 0.2#*Added a the '&xiEvent&r' Button#*Added a Text Position System# # # # # # # # # # ";}
		else {echo "&xbYou encountered a wild bug !# #&xbPossible Reasons :#*You may be missing a page in: '&xinews.php&r'#*The '&xiTab".($type+1)."MaxPages&r' may be to high#*Your code can be broken if it was edited# # #&xbNotes:#*If you're a user, contact the dev asap#*For further help contact Azias#&xiEmail in the Readme File# ";}
	break;

	// Type 1 (Events - Right)
	case 1:
		if($page == 1){echo "&xbHunger Games Event#&xuDate:&r 18th September#&xuEntrance:&r 1 Emerald#&xuPlace:&r In the Lost Wood#&xuReward:#*1st: 10 Emeralds#*2nd: 5 Emeralds#*3rd: 2 Emeralds# #&xuDescription:#A lots of players have to fight#to survive into the wild.#There's only 1 survivor.";}
		else if($page == 2){echo "&xbMob Arena Event#&xuDate:&r 31th October#&xuEntrance:&r It's Free :)#&xuPlace:&r In Endenfield Arena#&xuReward:#*1st: A Diamond Stuff#*2nd: An Iron Stuff#*3rd: A Wood and Leather Stuff# #&xuDescription:#You have to survive as long as you can#against various waves of monsters.#No custom stuff allowed !";}
		else if($page == 3){echo "&xbChristmas Treasure Hunt Event#&xuDate:&r 25th September#&xuEntrance:&r It's Free :)#&xuPlace:&r In all the Middle Earth#&xuReward:#*1st: The Big Treasure#*2nd: A Bag of Gold#*3rd: Some Sticks# #&xuDescription:#It's a long quest with tons of puzzles,# traps and secrets.#Don't forget to bring your spade !";}
		else {echo "&xbYou encountered a wild bug !# #&xbPossible Reasons :#*You may be missing a page in: '&xinews.php&r'#*The '&xiTab".($type+1)."MaxPages&r' may be to high#*Your code can be broken if it was edited# # #&xbNotes:#*If you're a user, contact the dev asap#*For further help contact Azias#&xiEmail in the Readme File# ";}
	break;

	// If the system bugged (the GUI will close itself)
	default:echo "BUG !!!";
}

// Text Formatting
// - - - - - - - - - - - -
// Colors:
// &0 -> Black
// &1 -> Dark Blue
// &2 -> Dark Green
// &3 -> Dark Aqua
// &4 -> Dark Red
// &5 -> Dark Purple
// &6 -> Gold
// &7 -> Gray
// &8 -> Dark Gray
// &9 -> Blue
// &a -> Green
// &b -> Aqua
// &c -> Red
// &d -> Light Purple
// &e -> Yellow
// &f -> White
// - - - - - - - - - - - -
// Others:
// &xb ->Bold
// &xu ->Underline
// &xi ->Italic
// &xs ->Striketrough
// &r  ->In-Line Reset
// &s  ->List Spaces
// 
// Made by AziasCreations Copyright 2012-2014
?>