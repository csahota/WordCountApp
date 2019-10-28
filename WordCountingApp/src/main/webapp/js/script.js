
$(document).ready(function(){
  $("p").click(function(){
    $(this).hide();
  });

  $("#send").click(function(){

	 var text = $("#text").val();
	 clearPage();
	 
	 $.post("DocumentServlet", {words: text},
		  function(data, status){
		    console.log("Data: " + data + "\nStatus: " + status);
		    $("#status")[0].innerHTML = data;
		    $("#submitted-text")[0].innerHTML = text;
		  });
	 $("#submit-response").show();
	 
  });
  
  $("#search").click(function(){

	 clearPage();
	 
	 $.get("DocumentServlet",{word: $("#search-word").val()},
		  function(data, status){
		    
		 	console.log("Data: " + data + "\nStatus: " + status);
		    
		 	var json = JSON.parse(data);
		 	
		 	$("#word")[0].innerHTML = json.word;
		 	
		    if(json.status == "SUCCESS"){
		    	
		    	$("#count")[0].innerHTML = json.wordCount;
		    	$("#first-date")[0].innerHTML = json.firstDate;
		    	$("#last-date")[0].innerHTML = json.lastDate;
		    	
		    	$("#count-wrapper").show();
		    	
		    } else {

		    	$("#message")[0].innerHTML = json.message;
		    	
		    	$("#msg-wrapper").show();
		    }
		    
			$("#search-response").show();
		  });
	      
	  });
  
});

function clearPage(){

	$("#text").val('');
	 
 	$("#word")[0].innerHTML = "";
	$("#count")[0].innerHTML = "";
	$("#first-date")[0].innerHTML = "";
	$("#last-date")[0].innerHTML = "";
	$("#message")[0].innerHTML = ""; 
	
	$("#status")[0].innerHTML = "";
    $("#submitted-text")[0].innerHTML = "";

	$("#msg-wrapper").hide();
	$("#count-wrapper").hide();
	
	$("#search-response").hide();
	 $("#submit-response").hide();
}

