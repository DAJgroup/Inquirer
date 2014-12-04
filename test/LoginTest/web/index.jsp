<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>Training project "Inquirer"</title>
		<link rel="shortcut icon" href="images/favicon.ico">
		<meta name="keywords" content="testing online DAJ Group create test">
		<meta name="author" content="DAJ Group">
		<meta name="description" content="Conditional title: Online testing creating system. Target: Creating a completely developed system of online testing. The system will have the admin part and the client part.">
		<link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
		<!-- <link rel="stylesheet" href="css/reset.min.css"> or reset.css -->
		<link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/normalize/2.1.0/normalize.css">
		<!-- <link rel="stylesheet" href="css/normalize.css"> or normalize.min.css -->
		<link rel="stylesheet" href="css/style.css">
		<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
		<!--[if lt IE 9]>
			<script src='//cdnjs.cloudflare.com/ajax/libs/html5shiv/3.6.2/html5shiv.js'></script>
		<![endif]-->
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
		<script src="js/jquery.min.js"></script>
		<!--<script src="http://cdnjs.cloudflare.com/ajax/libs/Chart.js/0.2.0/Chart.min.js"></script>-->
		<!--<script src="js/Chart.min.js"></script>-->
		<script src="js/script.js"></script>
		<link href="css/jquery.formstyler.css" rel="stylesheet">
		<script src="js/jquery.formstyler.min.js"></script>
	</head>
	<body>
		<div id="main">
			<div id="content" class="clearfix">
				<header>
					<a id="logo" href="#" title="logo"></a>
					<nav>
						<ul class="nav-list">
							<li><a title="Главная" href="#">Главная</a></li>
							<li><a title="Вход" href="#loginformwrapper" id="menu_login">Вход</a></li>
							<li><a title="Регистрация" href="#regformwrapper" id="menu_registration">Регистрация</a></li>
							<li><a title="Мой кабинет" href="#">Мой кабинет</a></li>
							<li><a title="О проекте" href="#">О проекте</a></li>
							<li><a title="FAQ" href="#">FAQ</a></li>
							<li><a title="jQuery Form Styler" href="http://dimox.name/jquery-form-styler/">jQuery Form Styler</a></li>
						</ul>
					</nav>

					<div class="allforms" id="loginformwrapper">
						<div class="popup_close" id="closeloginform"></div>
						<form id="loginform" action="AuthServlet" method="post" name="login" autocomplete="on">
							<label for="login">Логин:</label>
							<input type="text" class="login" name="UserName">
							<label for="password">Пароль:</label>
							<input type="password" class="password" name="UserPWD">
							<input type="submit" value="Войти">
						</form>
					</div>
					<div class="allforms" id="regformwrapper">
						<div class="popup_close" id="closeregform"></div>
						<form id="regform" action="RegServlet" method="post" name="registration" autocomplete="on">
							<label for="login">Логин:</label>
							<input type="text" class="login" name="NewUserName">
							<label for="email">E-mail:<small>please@email.me</small></label>
							<input type="text" id="email" name="NewUserEmail">
							<label for="password">Пароль:</label>
							<input type="password" class="password" name="NewUserPWD">
							<label for="repassword">Подтвердите пароль:</label>
							<input type="password" class="password" name="repassword">
							<label for="firstname">Имя:<small>just letters</small></label>
							<input type="text" id="firstname" name="firstname">
							<label for="lastname">Фамилия:<small>just letters</small></label>
							<input type="text" id="lastname" name="lastname">
							<input type="submit" value="Зарегистрироваться">
						</form>
					</div>
					<div id="mask" class="mask"></div>

				</header>
				<div class="clb"></div>

				<aside>
					<a href="http://sitear.ru/material/jquery-validaciya-form-i-polej"></a><br/>
				</aside>

				<section><h2>Hello World!</h2>
					<article><h3>Hi People!!!</h3></article>
				</section>
				<div align="center"><h3><%=request.getAttribute("Message")%></div>
			</div><!-- END of content-->
		</div><!-- END of main-->
		<footer>
			<p>
				<a href="http://jigsaw.w3.org/css-validator/check/referer">
					<img style="border:0;width:88px;height:31px"
						 src="http://jigsaw.w3.org/css-validator/images/vcss-blue"
						 alt="Правильный CSS!" />
				</a>
			</p>
			<p>Copyright &copy;&nbsp;2014&nbsp;DAJ Group.</p>
			<p>For more information please contact us <a href="mailto:email@email.com">email@email.com</a></p>
		</footer>
	</body>
</html>