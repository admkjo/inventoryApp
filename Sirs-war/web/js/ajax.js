var SLIDETIMER = 3;
var SLIDESPEED = 3;
var SCROLLTIMER = 3;
var SCROLLSPEED = 3;
var STARTINGOPACITY = 40;


function checkUncheck(frmname,prefix){
    var sid= prefix+"_0";
    var nm="";
    var state = document.getElementById(sid).checked;
    for (var i=0; i< document.forms.length; i++) {

        if(document.forms[i].name==frmname)
        {
            var allTags = document.forms[i];


            for (var j=0; j<allTags.length; j++) {
                //nm+=allTags[i].name+" \n ";
                if (allTags[j].name.indexOf(prefix)>-1) {
                    allTags[j].checked = state;
                }
            //alert(nm);
            }
        }

    }
}
// handles section to section scrolling of the content //
function slideContent(id,prefix,timer) {
    var div = document.getElementById(id);
    var slider = div.parentNode;
    clearInterval(slider.timer);
    slider.section = parseInt(id.replace(/\D/g,''));
    slider.target = div.offsetTop;
    slider.style.top = slider.style.top || '0px';
    slider.current = slider.style.top.replace('px','');
    slider.direction = (Math.abs(slider.current) > slider.target) ? 1 : -1;
    slider.style.opacity = STARTINGOPACITY * .01;
    slider.style.filter = 'alpha(opacity=' + STARTINGOPACITY + ')';
    slider.timer = setInterval( function() {
        slideAnimate(slider,prefix,timer)
    }, SLIDETIMER);
}

function slideAnimate(slider,prefix,timer) {
    var curr = Math.abs(slider.current);
    var tar = Math.abs(slider.target);
    var dir = slider.direction;
    if((tar - curr <= SLIDESPEED && dir == -1) || (curr - tar <= SLIDESPEED && dir == 1)) {
        slider.style.top = (slider.target * -1) + 'px';
        slider.style.opacity = 1;
        slider.style.filter = 'alpha(opacity=100)';
        clearInterval(slider.timer);
        if(slider.autoscroll) {
            setTimeout( function() {
                autoScroll(slider.id,prefix,timer)
            }, timer * 1000);
        }
    } else {
        var pos = (dir == 1) ? parseInt(slider.current) + SLIDESPEED : slider.current - SLIDESPEED;
        slider.current = pos;
        slider.style.top = pos + 'px';
    }
}

// handles manual scrolling of the content //
function scrollContent(id,dir) {
    var div = document.getElementById(id);
    clearInterval(div.timer);
    var sections = div.getElementsByTagName('div');
    var length = sections.length;
    var limit;
    if(dir == -1) {
        limit = 0;
    } else {
        if(length > 1) {
            limit = sections[length-1].offsetTop;
        } else {
            limit = sections[length-1].offsetHeight - div.parentNode.offsetHeight + 20;
        }
    }
    div.style.opacity = STARTINGOPACITY * .01;
    div.style.filter = 'alpha(opacity=' + STARTINGOPACITY + ')';
    div.timer = setInterval( function() {
        scrollAnimate(div,dir,limit)
    }, SCROLLTIMER);
}

function scrollAnimate(div,dir,limit) {
    div.style.top = div.style.top || '0px';
    var top = div.style.top.replace('px','');
    if(dir == 1) {
        if(limit - Math.abs(top) <= SCROLLSPEED) {
            cancelScroll(div.id);
            div.style.top = '-' + limit + 'px';
        } else {
            div.style.top = top - SCROLLSPEED + 'px';
        }
    } else {
        if(Math.abs(top) - limit <= SCROLLSPEED) {
            cancelScroll(div.id);
            div.style.top = limit + 'px';
        } else {
            div.style.top = parseInt(top) + SCROLLSPEED + 'px';
        }
    }
}

// cancel the scrolling on mouseout //
function cancelScroll(id) {
    var div = document.getElementById(id);
    div.style.opacity = 1;
    div.style.filter = 'alpha(opacity=100)';
    clearTimeout(div.timer);
}

