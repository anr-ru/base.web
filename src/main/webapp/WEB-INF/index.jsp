<!-- <%@taglib uri="http://www.springframework.org/tags" prefix="spring"%> -->

<!DOCTYPE html>
<html xmlns:spring="http://www.springframework.org/tags" ng-app="App" ng-strict-di>
	
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">

		<title>
			<spring:message code = "hello.world"/>
		</title>

        <link href="./css/styles.css" rel="stylesheet" />
        <link href="./bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="./bower_components/bootstrap/dist/css/bootstrap-theme.min.css" rel="stylesheet" />

		<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
		<!--[if lt IE 9]>
		  <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
		<![endif]-->	

        <script src="./js/scripts.js" type="text/javascript"></script>
        <script src="./js/main.js" type="text/javascript"></script>

	</head>

<body ng-controller="AppCtrl">

	<div class = "container">
		<h2>It's an empty bootstrap application</h2>
		<h2><p id="txt"><spring:message code = "hello.world" /></p></h2>
		
		
		<a href="#" ng-click="clicked()" id="lnk">Press me tenderly</a>
		
		<h3>{{messages}}</h3>
	</div>
	<br />
	
	
</body>

</html>
