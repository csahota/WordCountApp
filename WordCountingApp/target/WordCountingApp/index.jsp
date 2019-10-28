<html>
   	<link href="css/styles.css" rel="stylesheet" />
	<link href="css/bootstrap.min.css" rel="stylesheet" />
</head>

<body>
	<h5>Word Counting App</h5>
	<div class="container">
		<div class="row">
			<div class="col-sm-3">&nbsp;</div>
			<div class="col-sm-6"><textarea id="text" rows="3" cols="50" placeholder="Enter text here for ingestion"></textarea></div>
			<div class="col-sm-3">&nbsp;</div>
		</div>
		<div class="row">
			<div class="col-sm-7">&nbsp;</div>
			<div class="col-sm-2"><button class="btn" id="send">Send</button></div>
			<div class="col-sm-3">&nbsp;</div>
		</div>
	</div>	
	<div class="container">
		<div class="row">
			<div class="col-sm-3">&nbsp;</div>
			<div class="col-sm-6"><input type="text" id="search-word" placeholder="Return word count" /> &nbsp;&nbsp; <button class="btn" id="search">Search</button></div>
			<div class="col-sm-3">&nbsp;</div>
		</div>
	</div>
	<br />
	<div class="container" id="search-response" style="display:none;">
		<div class="row">
			<div class="col-sm-3">&nbsp;</div>
			<div class="col-sm-9">
				<div id="data" >
					<div>Searched Word : <b><span id="word" /></b></div>
					<div id="count-wrapper">
						<div>Number of records : <span id="count" /></div>
						<div>First submit time : <span id="first-date" /></div>
						<div>Last submit time : <span id="last-date" /></div>
					</div>
					<div id="msg-wrapper">
						<div>Message : <span id="message" /></div>
					</div>
				</div>
			</div>	
		</div>
	</div>
	<div class="container" id="submit-response" style="display:none;">
		<div class="row">
			<div class="col-sm-3">&nbsp;</div>
			<div class="col-sm-9">
				<div id="data" >
					<div>Submitted text : <b><span id="submitted-text" /></b></div>
					<div>Submit received : <span id="status" /></div>
				</div>
			</div>	
		</div>
	</div>
<script src="js/jquery-3.4.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/script.js"></script>
</body>
</html>