// initiate auto scrolling //
function autoScroll(id,prefix,timer,restart) {
    var div = document.getElementById(id);
    div.autoscroll = (!div.autoscroll && !restart) ? false : true;
    if(div.autoscroll) {
        var sections = div.getElementsByTagName('div');
        var length = sections.length;
        div.section = (div.section && div.section < length) ? div.section + 1 : 1;
        slideContent(prefix + '-' + div.section,prefix,timer);
    }
}

// cancel automatic scrolling //
function cancelAutoScroll(id) {
    var div = document.getElementById(id);
    div.autoscroll = false;
}
//Toggle
function toggleDiv(divId)
{
    var displayStatus = document.getElementById(divId).style.display;
    if(displayStatus=="none")
    {
        $("#"+divId).show("slow");
    }else{
        $("#"+divId).hide("slow");
    }
	
}
// JavaScript Document
addOnloads(initForms);
//Starting typewriter
function changeTxt(pos,num,divid){
    pos=pos%txt.length;
    num=num%txt.length;
    window.setTimeout("changeTxtArray("+(pos+1)+","+(num)+",'"+divid+"')",200);
}
	
function changeTxtArray(pos,num,divid){
    pos=pos%txt[num][0].length;
				
    document.getElementById(divid).innerHTML="<a href='."+txt[num][1]+"' style='color:black;'>"+  txt[num][0].substring(0,pos+1)+"_</a>";
    num=num%txt.length;
			
    if(pos == txt[num][0].length - 1){
        window.setTimeout("changeTxt("+(0)+","+(num+1)+",'"+divid+"')",2000);
    }
    else
    {
        window.setTimeout("changeTxtArray("+(pos+1)+","+(num)+",'"+divid+"')",200);
    }
}



/*

function changeTxt(pos,num,arrTxt){
			pos=pos%arrTxt.length;
			//alert(num+" Here Txt");
			//document.writeln("there\n"+pos);
			//alert(document.getElementById("poll").innerHTML);
			num=num%arrTxt.length;
			var tr = new Array();
			tr = arrTxt;
			alert(tr.length)
			//document.getElementById("poll").innerHTML="<h2>"+  txt[num].substring(0,pos)+"_</h2>";
			window.setTimeout("changeTxtArray("+(pos+1)+","+(num)+","+tr+")",300);
			
		
		}
	
function changeTxtArray(pos,num,arrTxt){
			pos=pos%arrTxt[num][0].length;
			var tr = new Array();
			tr = arrTxt;
			alert(tr.length)
			//alert(num);
			//document.writeln("there\n"+pos);
			//alert(document.getElementById("poll").innerHTML);
			
			document.getElementById("poll").innerHTML="<h2>"+  tr[num][0].substring(0,pos)+"_</h2>";
			num=num%arrTxt.length;
			if(pos == length){
				window.setTimeout("changeTxt("+(0)+","+(num+1)+","+tr+")",300);
			}else{
			window.setTimeout("changeTxtArray("+(pos+1)+","+(num)+","+tr+")",300);
		}
		}


/*
Typewriter Javascripts
	*/	


function addOnloads(fxn)
{
	
    var oldOnload = window.onload;
    if(typeof(oldOnload)=="function")
    {
        window.onload = function(){
            if(oldOnload){
                oldOnload();
            }
        }
        fxn();

    }else
        window.onload = fxn;
}

function cleartxt(divId)
{
    document.getElementById(divId).value="";
}
function assigntxt(divId,txt)
{
    document.getElementById(divId).value=txt;	
}
function showdiv(divId)
{
    $(function(){
        $("#"+divId).show("slow");
        return( false );
    }
    );
}
function hidediv(divId)
{
    $(function(){
        $("#"+divId).hide("slow");
        return( false );
    }
    );	
}
function hide(divId)
{
    $(function(){
        $("#"+divId).hide("scrollup");
        return( false );
    }
    );	
}

function initForms()
{
	
    for (var i=0; i< document.forms.length; i++) {
	
        if(document.forms[i].className.indexOf("validate")>-1) 
        {
            document.forms[i].onsubmit =  validForm;
        }
        else if(document.forms[i].className.indexOf("ajaxupload")>-1) 
        {
            document.forms[i].onsubmit =  startUpload;
        }
    }
}

