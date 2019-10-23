
$(document).ready(function(){
  $("p").click(function(){
    $(this).hide();
  });

  $("#send").click(function(){

	 $.post("DocumentServlet", {words: $("#text").val()},
		  function(data, status){
		    console.log("Data: " + data + "\nStatus: " + status);
		    $("div#data")[0].innerHTML = data;
		  });
	 $("#text").val('');
	 
  });
  
  $("#search").click(function(){
	   
	 $.get("DocumentServlet",{word: $("#word").val()},
		  function(data, status){
		    console.log("Data: " + data + "\nStatus: " + status);
		    $("div#data")[0].innerHTML = data;
		  });
	    
	  });
  
});