function checkUncheck(frmname,prefix){
    var sid= prefix+"_0";
    var nm="";
    var state = document.getElementById(sid).checked;
    for (var i=0; i< document.forms.length; i++) {
	
        if(document.forms[i].name==frmname) 
        {
            var allTags = document.forms[i];
   
   
            for (var j=0; j<allTags.length; j++) {
                //nm+=allTags[i].name+" \n ";
                if (allTags[j].name.indexOf(prefix)>-1) {
                    allTags[j].checked = state;
                }
            //alert(nm);
            }
        }
		
    }
}

function startUpload(){
    document.getElementById('f1_upload_process').style.visibility = 'visible';
    return true;
}
function stopUpload(success){
    var result = '';
    if (success == 1){
        document.getElementById('result').innerHTML = '<span class="msg">The file was uploaded successfully!<\/span><br/><br/>';
    }
    else {
        document.getElementById('result').innerHTML = '<span class="emsg">There was an error during file upload!<\/span><br/><br/>';
    }
    document.getElementById('f1_upload_process').style.visibility = 'hidden';
    return true;
}


function refreshRecap(){
    getData("../phpscripts/oldimg.php","recapdiv")
}

// form validation function //
function validate(form) {
    var name = form.name.value;
    var email = form.email.value;
    var gender = form.gender.value;
    var message = form.message.value;
    var nameRegex = /^[a-zA-Z]+(([\'\,\.\- ][a-zA-Z ])?[a-zA-Z]*)*$/;
    var emailRegex = /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/;
    var messageRegex = new RegExp(/<\/?\w+((\s+\w+(\s*=\s*(?:".*?"|'.*?'|[^'">\s]+))?)+\s*|\s*)\/?>/gim);
    if(name == "") {
        inlineMsg('name','<strong>Error</strong><br />You must enter your name.',2);
        return false;
    }
    if(!name.match(nameRegex)) {
        inlineMsg('name','<strong>Error</strong><br />You have entered an invalid name.',2);
        return false;
    }
    if(email == "") {
        inlineMsg('email','<strong>Error</strong><br />You must enter your email.',2);
        return false;
    }
    if(!email.match(emailRegex)) {
        inlineMsg('email','<strong>Error</strong><br />You have entered an invalid email.',2);
        return false;
    }
    if(gender == "") {
        inlineMsg('gender','<strong>Error</strong><br />You must select your gender.',2);
        return false;
    }
    if(message == "") {
        inlineMsg('message','You must enter a message.');
        return false;
    }
    if(message.match(messageRegex)) {
        inlineMsg('message','You have entered an invalid message.');
        return false;
    }
    return true;
}

// START OF MESSAGE SCRIPT //

var MSGTIMER = 20;
var MSGSPEED = 5;
var MSGOFFSET = 3;
var MSGHIDE = 3;

// build out the divs, set attributes and call the fade function //
function inlineMsg(target,string,autohide) {
    var msg;
    var msgcontent;
    if(!document.getElementById('emsg')) {
        msg = document.createElement('div');
        msg.id = 'msg';
        msgcontent = document.createElement('div');
        msgcontent.id = 'msgcontent';
        document.body.appendChild(msg);
        msg.appendChild(msgcontent);
        msg.style.filter = 'alpha(opacity=0)';
        msg.style.opacity = 0;
        msg.alpha = 0;
    } else {
        msg = document.getElementById('emsg');
        msgcontent = document.getElementById('msgcontent');
    }
    msgcontent.innerHTML = string;
    msg.style.display = 'block';
    var msgheight = msg.offsetHeight;
    var targetdiv = document.getElementById(target);
    targetdiv.focus();
    var targetheight = targetdiv.offsetHeight;
    var targetwidth = targetdiv.offsetWidth;
    var topposition = topPosition(targetdiv) - ((msgheight - targetheight) / 2);
    var leftposition = leftPosition(targetdiv) + targetwidth + MSGOFFSET;
    msg.style.top = topposition + 'px';
    msg.style.left = leftposition + 'px';
    clearInterval(msg.timer);
    msg.timer = setInterval("fadeMsg(1)", MSGTIMER);
    if(!autohide) {
        autohide = MSGHIDE;  
    }
    window.setTimeout("hideMsg()", (autohide * 1000));
}

// hide the form alert //
function hideMsg(msg) {
    var msg = document.getElementById('emsg');
    if(!msg.timer) {
        msg.timer = setInterval("fadeMsg(0)", MSGTIMER);
    }
}

// face the message box //
function fadeMsg(flag) {
    if(flag == null) {
        flag = 1;
    }
    var msg = document.getElementById('emsg');
    var value;
    if(flag == 1) {
        value = msg.alpha + MSGSPEED;
    } else {
        value = msg.alpha - MSGSPEED;
    }
    msg.alpha = value;
    msg.style.opacity = (value / 100);
    msg.style.filter = 'alpha(opacity=' + value + ')';
    if(value >= 99) {
        clearInterval(msg.timer);
        msg.timer = null;
    } else if(value <= 1) {
        msg.style.display = "none";
        clearInterval(msg.timer);
    }
}

// calculate the position of the element in relation to the left of the browser //
function leftPosition(target) {
    var left = 0;
    if(target.offsetParent) {
        while(1) {
            left += target.offsetLeft;
            if(!target.offsetParent) {
                break;
            }
            target = target.offsetParent;
        }
    } else if(target.x) {
        left += target.x;
    }
    return left;
}

// calculate the position of the element in relation to the top of the browser window //
function topPosition(target) {
    var top = 0;
    if(target.offsetParent) {
        while(1) {
            top += target.offsetTop;
            if(!target.offsetParent) {
                break;
            }
            target = target.offsetParent;
        }
    } else if(target.y) {
        top += target.y;
    }
    return top;
}

function setUserComment(comment_id,title){
    document.getElementById("user_comment_response").value=comment_id;
    document.getElementById("comment_title").innerHTML="<h3>"+title+"<span style='float:right'><a href='#add_comment' onclick='unsetComment()'>cancel reply</a></span></h3>";
}
function unsetComment(){
    document.getElementById("user_comment_response").value="";
    document.getElementById("comment_title").innerHTML="";
}
function validForm() {
    var allGood = true;
    var allTags = this.elements;
   
   
    for (var i=0; i<allTags.length; i++) {
        if (!validTag(allTags[i])) {
            allGood = false;
            document.getElementById("ermsg").style.display ="block";
            document.getElementById("ermsg").innerHTML="There are some errors with the form please check it";
            document.getElementById("ermsg").className+=" errortxt";
        }
    }
    return allGood;


    function validTag(thisTag) {
        var outClass = "";
        var allClasses = thisTag.className.split(" ");
	
	
        for (var j=0; j<allClasses.length; j++) {
            outClass += validBasedOnClass(allClasses[j]) + " ";
        }
	
        thisTag.className = outClass;
		
		
        if (outClass.indexOf("invalid") > -1) {
            //invalidLabel(thisTag.parentNode);
            invalidLabel(thisTag);
            thisTag.focus();
            if (thisTag.nodeName == "input") {
                inlineMsg("lname",'<strong>Error</strong><br />You must enter your name.',2);
                thisTag.select();
            }
            return false;
        }
        return true;

		
        function validBasedOnClass(thisClass) {
            var ranges, minRange;
            if(thisClass.substr(0,5) == "range"){
                ranges = (thisClass.substr(5));
				
                thisClass= "range";
				
            }
            var classBack = "";
            switch(thisClass) {
                case "":
                case "invalid":
                    break;
                case "reqd":
                    if (allGood && thisTag.value == "") {
                        // alert(thisTag.value );
                        classBack = "invalid ";
                    }
                    classBack += thisClass;
                    break;
                case "radio":
                    if (allGood && !radioPicked(thisTag.name)) {
                        classBack = "invalid ";
                    }
                    classBack += thisClass;
                    break;
                case "checked":
                    if (! thisTag.checked) {
                        classBack = "invalid ";
                    }
                    classBack += thisClass;
                    break;
                case "range":
                    if(allGood && !validateRange(ranges,thisTag.value)) {
                        classBack = "invalid ";
                    }
                    classBack += thisClass+ranges;
                    break;
					
                case "email":
                    if (allGood && !validEmail(thisTag.value)) {
                        classBack = "invalid ";
                    }
                    classBack += thisClass;
                    break;
                default:
                    //alert(thisClass);
                    /*if (allGood && !crossCheck(thisTag,thisClass)) {
						classBack = "invalid ";
					}*/
                    classBack += thisClass;
            }
            return classBack;
        }
				
        function crossCheck(inTag,otherFieldID) {
            if (!document.getElementById(otherFieldID)) {
                return false;
            }
            return (inTag.value == document.getElementById(otherFieldID).value);
        }
		
        function radioPicked(radioName) {
            var radioSet = "";

            for (var k=0; k<document.forms.length; k++) {
                if (!radioSet) {
                    radioSet = document.forms[k][radioName];
                }
            }
            if (!radioSet) return false;
            for (k=0; k<radioSet.length; k++) {
                if (radioSet[k].checked) {
                    return true;
                }
            }
            return false;
        }
				
        function validEmail(email) {
            var invalidChars = " /:,;";
		
            if (email == "") {
                return false;
            }
            for (var k=0; k<invalidChars.length; k++) {
                var badChar = invalidChars.charAt(k);
                if (email.indexOf(badChar) > -1) {
                    return false;
                }
            }
            var atPos = email.indexOf("@",1);
            if (atPos == -1) {
                return false;
            }
            if (email.indexOf("@",atPos+1) != -1) {
                return false;
            }
            var periodPos = email.indexOf(".",atPos);
            if (periodPos == -1) {	
                return false;
            }
            if (periodPos+3 > email.length)	{
                return false;
            }
            return true;
        }
        function validateRange(range,value){
            var ranges = range.split("-");
		
            minRange = ranges[0];
            maxRange = ranges[1];
            if(maxRange == null || maxRange.length()<1)
            {
                if(value.length() < minRange)
                    return false;
            }else{
                if(value.length() < minRange && value.length() > maxRange ) return false;
            }
            return true;
        }
		
        function invalidLabel(parentTag) {
            if (parentTag.nodeName == "LABEL") {
                parentTag.className += " invalid";
            }
        }
    }
}
function mkDev(divid){
    if(document.getElementById(divid)!= null)
    {
        document.getElementById(divid).style.display="block";
    }
}
function closeDiv(divid){
    document.getElementById(divid).style.display="none";
}
function qlink(val){
    location.href="http://"+val;
}
function populateDays(sIndex) {
    var monthDays = new Array(31,28,31,30,31,30,31,31,30,31,30,31);
    var monthStr = sIndex-1;
	
    if (monthStr != "" || monthStr < 1) {
        var theMonth = parseInt(monthStr);
					
        document.getElementById("days").options.length = 0;
        for(var i=0; i<monthDays[theMonth]; i++) {
            document.getElementById("days").options[i] = new Option(i+1);
        }
    }
}

function checkleapYear(year){
    if(document.getElementById("month").selectedIndex == 2)
    {
        if(year%4==0){
            document.getElementById("days").options.length = 0;
            for(var i=0; i<29; i++) {
                document.getElementById("days").options[i] = new Option(i+1);
            }
        }else{
            document.getElementById("days").options.length = 0;
            for(var i=0; i<28; i++) {
                document.getElementById("days").options[i] = new Option(i+1);
            }
        }
    }
}

function getData(dataSource,divId)
{
    document.getElementById(divId).style.display="block";
    document.getElementById(divId).innerHTML="<img src='images/load_pik.gif'> Loading..";
    var xmlhttp;
    if (window.XMLHttpRequest)
    {
        // code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else if (window.ActiveXObject)
    {
        // code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
    else
    {
        alert("Your browser does not support XMLHTTP!");
    }
    xmlhttp.onreadystatechange=function()
    {
        if(xmlhttp.readyState==4)
        {
            var otherPanel ="";
            if(divId=="changeDiv")
                otherPanel ="<div class='btop'><a href='#' onclick=\"hidediv('changeDiv')\" class='fright'>Close</a></top>"
            document.getElementById(divId).innerHTML = xmlhttp.responseText+otherPanel;

        } 
    }
    xmlhttp.open("GET",dataSource,true);
    xmlhttp.send(null);
}

function getPostData(dataSource,data,divId){
    if (window.XMLHttpRequest) {
        XMLHttpRequestObject = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        XMLHttpRequestObject = new ActiveXObject("Microsoft.XMLHttp");
    }
	
    if(XMLHttpRequestObject) {
        XMLHttpRequestObject.open("POST", dataSource);
        XMLHttpRequestObject.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded'); 
        XMLHttpRequestObject.onreadystatechange = function()
        {
				
            if (XMLHttpRequestObject.readyState == 4 && XMLHttpRequestObject.status == 200) {
                document.getElementById(divId).style.visibility = "visible";
                if(XMLHttpRequestObject.responseText == "login")
                    location="./?content=download"
                else
                    document.getElementById(divId).innerHTML = XMLHttpRequestObject.responseText;
			
            }
        }
        XMLHttpRequestObject.send(data);
    }
}

function processResourceDeleteClick(divId,val,content,cd,actDiv)
{	
    var pnode =document.getElementById(divId).parentNode;
    var snode = pnode.parentNode;
    if(confirm('Would you like to remove this  from this Page ')){
        snode.style.display="none";
        getData("pages/resourcesaction.php?aj&"+cd+"&r="+val+"&c="+content,actDiv);
    }
}
function processCommentDeleteClick(divId,content,cd,actDiv)
{
    var pnode =document.getElementById(divId).parentNode;
    var snode = pnode.parentNode;
    if(confirm('Would you like to remove this  from this Page ')){
        snode.style.display="none";
        getData("pages/page_comment_action.php?"+cd+"&r="+val+"&c="+content,actDiv);
    }
}
/*
<--- --------------------------------------------------------------------------------------- ----
	
	Blog Entry:
	Ask Ben: Print Part Of A Web Page With jQuery
	
	Code Snippet:
	2
	
	Author:
	Ben Nadel / Kinky Solutions
	
	Link:
	http://www.bennadel.com/index.cfm?dax=blog:1591.view
	
	Date Posted:
	May 21, 2009 at 9:10 PM
	
---- --------------------------------------------------------------------------------------- --->
*/

// Create a jquery plugin that prints the given element.
jQuery.fn.print = function(){
    // NOTE: We are trimming the jQuery collection down to the
    // first element in the collection.
    if (this.size() > 1){
        this.eq( 0 ).print();
        return;
    } else if (!this.size()){
        return;
    }
 
    // ASSERT: At this point, we know that the current jQuery
    // collection (as defined by THIS), contains only one
    // printable element.
 
    // Create a random name for the print frame.
    var strFrameName = ("printer-" + (new Date()).getTime());
 
    // Create an iFrame with the new name.
    var jFrame = $( "<iframe name='" + strFrameName + "'>" );
 
    // Hide the frame (sort of) and attach to the body.
    jFrame
    .css( "width", "1px" )
    .css( "height", "1px" )
    .css( "position", "absolute" )
    .css( "left", "-9999px" )
    .appendTo( $( "body:first" ) )
    ;
 
    // Get a FRAMES reference to the new frame.
    var objFrame = window.frames[ strFrameName ];
 
    // Get a reference to the DOM in the new frame.
    var objDoc = objFrame.document;
 
    // Grab all the style tags and copy to the new
    // document so that we capture look and feel of
    // the current document.
 
    // Create a temp document DIV to hold the style tags.
    // This is the only way I could find to get the style
    // tags into IE.
    var jStyleDiv = $( "<div>" ).append(
        $( "style" ).clone()
        );
 
    // Write the HTML for the document. In this, we will
    // write out the HTML of the current element.
    objDoc.open();
    objDoc.write( "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" );
    objDoc.write( "<html>" );
    objDoc.write( "<body>" );
    objDoc.write( "<head>" );
    objDoc.write( "<title>" );
    objDoc.write( document.title );
    objDoc.write( "</title>" );
    objDoc.write( jStyleDiv.html() );
    objDoc.write( "</head>" );
    objDoc.write( this.html() );
    objDoc.write( "</body>" );
    objDoc.write( "</html>" );
    objDoc.close();
 
    // Print the document.
    objFrame.focus();
    objFrame.print();
 
    // Have the frame remove itself in about a minute so that
    // we don't build up too many of these frames.
    setTimeout(
        function(){
            jFrame.remove();
        },
        (60 * 1000)
        );
}